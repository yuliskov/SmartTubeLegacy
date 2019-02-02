package com.liskovsoft.browser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CrashRecoveryHandler {
    private static final String TAG = CrashRecoveryHandler.class.getSimpleName();
    private static final boolean LOGV_ENABLED = Browser.LOGV_ENABLED;
    private static final String LOGTAG = "BrowserCrashRecovery";
    private static final String STATE_FILE = "browser_state.parcel";
    private static final int BUFFER_SIZE = 4096;
    private static final long BACKUP_DELAY = 500; // 500ms between writes
    /* This is the duration for which we will prompt to restore
     * instead of automatically restoring. The first time the browser crashes,
     * we will automatically restore. If we then crash again within XX minutes,
     * we will prompt instead of automatically restoring.
     */
    private static final long PROMPT_INTERVAL = 5 * 60 * 1000; // 5 minutes

    private static final int MSG_WRITE_STATE = 1;
    private static final int MSG_CLEAR_STATE = 2;
    private static final int MSG_PRELOAD_STATE = 3;

    private static CrashRecoveryHandler sInstance;

    private Controller mController;
    private Context mContext;
    private Handler mForegroundHandler;
    private Handler mBackgroundHandler;
    private boolean mIsPreloading = false;
    private boolean mDidPreload = false;
    private Bundle mRecoveryState = null;

    public static CrashRecoveryHandler initialize(Controller controller) {
        if (sInstance == null) {
            sInstance = new CrashRecoveryHandler(controller);
        } else {
            sInstance.mController = controller;
        }
        return sInstance;
    }

    public static CrashRecoveryHandler getInstance() {
        return sInstance;
    }

    private CrashRecoveryHandler(Controller controller) {
        mController = controller;
        mContext = mController.getActivity().getApplicationContext();
        mForegroundHandler = new Handler();
        mBackgroundHandler = new Handler(BackgroundHandler.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_WRITE_STATE:
                        Bundle saveState = (Bundle) msg.obj;
                        writeState(saveState);
                        break;
                    case MSG_CLEAR_STATE:
                        if (LOGV_ENABLED) {
                            Log.v(LOGTAG, "Clearing crash recovery state");
                        }
                        File state = new File(mContext.getCacheDir(), STATE_FILE);
                        if (state.exists()) {
                            state.delete();
                        }
                        break;
                    case MSG_PRELOAD_STATE:
                        mRecoveryState = loadCrashState();
                        synchronized (CrashRecoveryHandler.this) {
                            mIsPreloading = false;
                            mDidPreload = true;
                            CrashRecoveryHandler.this.notifyAll();
                        }
                        break;
                }
            }
        };
    }

    public void backupState() {
        mForegroundHandler.postDelayed(mCreateState, BACKUP_DELAY);
    }

    private Runnable mCreateState = new Runnable() {

        @Override
        public void run() {
            try {
                final Bundle state = mController.createSaveState();
                Message.obtain(mBackgroundHandler, MSG_WRITE_STATE, state)
                        .sendToTarget();
                // Remove any queued up saves
                mForegroundHandler.removeCallbacks(mCreateState);
            } catch (Throwable t) {
                Log.w(LOGTAG, "Failed to save state", t);
                return;
            }
        }

    };

    public void clearState() {
        mBackgroundHandler.sendEmptyMessage(MSG_CLEAR_STATE);
        updateLastRecovered(0);
    }

    private boolean mIsPaused;

    public void pauseState() {
        clearState();
        mIsPaused = true;
    }

    public void resumeState() {
        mIsPaused = false;
    }

    private boolean shouldRestore() {
        BrowserSettings browserSettings = getSettings();
        long lastRecovered = browserSettings.getLastRecovered();
        long timeSinceLastRecover = System.currentTimeMillis() - lastRecovered;
        return (timeSinceLastRecover > PROMPT_INTERVAL)
                || browserSettings.wasLastRunPaused();
    }

    /**
     * Initialize if that is not done already. Normal initialization happens in the {@link Browser Browser} class
     * @return settings initialized
     */
    private BrowserSettings getSettings() {
        BrowserSettings settings = BrowserSettings.getInstance();
        if (settings == null) {
            BrowserSettings.initialize(mContext.getApplicationContext());
            settings = BrowserSettings.getInstance();
        }
        return settings;
    }

    private void updateLastRecovered(long time) {
        BrowserSettings browserSettings = getSettings();
        browserSettings.setLastRecovered(time);
    }

    synchronized private Bundle loadCrashState() {
        // TODO: seems unneeded, but someone placed it here. Why?
        //if (!shouldRestore()) {
        //    return null;
        //}
        BrowserSettings browserSettings = getSettings();
        browserSettings.setLastRunPaused(false);
        Bundle state = null;
        Parcel parcel = Parcel.obtain();
        FileInputStream fin = null;
        try {
            File stateFile = new File(mContext.getCacheDir(), STATE_FILE);
            fin = new FileInputStream(stateFile);
            ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = fin.read(buffer)) > 0) {
                dataStream.write(buffer, 0, read);
            }
            byte[] data = dataStream.toByteArray();
            parcel.unmarshall(data, 0, data.length);
            parcel.setDataPosition(0);
            state = parcel.readBundle();
            if (state != null && !state.isEmpty()) {
                return state;
            }
        } catch (FileNotFoundException e) {
            // No state to recover
            Log.i(TAG, "No state to recover");
        } catch (Throwable e) {
            Log.w(TAG, "Failed to recover state!", e);
        } finally {
            parcel.recycle();
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) { }
            }
        }
        return null;
    }

    public void startRecovery(Intent intent) {
        synchronized (CrashRecoveryHandler.this) {
            while (mIsPreloading) {
                try {
                    CrashRecoveryHandler.this.wait();
                } catch (InterruptedException e) {}
            }
        }
        if (!mDidPreload) {
            mRecoveryState = loadCrashState();
        }
        updateLastRecovered(mRecoveryState != null
                ? System.currentTimeMillis() : 0);
        mController.doStart(mRecoveryState, intent);
        mRecoveryState = null;
        return;
    }

    public void preloadCrashState() {
        synchronized (CrashRecoveryHandler.this) {
            if (mIsPreloading) {
                return;
            }
            mIsPreloading = true;
        }
        mBackgroundHandler.sendEmptyMessage(MSG_PRELOAD_STATE);
    }

    /**
     * Writes the crash recovery state to a file synchronously.
     * Errors are swallowed, but logged.
     * @param state The state to write out
     */
    synchronized void writeState(Bundle state) {
        if (mIsPaused) {
            return;
        }
        Log.d(TAG, "Saving crash recovery state");
        Parcel p = Parcel.obtain();
        try {
            state.writeToParcel(p, 0);
            File stateJournal = new File(mContext.getCacheDir(),
                    STATE_FILE + ".journal");
            FileOutputStream fout = new FileOutputStream(stateJournal);
            fout.write(p.marshall());
            fout.close();
            File stateFile = new File(mContext.getCacheDir(),
                    STATE_FILE);
            if (!stateJournal.renameTo(stateFile)) {
                // Failed to rename, try deleting the existing
                // file and try again
                stateFile.delete();
                stateJournal.renameTo(stateFile);
            }
        } catch (Throwable e) {
            Log.e(TAG, "Failed to save persistent state", e);
        } finally {
            p.recycle();
        }
    }
}