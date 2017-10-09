package com.liskovsoft.smartyoutubetv.bootstrap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.liskovsoft.smartyoutubetv.R;

public class BootstrapActivity extends FullscreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);
    }

    public void selectFlavour(View view) {
        switch (view.getId()) {
            case R.id.button_webview:
                startActivity(this, com.liskovsoft.smartyoutubetv.flavours.webview.SmartYouTubeTVActivity.class);
                break;
            case R.id.button_xwalk:
                startActivity(this, com.liskovsoft.smartyoutubetv.flavours.xwalk.SmartYouTubeTVActivity.class);
                break;
            case R.id.button_exo:
                startActivity(this, com.liskovsoft.smartyoutubetv.flavours.exoplayer.SmartYouTubeTVActivity.class);
                break;
        }
    }

    private void startActivity(Context ctx, Class clazz) {
        Intent intent = new Intent();
        intent.setClass(ctx, clazz);
        startActivity(intent);
    }
}
