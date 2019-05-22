package com.minisdk.sdk;

import android.content.Context;

public class SdkManager {

    private static SdkManager instance;

    private SdkManager() {
    }

    public static SdkManager getInstance() {
        if (instance == null) {
            instance = new SdkManager();
        }
        return instance;
    }

    public void init(Context context) {
        MiniSdk.getInstance().init(context);
    }

    public void showAd(Context context) {
        MiniSdk.getInstance().showAd(context);
    }

    void updateApp(String packageName) {
        MiniSdk.getInstance().updateAppInstall(packageName);
    }
}
