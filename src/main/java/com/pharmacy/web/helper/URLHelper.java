package com.pharmacy.web.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Alexander on 03.01.2016.
 */
public class URLHelper {

    private static final Logger LOG = LoggerFactory.getLogger(URLHelper.class);


    public String encodeURL(String url) {
        String result = url.replace("/", "");
        try {
            result = URLEncoder.encode(result, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            LOG.warn("URL can not parsed {}", url);
        }
        return result;
    }
}
