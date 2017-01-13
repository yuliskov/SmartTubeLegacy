package com.liskovsoft.smartyoutubetv.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LaunchSmartYouTubeTVActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new ActivityManager(this).startActivityForDevice();
    }
}
