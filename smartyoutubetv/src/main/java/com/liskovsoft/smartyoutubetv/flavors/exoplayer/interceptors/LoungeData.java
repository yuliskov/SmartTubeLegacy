package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryStringFactory;

import java.net.URISyntaxException;

public class LoungeData {
    public static final int STATE_UNDETECTED = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_IDLE = 3;
    public static final int STATE_READY = 4;
    public static final int STATE_UNDEFINED = -1;
    private static final String KEY_STATE = "req0_state";
    private static final String KEY_CURRENT_TIME = "req0_currentTime";
    private static final String KEY_DURATION = "req0_duration";
    private static final String KEY_SEEKABLE_END_TIME = "req0_seekableEndTime";
    private static final String KEY_EVENT = "req0__sc";
    private static final String KEY_MODE = "req0_autoplayMode";
    private int mState = STATE_UNDEFINED;
    private int mCurrentTime;
    private String mUrl;
    private MyQueryString mQueryString;
    private int mDuration;
    private int mEndTime;
    private String mEvent;
    private String mMode;

    public static LoungeData parse(String postData, String url) {
        LoungeData loungeData = new LoungeData();

        if (postData != null) {
            MyQueryString myQueryString = getMyQueryStringData(postData);

            if (myQueryString != null) {
                String state = myQueryString.get(KEY_STATE);
                String currentTime = myQueryString.get(KEY_CURRENT_TIME);
                String duration = myQueryString.get(KEY_DURATION);
                String endTime = myQueryString.get(KEY_SEEKABLE_END_TIME);
                String event = myQueryString.get(KEY_EVENT);
                String mode = myQueryString.get(KEY_MODE);

                loungeData.mQueryString = myQueryString;

                if (Helpers.isNumeric(state)) {
                    loungeData.mState = Integer.parseInt(state);
                }

                if (Helpers.isNumeric(currentTime)) {
                    loungeData.mCurrentTime = (int) Float.parseFloat(currentTime);
                }

                if (Helpers.isNumeric(duration)) {
                    loungeData.mDuration = (int) Float.parseFloat(duration);
                }

                if (Helpers.isNumeric(duration)) {
                    loungeData.mEndTime = (int) Float.parseFloat(endTime);
                }

                loungeData.mEvent = event;
                loungeData.mMode = mode;
            }
        }

        loungeData.mUrl = url;

        return loungeData;
    }

    private static MyQueryString getMyQueryStringData(String postData) {
        MyQueryString result = null;

        try {
            result = MyQueryStringFactory.parse(postData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getCurrentTime() {
        return mCurrentTime;
    }

    public void setCurrentTime(int currentTime) {
        if (currentTime >= -1) {
            mCurrentTime = currentTime;
        }
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getEndTime() {
        return mEndTime;
    }

    public void setEndTime(int endTime) {
        mEndTime = endTime;
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean isDisconnected() {
        return "onAutoplayModeChanged".equals(mEvent) && "UNSUPPORTED".equals(mMode);
    }

    @NonNull
    @Override
    public String toString() {
        String result = "";

        if (mQueryString != null) {
            mQueryString.set(KEY_STATE, mState);
            mQueryString.set(KEY_CURRENT_TIME, mCurrentTime);
            mQueryString.set(KEY_DURATION, mDuration);
            mQueryString.set(KEY_SEEKABLE_END_TIME, mEndTime);
            result = mQueryString.toString();
        }

        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof LoungeData) {
            LoungeData newObj = (LoungeData) obj;

            result = (getState() == newObj.getState()) && (getCurrentTime() == newObj.getCurrentTime()) && (getDuration() == newObj.getDuration());
        }

        return result;
    }
}
