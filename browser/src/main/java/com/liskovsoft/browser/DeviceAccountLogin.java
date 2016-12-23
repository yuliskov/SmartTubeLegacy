package com.liskovsoft.browser;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class DeviceAccountLogin implements
        AccountManagerCallback<Bundle> {

    private final Activity mActivity;
    private final WebView mWebView;
    private final Tab mTab;
    private final WebViewController mWebViewController;
    private final AccountManager mAccountManager;
    Account[] mAccounts;
    private AutoLoginCallback mCallback;
    private String mAuthToken;

    // Current state of the login.
    private int mState = INITIAL;

    public static final int INITIAL = 0;
    public static final int FAILED = 1;
    public static final int PROCESSING = 2;

    public interface AutoLoginCallback {
        public void loginFailed();
    }

    public DeviceAccountLogin(Activity activity, WebView view, Tab tab,
                              WebViewController controller) {
        mActivity = activity;
        mWebView = view;
        mTab = tab;
        mWebViewController = controller;
        mAccountManager = AccountManager.get(activity);
    }

    public void handleLogin(String realm, String account, String args) {
        mAccounts = mAccountManager.getAccountsByType(realm);
        mAuthToken = "weblogin:" + args;

        // No need to display UI if there are no accounts.
        if (mAccounts.length == 0) {
            return;
        }

        // Verify the account before using it.
        for (Account a : mAccounts) {
            if (a.name.equals(account)) {
                // Handle the automatic login case where the service gave us an
                // account to use.
                mAccountManager.getAuthToken(a, mAuthToken, null,
                        mActivity, this, null);
                return;
            }
        }

        displayLoginUi();
    }

    @Override
    public void run(AccountManagerFuture<Bundle> value) {
        try {
            String result = value.getResult().getString(
                    AccountManager.KEY_AUTHTOKEN);
            if (result == null) {
                loginFailed();
            } else {
                mWebView.loadUrl(result);
                mTab.setDeviceAccountLogin(null);
                if (mTab.inForeground()) {
                    mWebViewController.hideAutoLogin(mTab);
                }
            }
        } catch (Exception e) {
            loginFailed();
        }
    }

    public int getState() {
        return mState;
    }

    private void loginFailed() {
        mState = FAILED;
        if (mTab.getDeviceAccountLogin() == null) {
            displayLoginUi();
        } else {
            if (mCallback != null) {
                mCallback.loginFailed();
            }
        }
    }

    private void displayLoginUi() {
        // Display the account picker.
        mTab.setDeviceAccountLogin(this);
        if (mTab.inForeground()) {
            mWebViewController.showAutoLogin(mTab);
        }
    }

    public void cancel() {
        mTab.setDeviceAccountLogin(null);
    }

    public void login(int accountIndex, AutoLoginCallback cb) {
        mState = PROCESSING;
        mCallback = cb;
        mAccountManager.getAuthToken(
                mAccounts[accountIndex], mAuthToken, null,
                mActivity, this, null);
    }

    public String[] getAccountNames() {
        String[] names = new String[mAccounts.length];
        for (int i = 0; i < mAccounts.length; i++) {
            names[i] = mAccounts[i].name;
        }
        return names;
    }
}
