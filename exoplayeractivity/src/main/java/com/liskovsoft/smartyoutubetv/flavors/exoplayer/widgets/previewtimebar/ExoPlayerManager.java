package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.previewtimebar;

import android.widget.ImageView;
import androidx.collection.ArraySet;
import com.bumptech.glide.request.target.Target;
import com.github.rubensousa.previewseekbar.PreviewLoader;
import com.google.android.exoplayer2.Player;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeStoryParser.Size;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeStoryParser.Storyboard;

import java.util.Set;

public class ExoPlayerManager implements PreviewLoader {
    private static final String TAG = ExoPlayerManager.class.getSimpleName();
    private PreviewTimeBar mPreviewTimeBar;
    private ImageView mImageView;
    private final Storyboard mStoryBoard;
    private static final int DIRECTION_RIGHT = 0;
    private static final int DIRECTION_LEFT = 1;
    private int mCurrentImgNum = -1;
    private Set<Integer> mCachedImageNums;
    private int mSeekDirection = DIRECTION_RIGHT;
    private static final int MAX_PRELOADED_IMAGES = 3;

    public ExoPlayerManager(PreviewTimeBar previewTimeBar,
                            ImageView imageView,
                            Storyboard storyboard) {
        mCachedImageNums = new ArraySet<>();
        mImageView = imageView;
        mPreviewTimeBar = previewTimeBar;
        mStoryBoard = storyboard;

        if (mStoryBoard == null) {
            mPreviewTimeBar.setPreviewEnabled(false);
        } else {
            mPreviewTimeBar.setPreviewEnabled(true);
        }

        preloadNextImage();
    }

    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == Player.STATE_READY && playWhenReady) {
            mPreviewTimeBar.hidePreview();
        }
    }

    private void preloadNextImage() {
        if (mStoryBoard == null) {
            return;
        }

        for (int i = 1; i <= MAX_PRELOADED_IMAGES; i++) {
            int imgNum = mSeekDirection == DIRECTION_RIGHT ? mCurrentImgNum + i : mCurrentImgNum - i; // get next image
            preloadImage(imgNum);
        }
    }

    private void preloadImage(int imgNum) {
        if (mCachedImageNums.contains(imgNum) || imgNum < 0) {
            return;
        }

        Log.d(TAG, "Oops, image #" + imgNum + " didn't cached yet");

        mCachedImageNums.add(imgNum);

        String link = mStoryBoard.getGroupUrl(imgNum);

        GlideApp.with(mImageView)
                .load(link)
                .preload();
    }

    @Override
    public void loadPreview(long currentPosition, long max) {
        if (mStoryBoard == null || mStoryBoard.getGroupDurationMS() == 0) {
            return;
        }

        int imgNum = (int) currentPosition / mStoryBoard.getGroupDurationMS();
        long realPosMS = currentPosition % mStoryBoard.getGroupDurationMS();
        Size size = mStoryBoard.getGroupSize();
        GlideThumbnailTransformation transformation =
                new GlideThumbnailTransformation(realPosMS, size.getWidth(), size.getHeight(), size.getRowCount(), size.getColCount(), size.getDurationEachMS());

        GlideApp.with(mImageView)
                .load(mStoryBoard.getGroupUrl(imgNum))
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .transform(transformation)
                .into(mImageView);

        if (mCurrentImgNum != imgNum) {
            mSeekDirection = mCurrentImgNum < imgNum ? DIRECTION_RIGHT : DIRECTION_LEFT;
            mCachedImageNums.add(imgNum);
            mCurrentImgNum = imgNum;

            preloadNextImage();
        }
    }
}