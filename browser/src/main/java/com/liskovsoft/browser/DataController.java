/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.liskovsoft.browser;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import com.liskovsoft.browser.helpers.BrowserContract;
import com.liskovsoft.browser.helpers.BrowserContract.History;
import com.liskovsoft.browser.provider.BrowserProvider2.Thumbnails;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataController {
    private static final String TAG = DataController.class.getSimpleName();
    // Message IDs
    private static final int HISTORY_UPDATE_VISITED = 100;
    private static final int HISTORY_UPDATE_TITLE = 101;
    private static final int QUERY_URL_IS_BOOKMARK = 200;
    private static final int TAB_LOAD_THUMBNAIL = 201;
    private static final int TAB_SAVE_THUMBNAIL = 202;
    private static final int TAB_DELETE_THUMBNAIL = 203;
    private static DataController sInstance;

    private Context mContext;
    private DataControllerHandler mDataHandler;
    private Handler mCbHandler; // To respond on the UI thread
    private ByteBuffer mBuffer; // to capture thumbnails

    /* package */ static interface OnQueryUrlIsBookmark {
        void onQueryUrlIsBookmark(String url, boolean isBookmark);
    }
    private static class CallbackContainer {
        Object replyTo;
        Object[] args;
    }

    private static class DCMessage {
        int what;
        Object obj;
        Object replyTo;
        DCMessage(int w, Object o) {
            what = w;
            obj = o;
        }
    }

    /* package */ static DataController getInstance(Context c) {
        if (sInstance == null) {
            sInstance = new DataController(c);
        }
        return sInstance;
    }

    private DataController(Context c) {
        mContext = c.getApplicationContext();
        mDataHandler = new DataControllerHandler();
        mDataHandler.start();
        mCbHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                CallbackContainer cc = (CallbackContainer) msg.obj;
                switch (msg.what) {
                    case QUERY_URL_IS_BOOKMARK: {
                        OnQueryUrlIsBookmark cb = (OnQueryUrlIsBookmark) cc.replyTo;
                        String url = (String) cc.args[0];
                        boolean isBookmark = (Boolean) cc.args[1];
                        cb.onQueryUrlIsBookmark(url, isBookmark);
                        break;
                    }
                }
            }
        };
    }

    public void updateVisitedHistory(String url) {
        mDataHandler.sendMessage(HISTORY_UPDATE_VISITED, url);
    }

    public void updateHistoryTitle(String url, String title) {
        mDataHandler.sendMessage(HISTORY_UPDATE_TITLE, new String[] { url, title });
    }

    public void queryBookmarkStatus(String url, OnQueryUrlIsBookmark replyTo) {
        if (url == null || url.trim().length() == 0) {
            // null or empty url is never a bookmark
            replyTo.onQueryUrlIsBookmark(url, false);
            return;
        }
        mDataHandler.sendMessage(QUERY_URL_IS_BOOKMARK, url.trim(), replyTo);
    }

    public void loadThumbnail(Tab tab) {
        mDataHandler.sendMessage(TAB_LOAD_THUMBNAIL, tab);
    }

    public void deleteThumbnail(Tab tab) {
        mDataHandler.sendMessage(TAB_DELETE_THUMBNAIL, tab.getId());
    }

    public void saveThumbnail(Tab tab) {
        mDataHandler.sendMessage(TAB_SAVE_THUMBNAIL, tab);
    }

    // The standard Handler and Message classes don't allow the queue manipulation
    // we want (such as peeking). So we use our own queue.
    class DataControllerHandler extends Thread {
        private BlockingQueue<DCMessage> mMessageQueue
                = new LinkedBlockingQueue<DCMessage>();

        public DataControllerHandler() {
            super("DataControllerHandler");
        }

        @Override
        public void run() {
            setPriority(Thread.MIN_PRIORITY);
            while (true) {
                try {
                    handleMessage(mMessageQueue.take());
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }

        void sendMessage(int what, Object obj) {
            DCMessage m = new DCMessage(what, obj);
            mMessageQueue.add(m);
        }

        void sendMessage(int what, Object obj, Object replyTo) {
            DCMessage m = new DCMessage(what, obj);
            m.replyTo = replyTo;
            mMessageQueue.add(m);
        }

        private void handleMessage(DCMessage msg) {
            switch (msg.what) {
            case HISTORY_UPDATE_VISITED:
                doUpdateVisitedHistory((String) msg.obj);
                break;
            case HISTORY_UPDATE_TITLE:
                String[] args = (String[]) msg.obj;
                doUpdateHistoryTitle(args[0], args[1]);
                break;
            case QUERY_URL_IS_BOOKMARK:
                // TODO: Look for identical messages in the queue and remove them
                // TODO: Also, look for partial matches and merge them (such as
                //       multiple callbacks querying the same URL)
                doQueryBookmarkStatus((String) msg.obj, msg.replyTo);
                break;
            case TAB_LOAD_THUMBNAIL:
                doLoadThumbnail((Tab) msg.obj);
                break;
            case TAB_DELETE_THUMBNAIL:
                ContentResolver cr = mContext.getContentResolver();
                try {
                    cr.delete(ContentUris.withAppendedId(
                            Thumbnails.CONTENT_URI, (Long)msg.obj),
                            null, null);
                } catch (Throwable t) {}
                break;
            case TAB_SAVE_THUMBNAIL:
                doSaveThumbnail((Tab)msg.obj);
                break;
            }
        }

        private byte[] getCaptureBlob(Tab tab) {
            synchronized (tab) {
                Bitmap capture = tab.getScreenshot();
                if (capture == null) {
                    return null;
                }
                if (mBuffer == null || mBuffer.limit() < capture.getByteCount()) {
                    mBuffer = ByteBuffer.allocate(capture.getByteCount());
                }
                capture.copyPixelsToBuffer(mBuffer);
                mBuffer.rewind();
                return mBuffer.array();
            }
        }

        private void doSaveThumbnail(Tab tab) {
            byte[] blob = getCaptureBlob(tab);
            if (blob == null) {
                return;
            }
            ContentResolver cr = mContext.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(Thumbnails._ID, tab.getId());
            values.put(Thumbnails.THUMBNAIL, blob);
            cr.insert(Thumbnails.CONTENT_URI, values);
        }

        private void doLoadThumbnail(Tab tab) {
            ContentResolver cr = mContext.getContentResolver();
            Cursor c = null;
            try {
                Uri uri = ContentUris.withAppendedId(Thumbnails.CONTENT_URI, tab.getId());
                c = cr.query(uri, new String[] {Thumbnails._ID,
                        Thumbnails.THUMBNAIL}, null, null, null);
                if (c.moveToFirst()) {
                    byte[] data = c.getBlob(1);
                    if (data != null && data.length > 0) {
                        tab.updateCaptureFromBlob(data);
                    }
                }
            } finally {
                if (c != null) {
                    c.close();
                }
            }
        }

        private void doUpdateVisitedHistory(String url) {
            ContentResolver cr = mContext.getContentResolver();
            Cursor c = null;
            try {
                c = cr.query(History.CONTENT_URI, new String[] { History._ID, History.VISITS },
                        History.URL + "=?", new String[] { url }, null);
                if (c.moveToFirst()) {
                    ContentValues values = new ContentValues();
                    values.put(History.VISITS, c.getInt(1) + 1);
                    values.put(History.DATE_LAST_VISITED, System.currentTimeMillis());
                    cr.update(ContentUris.withAppendedId(History.CONTENT_URI, c.getLong(0)),
                            values, null, null);
                } else {
                    //android.provider.Browser.truncateHistory(cr);
                    ContentValues values = new ContentValues();
                    values.put(History.URL, url);
                    values.put(History.VISITS, 1);
                    values.put(History.DATE_LAST_VISITED, System.currentTimeMillis());
                    values.put(History.TITLE, url);
                    values.put(History.DATE_CREATED, 0);
                    values.put(History.USER_ENTERED, 0);
                    cr.insert(History.CONTENT_URI, values);
                }
            } finally {
                if (c != null) c.close();
            }
        }

        private void doQueryBookmarkStatus(String url, Object replyTo) {
            // Check to see if the site is bookmarked
            Cursor cursor = null;
            boolean isBookmark = false;
            try {
                cursor = mContext.getContentResolver().query(
                        BookmarkUtils.getBookmarksUri(mContext),
                        new String[] { BrowserContract.Bookmarks.URL },
                        BrowserContract.Bookmarks.URL + " == ?",
                        new String[] { url },
                        null);
                if (cursor != null) // fix NPE on Android 5.1
                    isBookmark = cursor.moveToFirst();
            } catch (SQLiteException e) {
                Log.e(TAG, "Error checking for bookmark: " + e);
            } catch (SecurityException e) {
                // TODO: Permission Denial: opening provider that is not exported from uid
                Log.e(TAG, "Bookmark provider error: " + e);
            } finally {
                if (cursor != null) cursor.close();
            }
            CallbackContainer cc = new CallbackContainer();
            cc.replyTo = replyTo;
            cc.args = new Object[] { url, isBookmark };
            mCbHandler.obtainMessage(QUERY_URL_IS_BOOKMARK, cc).sendToTarget();
        }

        private void doUpdateHistoryTitle(String url, String title) {
            ContentResolver cr = mContext.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(History.TITLE, title);
            cr.update(History.CONTENT_URI, values, History.URL + "=?",
                    new String[] { url });
        }
    }
}
