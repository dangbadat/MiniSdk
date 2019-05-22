package com.minisdk.aysynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.minisdk.callback.RequestListener;
import com.minisdk.common.Common;
import com.minisdk.utils.TinyDB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Get extends AsyncTask<Void, Void, String> {

    private TinyDB tinyDB;
    private RequestListener requestListener;
    private String urlGet = "https://s825ir1mr1.execute-api.ap-southeast-1.amazonaws.com/getadspromo/adspromo";

    public Get(TinyDB tinyDB, RequestListener requestListener) {
        this.tinyDB = tinyDB;
        this.requestListener = requestListener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String json;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlGet);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

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

        json = response.toString();

        return json;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null && !s.equals("")) {
            tinyDB.putString(Common.JSON_ITEM, s);
            if (requestListener != null) {
                requestListener.onSuccess();
            }
        }
    }
}
