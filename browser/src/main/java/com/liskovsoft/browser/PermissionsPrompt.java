package com.liskovsoft.browser;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Enumeration;
import java.util.Vector;

public class PermissionsPrompt extends RelativeLayout {
    private TextView mMessage;
    private Button mAllowButton;
    private Button mDenyButton;
    private CheckBox mRemember;
    private PermissionRequest mRequest;

    public PermissionsPrompt(Context context) {
        this(context, null);
    }

    public PermissionsPrompt(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mMessage = (TextView) findViewById(R.id.message);
        mAllowButton = (Button) findViewById(R.id.allow_button);
        mDenyButton = (Button) findViewById(R.id.deny_button);
        mRemember = (CheckBox) findViewById(R.id.remember);

        mAllowButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(true);
            }
        });
        mDenyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(false);
            }
        });
    }

    public void show(PermissionRequest request) {
        mRequest = request;
        setMessage();
        mRemember.setChecked(true);
        setVisibility(View.VISIBLE);
    }

    public void setMessage() {
        String[] resources = mRequest.getResources();
        Vector<String> strings = new Vector<String>();
        for (String resource : resources) {
            if (resource.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                strings.add(getResources().getString(R.string.resource_video_capture));
            else if (resource.equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE))
                strings.add(getResources().getString(R.string.resource_audio_capture));
            else if (resource.equals(PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID))
                strings.add(getResources().getString(R.string.resource_protected_media_id));
        }
        if (strings.isEmpty()) return;

        Enumeration<String> e = strings.elements();
        StringBuilder sb = new StringBuilder(e.nextElement());
        if (e.hasMoreElements()) {
            sb.append(", ");
            sb.append(e.nextElement());
        }

        mMessage.setText(String.format(
                getResources().getString(R.string.permissions_prompt_message),
                mRequest.getOrigin(), sb.toString()));
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
        if (allow)
            mRequest.grant(mRequest.getResources());
        else
            mRequest.deny();
    }
}
