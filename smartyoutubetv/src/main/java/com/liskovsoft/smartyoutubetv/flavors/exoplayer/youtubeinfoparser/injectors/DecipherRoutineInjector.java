package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.DecipherOnlySignaturesDoneEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.DecipherOnlySignaturesEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.GetDecipherCodeDoneEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.GetDecipherCodeEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.PostDecipheredSignaturesEvent;
import com.liskovsoft.smartyoutubetv.injectors.ResourceInjectorBase;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecipherRoutineInjector extends ResourceInjectorBase {
    private static final String TAG = DecipherRoutineInjector.class.getSimpleName();
    private String mCombinedSignatures;
    private ArrayList<String> mSignatures;
    private String mDecipherRoutine;
    private String mDecipherCode;
    private List<String> mRawSignatures;
    private String mEventId;
    private int mId;

    public DecipherRoutineInjector(Context context) {
        this(context, null);
    }

    public DecipherRoutineInjector(Context context, WebView webView) {
        super(context, webView);
        Browser.getBus().register(this);
    }

    @Subscribe
    public void decipherSignature(DecipherOnlySignaturesEvent event) {
        mRawSignatures = event.getSignatures();
        mId = event.getId();
        if (signaturesAreNotCiphered()) {
            Browser.getBus().post(new DecipherOnlySignaturesDoneEvent(mRawSignatures, mId));
            return;
        }

        if (mDecipherCode == null) {
            Browser.getBus().post(new GetDecipherCodeEvent());
        } else {
            startDeciphering();
        }
    }

    @Subscribe
    public void getDecipherCodeDone(GetDecipherCodeDoneEvent event) {
        mDecipherCode = event.getCode();

        startDeciphering();
    }

    private void startDeciphering() {
        if (mDecipherCode == null) {
            Log.e(TAG, "Decipher code not found!");
            return;
        }

        extractSignatures();
        combineSignatures();
        combineId();
        combineDecipherRoutine();
        injectJSContentUnicode(mDecipherRoutine);
    }

    @Subscribe
    public void receiveDecipheredSignatures(PostDecipheredSignaturesEvent event) {
        String[] signatures = event.getSignatures();
        Browser.getBus().post(new DecipherOnlySignaturesDoneEvent(Arrays.asList(signatures), event.getId()));
    }

    private void combineDecipherRoutine() {
        mDecipherRoutine = mDecipherCode + ";";
        mDecipherRoutine += mCombinedSignatures;
        mDecipherRoutine += mEventId;
        mDecipherRoutine += "for (var i = 0; i < rawSignatures.length; i++) {rawSignatures[i] = decipherSignature(rawSignatures[i]);}; app.postDecipheredSignatures(rawSignatures, eventId);";
    }

    private void extractSignatures() {
        mSignatures = new ArrayList<>();
        for (String rawSignature : mRawSignatures) {
            mSignatures.add(String.format("\"%s\"", rawSignature));
        }
    }

    private void combineSignatures() {
        mCombinedSignatures = "var rawSignatures = [" + TextUtils.join(",", mSignatures) + "];";
    }

    private void combineId() {
        mEventId = "var eventId = " + mId + ";";
    }

    private boolean signaturesAreNotCiphered() {
        return mRawSignatures.size() == 0 || mRawSignatures.get(0) == null;
    }
}
