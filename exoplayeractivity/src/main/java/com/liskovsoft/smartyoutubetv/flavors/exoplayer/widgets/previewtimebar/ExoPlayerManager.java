package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.previewtimebar;

import android.widget.ImageView;
import com.bumptech.glide.request.target.Target;
import com.github.rubensousa.previewseekbar.PreviewLoader;
import com.google.android.exoplayer2.Player;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeStoryParser.Storyboard;

public class ExoPlayerManager implements PreviewLoader {
    private PreviewTimeBar mPreviewTimeBar;
    private ImageView mImageView;
    private final Storyboard mStoryBoard;

    public ExoPlayerManager(PreviewTimeBar previewTimeBar,
                            ImageView imageView,
                            Storyboard storyboard) {
        mImageView = imageView;
        mPreviewTimeBar = previewTimeBar;
        mStoryBoard = storyboard;
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

        GlideApp.with(mImageView)
                .load(mStoryBoard.getThumbSetLink(imgNum))
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .transform(new GlideThumbnailTransformation(realPosMS))
                .into(mImageView);
    }
}