package com.liskovsoft.browser;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

/**
 * TODO: not implemented
 */

/**
 * Simple bread crumb view
 * Use setController to receive callbacks from user interactions
 * Use pushView, popView, clear, and getTopData to change/access the view stack
 */
public class BreadCrumbView extends LinearLayout implements OnClickListener {
    public interface Controller {
        public void onTop(BreadCrumbView view, int level, Object data);
    }

    public BreadCrumbView(Context context) {
        super(context);
    }

    public BreadCrumbView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BreadCrumbView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View view) {

    }
}
