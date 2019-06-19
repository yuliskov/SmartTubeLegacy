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
    private static final int DIRECTION_RIGHT = 0;
    private static final int DIRECTION_LEFT = 1;
    private PreviewTimeBar mPreviewTimeBar;
    private ImageView mImageView;
    private final Storyboard mStoryBoard;
    private int mLastImgNum = -1;
    private Set<Integer> mCachedImages;
    private int mSeekDirection;
    private boolean mDontPreload;

    public ExoPlayerManager(PreviewTimeBar previewTimeBar,
                            ImageView imageView,
                            Storyboard storyboard) {
        mCachedImages = new ArraySet<>();
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
        if (mStoryBoard == null || mDontPreload) {
            return;
        }

        int imgNum = mSeekDirection == DIRECTION_RIGHT ? mLastImgNum + 1 : mLastImgNum - 1; // get next image

        if (mCachedImages.contains(imgNum) || imgNum < 0) {
            return;
        }

        Log.d(TAG, "Oops, image #" + imgNum + " didn't cached yet");

        mCachedImages.add(imgNum);

        String link = mStoryBoard.getThumbSetLink(imgNum);
        GlideApp.with(mImageView)
                .load(link)
                .preload();
    }

    @Override
    public void loadPreview(long currentPosition, long max) {
        if (mStoryBoard == null) {
            return;
        }

        int imgNum = (int) currentPosition / mStoryBoard.getThumbSetDurMS();
        long realPosMS = currentPosition % mStoryBoard.getThumbSetDurMS();
        Size size = mStoryBoard.getSize();
        GlideThumbnailTransformation transformation =
                new GlideThumbnailTransformation(realPosMS, size.getWidth(), size.getHeight(), size.getRowCount(), size.getColCount(), size.getDurationMS());

        GlideApp.with(mImageView)
                .load(mStoryBoard.getThumbSetLink(imgNum))
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .transform(transformation)
                .into(mImageView);

        mDontPreload = mLastImgNum == -1;

        mSeekDirection = mLastImgNum < imgNum ? DIRECTION_RIGHT : DIRECTION_LEFT;
        mCachedImages.add(imgNum);
        mLastImgNum = imgNum;
        preloadNextImage();
    }
}