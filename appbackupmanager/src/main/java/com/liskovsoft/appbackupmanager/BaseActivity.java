package com.liskovsoft.appbackupmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import com.liskovsoft.appbackupmanager.ui.LanguageHelper;
import org.openintents.openpgp.util.OpenPgpApi;

public class BaseActivity extends Activity
{
    final static String TAG = "oandbackup";
    public static final int OPENPGP_REQUEST_ENCRYPT = 3;
    public static final int OPENPGP_REQUEST_DECRYPT = 4;
    public static final int OPENPGP_REQUEST_TESTRESPONSE = 5;
    Crypto crypto;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.JELLY_BEAN && getParentActivityIntent() != null)
        {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String langCode = prefs.getString(Constants.PREFS_LANGUAGES,
            Constants.PREFS_LANGUAGES_DEFAULT);
        LanguageHelper.initLanguage(this, langCode);
        if(prefs.getBoolean(Constants.PREFS_ENABLECRYPTO, false))
            startCrypto();
    }
    @Override
    public void onDestroy()
    {
        if(crypto != null)
            crypto.unbind();
        super.onDestroy();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode == OPENPGP_REQUEST_ENCRYPT || requestCode == OPENPGP_REQUEST_DECRYPT)
            {
                if(data != null)
                    crypto.doAction(this, data, requestCode);
                else
                    crypto.setError();
            }
            if(requestCode == OPENPGP_REQUEST_TESTRESPONSE)
            {
                if(data != null)
                    crypto.testResponse(this, data, data.getLongArrayExtra(OpenPgpApi.RESULT_KEY_IDS));
                else
                    crypto.setError();
            }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            if(requestCode == OPENPGP_REQUEST_ENCRYPT || requestCode == OPENPGP_REQUEST_DECRYPT || requestCode == OPENPGP_REQUEST_TESTRESPONSE)
                crypto.cancel();
        }
    }
    public void startCrypto()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                crypto = new Crypto(PreferenceManager.getDefaultSharedPreferences(BaseActivity.this));
                crypto.bind(BaseActivity.this);
            }
        }).start();
    }
    public Crypto getCrypto()
    {
        if(crypto == null)
        {
            startCrypto();
            while(crypto == null)
            {
                try
                {
                    Thread.sleep(100);
                }
                catch(InterruptedException e)
                {
                    Log.w(TAG, "getCrypto thread interrupted");
                    Thread.currentThread().interrupt();
                }
            }
        }
        return crypto;
    }
}
