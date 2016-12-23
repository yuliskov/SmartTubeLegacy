package com.liskovsoft.browser;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

public class UrlSelectionActionMode implements ActionMode.Callback {

    private UiController mUiController;

    public UrlSelectionActionMode(UiController controller) {
        mUiController = controller;
    }

    // ActionMode.Callback implementation

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.url_selection, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.share) {
            mUiController.shareCurrentPage();
            mode.finish();
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

}
