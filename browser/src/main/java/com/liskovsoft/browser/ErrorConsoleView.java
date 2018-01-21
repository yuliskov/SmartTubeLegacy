/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liskovsoft.browser;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;
import android.widget.*;

import java.util.Vector;

/* package */ class ErrorConsoleView extends LinearLayout {

    /**
     * Define some constants to describe the visibility of the error console.
     */
    public static final int SHOW_MINIMIZED = 0;
    public static final int SHOW_MAXIMIZED = 1;
    public static final int SHOW_NONE      = 2;

    private TextView mConsoleHeader;
    private ErrorConsoleListView mErrorList;
    private LinearLayout mEvalJsViewGroup;
    private EditText mEvalEditText;
    private Button mEvalButton;
    private WebView mWebView;
    private int mCurrentShowState = SHOW_NONE;

    private boolean mSetupComplete = false;

    // Before we've been asked to display the console, cache any messages that should
    // be added to the console. Then when we do display the console, add them to the view
    // then.
    private Vector<ConsoleMessage> mErrorMessageCache;

    public ErrorConsoleView(Context context) {
        super(context);
    }

    public ErrorConsoleView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    /**
     * Removes all error messages from the console.
     */
    public void clearErrorMessages() {
        if (mSetupComplete) {
            mErrorList.clearErrorMessages();
        } else if (mErrorMessageCache != null) {
            mErrorMessageCache.clear();
        }
    }

    /**
     * Sets the webview that this console is associated with. Currently this is used so
     * we can call into webkit to evaluate JS expressions in the console.
     */
    public void setWebView(WebView webview) {
        mWebView = webview;
    }

    /**
     * Returns the current visibility state of the console.
     */
    public int getShowState() {
        if (mSetupComplete) {
            return mCurrentShowState;
        } else {
            return SHOW_NONE;
        }
    }

    /**
     * Adds a message to the set of messages the console uses.
     */
    public void addErrorMessage(ConsoleMessage consoleMessage) {
        if (mSetupComplete) {
            mErrorList.addErrorMessage(consoleMessage);
        } else {
            if (mErrorMessageCache == null) {
                mErrorMessageCache = new Vector<ConsoleMessage>();
            }
            mErrorMessageCache.add(consoleMessage);
        }
    }

    /**
     * Sets the visibility state of the console.
     */
    public void showConsole(int show_state) {
        commonSetupIfNeeded();
        switch (show_state) {
            case SHOW_MINIMIZED:
                mConsoleHeader.setVisibility(View.VISIBLE);
                mConsoleHeader.setText(R.string.error_console_header_text_minimized);
                mErrorList.setVisibility(View.GONE);
                mEvalJsViewGroup.setVisibility(View.GONE);
                break;

            case SHOW_MAXIMIZED:
                mConsoleHeader.setVisibility(View.VISIBLE);
                mConsoleHeader.setText(R.string.error_console_header_text_maximized);
                mErrorList.setVisibility(View.VISIBLE);
                mEvalJsViewGroup.setVisibility(View.VISIBLE);
                break;

            case SHOW_NONE:
                mConsoleHeader.setVisibility(View.GONE);
                mErrorList.setVisibility(View.GONE);
                mEvalJsViewGroup.setVisibility(View.GONE);
                break;
        }
        mCurrentShowState = show_state;
    }

    private void commonSetupIfNeeded() {
        if (mSetupComplete) {
            return;
        }

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.error_console, this);

        // Get references to each ui element.
        mConsoleHeader = (TextView) findViewById(R.id.error_console_header_id);
        mErrorList = (ErrorConsoleListView) findViewById(R.id.error_console_list_id);
        mEvalJsViewGroup = (LinearLayout) findViewById(R.id.error_console_eval_view_group_id);
        mEvalEditText = (EditText) findViewById(R.id.error_console_eval_text_id);
        mEvalButton = (Button) findViewById(R.id.error_console_eval_button_id);

        mEvalButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Send the javascript to be evaluated to webkit as a javascript: url
                // TODO: Can we expose access to webkit's JS interpreter here and evaluate it that
                // way? Note that this is called on the UI thread so we will need to post a message
                // to the WebCore thread to implement this.
                if (mWebView != null) {
                    mWebView.loadUrl("javascript:" + mEvalEditText.getText());
                }

                mEvalEditText.setText("");
            }
        });

        // Make clicking on the console title bar min/maximse it.
        mConsoleHeader.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (mCurrentShowState == SHOW_MINIMIZED) {
                    showConsole(SHOW_MAXIMIZED);
                } else {
                    showConsole(SHOW_MINIMIZED);
                }
            }
        });

        // Add any cached messages to the list now that we've assembled the view.
        if (mErrorMessageCache != null) {
            for (ConsoleMessage msg : mErrorMessageCache) {
                mErrorList.addErrorMessage(msg);
            }
            mErrorMessageCache.clear();
        }

        mSetupComplete = true;
    }

    /**
     * This class extends ListView to implement the View that will actually display the set of
     * errors encountered on the current page.
     */
    public static class ErrorConsoleListView extends ListView {
        // An adapter for this View that contains a list of error messages.
        private ErrorConsoleMessageList mConsoleMessages;

        public ErrorConsoleListView(Context context, AttributeSet attributes) {
            super(context, attributes);
            mConsoleMessages = new ErrorConsoleMessageList(context);
            setAdapter(mConsoleMessages);
        }

        public void addErrorMessage(ConsoleMessage consoleMessage) {
            mConsoleMessages.add(consoleMessage);
            setSelection(mConsoleMessages.getCount());
        }

        public void clearErrorMessages() {
            mConsoleMessages.clear();
        }

        /**
         * This class is an adapter for ErrorConsoleListView that contains the error console
         * message data.
         */
        private static class ErrorConsoleMessageList extends android.widget.BaseAdapter
                implements android.widget.ListAdapter {

            private Vector<ConsoleMessage> mMessages;
            private LayoutInflater mInflater;

            public ErrorConsoleMessageList(Context context) {
                mMessages = new Vector<ConsoleMessage>();
                mInflater = (LayoutInflater)context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
            }

            /**
             * Add a new message to the list and update the View.
             */
            public void add(ConsoleMessage consoleMessage) {
                mMessages.add(consoleMessage);
                notifyDataSetChanged();
            }

            /**
             * Remove all messages from the list and update the view.
             */
            public void clear() {
                mMessages.clear();
                notifyDataSetChanged();
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            public long getItemId(int position) {
                return position;
            }

            public Object getItem(int position) {
                return mMessages.get(position);
            }

            public int getCount() {
                return mMessages.size();
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            /**
             * Constructs a TwoLineListItem for the error at position.
             */
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                ConsoleMessage error = mMessages.get(position);

                if (error == null) {
                    return null;
                }

                if (convertView == null) {
                    view = mInflater.inflate(android.R.layout.two_line_list_item, parent, false);
                } else {
                    view = convertView;
                }

                TextView headline = (TextView) view.findViewById(android.R.id.text1);
                TextView subText = (TextView) view.findViewById(android.R.id.text2);
                headline.setText(error.sourceId() + ":" + error.lineNumber());
                subText.setText(error.message());
                switch (error.messageLevel()) {
                    case ERROR:
                        subText.setTextColor(Color.RED);
                        break;
                    case WARNING:
                        // Orange
                        subText.setTextColor(Color.rgb(255,192,0));
                        break;
                    case TIP:
                        subText.setTextColor(Color.BLUE);
                        break;
                    default:
                        subText.setTextColor(Color.LTGRAY);
                        break;
                }
                return view;
            }

        }
    }
}
