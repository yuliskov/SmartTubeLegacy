package com.liskovsoft.smartyoutubetv.misc;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MyHttpResponse {
    private InputStream mInputStream;
    private String mCharset;
    private String mType;

    private MyHttpResponse() {
        
    }
    public static MyHttpResponse fromUrl(String url) {
        URLConnection connection;
        MyHttpResponse result;
        try {
            URL myUrl = new URL(url);
            connection = myUrl.openConnection();
            InputStream is = connection.getInputStream();
            String charset = connection.getContentEncoding();
            String type = connection.getContentType();

            result = new MyHttpResponse();
            result.mInputStream = is;
            result.mCharset = charset;
            result.mType = type;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return result;
    }

    public InputStream getStream() {
        return mInputStream;
    }

    public String getCharset() {
        return mCharset;
    }

    public String getType() {
        return mType;
    }
}
