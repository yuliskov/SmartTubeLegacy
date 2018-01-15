/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.mit.mobile.android.appupdater.addons;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Progress listener example:
 * <pre>{@code
 * final ProgressListener progressListener = new ProgressListener() {
 *     @Override
 *     public void update(long bytesRead, long contentLength, boolean done) {
 *         System.out.println(bytesRead);
 *         System.out.println(contentLength);
 *         System.out.println(done);
 *         System.out.format("%d%% done\n", (100 * bytesRead) / contentLength);
 *     }
 * };
 * MyRequest.setProgressListener(progressListener);
 * }</pre>
 */
public final class MyDownloadManager {
    private static final String TAG = MyDownloadManager.class.getSimpleName();
    private final Context mContext;
    private final OkHttpClient mClient;
    private MyRequest mRequest;
    private long mRequestId;
    private GoogleResolver mResolver;
    private InputStream mResponseStream;

    public MyDownloadManager(Context context) {
        mContext = context;
        mResolver = new GoogleResolver(context);
        mClient = createOkHttpClient();
    }

    private void run() {
        if (!isNetworkAvailable()) {
            Helpers.showMessage(mContext, "Internet connection not available");
        }

        String url = mRequest.mDownloadUri.toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        for (int tries = 3; tries > 0; tries--) {
            try {
                Response response = mClient.newCall(request).execute();
                if (!response.isSuccessful()) throw new IllegalStateException("Unexpected code " + response);

                // NOTE: actual downloading is going here (while reading a stream)
                mResponseStream = new ByteArrayInputStream(response.body().bytes());
                break; // no exception is thrown - job is done
            } catch (SocketTimeoutException ex) {
                if (tries == 1) // swallow 3 times
                    throw new IllegalStateException(ex);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        }

    }

    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(), mRequest.mProgressListener)).build();
                    }
                })
                .dns(new Dns() {
                    @Override
                    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
                        List<InetAddress> hosts = mResolver.resolve(hostname);
                        return hosts.isEmpty() ? Dns.SYSTEM.lookup(hostname) : hosts; // use system dns as a fallback
                    }
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                  .readTimeout(10, TimeUnit.SECONDS)
                  .writeTimeout(10, TimeUnit.SECONDS)
                  .build();
    }

    private Uri streamToFile(InputStream is, Uri destination) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(destination.getPath());

            byte[] buffer = new byte[1024];
            int len1;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        } finally {
            Helpers.closeStream(fos);
            Helpers.closeStream(is);
        }

        return destination;
    }

    private Uri getDestination() {
        if (mRequest.mDestinationUri != null) {
            return mRequest.mDestinationUri;
        }

        // NOTE: Android 6.0 fix
        File cacheDir = mContext.getExternalCacheDir();
        if (cacheDir == null) { // try to use SDCard
            cacheDir = Environment.getExternalStorageDirectory();
            Helpers.showMessage(mContext,"Please, make sure that SDCard is mounted");
        }
        File outputFile = new File(cacheDir, "tmp_file");
        return Uri.fromFile(outputFile);
    }

    public long enqueue(MyRequest request) {
        mRequest = request;
        mRequestId = new Random().nextLong();
        run();
        return mRequestId;
    }

    public void remove(long downloadId) {
        mClient.dispatcher().cancelAll();
    }

    public Uri getUriForDownloadedFile(long requestId) {
        return streamToFile(mResponseStream, getDestination());
    }

    public InputStream getStreamForDownloadedFile(long requestId) {
        return mResponseStream;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;

        ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);

                    if (progressListener == null) {
                        return bytesRead;
                    }

                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

    public interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
    }

    public static class MyRequest {
        private final Uri mDownloadUri;
        private Uri mDestinationUri;
        private ProgressListener mProgressListener;

        public MyRequest(Uri uri) {
            mDownloadUri = uri;
        }

        public void setDestinationUri(Uri uri) {
            mDestinationUri = uri;
        }

        public void setProgressListener(ProgressListener listener) {
            mProgressListener = listener;
        }
    }
}
