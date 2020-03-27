package com.example.cipherapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Class that handles the construction of the query string in the request URL.
 */
class ParameterStringBuilder {

    /**
     * Constructs the query string.
     *
     * @param params A Map containing the keys and values to be passed to the API.
     * @return A query string.
     * @throws UnsupportedEncodingException Thrown when the encoding of the map entries is not UTF-8.
     */
    static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }
}
