package com.liskovsoft.smartyoutubetv.misc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.bootstrap.BootstrapActivity;
import com.liskovsoft.smartyoutubetv.flavors.common.TwoFragmentsManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.SendMessageCommand;
import com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTV1080Activity;
import com.liskovsoft.smartyoutubetv.flavors.xwalk.SmartYouTubeTV1080AltActivity;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class SmartUtils {
    public static final String KEYCODE_MEDIA_PLAY_PAUSE = "MEDIA_PLAY_PAUSE";
    public static final String KEYCODE_MEDIA_STOP = "MEDIA_STOP";
    public static final String KEYCODE_MEDIA_FAST_FORWARD = "KEYCODE_MEDIA_FAST_FORWARD";
    public static final String KEYCODE_MEDIA_REWIND = "KEYCODE_MEDIA_REWIND";
    public static final String KEYCODE_MEDIA_NEXT = "KEYCODE_MEDIA_NEXT";
    public static final String KEYCODE_MEDIA_PREVIOUS = "KEYCODE_MEDIA_PREVIOUS";
    public static final String KEY_DOWN = "KEY_DOWN";
    public static final String KEY_UP = "KEY_UP";
    public static final String KEYCODE_RIGHT = "RIGHT";
    public static final String KEYCODE_LEFT = "LEFT";

    @SuppressLint("WrongConstant")
    public static void returnToLaunchersDialogOrExit(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, BootstrapActivity.class);
        intent.putExtra(BootstrapActivity.SKIP_RESTORE, true);

        if (Helpers.isActivityExists(intent, context)) {
            context.startActivity(intent);
        }

        // Exit from the app in either case
        context.finish();
    }

    public static void restartToBootstrap(Context context) {
        restartApp(context, true);
    }

    public static void restartApp(Context context) {
        restartApp(context, false);
    }

    private static void restartApp(Context context, boolean toBootstrap) {
        SmartUtils.restart(context, toBootstrap);
    }

    private static void restart(Context context, boolean toBootstrap) {
        Intent intent = new Intent();

        intent.setClass(context, BootstrapActivity.class);

        if (toBootstrap) {
            intent.putExtra(BootstrapActivity.SKIP_RESTORE, true);
        }

        context.startActivity(intent);

        if (context instanceof Activity) {
            ((Activity)context).finish();
        }

        System.exit(0);
    }

    public static void killApp(Context context) {
        if (context instanceof Activity) {
            ((Activity)context).finish();
        }

        System.exit(0);
    }

    public static void sendMessage(String messageId, String messageData) {
        new SendMessageCommand(messageId, messageData).call();
    }

    /**
     * Converts number of objects to string that may be parsed as JSON object
     */
    public static String toJsonString(String... params) {
        StringBuilder result = new StringBuilder();

        result.append("{");

        for (int i = 0; i < params.length; i++) {
            if (i == 0) {
                result.append("\"");
                result.append(params[i]);
                result.append("\"");
            } else if (i % 2 != 0) {
                result.append(":");

                result.append("\"");
                result.append(params[i]);
                result.append("\"");
            } else {
                result.append(",");

                result.append("\"");
                result.append(params[i]);
                result.append("\"");
            }
        }

        result.append("}");

        return result.toString();
    }

    public static boolean isWebView(Context context) {
        return context instanceof SmartYouTubeTV1080Activity;
    }

    public static boolean isXWalk(Context context) {
        return context instanceof SmartYouTubeTV1080AltActivity;
    }

    public static boolean isExo(Context context) {
        return context instanceof TwoFragmentsManagerActivity;
    }

    public static boolean isAdBlockEnabled() {
        return SmartPreferences.AD_BLOCK_ENABLED.equals(CommonApplication.getPreferences().getAdBlockStatus());
    }
}
