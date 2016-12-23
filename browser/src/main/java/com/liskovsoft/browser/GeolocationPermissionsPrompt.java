package com.liskovsoft.browser;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GeolocationPermissionsPrompt extends RelativeLayout {
    private TextView mMessage;
    private Button mShareButton;
    private Button mDontShareButton;
    private CheckBox mRemember;
    private GeolocationPermissions.Callback mCallback;
    private String mOrigin;

    public GeolocationPermissionsPrompt(Context context) {
        this(context, null);
    }

    public GeolocationPermissionsPrompt(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mMessage = (TextView) findViewById(R.id.message);
        mShareButton = (Button) findViewById(R.id.share_button);
        mDontShareButton = (Button) findViewById(R.id.dont_share_button);
        mRemember = (CheckBox) findViewById(R.id.remember);

        mShareButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                handleButtonClick(true);
            }
        });
        mDontShareButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                handleButtonClick(false);
            }
        });
    }

    /**
     * Shows the prompt for the given origin. When the user clicks on one of
     * the buttons, the supplied callback is be called.
     */
    public void show(String origin, GeolocationPermissions.Callback callback) {
        mOrigin = origin;
        mCallback = callback;
        Uri uri = Uri.parse(mOrigin);
        setMessage("http".equals(uri.getScheme()) ?  mOrigin.substring(7) : mOrigin);
        // The checkbox should always be intially checked.
        mRemember.setChecked(true);
        setVisibility(View.VISIBLE);
    }

    /**
     * Hides the prompt.
     */
    public void hide() {
        setVisibility(View.GONE);
    }

    /**
     * Handles a click on one the buttons by invoking the callback.
     */
    private void handleButtonClick(boolean allow) {
        hide();

        boolean remember = mRemember.isChecked();
        if (remember) {
            Toast toast = Toast.makeText(
                    getContext(),
                    allow ? R.string.geolocation_permissions_prompt_toast_allowed :
                            R.string.geolocation_permissions_prompt_toast_disallowed,
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
        }

        mCallback.invoke(mOrigin, allow, remember);
    }

    /**
     * Sets the prompt's message.
     */
    private void setMessage(CharSequence origin) {
        mMessage.setText(String.format(
            getResources().getString(R.string.geolocation_permissions_prompt_message),
            origin));
    }
}
