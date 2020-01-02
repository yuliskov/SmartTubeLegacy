package com.liskovsoft.m3uparser.m3u.models;

import android.net.Uri;

import java.util.Arrays;

/**
 * https://pypkg.com/pypi/pytoutv/src/toutv/m3u8.py
 */
public class Stream {

    public final int bandWidth;
    public final String[] codecs;
    public final String resolution;
    public final String programId;
    public final String type;
    public final Uri uri;

    @Override
    public String toString() {
        return "Stream{" +
                "bandWidth=" + bandWidth +
                ", codecs=" + Arrays.toString(codecs) +
                ", resolution='" + resolution + '\'' +
                ", programId='" + programId + '\'' +
                ", type='" + type + '\'' +
                ", uri=" + uri +
                '}';
    }

    public static class Builder {
        private int bandWidth;
        private String[] codecs;
        private String resolution;
        private String programId;
        private String type;
        private Uri uri;


        public Builder(final int bandWidth) {
            this.bandWidth = bandWidth;
        }

        public Builder withType(final String value) {
            this.type = value;
            return this;
        }

        public Builder withCodecs(final String... values) {
            if (values != null) {
                this.codecs = new String[values.length];
                System.arraycopy(values, 0, this.codecs, 0, values.length);
            }
            return this;
        }

        public Builder withResolution(final String value) {
            this.resolution = value;
            return this;
        }

        public Builder withProgramId(final String value) {
            this.programId = value;
            return this;
        }

        public Builder withUri(final Uri value) {
            this.uri = value;
            return this;
        }

        public Stream build() {
            return new Stream(this);
        }
    }

    private Stream(Stream.Builder builder) {
        this.bandWidth = builder.bandWidth;
        this.codecs = builder.codecs;
        this.programId = builder.programId;
        this.resolution = builder.resolution;
        this.type = builder.type;
        this.uri = builder.uri;
    }
}
