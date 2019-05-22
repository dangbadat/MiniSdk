package com.minisdk.sdk;

import android.content.Context;

import com.minisdk.aysynctask.Get;
import com.minisdk.callback.RequestListener;
import com.minisdk.common.Common;
import com.minisdk.model.Item;
import com.minisdk.utils.MethodUtils;
import com.minisdk.utils.TinyDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class MiniSdk {

    private TinyDB tinyDB;
    private long timeBefore;
    private long timeCurrent;
    private ArrayList<Item> items;

    private static MiniSdk instance;

    static MiniSdk getInstance() {
        if (instance == null) {
            instance = new MiniSdk();
        }
        return instance;
    }

    private MiniSdk() {
    }

    void init(final Context context) {
        if (MethodUtils.isNetworkConnected(context)) {
            items = new ArrayList<>();
            tinyDB = new TinyDB(context);
            timeBefore = tinyDB.getLong(Common.TIME_GET, -1);
            timeCurrent = Calendar.getInstance().getTimeInMillis();
            if (MethodUtils.isGetItemFromServer(timeBefore, timeCurrent)) {
                new Get(tinyDB, new RequestListener() {
                    @Override
                    public void onSuccess() {
                        tinyDB.putLong(Common.TIME_GET, timeCurrent);
                        getItem(context);
                    }
                }).execute();
            } else {
                getItem(context);
            }
        }
    }

    void showAd(Context context) {
        if (MethodUtils.isNetworkConnected(context) && items != null && items.size() > 0) {
            int p = getPosition();
            if (p < items.size()) {
                String link = items.get(p).getLink_img();
                String package_name = items.get(p).getPackage_name();
                DialogAds dialogAds = new DialogAds(context, link, package_name);
                dialogAds.show();
            }
        }
    }

    void updatePosition() {
        tinyDB.putInt(Common.POSITION_SHOW, tinyDB.getInt(Common.POSITION_SHOW, 0) + 1);
        tinyDB.putInt(Common.COUNT_SHOW_AD, 0);
    }

    public void updateAppInstall(String packageName) {
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getPackage_name().equals(packageName)) {
                    items.remove(i);
                    return;
                }
            }
        }
    }

    private int getPosition() {
        int p = tinyDB.getInt(Common.POSITION_SHOW, 0);
        if (p >= items.size()) {
            tinyDB.putInt(Common.POSITION_SHOW, 0);
            tinyDB.putInt(Common.COUNT_SHOW_AD, 0);
            return 0;
        }

        int count = tinyDB.getInt(Common.COUNT_SHOW_AD, 0);
        int position;
        if (count < 5) {
            position = p;
        } else {
            if (p < items.size() - 1) {
                position = p + 1;
                tinyDB.putInt(Common.POSITION_SHOW, p + 1);
            } else {
                position = 0;
                tinyDB.putInt(Common.POSITION_SHOW, 0);
            }
            tinyDB.putInt(Common.COUNT_SHOW_AD, 0);
        }

        return position;
    }

    private void getItem(Context context) {
        String s = tinyDB.getString(Common.JSON_ITEM);
        items = MethodUtils.parseItem(context, s);
        Collections.sort(items, new ComparatorItem());
    }

    private class ComparatorItem implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            if (o1.getPriority() > o2.getPriority()) {
                return 1;
            }
            return -1;
        }
    }
}
