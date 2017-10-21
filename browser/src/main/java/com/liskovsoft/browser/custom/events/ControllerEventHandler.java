package com.liskovsoft.browser.custom.events;

import android.os.Bundle;

public interface ControllerEventHandler {
    void onControllerStart();
    void beforeSaveInstanceState(Bundle state);
    void beforeRestoreInstanceState(Bundle state);
}
