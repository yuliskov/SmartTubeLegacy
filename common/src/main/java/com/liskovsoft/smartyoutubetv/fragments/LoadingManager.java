package com.liskovsoft.smartyoutubetv.fragments;

public interface LoadingManager {
    void show();
    void hide();
    void showMessage(int resId);
    void showMessage(String message);
}
