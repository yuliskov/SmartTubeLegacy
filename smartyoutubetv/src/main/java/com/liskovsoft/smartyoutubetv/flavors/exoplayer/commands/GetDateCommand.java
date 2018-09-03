package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector.GenericStringResultEventWithId;
import com.squareup.otto.Subscribe;

import java.util.Random;

public class GetDateCommand extends GenericCommand {
    private final Callback mCallback;
    private final int mDateId = new Random().nextInt();
    /**
     * for details see {@link com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector}
     */
    private final String GET_DATE_COMMAND = "document.querySelector('.uploaded-date').innerHTML";
    private GenericStringResultReceiver mReceiver;

    public interface Callback {
        void onResult(String result);
    }

    private class GenericStringResultReceiver {
        public GenericStringResultReceiver() {
            Browser.getBus().register(this);
        }

        @Subscribe
        public void onGenericStringResult(GenericStringResultEventWithId event) {
            String result = event.getResult();

            if (event.getId() == mDateId) {
                Browser.getBus().unregister(this);
                mCallback.onResult(result);
            }
        }
    }

    public GetDateCommand(Callback callback) {
        mCallback = callback;
    }

    @Override
    public boolean call() {
        mReceiver = new GenericStringResultReceiver();
        passToBrowser(GET_DATE_COMMAND, mDateId);
        return true;
    }

    protected void passToBrowser(String hugeFunction, int id) {
        Browser.getBus().post(new GenericEventResourceInjector.JSStringResultEvent(hugeFunction, id));
    }
}
