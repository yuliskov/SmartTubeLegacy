package com.liskovsoft.browser.xwalk;

import android.animation.LayoutTransition;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.ContextMenu;
import android.view.Display;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewDebug.CapturedViewProperty;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.ViewStructure;
import android.view.ViewTreeObserver;
import android.view.WindowId;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import com.liskovsoft.browser.addons.HeadersBrowserWebView;
import org.xwalk.core.XWalkView;

import java.util.ArrayList;
import java.util.Map;

@SuppressLint("NewApi")
public class XWalkWebViewAllMethodsAdapter extends HeadersBrowserWebView {
    protected XWalkView mXWalkView;

    public XWalkWebViewAllMethodsAdapter(Map<String, String> headers, Context context) {
        this(headers, context, null);
    }

    public XWalkWebViewAllMethodsAdapter(Map<String, String> headers, Context context, AttributeSet attrs) {
        this(headers, context, null, 0);
    }

    public XWalkWebViewAllMethodsAdapter(Map<String, String> headers, Context context, AttributeSet attrs, int defStyleAttr) {
        this(headers, context, attrs, defStyleAttr, false);
    }

    public XWalkWebViewAllMethodsAdapter(Map<String, String> headers, Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        super(headers, context, attrs, defStyle, privateBrowsing);

        // cant init here, real init is done below
        //mXWalkView = new XWalkView(context, attrs);
    }

    @Override
    public void setOverScrollMode(int overScrollMode) {
        if (mXWalkView == null) {
            mXWalkView = new XWalkView(getContext());
        }
        mXWalkView.setOverScrollMode(overScrollMode);
    }

    @Override
    public void setForegroundGravity(int foregroundGravity) {
        mXWalkView.setForegroundGravity(foregroundGravity);
    }

    public void setMeasureAllChildren(boolean measureAll) {
        mXWalkView.setMeasureAllChildren(measureAll);
    }

    @Deprecated
    public boolean getConsiderGoneChildrenWhenMeasuring() {
        return mXWalkView.getConsiderGoneChildrenWhenMeasuring();
    }

    public boolean getMeasureAllChildren() {
        return mXWalkView.getMeasureAllChildren();
    }

    @Override
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return mXWalkView.generateLayoutParams(attrs);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return mXWalkView.shouldDelayChildPressedState();
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return mXWalkView.getAccessibilityClassName();
    }

    @Override
    @ExportedProperty(category = "focus", mapping = {@IntToString(from = 131072, to = "FOCUS_BEFORE_DESCENDANTS"), @IntToString(from = 262144, to =
            "FOCUS_AFTER_DESCENDANTS"), @IntToString(from = 393216, to = "FOCUS_BLOCK_DESCENDANTS")})
    public int getDescendantFocusability() {
        return mXWalkView.getDescendantFocusability();
    }

    @Override
    public void setDescendantFocusability(int focusability) {
        mXWalkView.setDescendantFocusability(focusability);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        mXWalkView.requestChildFocus(child, focused);
    }

    @Override
    public void focusableViewAvailable(View v) {
        mXWalkView.focusableViewAvailable(v);
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        return mXWalkView.showContextMenuForChild(originalView);
    }

    @Override
    public boolean showContextMenuForChild(View originalView, float x, float y) {
        return mXWalkView.showContextMenuForChild(originalView, x, y);
    }

    @Override
    public ActionMode startActionModeForChild(View originalView, Callback callback) {
        return mXWalkView.startActionModeForChild(originalView, callback);
    }

    @Override
    public ActionMode startActionModeForChild(View originalView, Callback callback, int type) {
        return mXWalkView.startActionModeForChild(originalView, callback, type);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        return mXWalkView.focusSearch(focused, direction);
    }

    @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
        return mXWalkView.requestChildRectangleOnScreen(child, rectangle, immediate);
    }

    @Override
    public boolean requestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        return mXWalkView.requestSendAccessibilityEvent(child, event);
    }

    @Override
    public boolean onRequestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        return mXWalkView.onRequestSendAccessibilityEvent(child, event);
    }

    @Override
    public void childHasTransientStateChanged(View child, boolean childHasTransientState) {
        mXWalkView.childHasTransientStateChanged(child, childHasTransientState);
    }

    @Override
    public boolean hasTransientState() {
        return mXWalkView.hasTransientState();
    }

    @Override
    public boolean dispatchUnhandledMove(View focused, int direction) {
        return mXWalkView.dispatchUnhandledMove(focused, direction);
    }

    @Override
    public void clearChildFocus(View child) {
        mXWalkView.clearChildFocus(child);
    }

    @Override
    public void clearFocus() {
        mXWalkView.clearFocus();
    }

    @Override
    public View getFocusedChild() {
        return mXWalkView.getFocusedChild();
    }

    @Override
    public boolean hasFocus() {
        return mXWalkView.hasFocus();
    }

    @Override
    public View findFocus() {
        return mXWalkView.findFocus();
    }

    @Override
    public boolean hasFocusable() {
        return mXWalkView.hasFocusable();
    }

    @Override
    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        mXWalkView.addFocusables(views, direction, focusableMode);
    }

    @Override
    public void setTouchscreenBlocksFocus(boolean touchscreenBlocksFocus) {
        mXWalkView.setTouchscreenBlocksFocus(touchscreenBlocksFocus);
    }

    @Override
    public boolean getTouchscreenBlocksFocus() {
        return mXWalkView.getTouchscreenBlocksFocus();
    }

    @Override
    public void findViewsWithText(ArrayList<View> outViews, CharSequence text, int flags) {
        mXWalkView.findViewsWithText(outViews, text, flags);
    }

    @Override
    public void dispatchWindowFocusChanged(boolean hasFocus) {
        mXWalkView.dispatchWindowFocusChanged(hasFocus);
    }

    @Override
    public void addTouchables(ArrayList<View> views) {
        mXWalkView.addTouchables(views);
    }

    @Override
    public void dispatchDisplayHint(int hint) {
        mXWalkView.dispatchDisplayHint(hint);
    }

    @Override
    public void dispatchWindowVisibilityChanged(int visibility) {
        mXWalkView.dispatchWindowVisibilityChanged(visibility);
    }

    @Override
    public void dispatchConfigurationChanged(Configuration newConfig) {
        mXWalkView.dispatchConfigurationChanged(newConfig);
    }

    @Override
    public void recomputeViewAttributes(View child) {
        mXWalkView.recomputeViewAttributes(child);
    }

    @Override
    public void bringChildToFront(View child) {
        mXWalkView.bringChildToFront(child);
    }

    @Override
    public boolean dispatchDragEvent(DragEvent event) {
        return mXWalkView.dispatchDragEvent(event);
    }

    @Override
    public void dispatchWindowSystemUiVisiblityChanged(int visible) {
        mXWalkView.dispatchWindowSystemUiVisiblityChanged(visible);
    }

    @Override
    public void dispatchSystemUiVisibilityChanged(int visible) {
        mXWalkView.dispatchSystemUiVisibilityChanged(visible);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        return mXWalkView.dispatchKeyEventPreIme(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mXWalkView.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return mXWalkView.dispatchKeyShortcutEvent(event);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        return mXWalkView.dispatchTrackballEvent(event);
    }

    @Override
    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        return mXWalkView.onResolvePointerIcon(event, pointerIndex);
    }

    @Override
    public void addChildrenForAccessibility(ArrayList<View> outChildren) {
        mXWalkView.addChildrenForAccessibility(outChildren);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return mXWalkView.onInterceptHoverEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mXWalkView.dispatchTouchEvent(ev);
    }

    @Override
    public void setMotionEventSplittingEnabled(boolean split) {
        mXWalkView.setMotionEventSplittingEnabled(split);
    }

    @Override
    public boolean isMotionEventSplittingEnabled() {
        return mXWalkView.isMotionEventSplittingEnabled();
    }

    @Override
    public boolean isTransitionGroup() {
        return mXWalkView.isTransitionGroup();
    }

    @Override
    public void setTransitionGroup(boolean isTransitionGroup) {
        mXWalkView.setTransitionGroup(isTransitionGroup);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        mXWalkView.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mXWalkView.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        return mXWalkView.requestFocus(direction, previouslyFocusedRect);
    }

    @Override
    public void dispatchProvideStructure(ViewStructure structure) {
        mXWalkView.dispatchProvideStructure(structure);
    }

    @Override
    public void notifySubtreeAccessibilityStateChanged(View child, View source, int changeType) {
        mXWalkView.notifySubtreeAccessibilityStateChanged(child, source, changeType);
    }

    @Override
    public boolean onNestedPrePerformAccessibilityAction(View target, int action, Bundle args) {
        return mXWalkView.onNestedPrePerformAccessibilityAction(target, action, args);
    }


    @Override
    public ViewGroupOverlay getOverlay() {
        return mXWalkView.getOverlay();
    }

    @Override
    @ExportedProperty(category = "drawing")
    public boolean getClipChildren() {
        return mXWalkView.getClipChildren();
    }

    @Override
    public void setClipChildren(boolean clipChildren) {
        mXWalkView.setClipChildren(clipChildren);
    }

    @Override
    public void setClipToPadding(boolean clipToPadding) {
        mXWalkView.setClipToPadding(clipToPadding);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public boolean getClipToPadding() {
        return mXWalkView.getClipToPadding();
    }

    @Override
    public void dispatchSetSelected(boolean selected) {
        mXWalkView.dispatchSetSelected(selected);
    }

    @Override
    public void dispatchSetActivated(boolean activated) {
        mXWalkView.dispatchSetActivated(activated);
    }

    @Override
    public void dispatchDrawableHotspotChanged(float x, float y) {
        mXWalkView.dispatchDrawableHotspotChanged(x, y);
    }

    @Override
    public void addView(View child) {
        mXWalkView.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        mXWalkView.addView(child, index);
    }

    @Override
    public void addView(View child, int width, int height) {
        mXWalkView.addView(child, width, height);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        mXWalkView.addView(child, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        mXWalkView.addView(child, index, params);
    }

    @Override
    public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
        mXWalkView.updateViewLayout(view, params);
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        mXWalkView.setOnHierarchyChangeListener(listener);
    }

    @Override
    public void onViewAdded(View child) {
        mXWalkView.onViewAdded(child);
    }

    @Override
    public void onViewRemoved(View child) {
        mXWalkView.onViewRemoved(child);
    }

    @Override
    public void removeView(View view) {
        mXWalkView.removeView(view);
    }

    @Override
    public void removeViewInLayout(View view) {
        mXWalkView.removeViewInLayout(view);
    }

    @Override
    public void removeViewsInLayout(int start, int count) {
        mXWalkView.removeViewsInLayout(start, count);
    }

    @Override
    public void removeViewAt(int index) {
        mXWalkView.removeViewAt(index);
    }

    @Override
    public void removeViews(int start, int count) {
        mXWalkView.removeViews(start, count);
    }

    @Override
    public void setLayoutTransition(LayoutTransition transition) {
        mXWalkView.setLayoutTransition(transition);
    }

    @Override
    public LayoutTransition getLayoutTransition() {
        return mXWalkView.getLayoutTransition();
    }

    @Override
    public void removeAllViews() {
        mXWalkView.removeAllViews();
    }

    @Override
    public void removeAllViewsInLayout() {
        mXWalkView.removeAllViewsInLayout();
    }

    @Override
    public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
        return mXWalkView.invalidateChildInParent(location, dirty);
    }

    @Override
    public boolean getChildVisibleRect(View child, Rect r, Point offset) {
        return mXWalkView.getChildVisibleRect(child, r, offset);
    }

    @Override
    public void startLayoutAnimation() {
        mXWalkView.startLayoutAnimation();
    }

    @Override
    public void scheduleLayoutAnimation() {
        mXWalkView.scheduleLayoutAnimation();
    }

    @Override
    public void setLayoutAnimation(LayoutAnimationController controller) {
        mXWalkView.setLayoutAnimation(controller);
    }

    @Override
    public LayoutAnimationController getLayoutAnimation() {
        return mXWalkView.getLayoutAnimation();
    }

    @Override
    @Deprecated
    public boolean isAnimationCacheEnabled() {
        return mXWalkView.isAnimationCacheEnabled();
    }

    @Override
    @Deprecated
    public void setAnimationCacheEnabled(boolean enabled) {
        mXWalkView.setAnimationCacheEnabled(enabled);
    }

    @Override
    @Deprecated
    public boolean isAlwaysDrawnWithCacheEnabled() {
        return mXWalkView.isAlwaysDrawnWithCacheEnabled();
    }

    @Override
    @Deprecated
    public void setAlwaysDrawnWithCacheEnabled(boolean always) {
        mXWalkView.setAlwaysDrawnWithCacheEnabled(always);
    }

    @Override
    @ExportedProperty(category = "drawing", mapping = {@IntToString(from = 0, to = "NONE"), @IntToString(from = 1, to = "ANIMATION"), @IntToString
            (from = 2, to = "SCROLLING"), @IntToString(from = 3, to = "ALL")})
    public int getPersistentDrawingCache() {
        return mXWalkView.getPersistentDrawingCache();
    }

    @Override
    public void setPersistentDrawingCache(int drawingCacheToKeep) {
        mXWalkView.setPersistentDrawingCache(drawingCacheToKeep);
    }

    @Override
    public int getLayoutMode() {
        return mXWalkView.getLayoutMode();
    }

    @Override
    public void setLayoutMode(int layoutMode) {
        mXWalkView.setLayoutMode(layoutMode);
    }

    @Override
    public int indexOfChild(View child) {
        return mXWalkView.indexOfChild(child);
    }

    @Override
    public int getChildCount() {
        return mXWalkView.getChildCount();
    }

    @Override
    public View getChildAt(int index) {
        return mXWalkView.getChildAt(index);
    }

    public static int getChildMeasureSpec(int spec, int padding, int childDimension) {
        return ViewGroup.getChildMeasureSpec(spec, padding, childDimension);
    }

    @Override
    public void clearDisappearingChildren() {
        mXWalkView.clearDisappearingChildren();
    }

    @Override
    public void startViewTransition(View view) {
        mXWalkView.startViewTransition(view);
    }

    @Override
    public void endViewTransition(View view) {
        mXWalkView.endViewTransition(view);
    }

    @Override
    public boolean gatherTransparentRegion(Region region) {
        return mXWalkView.gatherTransparentRegion(region);
    }

    @Override
    public void requestTransparentRegion(View child) {
        mXWalkView.requestTransparentRegion(child);
    }

    @Override
    public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
        return mXWalkView.dispatchApplyWindowInsets(insets);
    }

    @Override
    public AnimationListener getLayoutAnimationListener() {
        return mXWalkView.getLayoutAnimationListener();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void jumpDrawablesToCurrentState() {
        mXWalkView.jumpDrawablesToCurrentState();
    }

    @Override
    public void setAddStatesFromChildren(boolean addsStates) {
        mXWalkView.setAddStatesFromChildren(addsStates);
    }

    @Override
    public boolean addStatesFromChildren() {
        return mXWalkView.addStatesFromChildren();
    }

    @Override
    public void childDrawableStateChanged(View child) {
        mXWalkView.childDrawableStateChanged(child);
    }

    @Override
    public void setLayoutAnimationListener(AnimationListener animationListener) {
        mXWalkView.setLayoutAnimationListener(animationListener);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return mXWalkView.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mXWalkView.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        mXWalkView.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        mXWalkView.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        mXWalkView.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return mXWalkView.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return mXWalkView.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes() {
        return mXWalkView.getNestedScrollAxes();
    }

    @Override
    public String toString() {
        return mXWalkView.toString();
    }

    @Override
    public int getVerticalFadingEdgeLength() {
        return mXWalkView.getVerticalFadingEdgeLength();
    }

    @Override
    public void setFadingEdgeLength(int length) {
        mXWalkView.setFadingEdgeLength(length);
    }

    @Override
    public int getHorizontalFadingEdgeLength() {
        return mXWalkView.getHorizontalFadingEdgeLength();
    }

    @Override
    public int getVerticalScrollbarWidth() {
        return mXWalkView.getVerticalScrollbarWidth();
    }

    @Override
    public void setVerticalScrollbarPosition(int position) {
        mXWalkView.setVerticalScrollbarPosition(position);
    }

    @Override
    public int getVerticalScrollbarPosition() {
        return mXWalkView.getVerticalScrollbarPosition();
    }

    @Override
    public void setScrollIndicators(int indicators) {
        mXWalkView.setScrollIndicators(indicators);
    }

    @Override
    public void setScrollIndicators(int indicators, int mask) {
        mXWalkView.setScrollIndicators(indicators, mask);
    }

    @Override
    public int getScrollIndicators() {
        return mXWalkView.getScrollIndicators();
    }

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        mXWalkView.setOnScrollChangeListener(l);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        mXWalkView.setOnFocusChangeListener(l);
    }

    @Override
    public void addOnLayoutChangeListener(OnLayoutChangeListener listener) {
        mXWalkView.addOnLayoutChangeListener(listener);
    }

    @Override
    public void removeOnLayoutChangeListener(OnLayoutChangeListener listener) {
        mXWalkView.removeOnLayoutChangeListener(listener);
    }

    @Override
    public void addOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        mXWalkView.addOnAttachStateChangeListener(listener);
    }

    @Override
    public void removeOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        mXWalkView.removeOnAttachStateChangeListener(listener);
    }

    @Override
    public OnFocusChangeListener getOnFocusChangeListener() {
        return mXWalkView.getOnFocusChangeListener();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mXWalkView.setOnClickListener(l);
    }

    @Override
    public boolean hasOnClickListeners() {
        return mXWalkView.hasOnClickListeners();
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        mXWalkView.setOnLongClickListener(l);
    }

    @Override
    public void setOnContextClickListener(OnContextClickListener l) {
        mXWalkView.setOnContextClickListener(l);
    }

    @Override
    public void setOnCreateContextMenuListener(OnCreateContextMenuListener l) {
        mXWalkView.setOnCreateContextMenuListener(l);
    }

    @Override
    public boolean performClick() {
        return mXWalkView.performClick();
    }

    @Override
    public boolean callOnClick() {
        return mXWalkView.callOnClick();
    }

    @Override
    public boolean performLongClick() {
        return mXWalkView.performLongClick();
    }

    @Override
    public boolean performLongClick(float x, float y) {
        return mXWalkView.performLongClick(x, y);
    }

    @Override
    public boolean performContextClick(float x, float y) {
        return mXWalkView.performContextClick(x, y);
    }

    @Override
    public boolean performContextClick() {
        return mXWalkView.performContextClick();
    }

    @Override
    public boolean showContextMenu() {
        return mXWalkView.showContextMenu();
    }

    @Override
    public boolean showContextMenu(float x, float y) {
        return mXWalkView.showContextMenu(x, y);
    }

    @Override
    public ActionMode startActionMode(Callback callback) {
        return mXWalkView.startActionMode(callback);
    }

    @Override
    public ActionMode startActionMode(Callback callback, int type) {
        return mXWalkView.startActionMode(callback, type);
    }

    @Override
    public void setOnKeyListener(OnKeyListener l) {
        mXWalkView.setOnKeyListener(l);
    }

    @Override
    public void setOnGenericMotionListener(OnGenericMotionListener l) {
        mXWalkView.setOnGenericMotionListener(l);
    }

    @Override
    public void setOnHoverListener(OnHoverListener l) {
        mXWalkView.setOnHoverListener(l);
    }

    @Override
    public void setOnDragListener(OnDragListener l) {
        mXWalkView.setOnDragListener(l);
    }

    @Override
    public boolean requestRectangleOnScreen(Rect rectangle) {
        return mXWalkView.requestRectangleOnScreen(rectangle);
    }

    @Override
    public boolean requestRectangleOnScreen(Rect rectangle, boolean immediate) {
        return mXWalkView.requestRectangleOnScreen(rectangle, immediate);
    }

    @Override
    public void sendAccessibilityEvent(int eventType) {
        mXWalkView.sendAccessibilityEvent(eventType);
    }

    @Override
    public void announceForAccessibility(CharSequence text) {
        mXWalkView.announceForAccessibility(text);
    }

    @Override
    public void sendAccessibilityEventUnchecked(AccessibilityEvent event) {
        mXWalkView.sendAccessibilityEventUnchecked(event);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return mXWalkView.dispatchPopulateAccessibilityEvent(event);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        mXWalkView.onPopulateAccessibilityEvent(event);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        mXWalkView.onInitializeAccessibilityEvent(event);
    }

    @Override
    public AccessibilityNodeInfo createAccessibilityNodeInfo() {
        return mXWalkView.createAccessibilityNodeInfo();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        mXWalkView.onInitializeAccessibilityNodeInfo(info);
    }

    @Override
    public void onProvideStructure(ViewStructure structure) {
        mXWalkView.onProvideStructure(structure);
    }

    @Override
    public void onProvideVirtualStructure(ViewStructure structure) {
        mXWalkView.onProvideVirtualStructure(structure);
    }

    @Override
    public void setAccessibilityDelegate(AccessibilityDelegate delegate) {
        mXWalkView.setAccessibilityDelegate(delegate);
    }

    @Override
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        return mXWalkView.getAccessibilityNodeProvider();
    }

    @Override
    @ExportedProperty(category = "accessibility")
    public CharSequence getContentDescription() {
        return mXWalkView.getContentDescription();
    }

    @Override
    public void setContentDescription(CharSequence contentDescription) {
        mXWalkView.setContentDescription(contentDescription);
    }

    @Override
    public void setAccessibilityTraversalBefore(int beforeId) {
        mXWalkView.setAccessibilityTraversalBefore(beforeId);
    }

    @Override
    public int getAccessibilityTraversalBefore() {
        return mXWalkView.getAccessibilityTraversalBefore();
    }

    @Override
    public void setAccessibilityTraversalAfter(int afterId) {
        mXWalkView.setAccessibilityTraversalAfter(afterId);
    }

    @Override
    public int getAccessibilityTraversalAfter() {
        return mXWalkView.getAccessibilityTraversalAfter();
    }

    @Override
    @ExportedProperty(category = "accessibility")
    public int getLabelFor() {
        return mXWalkView.getLabelFor();
    }

    @Override
    public void setLabelFor(int id) {
        mXWalkView.setLabelFor(id);
    }

    @Override
    @ExportedProperty(category = "focus")
    public boolean isFocused() {
        return mXWalkView.isFocused();
    }

    @Override
    public boolean isScrollContainer() {
        return mXWalkView.isScrollContainer();
    }

    @Override
    public void setScrollContainer(boolean isScrollContainer) {
        mXWalkView.setScrollContainer(isScrollContainer);
    }

    @Override
    public int getDrawingCacheQuality() {
        return mXWalkView.getDrawingCacheQuality();
    }

    @Override
    public void setDrawingCacheQuality(int quality) {
        mXWalkView.setDrawingCacheQuality(quality);
    }

    @Override
    public boolean getKeepScreenOn() {
        return mXWalkView.getKeepScreenOn();
    }

    @Override
    public void setKeepScreenOn(boolean keepScreenOn) {
        mXWalkView.setKeepScreenOn(keepScreenOn);
    }

    @Override
    public int getNextFocusLeftId() {
        return mXWalkView.getNextFocusLeftId();
    }

    @Override
    public void setNextFocusLeftId(int nextFocusLeftId) {
        mXWalkView.setNextFocusLeftId(nextFocusLeftId);
    }

    @Override
    public int getNextFocusRightId() {
        return mXWalkView.getNextFocusRightId();
    }

    @Override
    public void setNextFocusRightId(int nextFocusRightId) {
        mXWalkView.setNextFocusRightId(nextFocusRightId);
    }

    @Override
    public int getNextFocusUpId() {
        return mXWalkView.getNextFocusUpId();
    }

    @Override
    public void setNextFocusUpId(int nextFocusUpId) {
        mXWalkView.setNextFocusUpId(nextFocusUpId);
    }

    @Override
    public int getNextFocusDownId() {
        return mXWalkView.getNextFocusDownId();
    }

    @Override
    public void setNextFocusDownId(int nextFocusDownId) {
        mXWalkView.setNextFocusDownId(nextFocusDownId);
    }

    @Override
    public int getNextFocusForwardId() {
        return mXWalkView.getNextFocusForwardId();
    }

    @Override
    public void setNextFocusForwardId(int nextFocusForwardId) {
        mXWalkView.setNextFocusForwardId(nextFocusForwardId);
    }

    @Override
    public boolean isShown() {
        return mXWalkView.isShown();
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return mXWalkView.onApplyWindowInsets(insets);
    }

    @Override
    public void setOnApplyWindowInsetsListener(OnApplyWindowInsetsListener listener) {
        mXWalkView.setOnApplyWindowInsetsListener(listener);
    }

    @Override
    public WindowInsets getRootWindowInsets() {
        return mXWalkView.getRootWindowInsets();
    }

    @Override
    public WindowInsets computeSystemWindowInsets(WindowInsets in, Rect outLocalInsets) {
        return mXWalkView.computeSystemWindowInsets(in, outLocalInsets);
    }

    @Override
    public void setFitsSystemWindows(boolean fitSystemWindows) {
        mXWalkView.setFitsSystemWindows(fitSystemWindows);
    }

    @Override
    @ExportedProperty
    public boolean getFitsSystemWindows() {
        return mXWalkView.getFitsSystemWindows();
    }

    @Override
    @Deprecated
    public void requestFitSystemWindows() {
        mXWalkView.requestFitSystemWindows();
    }

    @Override
    public void requestApplyInsets() {
        mXWalkView.requestApplyInsets();
    }

    @Override
    @ExportedProperty(mapping = {@IntToString(from = 0, to = "VISIBLE"), @IntToString(from = 4, to = "INVISIBLE"), @IntToString(from = 8, to =
            "GONE")})
    public int getVisibility() {
        return mXWalkView.getVisibility();
    }

    @Override
    @ExportedProperty
    public boolean isEnabled() {
        return mXWalkView.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        mXWalkView.setEnabled(enabled);
    }

    @Override
    public void setFocusable(boolean focusable) {
        mXWalkView.setFocusable(focusable);
    }

    @Override
    public void setFocusableInTouchMode(boolean focusableInTouchMode) {
        mXWalkView.setFocusableInTouchMode(focusableInTouchMode);
    }

    @Override
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        mXWalkView.setSoundEffectsEnabled(soundEffectsEnabled);
    }

    @Override
    @ExportedProperty
    public boolean isSoundEffectsEnabled() {
        return mXWalkView.isSoundEffectsEnabled();
    }

    @Override
    public void setHapticFeedbackEnabled(boolean hapticFeedbackEnabled) {
        mXWalkView.setHapticFeedbackEnabled(hapticFeedbackEnabled);
    }

    @Override
    @ExportedProperty
    public boolean isHapticFeedbackEnabled() {
        return mXWalkView.isHapticFeedbackEnabled();
    }

    @Override
    public void setLayoutDirection(int layoutDirection) {
        mXWalkView.setLayoutDirection(layoutDirection);
    }

    @Override
    @ExportedProperty(category = "layout", mapping = {@IntToString(from = 0, to = "RESOLVED_DIRECTION_LTR"), @IntToString(from = 1, to =
            "RESOLVED_DIRECTION_RTL")})
    public int getLayoutDirection() {
        return mXWalkView.getLayoutDirection();
    }

    @Override
    public void setHasTransientState(boolean hasTransientState) {
        mXWalkView.setHasTransientState(hasTransientState);
    }

    @Override
    public boolean isAttachedToWindow() {
        return mXWalkView.isAttachedToWindow();
    }

    @Override
    public boolean isLaidOut() {
        return mXWalkView.isLaidOut();
    }

    @Override
    public void setWillNotDraw(boolean willNotDraw) {
        mXWalkView.setWillNotDraw(willNotDraw);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public boolean willNotDraw() {
        return mXWalkView.willNotDraw();
    }

    @Override
    public void setWillNotCacheDrawing(boolean willNotCacheDrawing) {
        mXWalkView.setWillNotCacheDrawing(willNotCacheDrawing);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public boolean willNotCacheDrawing() {
        return mXWalkView.willNotCacheDrawing();
    }

    @Override
    @ExportedProperty
    public boolean isClickable() {
        return mXWalkView.isClickable();
    }

    @Override
    public void setClickable(boolean clickable) {
        mXWalkView.setClickable(clickable);
    }

    @Override
    public boolean isLongClickable() {
        return mXWalkView.isLongClickable();
    }

    @Override
    public void setLongClickable(boolean longClickable) {
        mXWalkView.setLongClickable(longClickable);
    }

    @Override
    public boolean isContextClickable() {
        return mXWalkView.isContextClickable();
    }

    @Override
    public void setContextClickable(boolean contextClickable) {
        mXWalkView.setContextClickable(contextClickable);
    }

    @Override
    public void setPressed(boolean pressed) {
        mXWalkView.setPressed(pressed);
    }

    @Override
    @ExportedProperty
    public boolean isPressed() {
        return mXWalkView.isPressed();
    }

    @Override
    public boolean isSaveEnabled() {
        return mXWalkView.isSaveEnabled();
    }

    @Override
    public void setSaveEnabled(boolean enabled) {
        mXWalkView.setSaveEnabled(enabled);
    }

    @Override
    @ExportedProperty
    public boolean getFilterTouchesWhenObscured() {
        return mXWalkView.getFilterTouchesWhenObscured();
    }

    @Override
    public void setFilterTouchesWhenObscured(boolean enabled) {
        mXWalkView.setFilterTouchesWhenObscured(enabled);
    }

    @Override
    public boolean isSaveFromParentEnabled() {
        return mXWalkView.isSaveFromParentEnabled();
    }

    @Override
    public void setSaveFromParentEnabled(boolean enabled) {
        mXWalkView.setSaveFromParentEnabled(enabled);
    }

    @Override
    public View focusSearch(int direction) {
        return mXWalkView.focusSearch(direction);
    }

    @Override
    public ArrayList<View> getFocusables(int direction) {
        return mXWalkView.getFocusables(direction);
    }

    @Override
    public void addFocusables(ArrayList<View> views, int direction) {
        mXWalkView.addFocusables(views, direction);
    }

    @Override
    public ArrayList<View> getTouchables() {
        return mXWalkView.getTouchables();
    }

    @Override
    public boolean isAccessibilityFocused() {
        return mXWalkView.isAccessibilityFocused();
    }

    @Override
    @ExportedProperty(category = "accessibility", mapping = {@IntToString(from = 0, to = "auto"), @IntToString(from = 1, to = "yes"), @IntToString
            (from = 2, to = "no"), @IntToString(from = 4, to = "noHideDescendants")})
    public int getImportantForAccessibility() {
        return mXWalkView.getImportantForAccessibility();
    }

    @Override
    public void setAccessibilityLiveRegion(int mode) {
        mXWalkView.setAccessibilityLiveRegion(mode);
    }

    @Override
    public int getAccessibilityLiveRegion() {
        return mXWalkView.getAccessibilityLiveRegion();
    }

    @Override
    public void setImportantForAccessibility(int mode) {
        mXWalkView.setImportantForAccessibility(mode);
    }

    @Override
    public boolean isImportantForAccessibility() {
        return mXWalkView.isImportantForAccessibility();
    }

    @Override
    public ViewParent getParentForAccessibility() {
        return mXWalkView.getParentForAccessibility();
    }

    @Override
    public boolean dispatchNestedPrePerformAccessibilityAction(int action, Bundle arguments) {
        return mXWalkView.dispatchNestedPrePerformAccessibilityAction(action, arguments);
    }

    @Override
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        return mXWalkView.performAccessibilityAction(action, arguments);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void dispatchStartTemporaryDetach() {
        mXWalkView.dispatchStartTemporaryDetach();
    }

    @Override
    public void onStartTemporaryDetach() {
        mXWalkView.onStartTemporaryDetach();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void dispatchFinishTemporaryDetach() {
        mXWalkView.dispatchFinishTemporaryDetach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        mXWalkView.onFinishTemporaryDetach();
    }

    @Override
    public DispatcherState getKeyDispatcherState() {
        return mXWalkView.getKeyDispatcherState();
    }

    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        return mXWalkView.onFilterTouchEventForSecurity(event);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return mXWalkView.dispatchGenericMotionEvent(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        mXWalkView.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    public boolean hasWindowFocus() {
        return mXWalkView.hasWindowFocus();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        mXWalkView.onVisibilityAggregated(isVisible);
    }

    @Override
    public int getWindowVisibility() {
        return mXWalkView.getWindowVisibility();
    }

    @Override
    public void getWindowVisibleDisplayFrame(Rect outRect) {
        mXWalkView.getWindowVisibleDisplayFrame(outRect);
    }

    @Override
    @ExportedProperty
    public boolean isInTouchMode() {
        return mXWalkView.isInTouchMode();
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        return mXWalkView.onKeyPreIme(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mXWalkView.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return mXWalkView.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mXWalkView.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return mXWalkView.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        return mXWalkView.onKeyShortcut(keyCode, event);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return mXWalkView.onCheckIsTextEditor();
    }

    @Override
    public boolean checkInputConnectionProxy(View view) {
        return mXWalkView.checkInputConnectionProxy(view);
    }

    @Override
    public void createContextMenu(ContextMenu menu) {
        mXWalkView.createContextMenu(menu);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        return mXWalkView.onTrackballEvent(event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mXWalkView.onGenericMotionEvent(event);
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        return mXWalkView.onHoverEvent(event);
    }

    @Override
    @ExportedProperty
    public boolean isHovered() {
        return mXWalkView.isHovered();
    }

    @Override
    public void setHovered(boolean hovered) {
        mXWalkView.setHovered(hovered);
    }

    @Override
    public void onHoverChanged(boolean hovered) {
        mXWalkView.onHoverChanged(hovered);
    }

    @Override
    public void cancelLongPress() {
        mXWalkView.cancelLongPress();
    }

    @Override
    public void setTouchDelegate(TouchDelegate delegate) {
        mXWalkView.setTouchDelegate(delegate);
    }

    @Override
    public TouchDelegate getTouchDelegate() {
        return mXWalkView.getTouchDelegate();
    }

    @Override
    public void bringToFront() {
        mXWalkView.bringToFront();
    }

    @Override
    public void setScrollX(int value) {
        mXWalkView.setScrollX(value);
    }

    @Override
    public void setScrollY(int value) {
        mXWalkView.setScrollY(value);
    }

    @Override
    public void getDrawingRect(Rect outRect) {
        mXWalkView.getDrawingRect(outRect);
    }

    @Override
    public Matrix getMatrix() {
        return mXWalkView.getMatrix();
    }

    @Override
    public float getCameraDistance() {
        return mXWalkView.getCameraDistance();
    }

    @Override
    public void setCameraDistance(float distance) {
        mXWalkView.setCameraDistance(distance);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getRotation() {
        return mXWalkView.getRotation();
    }

    @Override
    public void setRotation(float rotation) {
        mXWalkView.setRotation(rotation);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getRotationY() {
        return mXWalkView.getRotationY();
    }

    @Override
    public void setRotationY(float rotationY) {
        mXWalkView.setRotationY(rotationY);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getRotationX() {
        return mXWalkView.getRotationX();
    }

    @Override
    public void setRotationX(float rotationX) {
        mXWalkView.setRotationX(rotationX);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getScaleX() {
        return mXWalkView.getScaleX();
    }

    @Override
    public void setScaleX(float scaleX) {
        mXWalkView.setScaleX(scaleX);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getScaleY() {
        return mXWalkView.getScaleY();
    }

    @Override
    public void setScaleY(float scaleY) {
        mXWalkView.setScaleY(scaleY);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getPivotX() {
        return mXWalkView.getPivotX();
    }

    @Override
    public void setPivotX(float pivotX) {
        mXWalkView.setPivotX(pivotX);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getPivotY() {
        return mXWalkView.getPivotY();
    }

    @Override
    public void setPivotY(float pivotY) {
        mXWalkView.setPivotY(pivotY);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getAlpha() {
        return mXWalkView.getAlpha();
    }

    @Override
    public void forceHasOverlappingRendering(boolean hasOverlappingRendering) {
        mXWalkView.forceHasOverlappingRendering(hasOverlappingRendering);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public boolean hasOverlappingRendering() {
        return mXWalkView.hasOverlappingRendering();
    }

    @Override
    public void setAlpha(float alpha) {
        mXWalkView.setAlpha(alpha);
    }

    @Override
    public boolean isDirty() {
        return mXWalkView.isDirty();
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getX() {
        return mXWalkView.getX();
    }

    @Override
    public void setX(float x) {
        mXWalkView.setX(x);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getY() {
        return mXWalkView.getY();
    }

    @Override
    public void setY(float y) {
        mXWalkView.setY(y);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getZ() {
        return mXWalkView.getZ();
    }

    @Override
    public void setZ(float z) {
        mXWalkView.setZ(z);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getElevation() {
        return mXWalkView.getElevation();
    }

    @Override
    public void setElevation(float elevation) {
        mXWalkView.setElevation(elevation);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getTranslationX() {
        return mXWalkView.getTranslationX();
    }

    @Override
    public void setTranslationX(float translationX) {
        mXWalkView.setTranslationX(translationX);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getTranslationY() {
        return mXWalkView.getTranslationY();
    }

    @Override
    public void setTranslationY(float translationY) {
        mXWalkView.setTranslationY(translationY);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public float getTranslationZ() {
        return mXWalkView.getTranslationZ();
    }

    @Override
    public void setTranslationZ(float translationZ) {
        mXWalkView.setTranslationZ(translationZ);
    }

    @Override
    public StateListAnimator getStateListAnimator() {
        return mXWalkView.getStateListAnimator();
    }

    @Override
    public void setStateListAnimator(StateListAnimator stateListAnimator) {
        mXWalkView.setStateListAnimator(stateListAnimator);
    }

    @Override
    public void setClipToOutline(boolean clipToOutline) {
        mXWalkView.setClipToOutline(clipToOutline);
    }

    @Override
    public void setOutlineProvider(ViewOutlineProvider provider) {
        mXWalkView.setOutlineProvider(provider);
    }

    @Override
    public ViewOutlineProvider getOutlineProvider() {
        return mXWalkView.getOutlineProvider();
    }

    @Override
    public void invalidateOutline() {
        mXWalkView.invalidateOutline();
    }

    @Override
    public void getHitRect(Rect outRect) {
        mXWalkView.getHitRect(outRect);
    }

    @Override
    public void getFocusedRect(Rect r) {
        mXWalkView.getFocusedRect(r);
    }

    @Override
    public boolean getGlobalVisibleRect(Rect r, Point globalOffset) {
        return mXWalkView.getGlobalVisibleRect(r, globalOffset);
    }

    @Override
    public void offsetTopAndBottom(int offset) {
        mXWalkView.offsetTopAndBottom(offset);
    }

    @Override
    public void offsetLeftAndRight(int offset) {
        mXWalkView.offsetLeftAndRight(offset);
    }

    @Override
    @ExportedProperty(deepExport = true, prefix = "layout_")
    public ViewGroup.LayoutParams getLayoutParams() {
        return mXWalkView.getLayoutParams();
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        mXWalkView.setLayoutParams(params);
    }

    @Override
    public void invalidate(Rect dirty) {
        mXWalkView.invalidate(dirty);
    }

    @Override
    public void invalidate(int l, int t, int r, int b) {
        mXWalkView.invalidate(l, t, r, b);
    }

    @Override
    public void invalidate() {
        mXWalkView.invalidate();
    }

    @Override
    @ExportedProperty(category = "drawing")
    public boolean isOpaque() {
        return mXWalkView.isOpaque();
    }

    @Override
    public Handler getHandler() {
        return mXWalkView.getHandler();
    }

    @Override
    public boolean post(Runnable action) {
        return mXWalkView.post(action);
    }

    @Override
    public boolean postDelayed(Runnable action, long delayMillis) {
        return mXWalkView.postDelayed(action, delayMillis);
    }

    @Override
    public void postOnAnimation(Runnable action) {
        mXWalkView.postOnAnimation(action);
    }

    @Override
    public void postOnAnimationDelayed(Runnable action, long delayMillis) {
        mXWalkView.postOnAnimationDelayed(action, delayMillis);
    }

    @Override
    public boolean removeCallbacks(Runnable action) {
        return mXWalkView.removeCallbacks(action);
    }

    @Override
    public void postInvalidate() {
        mXWalkView.postInvalidate();
    }

    @Override
    public void postInvalidate(int left, int top, int right, int bottom) {
        mXWalkView.postInvalidate(left, top, right, bottom);
    }

    @Override
    public void postInvalidateDelayed(long delayMilliseconds) {
        mXWalkView.postInvalidateDelayed(delayMilliseconds);
    }

    @Override
    public void postInvalidateDelayed(long delayMilliseconds, int left, int top, int right, int bottom) {
        mXWalkView.postInvalidateDelayed(delayMilliseconds, left, top, right, bottom);
    }

    @Override
    public void postInvalidateOnAnimation() {
        mXWalkView.postInvalidateOnAnimation();
    }

    @Override
    public void postInvalidateOnAnimation(int left, int top, int right, int bottom) {
        mXWalkView.postInvalidateOnAnimation(left, top, right, bottom);
    }

    @Override
    public void computeScroll() {
        mXWalkView.computeScroll();
    }

    @Override
    public boolean isHorizontalFadingEdgeEnabled() {
        return mXWalkView.isHorizontalFadingEdgeEnabled();
    }

    @Override
    public void setHorizontalFadingEdgeEnabled(boolean horizontalFadingEdgeEnabled) {
        mXWalkView.setHorizontalFadingEdgeEnabled(horizontalFadingEdgeEnabled);
    }

    @Override
    public boolean isVerticalFadingEdgeEnabled() {
        return mXWalkView.isVerticalFadingEdgeEnabled();
    }

    @Override
    public void setVerticalFadingEdgeEnabled(boolean verticalFadingEdgeEnabled) {
        mXWalkView.setVerticalFadingEdgeEnabled(verticalFadingEdgeEnabled);
    }

    @Override
    public boolean isHorizontalScrollBarEnabled() {
        return mXWalkView.isHorizontalScrollBarEnabled();
    }

    @Override
    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        mXWalkView.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
    }

    @Override
    public boolean isVerticalScrollBarEnabled() {
        return mXWalkView.isVerticalScrollBarEnabled();
    }

    @Override
    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        mXWalkView.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
    }

    @Override
    public void setScrollbarFadingEnabled(boolean fadeScrollbars) {
        mXWalkView.setScrollbarFadingEnabled(fadeScrollbars);
    }

    @Override
    public boolean isScrollbarFadingEnabled() {
        return mXWalkView.isScrollbarFadingEnabled();
    }

    @Override
    public int getScrollBarDefaultDelayBeforeFade() {
        return mXWalkView.getScrollBarDefaultDelayBeforeFade();
    }

    @Override
    public void setScrollBarDefaultDelayBeforeFade(int scrollBarDefaultDelayBeforeFade) {
        mXWalkView.setScrollBarDefaultDelayBeforeFade(scrollBarDefaultDelayBeforeFade);
    }

    @Override
    public int getScrollBarFadeDuration() {
        return mXWalkView.getScrollBarFadeDuration();
    }

    @Override
    public void setScrollBarFadeDuration(int scrollBarFadeDuration) {
        mXWalkView.setScrollBarFadeDuration(scrollBarFadeDuration);
    }

    @Override
    public int getScrollBarSize() {
        return mXWalkView.getScrollBarSize();
    }

    @Override
    public void setScrollBarSize(int scrollBarSize) {
        mXWalkView.setScrollBarSize(scrollBarSize);
    }

    @Override
    public void setScrollBarStyle(int style) {
        mXWalkView.setScrollBarStyle(style);
    }

    @Override
    @ExportedProperty(mapping = {@IntToString(from = 0, to = "INSIDE_OVERLAY"), @IntToString(from = 16777216, to = "INSIDE_INSET"), @IntToString(from = 33554432, to = "OUTSIDE_OVERLAY"), @IntToString(from = 50331648, to = "OUTSIDE_INSET")})
    public int getScrollBarStyle() {
        return mXWalkView.getScrollBarStyle();
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return mXWalkView.canScrollHorizontally(direction);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return mXWalkView.canScrollVertically(direction);
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        mXWalkView.onScreenStateChanged(screenState);
    }

    @Override
    public void onRtlPropertiesChanged(int layoutDirection) {
        mXWalkView.onRtlPropertiesChanged(layoutDirection);
    }

    @Override
    public boolean canResolveLayoutDirection() {
        return mXWalkView.canResolveLayoutDirection();
    }

    @Override
    public boolean isLayoutDirectionResolved() {
        return mXWalkView.isLayoutDirectionResolved();
    }

    @Override
    public IBinder getWindowToken() {
        return mXWalkView.getWindowToken();
    }

    @Override
    public WindowId getWindowId() {
        return mXWalkView.getWindowId();
    }

    @Override
    public IBinder getApplicationWindowToken() {
        return mXWalkView.getApplicationWindowToken();
    }

    @Override
    public Display getDisplay() {
        return mXWalkView.getDisplay();
    }

    @Override
    public void onCancelPendingInputEvents() {
        mXWalkView.onCancelPendingInputEvents();
    }

    @Override
    public void saveHierarchyState(SparseArray<Parcelable> container) {
        mXWalkView.saveHierarchyState(container);
    }

    @Override
    public void restoreHierarchyState(SparseArray<Parcelable> container) {
        mXWalkView.restoreHierarchyState(container);
    }

    @Override
    public long getDrawingTime() {
        return mXWalkView.getDrawingTime();
    }

    @Override
    public void setDuplicateParentStateEnabled(boolean enabled) {
        mXWalkView.setDuplicateParentStateEnabled(enabled);
    }

    @Override
    public boolean isDuplicateParentStateEnabled() {
        return mXWalkView.isDuplicateParentStateEnabled();
    }

    @Override
    public void setLayerPaint(Paint paint) {
        mXWalkView.setLayerPaint(paint);
    }

    @Override
    public int getLayerType() {
        return mXWalkView.getLayerType();
    }

    @Override
    public void buildLayer() {
        mXWalkView.buildLayer();
    }

    @Override
    public void setDrawingCacheEnabled(boolean enabled) {
        mXWalkView.setDrawingCacheEnabled(enabled);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public boolean isDrawingCacheEnabled() {
        return mXWalkView.isDrawingCacheEnabled();
    }

    @Override
    public Bitmap getDrawingCache() {
        return mXWalkView.getDrawingCache();
    }

    @Override
    public Bitmap getDrawingCache(boolean autoScale) {
        return mXWalkView.getDrawingCache(autoScale);
    }

    @Override
    public void destroyDrawingCache() {
        mXWalkView.destroyDrawingCache();
    }

    @Override
    public void setDrawingCacheBackgroundColor(int color) {
        mXWalkView.setDrawingCacheBackgroundColor(color);
    }

    @Override
    public int getDrawingCacheBackgroundColor() {
        return mXWalkView.getDrawingCacheBackgroundColor();
    }

    @Override
    public void buildDrawingCache() {
        mXWalkView.buildDrawingCache();
    }

    @Override
    public void buildDrawingCache(boolean autoScale) {
        mXWalkView.buildDrawingCache(autoScale);
    }

    @Override
    public boolean isInEditMode() {
        return mXWalkView.isInEditMode();
    }


    @Override
    @ExportedProperty(category = "drawing")
    public boolean isHardwareAccelerated() {
        return mXWalkView.isHardwareAccelerated();
    }

    @Override
    public void setClipBounds(Rect clipBounds) {
        mXWalkView.setClipBounds(clipBounds);
    }

    @Override
    public Rect getClipBounds() {
        return mXWalkView.getClipBounds();
    }

    @Override
    public boolean getClipBounds(Rect outRect) {
        return mXWalkView.getClipBounds(outRect);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        mXWalkView.draw(canvas);
    }

    @Override
    @ExportedProperty(category = "drawing")
    public int getSolidColor() {
        return mXWalkView.getSolidColor();
    }

    @Override
    public boolean isLayoutRequested() {
        return mXWalkView.isLayoutRequested();
    }

    @Override
    public Resources getResources() {
        return mXWalkView.getResources();
    }

    @Override
    public void invalidateDrawable(Drawable drawable) {
        mXWalkView.invalidateDrawable(drawable);
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        mXWalkView.scheduleDrawable(who, what, when);
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        mXWalkView.unscheduleDrawable(who, what);
    }

    @Override
    public void unscheduleDrawable(Drawable who) {
        mXWalkView.unscheduleDrawable(who);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void drawableHotspotChanged(float x, float y) {
        mXWalkView.drawableHotspotChanged(x, y);
    }

    @Override
    public void refreshDrawableState() {
        mXWalkView.refreshDrawableState();
    }

    public static int[] mergeDrawableStates(int[] baseState, int[] additionalState) {
        return View.mergeDrawableStates(baseState, additionalState);
    }

    @Override
    public void setBackgroundResource(int resid) {
        mXWalkView.setBackgroundResource(resid);
    }

    @Override
    public void setBackground(Drawable background) {
        mXWalkView.setBackground(background);
    }

    @Override
    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        mXWalkView.setBackgroundDrawable(background);
    }

    @Override
    public Drawable getBackground() {
        return mXWalkView.getBackground();
    }

    @Override
    public void setBackgroundTintList(ColorStateList tint) {
        mXWalkView.setBackgroundTintList(tint);
    }

    @Override
    public ColorStateList getBackgroundTintList() {
        return mXWalkView.getBackgroundTintList();
    }

    @Override
    public void setBackgroundTintMode(Mode tintMode) {
        mXWalkView.setBackgroundTintMode(tintMode);
    }

    @Override
    public Mode getBackgroundTintMode() {
        return mXWalkView.getBackgroundTintMode();
    }

    @Override
    public Drawable getForeground() {
        return mXWalkView.getForeground();
    }

    @Override
    public void setForeground(Drawable foreground) {
        mXWalkView.setForeground(foreground);
    }

    @Override
    public int getForegroundGravity() {
        return mXWalkView.getForegroundGravity();
    }

    @Override
    public void setForegroundTintList(ColorStateList tint) {
        mXWalkView.setForegroundTintList(tint);
    }

    @Override
    public ColorStateList getForegroundTintList() {
        return mXWalkView.getForegroundTintList();
    }

    @Override
    public void setForegroundTintMode(Mode tintMode) {
        mXWalkView.setForegroundTintMode(tintMode);
    }

    @Override
    public Mode getForegroundTintMode() {
        return mXWalkView.getForegroundTintMode();
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        mXWalkView.onDrawForeground(canvas);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        mXWalkView.setPadding(left, top, right, bottom);
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        mXWalkView.setPaddingRelative(start, top, end, bottom);
    }

    @Override
    public int getPaddingTop() {
        return mXWalkView.getPaddingTop();
    }

    @Override
    public int getPaddingBottom() {
        return mXWalkView.getPaddingBottom();
    }

    @Override
    public int getPaddingLeft() {
        return mXWalkView.getPaddingLeft();
    }

    @Override
    public int getPaddingStart() {
        return mXWalkView.getPaddingStart();
    }

    @Override
    public int getPaddingRight() {
        return mXWalkView.getPaddingRight();
    }

    @Override
    public int getPaddingEnd() {
        return mXWalkView.getPaddingEnd();
    }

    @Override
    public boolean isPaddingRelative() {
        return mXWalkView.isPaddingRelative();
    }

    @Override
    public void setSelected(boolean selected) {
        mXWalkView.setSelected(selected);
    }

    @Override
    @ExportedProperty
    public boolean isSelected() {
        return mXWalkView.isSelected();
    }

    @Override
    public void setActivated(boolean activated) {
        mXWalkView.setActivated(activated);
    }

    @Override
    @ExportedProperty
    public boolean isActivated() {
        return mXWalkView.isActivated();
    }

    @Override
    public ViewTreeObserver getViewTreeObserver() {
        return mXWalkView.getViewTreeObserver();
    }

    @Override
    public View getRootView() {
        return mXWalkView.getRootView();
    }

    @Override
    public void getLocationOnScreen(int[] outLocation) {
        mXWalkView.getLocationOnScreen(outLocation);
    }

    @Override
    public void getLocationInWindow(int[] outLocation) {
        mXWalkView.getLocationInWindow(outLocation);
    }

    @Override
    public void setId(int id) {
        mXWalkView.setId(id);
    }

    @Override
    @CapturedViewProperty
    public int getId() {
        return mXWalkView.getId();
    }

    @Override
    @ExportedProperty
    public Object getTag() {
        return mXWalkView.getTag();
    }

    @Override
    public void setTag(Object tag) {
        mXWalkView.setTag(tag);
    }

    @Override
    public Object getTag(int key) {
        return mXWalkView.getTag(key);
    }

    @Override
    public void setTag(int key, Object tag) {
        mXWalkView.setTag(key, tag);
    }

    @Override
    @ExportedProperty(category = "layout")
    public int getBaseline() {
        return mXWalkView.getBaseline();
    }

    @Override
    public boolean isInLayout() {
        return mXWalkView.isInLayout();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void requestLayout() {
        mXWalkView.requestLayout();
    }

    @Override
    public void forceLayout() {
        mXWalkView.forceLayout();
    }

    public static int combineMeasuredStates(int curState, int newState) {
        return View.combineMeasuredStates(curState, newState);
    }

    public static int resolveSize(int size, int measureSpec) {
        return View.resolveSize(size, measureSpec);
    }

    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        return View.resolveSizeAndState(size, measureSpec, childMeasuredState);
    }

    public static int getDefaultSize(int size, int measureSpec) {
        return View.getDefaultSize(size, measureSpec);
    }

    @Override
    public int getMinimumHeight() {
        return mXWalkView.getMinimumHeight();
    }

    @Override
    public void setMinimumHeight(int minHeight) {
        mXWalkView.setMinimumHeight(minHeight);
    }

    @Override
    public int getMinimumWidth() {
        return mXWalkView.getMinimumWidth();
    }

    @Override
    public void setMinimumWidth(int minWidth) {
        mXWalkView.setMinimumWidth(minWidth);
    }

    @Override
    public Animation getAnimation() {
        return mXWalkView.getAnimation();
    }

    @Override
    public void startAnimation(Animation animation) {
        mXWalkView.startAnimation(animation);
    }

    @Override
    public void clearAnimation() {
        mXWalkView.clearAnimation();
    }

    @Override
    public void setAnimation(Animation animation) {
        mXWalkView.setAnimation(animation);
    }

    @Override
    public void playSoundEffect(int soundConstant) {
        mXWalkView.playSoundEffect(soundConstant);
    }

    @Override
    public boolean performHapticFeedback(int feedbackConstant) {
        return mXWalkView.performHapticFeedback(feedbackConstant);
    }

    @Override
    public boolean performHapticFeedback(int feedbackConstant, int flags) {
        return mXWalkView.performHapticFeedback(feedbackConstant, flags);
    }

    @Override
    public void setSystemUiVisibility(int visibility) {
        mXWalkView.setSystemUiVisibility(visibility);
    }

    @Override
    public int getSystemUiVisibility() {
        return mXWalkView.getSystemUiVisibility();
    }

    @Override
    public int getWindowSystemUiVisibility() {
        return mXWalkView.getWindowSystemUiVisibility();
    }

    @Override
    public void onWindowSystemUiVisibilityChanged(int visible) {
        mXWalkView.onWindowSystemUiVisibilityChanged(visible);
    }

    @Override
    public void setOnSystemUiVisibilityChangeListener(OnSystemUiVisibilityChangeListener l) {
        mXWalkView.setOnSystemUiVisibilityChangeListener(l);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        return mXWalkView.onDragEvent(event);
    }

    public static View inflate(Context context, int resource, ViewGroup root) {
        return View.inflate(context, resource, root);
    }

    @Override
    public int getOverScrollMode() {
        return mXWalkView.getOverScrollMode();
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mXWalkView.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mXWalkView.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mXWalkView.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mXWalkView.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mXWalkView.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mXWalkView.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mXWalkView.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mXWalkView.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mXWalkView.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public void setTextDirection(int textDirection) {
        mXWalkView.setTextDirection(textDirection);
    }

    @Override
    @ExportedProperty(category = "text", mapping = {@IntToString(from = 0, to = "INHERIT"), @IntToString(from = 1, to = "FIRST_STRONG"), @IntToString(from = 2, to = "ANY_RTL"), @IntToString(from = 3, to = "LTR"), @IntToString(from = 4, to = "RTL"), @IntToString(from = 5, to = "LOCALE"), @IntToString(from = 6, to = "FIRST_STRONG_LTR"), @IntToString(from = 7, to = "FIRST_STRONG_RTL")})
    public int getTextDirection() {
        return mXWalkView.getTextDirection();
    }

    @Override
    public boolean canResolveTextDirection() {
        return mXWalkView.canResolveTextDirection();
    }

    @Override
    public boolean isTextDirectionResolved() {
        return mXWalkView.isTextDirectionResolved();
    }

    @Override
    public void setTextAlignment(int textAlignment) {
        mXWalkView.setTextAlignment(textAlignment);
    }

    @Override
    @ExportedProperty(category = "text", mapping = {@IntToString(from = 0, to = "INHERIT"), @IntToString(from = 1, to = "GRAVITY"), @IntToString(from = 2, to = "TEXT_START"), @IntToString(from = 3, to = "TEXT_END"), @IntToString(from = 4, to = "CENTER"), @IntToString(from = 5, to = "VIEW_START"), @IntToString(from = 6, to = "VIEW_END")})
    public int getTextAlignment() {
        return mXWalkView.getTextAlignment();
    }

    @Override
    public boolean canResolveTextAlignment() {
        return mXWalkView.canResolveTextAlignment();
    }

    @Override
    public boolean isTextAlignmentResolved() {
        return mXWalkView.isTextAlignmentResolved();
    }

    public static int generateViewId() {
        return View.generateViewId();
    }

    @Override
    public void setPointerIcon(PointerIcon pointerIcon) {
        mXWalkView.setPointerIcon(pointerIcon);
    }

    @Override
    public PointerIcon getPointerIcon() {
        return mXWalkView.getPointerIcon();
    }

    @Override
    public ViewPropertyAnimator animate() {
        return mXWalkView.animate();
    }

    @Override
    @ExportedProperty
    public String getTransitionName() {
        return mXWalkView.getTransitionName();
    }
}
