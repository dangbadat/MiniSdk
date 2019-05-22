package com.minisdk.aysynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.minisdk.sdk.SdkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Post extends AsyncTask<Void, Void, Void> {

    private String link = "https://s825ir1mr1.execute-api.ap-southeast-1.amazonaws.com/getadspromo/adspromo";
    private String packangeName;

    public Post(String packangeName) {
        this.packangeName = packangeName;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String jsonString;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(link);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            String params = "{\"package_name\" : \"" + packangeName + "\"}";
            writer.write(params);

            writer.flush();
            writer.close();
            os.close();

            con.connect();

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        jsonString = response.toString();
        return null;
    }
}
