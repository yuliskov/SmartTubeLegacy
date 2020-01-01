package com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.externalplayer;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ExoInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser.VideoMetadata;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.fragments.ActivityResult;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class ExternalPlayerWrapper extends OnMediaFoundCallback implements ActivityResult {
    private static final String TAG = ExternalPlayerWrapper.class.getSimpleName();
    private final Context mContext;
    private final ExoInterceptor mInterceptor;
    private static final String MPD_FILE_NAME = "tmp_video.mpd";
    private static final String VLC_PACKAGE_NAME = "org.videolan.vlc";
    private static final String PLAYER_ACTIVITY_NAME = "org.videolan.vlc.gui.video.VideoPlayerActivity";
    private final File mMpdFile;
    private final UserAgentManager mUAManager;
    private Uri mDashUrl;
    private Uri mHlsUrl;
    private VideoMetadata mMetadata;
    private List<String> mUrlList;
    private InputStream mMpdContent;

    public ExternalPlayerWrapper(Context context, ExoInterceptor interceptor) {
        mUAManager = new UserAgentManager();
        mContext = context;
        mInterceptor = interceptor;
        mMpdFile = new File(FileHelpers.getDownloadDir(mContext), MPD_FILE_NAME);
    }

    @Override
    public void onStart() {
        cleanup();
    }

    @Override
    public void onDashMPDFound(InputStream mpdContent) {
        mMpdContent = mpdContent;
    }

    //@Override
    //public void onDashUrlFound(Uri dashUrl) {
    //    mDashUrl = dashUrl;
    //}

    @Override
    public void onHLSFound(Uri hlsUrl) {
        mHlsUrl = hlsUrl;
    }

    @Override
    public void onMetadata(VideoMetadata metadata) {
        mMetadata = metadata;
    }

    /**
     * Plain low quality formats.
     */
    @Override
    public void onUrlListFound(List<String> uriList) {
        mUrlList = uriList;
    }

    @Override
    public void onDone() {
        if (mUrlList != null) {
            mMpdContent = null;
            mHlsUrl = null;
            mDashUrl = null;
        }

        openExternalPlayer();
        cleanup();
    }

    private void cleanup() {
        mDashUrl = null;
        mHlsUrl = null;
        mUrlList = null;
        mMetadata = null;
        mMpdContent = null;
    }

    @Override
    public void onResult(int resultCode, Intent data) {
        Log.d(TAG, "External player is closed. Result: " + resultCode + ". Data: " + data);
        mInterceptor.closePlayer();
    }

    /**
     * <a href="https://wiki.videolan.org/Android_Player_Intents/">VLC intent</a><br/>
     * <a href="http://mx.j2inter.com/api">MX Player intent</a>
     */
    private void openExternalPlayer() {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        initIntent(intent);
        prepareIntent(intent);

        openPlayer(intent);
    }

    private void initIntent(Intent intent) {
        if (mMpdContent != null) {
            FileHelpers.streamToFile(mMpdContent, mMpdFile);
            // NOTE: Don't use parseFile or you will get FileUriExposedException
            intent.setDataAndType(Uri.parse(mMpdFile.toString()), "video/*");
        } else if (mDashUrl != null) {
            intent.setDataAndType(mDashUrl, "video/*"); // mpd
        } else if (mHlsUrl != null) {
            intent.setDataAndType(mHlsUrl, "application/x-mpegURL"); // m3u8
        } else if (mUrlList != null) {
            intent.setDataAndType(Uri.parse(mUrlList.get(0)), "video/*");
        } else {
            Log.d(TAG, "Unrecognized content type");
        }
    }

    private void prepareIntent(Intent intent) {
        // Common extras
        intent.putExtra("title", mMetadata == null ? "Untitled" : mMetadata.getTitle());
        intent.putExtra("position", 0); // from beginning

        // VLC
        intent.putExtra("from_start", true);
        if (mMetadata != null) {
            intent.putExtra("extra_duration", 60);
        }

        // MX
        intent.putExtra("return_result", true);
        intent.putExtra("headers", new String[]{"User-Agent", mUAManager.getUA()});

        //intent.setPackage(VLC_PACKAGE_NAME);
        //intent.setComponent(new ComponentName(VLC_PACKAGE_NAME, PLAYER_ACTIVITY_NAME));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // merge new activity with current one
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // merge new activity with current one
    }

    private void openPlayer(Intent intent) {
        try {
            Log.d(TAG, "Starting external player...");
            ((FragmentManager)mContext).startActivityForResult(intent, this);
        } catch (ActivityNotFoundException e) {
            MessageHelpers.showMessage(mContext, R.string.message_install_vlc_player);
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            mInterceptor.closePlayer();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            mInterceptor.closePlayer();
        }
    }
}
