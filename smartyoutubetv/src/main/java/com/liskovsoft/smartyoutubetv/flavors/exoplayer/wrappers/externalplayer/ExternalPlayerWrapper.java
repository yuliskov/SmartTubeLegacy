package com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.externalplayer;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.m3uparser.m3u.M3UParser;
import com.liskovsoft.m3uparser.m3u.models.Playlist;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ExoInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.HistoryInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser.VideoMetadata;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.OnMediaFoundCallback;
import com.liskovsoft.smartyoutubetv.fragments.ActivityResult;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class ExternalPlayerWrapper extends OnMediaFoundCallback implements ActivityResult {
    private static final String TAG = ExternalPlayerWrapper.class.getSimpleName();
    protected final Context mContext;
    protected final ExoInterceptor mInterceptor;
    private static final String MPD_FILE_NAME = "tmp_video.mpd";
    private static final String VLC_PACKAGE_NAME = "org.videolan.vlc";
    private static final String PLAYER_ACTIVITY_NAME = "org.videolan.vlc.gui.video.VideoPlayerActivity";
    private final File mMpdFile;
    private final UserAgentManager mUAManager;
    private final SmartPreferences mPrefs;
    private final HistoryInterceptor mHistory;
    private Uri mDashUrl;
    private Uri mHlsUrl;
    protected VideoMetadata mMetadata;
    private List<String> mUrlList;
    private InputStream mMpdContent;
    private static final String MIME_MP4 = "video/mp4";
    private static final String MIME_HLS = "application/x-mpegURL";

    protected ExternalPlayerWrapper(Context context, ExoInterceptor interceptor) {
        mUAManager = new UserAgentManager();
        mContext = context;
        mInterceptor = interceptor;
        mMpdFile = new File(FileHelpers.getDownloadDir(mContext), MPD_FILE_NAME);
        mPrefs = CommonApplication.getPreferences();
        mHistory = interceptor.getHistoryInterceptor();
    }

    public static OnMediaFoundCallback create(Context context, ExoInterceptor interceptor) {
        if (SmartPreferences.USE_EXTERNAL_PLAYER_KODI.equals(CommonApplication.getPreferences().getUseExternalPlayer())) {
            return new KodiPlayerWrapper(context, interceptor);
        }

        return new ExternalPlayerWrapper(context, interceptor);
    }

    @Override
    public void onStart() {
        mHistory.onStart();
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
    public boolean getVLCFix() {
        // Fix LQ playback in external players.
        // Remain one item in the list.
        // Put avc on top
        return SmartPreferences.USE_EXTERNAL_PLAYER_FHD.equals(mPrefs.getUseExternalPlayer());
    }

    @Override
    public void onDone() {
        if (mUrlList != null && SmartPreferences.USE_EXTERNAL_PLAYER_SD.equals(mPrefs.getUseExternalPlayer())) {
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
        mHistory.updatePosition(0);
    }

    /**
     * <a href="https://wiki.videolan.org/Android_Player_Intents/">VLC intent</a><br/>
     * <a href="http://mx.j2inter.com/api">MX Player intent</a>
     */
    protected void openExternalPlayer() {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        initIntent(intent);
        prepareIntent(intent);

        openPlayer(intent);
    }

    private void initIntent(Intent intent) {
        if (mMpdContent != null) {
            FileHelpers.streamToFile(mMpdContent, mMpdFile);
            // NOTE: Don't use fromFile or you will get FileUriExposedException
            //intent.setDataAndType(FileHelpers.getFileUri(mContext, mMpdFile), MIME_MP4);
            // VLC fix
            intent.setDataAndType(Uri.parse(mMpdFile.toString()), MIME_MP4);
        } else if (mDashUrl != null) {
            intent.setDataAndType(mDashUrl, MIME_MP4); // mpd
        } else if (mHlsUrl != null) {
            M3UParser parser = new M3UParser();
            Playlist pl = parser.parse(mHlsUrl);

            // find FHD stream
            if (pl.getStreams() != null && pl.getStreams().size() > 0) {
                mHlsUrl = pl.getStreams().get(pl.getStreams().size() - 1).uri;
            }

            intent.setDataAndType(mHlsUrl, MIME_HLS); // m3u8
        } else if (mUrlList != null) {
            intent.setDataAndType(Uri.parse(mUrlList.get(0)), MIME_MP4);
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
            MessageHelpers.showMessage(mContext, R.string.message_install_player);
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
