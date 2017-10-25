package com.liskovsoft.smartyoutubetv.bootstrap;

import android.content.pm.ActivityInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager.LayoutParams;

public class ActivityBase extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        makeActivityFullscreen();
        hideTitleBar();
        makeActivityHorizontal();
    }

    private void makeActivityHorizontal() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void hideTitleBar() {
        // NOTE: we must set theme before ui instantiation
        setTheme(com.liskovsoft.browser.R.style.SimpleUITheme);
    }

    private void makeActivityFullscreen() {
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        if (VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}