package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
// import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.ExtendedDataHolder;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public final class SampleHelpers {
    public static Sample buildFromList(List<String> uris) {
        UriSample[] samples = new UriSample[uris.size()];
        for (int i = 0; i < uris.size(); i++) {
            samples[i] = new UriSample("Sample Video", uris.get(i));
        }
        return new PlaylistSample("Sample Playlist", samples);
    }

    public static Sample buildFromUri(Uri contentUrl) {
        return buildFromUri(contentUrl, "Sample Video");
    }

    public static Sample buildFromUri(Uri contentUrl, String title) {
        return buildFromUri(contentUrl, title, "");
    }

    public static Sample buildFromUri(Uri contentUrl, String title, String title2) {
        return new UriSample(mergeTitles(title, title2), contentUrl.toString());
    }

    public static Sample buildFromMPDPlaylist(InputStream mpdPlaylist) {
        return buildFromMPDPlaylist(mpdPlaylist, "Sample Video");
    }

    public static Sample buildFromMPDPlaylist(InputStream mpdPlaylist, String title) {
        return buildFromMPDPlaylist(mpdPlaylist, title, "");
    }

    public static Sample buildFromMPDPlaylist(InputStream mpdPlaylist, String title, String title2) {
        return new MPDSample(mergeTitles(title, title2), "https://example.com/test.mpd", mpdPlaylist);
    }

    private static String mergeTitles(String title, String title2) {
        return title + ExoPlayerFragment.DELIMITER + title2;
    }

    public abstract static class Sample {

        public final String mName;
        public final boolean mPreferExtensionDecoders;
        public final UUID mDrmSchemeUuid;
        public final String mDrmLicenseUrl;
        public final String[] mDrmKeyRequestProperties;

        public Sample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                      String[] drmKeyRequestProperties, boolean preferExtensionDecoders) {
            mName = name;
            mDrmSchemeUuid = drmSchemeUuid;
            mDrmLicenseUrl = drmLicenseUrl;
            mDrmKeyRequestProperties = drmKeyRequestProperties;
            mPreferExtensionDecoders = preferExtensionDecoders;

            // need to be cleared, see PlayerActivity for details
            // ExtendedDataHolder.getInstance().putExtra(PlayerActivity.MPD_CONTENT_EXTRA, null);
        }

        public Intent buildIntent(Context context) {
            Intent intent = new Intent(context, ExoPlayerFragment.class);
            intent.putExtra(ExoPlayerFragment.PREFER_EXTENSION_DECODERS, mPreferExtensionDecoders);
            if (mDrmSchemeUuid != null) {
                intent.putExtra(ExoPlayerFragment.DRM_SCHEME_UUID_EXTRA, mDrmSchemeUuid.toString());
                intent.putExtra(ExoPlayerFragment.DRM_LICENSE_URL, mDrmLicenseUrl);
                intent.putExtra(ExoPlayerFragment.DRM_KEY_REQUEST_PROPERTIES, mDrmKeyRequestProperties);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // merge new activity with current one
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // merge new activity with current one
            intent.putExtra(ExoPlayerFragment.VIDEO_TITLE, this.mName);
            return intent;
        }

    }

    public static final class MPDSample extends Sample {
        public final String mUri;
        public final String mExtension;
        private final String mMpdContent;

        public MPDSample(String name, String uri, InputStream mpdStream) {
            this(name, null, null, null, true, uri, "mpd", mpdStream);
        }

        public MPDSample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                         String[] drmKeyRequestProperties, boolean preferExtensionDecoders, String uri,
                         String extension, InputStream mpdStream) {
            super(name, drmSchemeUuid, drmLicenseUrl, drmKeyRequestProperties, preferExtensionDecoders);
            // this.mpdContent = null;
            this.mMpdContent = Helpers.toString(mpdStream);
            // fix TransactionTooLargeException (not actual, since we're passing intent between fragments)
            // ExtendedDataHolder.getInstance().putExtra(PlayerActivity.MPD_CONTENT_EXTRA, Helpers.toString(mpdStream));

            mExtension = extension;
            mUri = uri;
        }

        @Override
        public Intent buildIntent(Context context) {
            return super.buildIntent(context)
                    .setData(Uri.parse(mUri))
                    .putExtra(ExoPlayerFragment.MPD_CONTENT_EXTRA, mMpdContent)
                    .putExtra(ExoPlayerFragment.EXTENSION_EXTRA, mExtension)
                    .setAction(ExoPlayerFragment.ACTION_VIEW);
        }

    }

    public static final class UriSample extends Sample {

        public final String mUri;
        public final String mExtension;

        public UriSample(String name, String uri) {
            this(name, null, null, null, true, uri, null);
        }

        public UriSample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                         String[] drmKeyRequestProperties, boolean preferExtensionDecoders, String uri,
                         String extension) {
            super(name, drmSchemeUuid, drmLicenseUrl, drmKeyRequestProperties, preferExtensionDecoders);
            mUri = uri;
            mExtension = extension;
        }

        @Override
        public Intent buildIntent(Context context) {
            return super.buildIntent(context)
                    .setData(Uri.parse(mUri))
                    .putExtra(ExoPlayerFragment.EXTENSION_EXTRA, mExtension)
                    .setAction(ExoPlayerFragment.ACTION_VIEW);
        }

    }

    public static final class PlaylistSample extends Sample {

        public final UriSample[] mChildren;

        public PlaylistSample(String name, UriSample... children) {
            this(name, null, null, null, true, children);
        }

        public PlaylistSample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                              String[] drmKeyRequestProperties, boolean preferExtensionDecoders,
                              UriSample... children) {
            super(name, drmSchemeUuid, drmLicenseUrl, drmKeyRequestProperties, preferExtensionDecoders);
            mChildren = children;
        }

        @Override
        public Intent buildIntent(Context context) {
            String[] uris = new String[mChildren.length];
            String[] extensions = new String[mChildren.length];
            for (int i = 0; i < mChildren.length; i++) {
                uris[i] = mChildren[i].mUri;
                extensions[i] = mChildren[i].mExtension;
            }
            return super.buildIntent(context)
                    .putExtra(ExoPlayerFragment.URI_LIST_EXTRA, uris)
                    .putExtra(ExoPlayerFragment.EXTENSION_LIST_EXTRA, extensions)
                    .setAction(ExoPlayerFragment.ACTION_VIEW_LIST);
        }

    }
}
