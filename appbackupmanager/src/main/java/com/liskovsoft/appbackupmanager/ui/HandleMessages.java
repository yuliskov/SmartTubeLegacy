package com.liskovsoft.appbackupmanager.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

public class HandleMessages
{
    final static String TAG = HandleMessages.class.getSimpleName();
    final static int SHOW_DIALOG = 0;
    final static int DISMISS_DIALOG = 1;

    private final ProgressHandler handler;
    private static WeakReference<Context> mContext;

    public HandleMessages(Context context)
    {
        /**
            * use weakreference to avoid another memory leak
            * http://www.androiddesignpatterns.com/2013/01/inner-class-handler-memory-leak.html
        */
        mContext = new WeakReference<Context>(context);
        /**
            * the handler is bound to the looper of the thread were it was created
            * it is therefore important to initialize this class on the main thread
        */
        handler = new ProgressHandler();
    }
    public void setMessage(String title, String msg)
    {
        Message message = Message.obtain(handler, SHOW_DIALOG);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", msg);
        message.setData(bundle);
        message.sendToTarget();
    }
    public void reShowMessage()
    {
        setMessage(handler.getTitle(), handler.getMessage());
    }
    public boolean isShowing()
    {
        return handler.isShowing();
    }
    public void showMessage(String title, String message)
    {
        setMessage(title, message);
    }
    public void changeMessage(String title, String message)
    {
        setMessage(title, message);
    }
    public void endMessage()
    {
        Message endMessage = Message.obtain();
        endMessage.what = DISMISS_DIALOG;
        handler.sendMessage(endMessage);
    }
    /**
        * handlers should be static to avoid memory leaks
        * https://groups.google.com/forum/#!msg/android-developers/1aPZXZG6kWk/lIYDavGYn5UJ
    */
    private static class ProgressHandler extends Handler
    {
        private static ProgressDialog progress = null;
        private static String title, msg;
        @Override
        public void handleMessage(Message message)
        {
            title = message.getData().getString("title");
            msg = message.getData().getString("message");
            switch(message.what)
            {
                case SHOW_DIALOG:
                    Context context;
                    /**
                        * change the current running progressdialog if it exists
                        * to avoid losing the reference
                        * TODO: allow more than one progressdialog at a time
                        * this could be handled with asynctask but that is discouraged for long operations
                    */
                    if(progress != null && progress.isShowing())
                    {
                        progress.setTitle(title);
                        progress.setMessage(msg);
                    }
                    else if((context = mContext.get()) != null)
                    {
                        // TODO: notice if a BadTokenException might seldomly occur here
                        progress = ProgressDialog.show(context, title, msg, true, false);
                    }
                    else
                    {
                        Log.e(TAG, "context from weakreference is null");
                    }
                    break;
                case DISMISS_DIALOG:
                    if(progress != null)
                    {
                        try
                        {
                            progress.dismiss();
                        }
                        catch(IllegalArgumentException e)
                        {
                            Log.e(TAG, "could not dismiss dialog: " + e.toString());
                            e.printStackTrace();
                        }
                        finally
                        {
                            progress = null;
                        }
                    }
                    break;
            }
        }
        public boolean isShowing()
        {
            if(progress != null)
                return progress.isShowing();
            return false;
        }
        public String getTitle()
        {
            if(title != null)
                return title;
            return "";
        }
        public String getMessage()
        {
            if(msg != null)
                return msg;
            return "";
        }
    };
}