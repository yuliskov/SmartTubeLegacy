package com.liskovsoft.browser.provider;

import android.net.Uri;
import com.liskovsoft.browser.helpers.BrowserContract;

public class BrowserProvider2 {
    public static interface Thumbnails {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                BrowserContract.AUTHORITY_URI, "thumbnails");
        public static final String _ID = "_id";
        public static final String THUMBNAIL = "thumbnail";
    }
}
