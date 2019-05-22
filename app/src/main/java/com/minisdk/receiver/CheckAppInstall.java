package com.minisdk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.minisdk.aysynctask.Post;
import com.minisdk.common.Common;
import com.minisdk.sdk.SdkManager;
import com.minisdk.utils.MethodUtils;
import com.minisdk.utils.TinyDB;

public class CheckAppInstall extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TinyDB tinyDB = new TinyDB(context);
        String packageNameCheck = intent.getStringExtra(Common.PACKAGE_NAME);
        boolean isInstall = MethodUtils.isAppInstall(context, packageNameCheck);
        if (isInstall) {
            new Post(packageNameCheck).execute();
            tinyDB.putBoolean(Common.WAIT_CHECKING + packageNameCheck, false);
            SdkManager.getInstance().updateApp(packageNameCheck);
        }
    }
}
