package com.liskovsoft.smartyoutubetv.exoplayer.commands;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import java.util.concurrent.Callable;

public class PressBackCommand implements Callable<Boolean> {
    private Context mContext;

    public PressBackCommand(Context context) {
        mContext = context;
    }

    @Override
    public Boolean call() {
        pressBackButton();
        return true;
    }

    private void pressBackButton() {
        if (!(mContext instanceof AppCompatActivity))
            return;
        AppCompatActivity activity = (AppCompatActivity) mContext;
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
    }
}
