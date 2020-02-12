package com.liskovsoft.smartyoutubetv.fragments;

import android.content.Intent;
import com.liskovsoft.smartyoutubetv.fragments.GenericFragment;

public interface PlayerFragment extends GenericFragment {
    void openVideo(Intent videoIntent);
    void close();
}
