package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.SingleDialogSource;
import com.liskovsoft.sharedutils.dialogs.SingleChoiceSelectorDialog;
import com.liskovsoft.smartyoutubetv.injectors.WebViewJavaScriptInterface;

import java.util.ArrayList;
import java.util.List;

public class CodecSelectorAddon {
    private static final String MP4 = "mp4";
    private static final String WEBM = "webm";
    private final Context mContext;
    private final WebViewJavaScriptInterface mJavaScriptInterface;

    public CodecSelectorAddon(Context context, WebViewJavaScriptInterface javaScriptInterface) {
        mContext = context;
        mJavaScriptInterface = javaScriptInterface;
    }

    private class CodecSelectorDialogItem extends DialogItem {
        private final SmartPreferences mPrefs;
        private final String mTag;

        public CodecSelectorDialogItem(String title, String tag, SmartPreferences prefs) {
            super(title, false);

            mTag = tag;
            mPrefs = prefs;
        }

        @Override
        public boolean getChecked() {
            return mTag.equals(mPrefs.getPreferredCodec());
        }

        @Override
        public void setChecked(boolean checked) {
            mPrefs.setPreferredCodec(mTag);

            // restart app
            MessageHelpers.showMessage(mContext, R.string.restart_app_msg);
        }
    }


    private class CodecSelectorDialogSource implements SingleDialogSource {
        private final SmartPreferences mPrefs;
        private final List<DialogItem> mCodecs;

        public CodecSelectorDialogSource() {
            mPrefs = CommonApplication.getPreferences();
            mCodecs = new ArrayList<>();
            mCodecs.add(new CodecSelectorDialogItem("Auto", "", mPrefs));
            mCodecs.add(new CodecSelectorDialogItem("AVC", MP4, mPrefs));
            mCodecs.add(new CodecSelectorDialogItem("VP9", WEBM, mPrefs));
        }

        @Override
        public List<DialogItem> getItems() {
            return mCodecs;
        }

        @Override
        public String getTitle() {
            return mContext.getString(R.string.codec_selector_title);
        }
    }

    public void run() {
        SingleChoiceSelectorDialog.create(mContext, new CodecSelectorDialogSource(), R.style.AppDialog);
    }

    public String getPreferredCodec() {
        return CommonApplication.getPreferences().getPreferredCodec();
    }
}
