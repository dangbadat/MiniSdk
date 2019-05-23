package com.minisdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;

import com.minisdk.glide.Glide;
import com.minisdk.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MethodUtils {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isAppInstall(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        List<ApplicationInfo> applicationInfos = manager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo app : applicationInfos) {
            if (manager.getLaunchIntentForPackage(app.packageName) != null) {
                if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0 || (app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {

                } else {
                    if (app.packageName.equals(packageName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void openAppInGooglePlay(Context context, String packageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    public static boolean isGetItemFromServer(long timeBefore, long timeCurrent) {
        long twoHour = 2 * 60 * 60 * 1000;
        if (timeBefore + twoHour <= timeCurrent) {
            return true;
        }
        return false;
    }

    public static ArrayList<Item> parseItem(Context context, String json) {
        ArrayList<Item> items = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("Items");
            for (int i = 0; i < jsonArray.length(); i++) {
                Item item = new Item();
                JSONObject object = (JSONObject) jsonArray.get(i);
                String package_name = object.getString("package_name");

                String link_img = object.getString("link_img");
                int priority = object.getInt("priority");

                item.setLink_img(link_img);
                item.setPriority(priority);
                item.setPackage_name(package_name);

                if (!isAppInstall(context, package_name)) {
                    items.add(item);
                    Glide.with(context).load(link_img).preload();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }
}
