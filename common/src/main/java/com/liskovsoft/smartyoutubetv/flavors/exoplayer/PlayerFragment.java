package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import android.content.Intent;
import com.liskovsoft.browser.fragments.GenericFragment;

public interface PlayerFragment extends GenericFragment {
    void openIntent(Intent intent);
}
