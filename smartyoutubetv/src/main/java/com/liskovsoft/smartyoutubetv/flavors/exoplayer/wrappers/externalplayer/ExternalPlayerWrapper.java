package com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.externalplayer;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.helpers.FileHelpers;
import com.liskovsoft.smartyoutubetv.common.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.common.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ExoInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.fragments.ActivityResult;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;

import java.io.File;
import java.io.InputStream;

public class ExternalPlayerWrapper extends OnMediaFoundCallback implements ActivityResult {
    private static final String TAG = ExternalPlayerWrapper.class.getSimpleName();
    private final Context mContext;
    private final ExoInterceptor mInterceptor;
    private static final int TYPE_DASH_CONTENT = 0;
    private static final int TYPE_DASH_URL = 1;
    private static final int TYPE_HLS_URL = 2;
    private static final String MPD_FILE_NAME = "tmp_video.mpd";
    private final File mMpdFile;
    private int mContentType;
    private Uri mDashUrl;
    private Uri mHlsUrl;

    public ExternalPlayerWrapper(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
        mMpdFile = new File(FileHelpers.getCacheDir(mContext), MPD_FILE_NAME);
    }

    @Override
    public void onDashMPDFound(InputStream mpdContent) {
        FileHelpers.streamToFile(mpdContent, mMpdFile);
        mContentType = TYPE_DASH_CONTENT;
    }

    @Override
    public void onDashUrlFound(Uri dashUrl) {
        mDashUrl = dashUrl;
        mContentType = TYPE_DASH_URL;
    }

    @Override
    public void onHLSFound(Uri hlsUrl) {
        mHlsUrl = hlsUrl;
        mContentType = TYPE_HLS_URL;
    }

    @Override
    public void onDone() {
        openInVLCPlayer();
    }

    @Override
    public void onResult(int resultCode, Intent data) {
        Log.d(TAG, "External player is closed: " + resultCode + " " + data);
        mInterceptor.closePlayer();
    }

    /**
     * <a href="https://wiki.videolan.org/Android_Player_Intents/">Create VLC intent</a>
     */
    private void openInVLCPlayer() {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        switch (mContentType) {
            case TYPE_DASH_CONTENT:
                intent.setDataAndType(FileHelpers.getFileUri(mContext, mMpdFile), "video/*");
                break;
            case TYPE_DASH_URL:
                intent.setDataAndType(mDashUrl, "video/*"); // mpd
                break;
            case TYPE_HLS_URL:
                intent.setDataAndType(mHlsUrl, "application/x-mpegURL"); // m3u8
                break;
            default:
                Log.d(TAG, "Unrecognized content type");
                break;
        }

        intent.setPackage("org.videolan.vlc");
        //intent.setComponent(new ComponentName("org.videolan.vlc", "org.videolan.vlc.gui.video.VideoPlayerActivity"));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // merge new activity with current one
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // merge new activity with current one

        try {
            ((FragmentManager)mContext).startActivityForResult(intent, this);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            MessageHelpers.showMessage(mContext, R.string.message_install_vlc_player);
            mInterceptor.closePlayer();
        }
    }
}
