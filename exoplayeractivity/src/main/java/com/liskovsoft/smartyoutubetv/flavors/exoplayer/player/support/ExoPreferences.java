package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.exoplayer2.C;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.PlayerUtil;

public final class ExoPreferences {
    private static ExoPreferences sInstance;
    private final Context mContext;
    private SharedPreferences mPrefs;
    private static final String SELECTED_TRACK_ID = "selectedTrackId";
    private static final String SELECTED_TRACK_HEIGHT = "selectedTrackHeight";
    private static final String SELECTED_TRACK_CODEC = "selectedTrackCodec";
    private static final String SELECTED_AUDIO_TRACK_CODEC = "selectedAudioTrackCodec";
    private static final String SELECTED_AUDIO_TRACK_BITRATE = "selected_audio_track_bitrate";
    private static final String HIDE_PLAYBACK_ERRORS = "hidePlaybackErrors";
    private static final String SUBTITLE_LANG = "subtitleLang";
    private static final String AUTOFRAMERATE_CHECKED = "display_rate_switch";
    private static final String SWITCH_TO_UHD_CHECKED = "switch_to_uhd";
    private static final String PREFERRED_FORMAT = "preferredCodec";
    private static final String VIDEO_ZOOM_MODE = "videoZoomMode";
    private static final String SELECTED_TRACK_FPS = "selectedTrackFps";
    private static final String CURRENT_SPEED = "currentSpeed";
    private static final String RESTORE_SPEED = "restoreSpeed";
    public static final String FORMAT_ANY = "format_any";

    public static ExoPreferences instance(Context ctx) {
        if (sInstance == null)
            sInstance = new ExoPreferences(ctx);
        return sInstance;
    }

    public ExoPreferences(Context context) {
        mContext = context.getApplicationContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public String getSelectedTrackId() {
        return mPrefs.getString(SELECTED_TRACK_ID, null);
    }

    public void setSelectedTrackId(String id) {
        mPrefs.edit()
                .putString(SELECTED_TRACK_ID, id)
                .apply();
    }

    /**
     * By default (first run or user never opened track dialog)<br/>
     * select no more than 1080p format for legacy devices support
     * @return track height
     */
    public int getSelectedTrackHeight() {
        return mPrefs.getInt(SELECTED_TRACK_HEIGHT, 1080); // select fhd track by default
    }

    public void setSelectedTrackHeight(int height) {
        mPrefs.edit()
                .putInt(SELECTED_TRACK_HEIGHT, height)
                .apply();
    }

    public String getSelectedTrackCodecs() {
        return mPrefs.getString(SELECTED_TRACK_CODEC, PlayerUtil.CODEC_SHORT_AVC);
    }

    public void setSelectedTrackCodecs(String codec) {
        mPrefs.edit()
                .putString(SELECTED_TRACK_CODEC, codec)
                .apply();
    }

    public String getSelectedAudioTrackCodecs() {
        return mPrefs.getString(SELECTED_AUDIO_TRACK_CODEC, PlayerUtil.CODEC_SHORT_MP4A);
    }

    public void setSelectedAudioTrackCodecs(String codec) {
        mPrefs.edit()
                .putString(SELECTED_AUDIO_TRACK_CODEC, codec)
                .apply();
    }

    public int getSelectedAudioTrackBitrate() {
        return mPrefs.getInt(SELECTED_AUDIO_TRACK_BITRATE, 0);
    }

    public void setSelectedAudioTrackBitrate(int bitrate) {
        mPrefs.edit()
                .putInt(SELECTED_AUDIO_TRACK_BITRATE, bitrate)
                .apply();
    }

    public float getSelectedTrackFps() {
        return mPrefs.getFloat(SELECTED_TRACK_FPS, 30); // select 30fps track by default
    }

    public void setSelectedTrackFps(float fps) {
        mPrefs.edit()
                .putFloat(SELECTED_TRACK_FPS, fps)
                .apply();
    }

    public void resetPosition(String key) {
        mPrefs.edit()
                .remove(key)
                .apply();
    }

    public void setPosition(String key, long val) {
        mPrefs.edit()
                .putLong(key, val)
                .apply();
    }

    public long getPosition(String key) {
        return mPrefs.getLong(key, C.TIME_UNSET);
    }

    public boolean getAutoframerateChecked() {
        boolean isChecked = mPrefs.getBoolean(AUTOFRAMERATE_CHECKED, false);
        return isChecked;
    }

    public void setAutoframerateChecked(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(AUTOFRAMERATE_CHECKED, isChecked)
                .apply();
    }

    public boolean getSwitchToUHDChecked() {
        boolean isChecked = mPrefs.getBoolean(SWITCH_TO_UHD_CHECKED, false);
        return isChecked;
    }

    public void setSwitchToUHDChecked(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(SWITCH_TO_UHD_CHECKED, isChecked)
                .apply();
    }

    public void setHidePlaybackErrors(boolean hideErrors) {
        mPrefs.edit()
                .putBoolean(HIDE_PLAYBACK_ERRORS, hideErrors)
                .apply();
    }

    public boolean getHidePlaybackErrors() {
        return mPrefs.getBoolean(HIDE_PLAYBACK_ERRORS, true);
    }

    public void setSubtitleLang(String lang) {
        mPrefs.edit()
                .putString(SUBTITLE_LANG, lang)
                .apply();
    }

    public String getSubtitleLang() {
        return mPrefs.getString(SUBTITLE_LANG, null);
    }

    /**
     * Height, fps, codec delimited by vertical line e.g. <code>2160|60|vp9</code>
     * <br/>
     * NOTE: vertical line must be escaped <code>\\|</code>
     */
    public void setPreferredFormat(String codec) {
        mPrefs.edit()
                .putString(PREFERRED_FORMAT, codec)
                .apply();
    }

    /**
     * Height, fps, codec delimited by vertical line e.g. <code>2160|60|vp9</code>
     * <br/>
     * NOTE: vertical line must be escaped <code>\\|</code>
     * @return codec and maximum height
     */
    public String getPreferredFormat() {
        return mPrefs.getString(PREFERRED_FORMAT, FORMAT_ANY);
    }

    public void setCheckedState(int id, boolean isChecked) {
        mPrefs.edit()
                .putBoolean(String.valueOf(id), isChecked)
                .apply();
    }

    public boolean getCheckedState(int id) {
        return mPrefs.getBoolean(String.valueOf(id), false);
    }

    /**
     * By default (first run or user never opened track dialog)<br/>
     * select no more than 1080p format for legacy devices support
     * @return track height
     */
    public int getVideoZoomMode() {
        return mPrefs.getInt(VIDEO_ZOOM_MODE, VideoZoomManager.MODE_DEFAULT); // select fhd track by default
    }

    public void setVideoZoomMode(int mode) {
        mPrefs.edit()
                .putInt(VIDEO_ZOOM_MODE, mode)
                .apply();
    }

    public void setCurrentSpeed(String speed) {
        mPrefs.edit()
                .putString(CURRENT_SPEED, speed)
                .apply();
    }
    
    public String getCurrentSpeed() {
        return mPrefs.getString(CURRENT_SPEED, "1.0");
    }

    public boolean getRestoreSpeed() {
        return mPrefs.getBoolean(RESTORE_SPEED, false);
    }

    public void setRestoreSpeed(boolean isChecked) {
        mPrefs.edit()
                .putBoolean(RESTORE_SPEED, isChecked)
                .apply();
    }
}
