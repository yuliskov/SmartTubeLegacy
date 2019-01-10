package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.SingleDialogSource;
import com.liskovsoft.smartyoutubetv.injectors.WebViewJavaScriptInterface;

import java.util.ArrayList;
import java.util.List;

public class CodecSelectorAddon {
    private static final String MP4 = "mp4";
    private static final String WEBM = "webm";
    private final Context mContext;
    private final WebViewJavaScriptInterface mJavaScriptInterface;
    private final List<DialogItem> mCodecs;

    public CodecSelectorAddon(Context context, WebViewJavaScriptInterface javaScriptInterface) {
        mContext = context;
        mJavaScriptInterface = javaScriptInterface;
        mCodecs = new ArrayList<>();
        mCodecs.add(DialogItem.create("Auto", ""));
        mCodecs.add(DialogItem.create("AVC", MP4));
        mCodecs.add(DialogItem.create("VP9", WEBM));
    }

    private class CodecSelectorDialogSource implements SingleDialogSource {
        @Override
        public List<DialogItem> getItems() {
            return mCodecs;
        }

        @Override
        public Object getSelectedItemTag() {
            // get codec from the settings
            return SmartPreferences.instance(mContext).getPreferredCodec();
        }

        @Override
        public void setSelectedItemTag(Object tag) {
            // update settings
            SmartPreferences.instance(mContext).setPreferredCodec((String) tag);
            // restart app
            MessageHelpers.showMessage(mContext, R.string.restart_app_msg);
        }

        @Override
        public String getTitle() {
            return mContext.getString(R.string.codec_selector_title);
        }
    }

    public void run() {
        GenericSelectorDialog.create(mContext, new CodecSelectorDialogSource());
    }

    public String getPreferredCodec() {
        return SmartPreferences.instance(mContext).getPreferredCodec();
    }
}
