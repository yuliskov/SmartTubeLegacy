package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.previewtimebar;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class GlideThumbnailTransformation extends BitmapTransformation {
    private int mMaxLines;
    private int mMaxColumns;
    private int mThumbnailsEach; // duration of each thumbnail in millisseconds

    private int x;
    private int y;

    public GlideThumbnailTransformation(long position) {
        this(position, 7, 7, 5000);
    }

    public GlideThumbnailTransformation(long position, int maxLines, int maxColumns, int thumbEachMS) {
        mMaxLines = maxLines;
        mMaxColumns = maxColumns;
        mThumbnailsEach = thumbEachMS;

        int square = (int) position / mThumbnailsEach;
        y = square / mMaxLines;
        x = square % mMaxColumns;
    }

    private int getX() {
        return x;
    }

    private int getY() {
        return y;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform,
                               int outWidth, int outHeight) {
        int width = toTransform.getWidth() / mMaxColumns;
        int height = toTransform.getHeight() / mMaxLines;
        return Bitmap.createBitmap(toTransform, x * width, y * height, width, height);
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        byte[] data = ByteBuffer.allocate(8).putInt(x).putInt(y).array();
        messageDigest.update(data);
    }

    @Override
    public int hashCode() {
        return (String.valueOf(x) + String.valueOf(y)).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GlideThumbnailTransformation)) {
            return false;
        }
        GlideThumbnailTransformation transformation = (GlideThumbnailTransformation) obj;
        return transformation.getX() == x && transformation.getY() == y;
    }
}
