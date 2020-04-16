package com.liskovsoft.smartyoutubetv.interceptors.ads.contentfilter;

import androidx.core.util.Pair;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.io.*;
import java.util.*;

class ReplacingInputStream extends FilterInputStream {
    private static final String TAG = ReplacingInputStream.class.getSimpleName();
    private LinkedList<Integer> mInQueue = new LinkedList<>();
    private LinkedList<Integer> mOutQueue = new LinkedList<>();
    private static String DEFAULT_ENCODING = "UTF-8";
    private final ReplacePair[] mPairs;

    static class ReplacePair extends Pair<String, String> {
        public boolean matchFound;

        public ReplacePair(String first, String second) {
            super(first, second);
        }
    }
    
    protected ReplacingInputStream(InputStream in, ReplacePair... pairs) {
        super(in);
        mPairs = pairs;
    }

    private boolean isMatchFound() throws UnsupportedEncodingException {
        Iterator<Integer> inIter = mInQueue.iterator();

        int maxLength = findMaxSearchLength();
        boolean matchFound = false;

        for (int i = 0; i < maxLength; i++) {
            if (!inIter.hasNext()) {
                break;
            }

            Integer next = inIter.next();

            for (ReplacePair pair : mPairs) {
                if (i < pair.first.getBytes(DEFAULT_ENCODING).length && (pair.matchFound || i == 0)) { // NOTE: it's buggy because of different strings length
                    pair.matchFound = pair.first.getBytes(DEFAULT_ENCODING)[i] == next;

                    if (pair.matchFound && (pair.first.getBytes(DEFAULT_ENCODING).length - 1) == i) {
                        matchFound = true;
                        break;
                    }
                }
            }
        }

        return matchFound;
    }

    private void readAhead() throws IOException {
        // Work up some look-ahead.
        int maxLength = findMaxSearchLength();
        while (mInQueue.size() < maxLength) {
            int next = super.read();
            mInQueue.offer(next);
            if (next == -1) {
                break;
            }
        }
    }

    private int findMaxSearchLength() throws UnsupportedEncodingException {
        int length = 0;

        for (Pair<String, String> pair : mPairs) {
            if (pair.first.getBytes(DEFAULT_ENCODING).length > length) {
                length = pair.first.getBytes(DEFAULT_ENCODING).length;
            }
        }

        return length;
    }

    private int findMatchedSearchLength() throws UnsupportedEncodingException {
        int length = 0;

        for (ReplacePair pair : mPairs) {
            if (pair.matchFound) {
                length = pair.first.getBytes(DEFAULT_ENCODING).length;
                break;
            }
        }

        return length;
    }

    private byte[] findReplacement() throws UnsupportedEncodingException {
        byte[] result = null;

        for (ReplacePair pair : mPairs) {
            if (pair.matchFound) {
                result = pair.second.getBytes(DEFAULT_ENCODING);
                break;
            }
        }

        return result;
    }

    @Override
    public int read() throws IOException {
        // Next byte already determined.
        if (mOutQueue.isEmpty()) {
            readAhead();

            if (isMatchFound()) {
                int matchedLength = findMatchedSearchLength();

                for (int i = 0; i < matchedLength; i++) {
                    mInQueue.remove();
                }

                byte[] replacement = findReplacement();

                for (byte b : replacement) {
                    mOutQueue.offer((int) b);
                }
            } else {
                mOutQueue.add(mInQueue.remove());
            }
        }

        return mOutQueue.remove();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte)c;

        int i = 1;
        try {
            for (; i < len ; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte)c;
            }
        } catch (IOException ee) {
        }
        return i;
    }
}
