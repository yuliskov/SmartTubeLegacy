package com.liskovsoft.smartyoutubetv.voicesearch;

import android.content.Intent;

public interface ActivityListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
