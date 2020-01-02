package com.liskovsoft.m3uparser.core.uri;

import java.util.ArrayList;
import java.util.List;


public class UriMatcher {
    public static final int NO_MATCH = -1;
    private static final int EXACT = 0;
    private static final int NUMBER = 1;
    private static final int TEXT = 2;

    private int mCode;
    private int mWhich;
    private String mText;
    private ArrayList<UriMatcher> mChildren;

    /**
     * Creates the root node of the URI tree.
     *
     * @param code the code to match for the root URI
     */
    public UriMatcher(int code) {
        mCode = code;
        mWhich = -1;
        mChildren = new ArrayList<UriMatcher>();
        mText = null;
    }
    private UriMatcher()
    {
        mCode = NO_MATCH;
        mWhich = -1;
        mChildren = new ArrayList<UriMatcher>();
        mText = null;
    }
    /**
     * Add a URI to match, and the code to return when this URI is
     * matched. URI nodes may be exact match string, the token "*"
     * that matches any text, or the token "#" that matches only
     * numbers.
     *
     * @param authority the authority to match
     * @param path the path to match. * may be used as a wild card for
     * any text, and # may be used as a wild card for numbers.
     * @param code the code that is returned when a URI is matched
     * against the given components. Must be positive.
     */
    public void addURI(String authority, String path, int code)
    {
        if (code < 0) {
            throw new IllegalArgumentException("code " + code + " is invalid: it must be positive");
        }
        String[] tokens = null;
        if (path != null) {
            String newPath = path;
            // Strip leading slash if present.
            if (path.length() > 1 && path.charAt(0) == '/') {
                newPath = path.substring(1);
            }
            tokens = newPath.split("/");
        }
        int numTokens = (tokens != null) ? tokens.length : 0;
        UriMatcher node = this;
        for (int i = -1; i < numTokens; i++) {
            String token = (i < 0) ? authority : tokens[i];
            ArrayList<UriMatcher> children = node.mChildren;
            int numChildren = children.size();
            UriMatcher child;
            int j;
            for (j = 0; j < numChildren; j++) {
                child = children.get(j);
                if (token.equals(child.mText)) {
                    node = child;
                    break;
                }
            }
            if (j == numChildren) {
                // Child not found, create it
                child = new UriMatcher();
                if (token.equals("#")) {
                    child.mWhich = NUMBER;
                } else if (token.equals("*")) {
                    child.mWhich = TEXT;
                } else {
                    child.mWhich = EXACT;
                }
                child.mText = token;
                node.mChildren.add(child);
                node = child;
            }
        }
        node.mCode = code;
    }
    /**
     * Try to match against the path in a url.
     *
     * @param uri       The url whose path we will match against.
     *
     * @return  The code for the matched node (added using addURI),
     * or -1 if there is no matched node.
     */
    public int match(Uri uri)
    {
        final List<String> pathSegments = uri.getPathSegments();
        final int li = pathSegments.size();
        UriMatcher node = this;
        if (li == 0 && uri.getAuthority() == null) {
            return this.mCode;
        }
        for (int i=-1; i<li; i++) {
            String u = i < 0 ? uri.getAuthority() : pathSegments.get(i);
            ArrayList<UriMatcher> list = node.mChildren;
            if (list == null) {
                break;
            }
            node = null;
            int lj = list.size();
            for (int j=0; j<lj; j++) {
                UriMatcher n = list.get(j);
                which_switch:
                switch (n.mWhich) {
                    case EXACT:
                        if (n.mText.equals(u)) {
                            node = n;
                        }
                        break;
                    case NUMBER:
                        int lk = u.length();
                        for (int k=0; k<lk; k++) {
                            char c = u.charAt(k);
                            if (c < '0' || c > '9') {
                                break which_switch;
                            }
                        }
                        node = n;
                        break;
                    case TEXT:
                        node = n;
                        break;
                }
                if (node != null) {
                    break;
                }
            }
            if (node == null) {
                return NO_MATCH;
            }
        }
        return node.mCode;
    }
}
