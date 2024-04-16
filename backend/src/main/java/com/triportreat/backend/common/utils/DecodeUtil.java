package com.triportreat.backend.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DecodeUtil {
    private static final String DECODE_TYPE = "UTF-8";

    public static String decodeUrl(String url) {
        try {
            return URLDecoder.decode(url, DECODE_TYPE);
        } catch (UnsupportedEncodingException e) {

        }
        return url;
    }

}
