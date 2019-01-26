package com.liskovsoft.smartyoutubetv.common.mylogger;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.AppInfoHelpers;
import com.liskovsoft.smartyoutubetv.common.helpers.FileHelpers;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class FileLogger extends MyLogger {
    private final Context mContext;
    private BufferedWriter mWriter;

    public FileLogger(Context context) {
        mContext = context;
    }

    @Override
    public void d(String tag, String msg) {
        append(String.format("DEBUG: %s: %s", tag, msg));
    }

    @Override
    public void i(String tag, String msg) {
        append(String.format("INFO: %s: %s", tag, msg));
    }

    @Override
    public void w(String tag, String msg) {
        append(String.format("WARN: %s: %s", tag, msg));
    }

    @Override
    public void e(String tag, String msg) {
        append(String.format("ERROR: %s: %s", tag, msg));
    }

    private void append(String text)
    {
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = getWriter();
            buf.append(text);
            buf.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedWriter getWriter() {
        if (mWriter == null) {
            File logFile = getLogFile(mContext);

            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                //BufferedWriter for performance, true to set append to file flag
                mWriter = new BufferedWriter(new FileWriter(logFile, false));
                writeLogHeader();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mWriter;
    }

    public static String getLogPath(Context context) {
        return getLogFile(context).toString();
    }

    private static File getLogFile(Context context) {
        return new File(FileHelpers.getCacheDir(context), "log.txt");
    }

    private void writeLogHeader() {
        String time = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.US).format(new Date());
        String version = AppInfoHelpers.getAppVersionRobust(mContext);
        append("---------------------------------------");
        append("----------- STARTING LOG --------------");
        append("-------- " +    time     + " ----------");
        append("------- "  +   version   +   " --------");
        append("---------------------------------------");
    }

    @Override
    public void flush() {
        if (mWriter != null) {
            try {
                mWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
