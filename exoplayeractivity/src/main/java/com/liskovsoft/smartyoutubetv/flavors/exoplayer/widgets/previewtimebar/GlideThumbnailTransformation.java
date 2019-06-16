package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.previewtimebar;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.nio.ByteBuffer;
import java.security.MessageDigest;


public class GlideThumbnailTransformation extends BitmapTransformation {

    public static final int MAX_LINES = 7;
    public static final int MAX_COLUMNS = 7;
    public static final int THUMBNAILS_EACH = 5000; // millisseconds

    private int x;
    private int y;

    public GlideThumbnailTransformation(long position) {
        int square = (int) position / THUMBNAILS_EACH;
        y = square / MAX_LINES;
        x = square % MAX_COLUMNS;
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
        int width = toTransform.getWidth() / MAX_COLUMNS;
        int height = toTransform.getHeight() / MAX_LINES;
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
