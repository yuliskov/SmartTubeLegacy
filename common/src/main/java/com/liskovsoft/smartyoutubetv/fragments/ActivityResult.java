package com.liskovsoft.smartyoutubetv.fragments;

import android.content.Intent;

public interface ActivityResult {
    void onResult(int resultCode, Intent data);
}
