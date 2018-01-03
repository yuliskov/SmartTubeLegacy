package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.Utils;

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
        return title + PlayerActivity.DELIMITER + title2;
    }

    public abstract static class Sample {

        public final String name;
        public final boolean preferExtensionDecoders;
        public final UUID drmSchemeUuid;
        public final String drmLicenseUrl;
        public final String[] drmKeyRequestProperties;

        public Sample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                      String[] drmKeyRequestProperties, boolean preferExtensionDecoders) {
            this.name = name;
            this.drmSchemeUuid = drmSchemeUuid;
            this.drmLicenseUrl = drmLicenseUrl;
            this.drmKeyRequestProperties = drmKeyRequestProperties;
            this.preferExtensionDecoders = preferExtensionDecoders;
        }

        public Intent buildIntent(Context context) {
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra(PlayerActivity.PREFER_EXTENSION_DECODERS, preferExtensionDecoders);
            if (drmSchemeUuid != null) {
                intent.putExtra(PlayerActivity.DRM_SCHEME_UUID_EXTRA, drmSchemeUuid.toString());
                intent.putExtra(PlayerActivity.DRM_LICENSE_URL, drmLicenseUrl);
                intent.putExtra(PlayerActivity.DRM_KEY_REQUEST_PROPERTIES, drmKeyRequestProperties);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // merge new activity with current one
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // merge new activity with current one
            intent.putExtra(PlayerActivity.VIDEO_TITLE, this.name);
            return intent;
        }

    }

    public static final class MPDSample extends Sample {
        public final String uri;
        public final String extension;
        private final String mpdContent;

        public MPDSample(String name, String uri, InputStream mpdStream) {
            this(name, null, null, null, true, uri, "mpd", mpdStream);
        }

        public MPDSample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                         String[] drmKeyRequestProperties, boolean preferExtensionDecoders, String uri,
                         String extension, InputStream mpdStream) {
            super(name, drmSchemeUuid, drmLicenseUrl, drmKeyRequestProperties, preferExtensionDecoders);
            this.mpdContent = Utils.toString(mpdStream);
            this.extension = extension;
            this.uri = uri;
        }

        @Override
        public Intent buildIntent(Context context) {
            return super.buildIntent(context)
                    .setData(Uri.parse(uri))
                    .putExtra(PlayerActivity.MPD_CONTENT_EXTRA, mpdContent)
                    .putExtra(PlayerActivity.EXTENSION_EXTRA, extension)
                    .setAction(PlayerActivity.ACTION_VIEW);
        }

    }

    public static final class UriSample extends Sample {

        public final String uri;
        public final String extension;

        public UriSample(String name, String uri) {
            this(name, null, null, null, true, uri, null);
        }

        public UriSample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                         String[] drmKeyRequestProperties, boolean preferExtensionDecoders, String uri,
                         String extension) {
            super(name, drmSchemeUuid, drmLicenseUrl, drmKeyRequestProperties, preferExtensionDecoders);
            this.uri = uri;
            this.extension = extension;
        }

        @Override
        public Intent buildIntent(Context context) {
            return super.buildIntent(context)
                    .setData(Uri.parse(uri))
                    .putExtra(PlayerActivity.EXTENSION_EXTRA, extension)
                    .setAction(PlayerActivity.ACTION_VIEW);
        }

    }

    public static final class PlaylistSample extends Sample {

        public final UriSample[] children;

        public PlaylistSample(String name, UriSample... children) {
            this(name, null, null, null, true, children);
        }

        public PlaylistSample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                              String[] drmKeyRequestProperties, boolean preferExtensionDecoders,
                              UriSample... children) {
            super(name, drmSchemeUuid, drmLicenseUrl, drmKeyRequestProperties, preferExtensionDecoders);
            this.children = children;
        }

        @Override
        public Intent buildIntent(Context context) {
            String[] uris = new String[children.length];
            String[] extensions = new String[children.length];
            for (int i = 0; i < children.length; i++) {
                uris[i] = children[i].uri;
                extensions[i] = children[i].extension;
            }
            return super.buildIntent(context)
                    .putExtra(PlayerActivity.URI_LIST_EXTRA, uris)
                    .putExtra(PlayerActivity.EXTENSION_LIST_EXTRA, extensions)
                    .setAction(PlayerActivity.ACTION_VIEW_LIST);
        }

    }
}
