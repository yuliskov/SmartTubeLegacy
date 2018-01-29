package com.liskovsoft.browser;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;


public interface ActivityController {
    void start(Intent intent);

    void onSaveInstanceState(Bundle outState);

    void handleNewIntent(Intent intent);

    void onResume();

    boolean onMenuOpened(int featureId, Menu menu);

    void onOptionsMenuClosed(Menu menu);

    void onContextMenuClosed(Menu menu);

    void onPause();

    void onDestroy();

    void onConfgurationChanged(Configuration newConfig);

    void onLowMemory();

    boolean onCreateOptionsMenu(Menu menu);

    boolean onPrepareOptionsMenu(Menu menu);

    boolean onOptionsItemSelected(MenuItem item);

    void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo);

    boolean onContextItemSelected(MenuItem item);

    boolean onKeyDown(int keyCode, KeyEvent event);

    boolean onKeyLongPress(int keyCode, KeyEvent event);

    boolean onKeyUp(int keyCode, KeyEvent event);

    void onActionModeStarted(ActionMode mode);

    void onActionModeFinished(ActionMode mode);

    void onActivityResult(int requestCode, int resultCode, Intent intent);

    boolean onSearchRequested();

    boolean dispatchKeyEvent(KeyEvent event);

    boolean dispatchKeyShortcutEvent(KeyEvent event);

    boolean dispatchTouchEvent(MotionEvent ev);

    boolean dispatchTrackballEvent(MotionEvent ev);

    boolean dispatchGenericMotionEvent(MotionEvent ev);

}
