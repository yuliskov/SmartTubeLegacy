package com.liskovsoft.smartyoutubetv.common.mylogger;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.helpers.FileHelpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        append("---------------------------------------");
        append("----------- STARTING LOG --------------");
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
