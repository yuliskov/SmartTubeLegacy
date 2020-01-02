package com.liskovsoft.m3uparser.core.uri;

import com.liskovsoft.m3uparser.core.utils.Strings;

public enum UriScheme {

    FILE(1, "file"),
    HTTP(2, "http"),
    FTP(3, "ftp"),
    CDN(4, "cdn"),
    RESOURCE(5, "res"),

    UNKNOWN(100, "unknown");


    private final int id;
    private final String  name;

    UriScheme(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return this.id; }

    public String getName() { return this.name; }

    public static UriScheme fromId(final int value) {
        for (UriScheme scheme : values()) {
            if (value == scheme.getId())
                return scheme;
        }

        return UriScheme.UNKNOWN;
    }

    public static UriScheme fromName(final String value) {
        if (!Strings.isNullOrEmpty(value)) {
            for (UriScheme scheme : values()) {
                if (value.equalsIgnoreCase(scheme.getName()))
                    return scheme;
            }
        }

        return UriScheme.UNKNOWN;
    }
}
