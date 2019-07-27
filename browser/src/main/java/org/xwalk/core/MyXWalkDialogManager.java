// Copyright (c) 2015 Intel Corporation. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.xwalk.core;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

/**
 * <p>By <code>XWalkDialogManager</code>, you can customize what the dialog looks like when
 * initializing Crosswalk Project runtime. Please note that you can only specify how the dialog to
 * be displayed but cann't specify how it reacts.
 *
 * <p>Here is the sample code when using {@link XWalkActivity}:</p>
 *
 * <pre>
 * import android.app.AlertDialog;
 * import android.os.Bundle;
 *
 * import org.xwalk.core.XWalkActivity;
 * import org.xwalk.core.XWalkDialogManager;
 * import org.xwalk.core.XWalkView;
 *
 * public class MainActivity extends XWalkActivity {
 *     private XWalkView mXWalkView;
 *
 *     &#64;Override
 *     protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *
 *         setContentView(R.layout.activity_main);
 *         mXWalkView = (XWalkView) findViewById(R.id.xwalkview);
 *
 *         // Get default dialog and modifiy it as needed, or set a completely customized dialog.
 *
 *         XWalkDialogManager dialogManager = getDialogManager();
 *         AlertDialog dialog = dialogManager.getAlertDialog(XWalkDialogManager.DIALOG_NOT_FOUND);
 *         dialog.setTitle("TestTitle");
 *         dialog.setMessage("TestMessage");
 *     }
 *
 *     &#64;Override
 *     public void onXWalkReady() {
 *         mXWalkView.loadUrl("https://crosswalk-project.org/");
 *     }
 * }
 * </pre>
 *
 * <p>And when using {@link XWalkUpdater}:</p>
 *
 * <pre>
 * import android.app.Activity;
 * import android.app.AlertDialog;
 * import android.content.DialogInterface;
 * import android.content.DialogInterface.OnClickListener;
 * import android.os.Bundle;
 *
 * import org.xwalk.core.XWalkDialogManager;
 * import org.xwalk.core.XWalkInitializer;
 * import org.xwalk.core.XWalkUpdater;
 * import org.xwalk.core.XWalkView;
 *
 * public class MainActivity extends Activity implements
 *        XWalkInitializer.XWalkInitListener,
 *        XWalkUpdater.XWalkUpdateListener {
 *
 *     private XWalkInitializer mXWalkInitializer;
 *     private XWalkUpdater mXWalkUpdater;
 *     private XWalkView mXWalkView;
 *     private XWalkDialogManager mDialogManager;
 *
 *     &#64;Override
 *     protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *
 *         mXWalkInitializer = new XWalkInitializer(this, this);
 *         mXWalkInitializer.initAsync();
 *
 *         setContentView(R.layout.activity_main);
 *         mXWalkView = (XWalkView) findViewById(R.id.xwalkview);
 *     }
 *
 *     &#64;Override
 *     protected void onResume() {
 *         super.onResume();
 *         mXWalkInitializer.initAsync();
 *     }
 *
 *     &#64;Override
 *     public void onXWalkInitStarted() {
 *     }
 *
 *     &#64;Override
 *     public void onXWalkInitCancelled() {
 *         finish();
 *     }
 *
 *     &#64;Override
 *     public void onXWalkInitFailed() {
 *         if (mXWalkUpdater == null) {
 *             // Get default dialog and modifiy it as needed, or set a completely customized dialog.
 *
 *             AlertDialog dialog = new AlertDialog.Builder(this).create();
 *             dialog.setIcon(android.R.drawable.ic_dialog_alert);
 *             dialog.setTitle("TextTitle");
 *             dialog.setMessage("TextMessage");
 *             dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Download", (OnClickListener) null);
 *
 *             mDialogManager = new XWalkDialogManager(this);
 *             mDialogManager.setAlertDialog(XWalkDialogManager.DIALOG_NOT_FOUND, dialog);
 *             mXWalkUpdater = new XWalkUpdater(this, this, mDialogManager);
 *         }
 *         mXWalkUpdater.updateXWalkRuntime();
 *     }
 *
 *     &#64;Override
 *     public void onXWalkInitCompleted() {
 *         mXWalkView.loadUrl("https://crosswalk-project.org/");
 *     }
 *
 *     &#64;Override
 *     public void onXWalkUpdateCancelled() {
 *         finish();
 *     }
 * }
 * </pre>
 *
 */

public class MyXWalkDialogManager {
    /**
     * <B>Dialog Type</B>: AlertDialog<br>
     * <B>Dialog Information</B>: The Crosswalk Project runtime is not found.<br>
     * <B>Positive Button</B>(<I>Mandatory</I>): download Crosswalk Project runtime<br>
     * <B>Negative Button</B>(<I>Optional</I>): invoke {@link XWalkActivity#onXWalkFailed()} /
     * {@link XWalkUpdater.XWalkUpdateListener#onXWalkUpdateCancelled()}
     *
     */
    public static final int DIALOG_NOT_FOUND = 1;

    /**
     * <B>Dialog Type</B>: AlertDialog<br>
     * <B>Dialog Information</B>: The version of Crosswalk Project runtime is older than the application.<br>
     * <B>Positive Button</B>(<I>Mandatory</I>): download Crosswalk Project runtime<br>
     * <B>Negative Button</B>(<I>Optional</I>): invoke {@link XWalkActivity#onXWalkFailed()} /
     * {@link XWalkUpdater.XWalkUpdateListener#onXWalkUpdateCancelled()}
     */
    public static final int DIALOG_OLDER_VERSION = 2;

    /**
     * <B>Dialog Type</B>: AlertDialog<br>
     * <B>Dialog Information</B>: The version of Crosswalk Project runtime is newer than the application.<br>
     * <B>Negative Button</B>(<I>Mandatory</I>): invoke {@link XWalkActivity#onXWalkFailed()} /
     * {@link XWalkUpdater.XWalkUpdateListener#onXWalkUpdateCancelled()}
     */
    public static final int DIALOG_NEWER_VERSION = 3;

    /**
     * <B>Dialog Type</B>: AlertDialog<br>
     * <B>Dialog Information</B>: Mismatch of CPU Architecture for Crosswalk Project Runtime.<br>
     * <B>Positive Button</B>(<I>Mandatory</I>): download Crosswalk Project runtime<br>
     * <B>Negative Button</B>(<I>Optional</I>): invoke {@link XWalkActivity#onXWalkFailed()} /
     * {@link XWalkUpdater.XWalkUpdateListener#onXWalkUpdateCancelled()}
     */
    public static final int DIALOG_ARCHITECTURE_MISMATCH = 4;

    /**
     * <B>Dialog Type</B>: AlertDialog<br>
     * <B>Dialog Information</B>: The Crosswalk signature verification failed.<br>
     * <B>Negative Button</B>(<I>Mandatory</I>): invoke {@link XWalkActivity#onXWalkFailed()} /
     * {@link XWalkUpdater.XWalkUpdateListener#onXWalkUpdateCancelled()}
     */
    public static final int DIALOG_SIGNATURE_CHECK_ERROR = 5;

    /**
     * <B>Dialog Type</B>: AlertDialog<br>
     * <B>Dialog Information</B>: Failed to download Crosswalk Project runtime.<br>
     * <B>Positive Button</B>(<I>Mandatory</I>): re-download Crosswalk Project runtime<br>
     * <B>Negative Button</B>(<I>Optional</I>): invoke {@link XWalkActivity#onXWalkFailed()} /
     * {@link XWalkUpdater.XWalkUpdateListener#onXWalkUpdateCancelled()}
     */
    public static final int DIALOG_DOWNLOAD_ERROR = 6;

    /**
     * <B>Dialog Type</B>: AlertDialog<br>
     * <B>Dialog Information</B>: Select stores that support Crosswalk Project runtime.<br>
     * <B>Positive Button</B>(<I>Mandatory</I>): go to the store's page<br>
     */
    public static final int DIALOG_SELECT_STORE = 7;

    /**
     * <B>Dialog Type</B>: AlertDialog<br>
     * <B>Dialog Information</B>: The stores on the device don't support Crosswalk Project. <br>
     * <B>Negative Button</B>(<I>Mandatory</I>): invoke {@link XWalkActivity#onXWalkFailed()} /
     * {@link XWalkUpdater.XWalkUpdateListener#onXWalkUpdateCancelled()}
     */
    public static final int DIALOG_UNSUPPORTED_STORE = 8;

    /**
     * <B>Dialog Type</B>: ProgressDialog<br>
     * <B>Dialog Information</B>: Decompressing Crosswalk Project runtime.<br>
     * <B>Negative Button</B>(<I>Optional</I>): invoke {@link XWalkActivity#onXWalkFailed()}
     */
    public static final int DIALOG_DECOMPRESSING = 11;

    /**
     * <B>Dialog Type</B>: ProgressDialog<br>
     * <B>Dialog Information</B>: Downloading Crosswalk Project runtime.<br>
     * <B>Negative Button</B>(<I>Optional</I>): invoke {@link XWalkActivity#onXWalkFailed()} /
     * {@link XWalkUpdater.XWalkUpdateListener#onXWalkUpdateCancelled()}
     */
    public static final int DIALOG_DOWNLOADING = 12;

    private static final String TAG = "XWalkLib";

    private FragmentActivity mContext;
    private Dialog mActiveDialog;

    private AlertDialog mNotFoundDialog;
    private AlertDialog mOlderVersionDialog;
    private AlertDialog mNewerVersionDialog;
    private AlertDialog mArchitectureMismatchDialog;
    private AlertDialog mSignatureCheckErrorDialog;
    private AlertDialog mDownloadErrorDialog;
    private AlertDialog mSelectStoreDialog;
    private AlertDialog mUnsupportedStoreDialog;
    private ProgressDialog mDecompressingDialog;
    private ProgressDialog mDownloadingDialog;

    private static class ButtonAction {
        ButtonAction(int which, Runnable command, boolean mandatory) {
            mWhich = which;
            mClickAction = command;
            mMandatory = mandatory;
        }

        int mWhich;
        Runnable mClickAction;
        boolean mMandatory;
    }

    /**
     * Create XWalkDialogManager for {@link XWalkUpdater} or {@link XWalkActivity}.
     *
     * @param context The context that is used by {@link XWalkUpdater} or {@link XWalkActivity}
     *        itself
     * @since 7.0
     */
    public MyXWalkDialogManager(FragmentActivity context) {
        mContext = context;
    }

    /**
     * Set up alert dialog for each situation. <br>
     * Each situation has its default dialog, so you don't need to set up all of the dialogs.
     * You can specify how the dialog to be displayed but cann't specify how it reacts. The OnClick
     * listener of all buttons will be overwrote with predefined action. For detailed information
     * of each dialog, please refer to the description of the dialog ID.
     *
     * @param id the dialog ID
     * @param dialog the customized dialog
     * @since 7.0
     */
    public void setAlertDialog(int id, AlertDialog dialog) {
        if (dialog instanceof ProgressDialog || dialog instanceof DatePickerDialog
                || dialog instanceof TimePickerDialog) {
            throw new IllegalArgumentException("The type of dialog must be AlertDialog");
        }

        if (id == DIALOG_NOT_FOUND) {
            mNotFoundDialog = dialog;
        } else if (id == DIALOG_OLDER_VERSION) {
            mOlderVersionDialog = dialog;
        } else if (id == DIALOG_NEWER_VERSION) {
            mNewerVersionDialog = dialog;
        } else if (id == DIALOG_ARCHITECTURE_MISMATCH) {
            mArchitectureMismatchDialog = dialog;
        } else if (id == DIALOG_SIGNATURE_CHECK_ERROR) {
            mSignatureCheckErrorDialog = dialog;
        } else if (id == DIALOG_DOWNLOAD_ERROR) {
            mDownloadErrorDialog = dialog;
        } else if (id == DIALOG_SELECT_STORE) {
            mSelectStoreDialog = dialog;
        } else if (id == DIALOG_UNSUPPORTED_STORE) {
            mUnsupportedStoreDialog = dialog;
        } else {
            throw new IllegalArgumentException("Invalid dialog id " + id);
        }
    }

    /**
     * Set up progress dialog for each situation. <br>
     * Each situation has its default dialog, so you don't need to set up all of the dialogs.
     * You can specify how the dialog to be displayed but cann't specify how it reacts. The OnClick
     * listener of all buttons will be overwrote with defined actions. For the detailed information
     * of each dialog, please refer to the description of dialog ID.
     *
     * @param id the dialog ID
     * @param dialog the customized dialog
     * @since 7.0
     */
    public void setProgressDialog(int id, ProgressDialog dialog) {
        if (id == DIALOG_DECOMPRESSING) {
            mDecompressingDialog = dialog;
        } else if (id == DIALOG_DOWNLOADING) {
            mDownloadingDialog = dialog;
        } else {
            throw new IllegalArgumentException("Invalid dialog id " + id);
        }
    }

    /**
     * Get alert dialog for each situation. <br>
     *
     * @param id the dialog ID
     * @return the dialog for this situation. If you haven't set up the dialog before, it will
     *         return default dialog.
     * @since 7.0
     */
    public AlertDialog getAlertDialog(int id) {
        if (id == DIALOG_NOT_FOUND) {
            if (mNotFoundDialog == null) {
                mNotFoundDialog = buildAlertDialog();
                setTitle(mNotFoundDialog, R.string.startup_not_found_title);
                setMessage(mNotFoundDialog, R.string.startup_not_found_message);
                setPositiveButton(mNotFoundDialog, R.string.xwalk_get_crosswalk);
                setNegativeButton(mNotFoundDialog, R.string.xwalk_close);
            }
            return mNotFoundDialog;
        } else if (id == DIALOG_OLDER_VERSION) {
            if (mOlderVersionDialog == null) {
                mOlderVersionDialog = buildAlertDialog();
                setTitle(mOlderVersionDialog, R.string.startup_older_version_title);
                setMessage(mOlderVersionDialog, R.string.startup_older_version_message);
                setPositiveButton(mOlderVersionDialog, R.string.xwalk_get_crosswalk);
                setNegativeButton(mOlderVersionDialog, R.string.xwalk_close);
            }
            return mOlderVersionDialog;
        } else if (id == DIALOG_NEWER_VERSION) {
            if (mNewerVersionDialog == null) {
                mNewerVersionDialog = buildAlertDialog();
                setTitle(mNewerVersionDialog, R.string.startup_newer_version_title);
                setMessage(mNewerVersionDialog, R.string.startup_newer_version_message);
                setNegativeButton(mNewerVersionDialog, R.string.xwalk_close);
            }
            return mNewerVersionDialog;
        } else if (id == DIALOG_ARCHITECTURE_MISMATCH) {
            if (mArchitectureMismatchDialog == null) {
                mArchitectureMismatchDialog = buildAlertDialog();
                setTitle(mArchitectureMismatchDialog, R.string.startup_architecture_mismatch_title);
                setMessage(mArchitectureMismatchDialog,
                        R.string.startup_architecture_mismatch_message);
                setPositiveButton(mArchitectureMismatchDialog, R.string.xwalk_get_crosswalk);
                setNegativeButton(mArchitectureMismatchDialog, R.string.xwalk_close);
            }
            return mArchitectureMismatchDialog;
        } else if (id == DIALOG_SIGNATURE_CHECK_ERROR) {
            if (mSignatureCheckErrorDialog == null) {
                mSignatureCheckErrorDialog = buildAlertDialog();
                setTitle(mSignatureCheckErrorDialog, R.string.startup_signature_check_error_title);
                setMessage(mSignatureCheckErrorDialog,
                        R.string.startup_signature_check_error_message);
                setNegativeButton(mSignatureCheckErrorDialog, R.string.xwalk_close);
            }
            return mSignatureCheckErrorDialog;
        } else if (id == DIALOG_DOWNLOAD_ERROR) {
            if (mDownloadErrorDialog == null) {
                mDownloadErrorDialog = buildAlertDialog();
                setTitle(mDownloadErrorDialog, R.string.crosswalk_install_title);
                setMessage(mDownloadErrorDialog, R.string.download_failed_message);
                setPositiveButton(mDownloadErrorDialog, R.string.xwalk_retry);
                setNegativeButton(mDownloadErrorDialog, R.string.xwalk_cancel);
            }
            return mDownloadErrorDialog;
        } else if (id == DIALOG_SELECT_STORE) {
            if (mSelectStoreDialog == null) {
                mSelectStoreDialog = buildAlertDialog();
                setTitle(mSelectStoreDialog, R.string.crosswalk_install_title);
                setPositiveButton(mSelectStoreDialog, R.string.xwalk_continue);
            }
            return mSelectStoreDialog;
        } else if (id == DIALOG_UNSUPPORTED_STORE) {
            if (mUnsupportedStoreDialog == null) {
                mUnsupportedStoreDialog = buildAlertDialog();
                setTitle(mUnsupportedStoreDialog, R.string.crosswalk_install_title);
                setMessage(mUnsupportedStoreDialog, R.string.unsupported_store_message);
                setNegativeButton(mUnsupportedStoreDialog, R.string.xwalk_close);
            }
            return mUnsupportedStoreDialog;
        } else {
            throw new IllegalArgumentException("Invalid dialog id " + id);
        }
    }

    /**
     * Get progress dialog for each situation. <br>
     *
     * @param id the dialog ID
     * @return the dialog for this situation. If you haven't set up the dialog before, it will
     *         return default dialog.
     * @since 7.0
     */
    public ProgressDialog getProgressDialog(int id) {
        if (id == DIALOG_DECOMPRESSING) {
            if (mDecompressingDialog == null) {
                mDecompressingDialog = buildProgressDialog();
                setTitle(mDecompressingDialog, R.string.crosswalk_install_title);
                setMessage(mDecompressingDialog, R.string.decompression_progress_message);
                setNegativeButton(mDecompressingDialog, R.string.xwalk_cancel);
                mDecompressingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            }
            return mDecompressingDialog;
        } else if (id == DIALOG_DOWNLOADING) {
            if (mDownloadingDialog == null) {
                mDownloadingDialog = buildProgressDialog();
                setTitle(mDownloadingDialog, R.string.crosswalk_install_title);
                setMessage(mDownloadingDialog, R.string.download_progress_message);
                setNegativeButton(mDownloadingDialog, R.string.xwalk_cancel);
                mDownloadingDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            }
            return mDownloadingDialog;
        } else {
            throw new IllegalArgumentException("Invalid dialog id " + id);
        }
    }

    void showInitializationError(int status, Runnable cancelCommand, Runnable downloadCommand) {
        AlertDialog dialog = null;
        ArrayList<ButtonAction> actions = new ArrayList<ButtonAction>();
        if (status == XWalkLibraryInterface.STATUS_NOT_FOUND) {
            dialog = getAlertDialog(DIALOG_NOT_FOUND);
            actions.add(new ButtonAction(DialogInterface.BUTTON_POSITIVE, downloadCommand, true));
            actions.add(new ButtonAction(DialogInterface.BUTTON_NEGATIVE, cancelCommand, false));
        } else if (status == XWalkLibraryInterface.STATUS_OLDER_VERSION) {
            dialog = getAlertDialog(DIALOG_OLDER_VERSION);
            actions.add(new ButtonAction(DialogInterface.BUTTON_POSITIVE, downloadCommand, true));
            actions.add(new ButtonAction(DialogInterface.BUTTON_NEGATIVE, cancelCommand, false));
        } else if (status == XWalkLibraryInterface.STATUS_NEWER_VERSION) {
            dialog = getAlertDialog(DIALOG_NEWER_VERSION);
            actions.add(new ButtonAction(DialogInterface.BUTTON_NEGATIVE, cancelCommand, true));
        } else if (status == XWalkLibraryInterface.STATUS_ARCHITECTURE_MISMATCH) {
            dialog = getAlertDialog(DIALOG_ARCHITECTURE_MISMATCH);
            actions.add(new ButtonAction(DialogInterface.BUTTON_POSITIVE, downloadCommand, true));
            actions.add(new ButtonAction(DialogInterface.BUTTON_NEGATIVE, cancelCommand, false));
        } else if (status == XWalkLibraryInterface.STATUS_SIGNATURE_CHECK_ERROR) {
            dialog = getAlertDialog(DIALOG_SIGNATURE_CHECK_ERROR);
            actions.add(new ButtonAction(DialogInterface.BUTTON_NEGATIVE, cancelCommand, true));
        } else {
            throw new IllegalArgumentException("Invalid status " + status);
        }
        showDialog(dialog, actions);
    }

    void showDownloadError(Runnable cancelCommand, Runnable downloadCommand) {
        AlertDialog dialog = getAlertDialog(DIALOG_DOWNLOAD_ERROR);
        ArrayList<ButtonAction> actions = new ArrayList<ButtonAction>();
        actions.add(new ButtonAction(DialogInterface.BUTTON_POSITIVE, downloadCommand, true));
        actions.add(new ButtonAction(DialogInterface.BUTTON_NEGATIVE, cancelCommand, false));
        showDialog(dialog, actions);
    }

    void showSelectStore(Runnable downloadCommand, String storeName) {
        AlertDialog dialog = getAlertDialog(DIALOG_SELECT_STORE);
        String message = mContext.getString(R.string.select_store_message);
        setMessage(dialog, message.replace("STORE_NAME", storeName));

        ArrayList<ButtonAction> actions = new ArrayList<ButtonAction>();
        actions.add(new ButtonAction(DialogInterface.BUTTON_POSITIVE, downloadCommand, true));
        showDialog(dialog, actions);
    }

    void showUnsupportedStore(Runnable cancelCommand) {
        AlertDialog dialog = getAlertDialog(DIALOG_UNSUPPORTED_STORE);
        ArrayList<ButtonAction> actions = new ArrayList<ButtonAction>();
        actions.add(new ButtonAction(DialogInterface.BUTTON_NEGATIVE, cancelCommand, true));
        showDialog(dialog, actions);
    }

    void showDecompressProgress(Runnable cancelCommand) {
        ProgressDialog dialog = getProgressDialog(DIALOG_DECOMPRESSING);
        ArrayList<ButtonAction> actions = new ArrayList<ButtonAction>();
        actions.add(new ButtonAction(DialogInterface.BUTTON_NEGATIVE, cancelCommand, false));
        showDialog(dialog, actions);
    }

    void showDownloadProgress(Runnable cancelCommand) {
        ProgressDialog dialog = getProgressDialog(DIALOG_DOWNLOADING);
        ArrayList<ButtonAction> actions = new ArrayList<ButtonAction>();
        actions.add(new ButtonAction(DialogInterface.BUTTON_NEGATIVE, cancelCommand, false));
        showDialog(dialog, actions);
    }

    void dismissDialog() {
        if (mActiveDialog == null || mContext.isFinishing()) {
            return;
        }
        if (mActiveDialog.isShowing()) {
            mActiveDialog.dismiss();
        }
        mActiveDialog = null;
    }

    void setProgress(int progress, int max) {
        if (mActiveDialog == null) {
            return;
        }
        ProgressDialog dialog = (ProgressDialog) mActiveDialog;
        dialog.setIndeterminate(false);
        dialog.setMax(max);
        dialog.setProgress(progress);
    }

    boolean isShowingDialog() {
        return mActiveDialog != null && mActiveDialog.isShowing();
    }

    private AlertDialog buildAlertDialog() {
        AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private ProgressDialog buildProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private void setTitle(AlertDialog dialog, int resourceId) {
        dialog.setTitle(mContext.getString(resourceId));
    }

    private void setMessage(AlertDialog dialog, int resourceId) {
        setMessage(dialog, mContext.getString(resourceId));
    }

    private void setMessage(AlertDialog dialog, String text) {
        text = text.replaceAll("APP_NAME", XWalkEnvironment.getApplicationName());
        if (text.startsWith("this")) {
            text = text.replaceFirst("this", "This");
        }
        dialog.setMessage(text);
    }

    private void setPositiveButton(AlertDialog dialog, int resourceId) {
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, mContext.getString(resourceId),
                (OnClickListener) null);
    }

    private void setNegativeButton(AlertDialog dialog, int resourceId) {
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, mContext.getString(resourceId),
                (OnClickListener) null);
    }

    private void showDialog(final AlertDialog dialog, final ArrayList<ButtonAction> actions) {
        dialog.setOnShowListener(d -> {
            for (ButtonAction action : actions) {
                Button button = dialog.getButton(action.mWhich);
                if (button == null) {
                    if (action.mMandatory) {
                        throw new RuntimeException("Button " + action.mWhich + " is mandatory");
                    } else {
                        continue;
                    }
                }

                if (action.mClickAction != null) {
                    final Runnable command = action.mClickAction;
                    button.setOnClickListener(view -> {
                        dismissDialog();
                        command.run();
                    });
                }
            }
        });

        mActiveDialog = dialog;

        if (!mContext.isFinishing()) {
            mActiveDialog.show();
        }
    }
}
