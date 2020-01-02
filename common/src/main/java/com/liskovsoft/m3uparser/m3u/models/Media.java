package com.liskovsoft.m3uparser.m3u.models;

import com.liskovsoft.m3uparser.core.uri.Uri;
import com.liskovsoft.m3uparser.core.utils.Strings;

public class Media {
    public enum Type {
        AUDIO("AUDIO"),
        SUBTITLES("SUBTITLES"),
        VIDEO("VIDEO");

        final String value;

        Type(final String value) {
            this.value = value;
        }

        public static Media.Type fromValue(final String value) {
            if (!Strings.isNullOrEmpty(value)) {
                for (Media.Type type: Media.Type.values()) {
                    if (type.value.equalsIgnoreCase(value)) {
                        return type;
                    }
                }
            }

            return null;
        }
    }

    public final Media.Type  type;
    public final String name;
    public final String groupId;
    public final Uri uri;

    @Override
    public String toString() {
        return "Media{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", groupId='" + groupId + '\'' +
                ", uri=" + uri +
                '}';
    }

    public static class Builder {
        private Media.Type type;
        private String name;
        private String groupId;
        private Uri uri;


        public Builder(final String type) {
            this.type = Media.Type.fromValue(type);
        }

        public Media.Builder withName(final String value) {
            this.name = value;
            return this;
        }

        public Media.Builder withGroupId(final String value) {
            this.groupId = value;
            return this;
        }

        public Media.Builder withUri(final String value) {
            if (!Strings.isNullOrEmpty(value))
                this.uri = Uri.parse(value);
            return this;
        }

        public Media.Builder withUri(final Uri value) {
            this.uri = value;
            return this;
        }

        public Media build() {
            return new Media(this);
        }
    }

    private Media(Media.Builder builder) {
        this.type = builder.type;
        this.name = builder.name;
        this.groupId = builder.groupId;
        this.uri = builder.uri;
    }
}
