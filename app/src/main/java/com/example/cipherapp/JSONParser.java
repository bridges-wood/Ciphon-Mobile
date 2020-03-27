package com.example.cipherapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JSONParser {

    /**
     * Opens connection to API hosted at {@param address}.
     *
     * @param address    The URL at which the API is hosted.
     * @param cipherText The text to be passed to the API for decryption.
     * @return The decrypted text, or an error message describing the failure.
     */
    public static String getJSON(String address, String cipherText) {
        JSONObject jObj = null;
        String result = "";
        String json = "";
        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("cipher", cipherText);
            String requestParams = ParameterStringBuilder.getParamsString(parameters);
            URL url = new URL(address + "?" + requestParams);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET"); // Makes a get request.

            int status = con.getResponseCode();
            Reader streamReader;
            if (status > 299) { // 200 - 299 is all successful HTTP statuses.
                streamReader = new InputStreamReader(con.getErrorStream());
                // If the request has failed we look at the error log.
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
            }
            BufferedReader in = new BufferedReader(streamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            in.close();
            json = sb.toString();
            jObj = new JSONObject(json);
        } catch (IOException e) {
            Log.e("JSON Parser", "Error connecting to server " + e.toString());
            result = "Internal failure in connecting to API - please try again.";
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            result = "Internal failure in parsing API response - please try again.";
            result += "Response " + json; // If there was an error, pass it to the user.
        }

        if (!result.isEmpty()) return result;

        try {
            String status = jObj.getString("status");
            if (status.equals("success")) {
                result = jObj.getString("data");
            } else {
                result = jObj.getString("error");
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "JSON does not contain field " + e.toString());
            result = "Internal failure in parsing API response - please try again.";
        }

        return result;
    }
}

