package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.interceptors.GetDecipherCodeDoneEvent;
import com.liskovsoft.smartyoutubetv.interceptors.GetDecipherCodeEvent;
import com.liskovsoft.smartyoutubetv.webviewstuff.ResourceInjectorBase;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class DecipherRoutineInjector extends ResourceInjectorBase {
    private List<YouTubeMediaItem> mMediaItems;
    private String mCombinedSignatures;
    private ArrayList<String> mSignatures;
    private String mDecipherRoutine;
    private String mDecipherCode;

    public DecipherRoutineInjector(Context context, WebView webView) {
        super(context, webView);
        Browser.getBus().register(this);
    }

    @Subscribe
    public void decipherSignature(DecipherSignaturesEvent event) {
        mMediaItems = event.getMediaItems();
        if (isSignatureNotCiphered()) {
            Browser.getBus().post(new DecipherSignaturesDoneEvent(mMediaItems));
            return;
        }

        Browser.getBus().post(new GetDecipherCodeEvent());
    }

    @Subscribe
    public void getDecipherCodeDone(GetDecipherCodeDoneEvent event) {
        mDecipherCode = event.getCode();

        extractSignatures();
        combineSignatures();
        combineDecipherRoutine();
        injectJSContentUnicode(mDecipherRoutine);
    }

    @Subscribe
    public void receiveDecipheredSignatures(PostDecipheredSignaturesEvent event) {
        String[] signatures = event.getSignatures();
        applyNewSignatures(signatures);
        Browser.getBus().post(new DecipherSignaturesDoneEvent(mMediaItems));
    }

    private void applyNewSignatures(String[] signatures) {
        for (int i = 0; i < signatures.length; i++) {
            YouTubeMediaItem item = mMediaItems.get(i);
            String url = item.getUrl();
            item.setUrl(String.format("%s&signature=%s", url, signatures[i]));
            item.setS(null);
        }
    }

    private void combineDecipherRoutine() {
        mDecipherRoutine = mDecipherCode + ";";
        mDecipherRoutine += mCombinedSignatures;
        mDecipherRoutine += "for (var i = 0; i < rawSignatures.length; i++) {rawSignatures[i] = FQ(rawSignatures[i]);}; app.postDecipheredSignatures(rawSignatures);";
    }

    private void extractSignatures() {
        mSignatures = new ArrayList<>();
        for (YouTubeMediaItem item : mMediaItems) {
            mSignatures.add(String.format("\"%s\"", item.getS()));
        }
    }

    private void combineSignatures() {
        mCombinedSignatures = "var rawSignatures = [" + TextUtils.join(",", mSignatures) + "];";
    }

    private boolean isSignatureNotCiphered() {
        return mMediaItems.size() == 0 || mMediaItems.get(0).getS() == null;
    }
}
