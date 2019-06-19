package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.previewtimebar;

import android.widget.ImageView;
import com.bumptech.glide.request.target.Target;
import com.github.rubensousa.previewseekbar.PreviewLoader;
import com.google.android.exoplayer2.Player;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeStoryParser.Size;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeStoryParser.Storyboard;

import java.util.ArrayList;
import java.util.List;

public class ExoPlayerManager implements PreviewLoader {
    private static final String TAG = ExoPlayerManager.class.getSimpleName();
    private PreviewTimeBar mPreviewTimeBar;
    private ImageView mImageView;
    private final Storyboard mStoryBoard;
    private int mLastImgNum = -1;
    private List<Integer> mLoadedImages;

    public ExoPlayerManager(PreviewTimeBar previewTimeBar,
                            ImageView imageView,
                            Storyboard storyboard) {
        mLoadedImages = new ArrayList<>();
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

        mLastImgNum = imgNum;
        preloadNextImage();
    }

    private void preloadNextImage() {
        if (mStoryBoard == null) {
            return;
        }

        mLastImgNum++; // get next image

        if (mLoadedImages.contains(mLastImgNum)) {
            return;
        }

        Log.d(TAG, "Oops, image #" + mLastImgNum + " didn't cached yet");

        mLoadedImages.add(mLastImgNum);

        String link = mStoryBoard.getThumbSetLink(mLastImgNum);
        GlideApp.with(mImageView)
                .load(link)
                .preload();
    }
}