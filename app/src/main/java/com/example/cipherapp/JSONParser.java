package com.example.cipherapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class JSONParser {

    JSONObject jObj = null;
    String json = "";
    String result = "";

    public JSONParser() {
    }

    public String getJSON(String address, String cipherText) {
        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("cipher", cipherText);
            String requestParams = ParameterStringBuilder.getParamsString(parameters);
            URL url = new URL(address + requestParams);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            Reader streamReader = null;
            if (status > 299) {
                streamReader = new InputStreamReader(con.getErrorStream());
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
            }
            BufferedReader in = new BufferedReader(streamReader);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = in.readLine()) != null) {
                sb.append(line + "\n");
            }
            in.close();
            json = sb.toString();
            jObj = new JSONObject(json);
        } catch (IOException e) {
            Log.e("JSON Parser", "Error connecting to server " + e.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        try {
            String status = jObj.getString("status");
            if (status == "success") {
                result = jObj.getString("data");
            } else {
                result = jObj.getString("error");
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "JSON does not contain field " + e.toString());
        }

        return result;
    }
}

