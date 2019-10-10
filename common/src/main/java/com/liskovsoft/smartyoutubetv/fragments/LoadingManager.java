package com.liskovsoft.smartyoutubetv.fragments;

public interface LoadingManager {
    String HIDE_TIPS = "hide_tips";
    void show();
    void hide();
    void showMessage(int resId);
    void showMessage(String message);
}
