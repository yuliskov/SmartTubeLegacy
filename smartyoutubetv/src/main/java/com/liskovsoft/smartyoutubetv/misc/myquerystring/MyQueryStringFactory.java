package com.liskovsoft.smartyoutubetv.misc.myquerystring;

public class MyQueryStringFactory {
    public static MyQueryString parse(String url) {
        MyQueryString queryString = MyUrlEncodedQueryString.parse(url);

        if (queryString.isValid()) {
            return queryString;
        }

        queryString = MyPathQueryString.parse(url);

        if (queryString.isValid()) {
            return queryString;
        }

        return MyNullQueryString.parse(url);
    }
}
