package com.minisdk.sdk;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.minisdk.R;
import com.minisdk.common.Common;
import com.minisdk.glide.Glide;
import com.minisdk.glide.load.DataSource;
import com.minisdk.glide.load.engine.GlideException;
import com.minisdk.glide.request.RequestListener;
import com.minisdk.glide.request.target.Target;
import com.minisdk.receiver.CheckAppInstall;
import com.minisdk.utils.MethodUtils;
import com.minisdk.utils.TinyDB;

import static android.content.Context.ALARM_SERVICE;


public class DialogAds extends Dialog {

    private Context context;
    private ImageView image;
    private ImageView exit;
    private ProgressBar progressBar;
    private String link_img;
    private String package_name;
    private TinyDB tinyDB;

    public DialogAds(Context context, String link, String packagaName) {
        super(context);
        link_img = link;
        package_name = packagaName;
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void show() {
        tinyDB.putInt(Common.COUNT_SHOW_AD, tinyDB.getInt(Common.COUNT_SHOW_AD, 0) + 1);
        super.show();
    }

    private void init(Context context) {
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (isFullScreen(activity)) {
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
            this.getWindow().getDecorView().setSystemUiVisibility(activity.getWindow().getDecorView().getSystemUiVisibility());
        }

        setContentView(R.layout.view_ad);
        tinyDB = new TinyDB(getContext());
        findViews();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setData();
    }

    private void findViews() {
        progressBar = findViewById(R.id.progressBar);
        image = findViewById(R.id.image);
        exit = findViewById(R.id.exit);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmCheckAppInstall(context, package_name);
                MiniSdk.getInstance().updatePosition();
                MethodUtils.openAppInGooglePlay(context, package_name);
                dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setData() {
        Glide.with(getContext()).load(link_img).optionalFitCenter().addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                dismiss();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                exit.setVisibility(View.VISIBLE);
                return false;
            }
        }).into(image);
    }

    private boolean isFullScreen(Activity context) {
        int flg = context.getWindow().getAttributes().flags;
        boolean flag = false;
        if ((flg & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            flag = true;
        }
        return flag;
    }

    private void alarmCheckAppInstall(Context context, String package_name) {
        String key = Common.WAIT_CHECKING + package_name;
        boolean waitingCheck = tinyDB.getBoolean(key, false);
        if (!waitingCheck) {
            int requestCode = tinyDB.getInt(Common.REQUEST_CODE_ALARM, 0);
            Intent intent = new Intent(context, CheckAppInstall.class);
            intent.putExtra(Common.PACKAGE_NAME, package_name);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode + 1, intent, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Common.TIME_DELAY_CHECK_APP_INSTALL, pendingIntent);
            tinyDB.putBoolean(key, true);
            tinyDB.putInt(Common.REQUEST_CODE_ALARM, requestCode + 1);
        }
    }
}
