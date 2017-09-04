package com.liskovsoft.smartyoutubetv.exoplayer.interceptors;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.exoplayer.commands.PressBackCommand2;
import com.liskovsoft.smartyoutubetv.exoplayer.commands.PressNextCommand;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import okhttp3.MediaType;

import java.io.ByteArrayInputStream;

public class PlayEndInterceptor extends RequestInterceptor {
    private final Context mContext;
    private final PressNextCommand mPressNextCommand;
    private int mCounter;

    public PlayEndInterceptor(Context context) {
        mContext = context;
        mPressNextCommand = new PressNextCommand(new PressBackCommand2());
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (mCounter == 0) {
            mPressNextCommand.call();
        }

        mCounter++;

        return null;
    }

    public void reset() {
        mCounter = 0;
    }
}
