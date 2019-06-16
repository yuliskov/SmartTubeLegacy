package com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.previewtimebar;

import android.widget.ImageView;
import com.bumptech.glide.request.target.Target;
import com.github.rubensousa.previewseekbar.PreviewLoader;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.google.android.exoplayer2.Player;

public class ExoPlayerManager implements PreviewLoader {
    private PreviewTimeBar mPreviewTimeBar;
    private String mThumbnailsUrl;
    private ImageView mImageView;

    public ExoPlayerManager(PreviewTimeBar previewTimeBar,
                            ImageView imageView,
                            String thumbnailsUrl) {
        mImageView = imageView;
        mPreviewTimeBar = previewTimeBar;
        mThumbnailsUrl = thumbnailsUrl;
    }

    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == Player.STATE_READY && playWhenReady) {
            mPreviewTimeBar.hidePreview();
        }
    }

    @Override
    public void loadPreview(long currentPosition, long max) {
        GlideApp.with(mImageView)
                .load(mThumbnailsUrl)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .transform(new GlideThumbnailTransformation(currentPosition))
                .into(mImageView);
    }
}