package com.appforysy.plathform;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.toolmvplibrary.tool_app.LogUtil;
import com.appforysy.activity.activity_loading.ActivityLoading;
import com.toolmvplibrary.tool_app.SimpleActivityLifecycle;

public class ToMainActivityBroadcastReceiver extends BroadcastReceiver {
//    public static final String IS_TO_FIRST_FRAGMENT = "isToFirstFragment";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            //用这个方法实现点击notification后的事件  不知为何不能自动清掉已点击的notification  故自己手动清就ok了
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(intent.getIntExtra("notificationId", -1));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Toast.makeText(context, "测试数据", Toast.LENGTH_LONG).show();
        if (SimpleActivityLifecycle.getInstance().isForeground()) {
            LogUtil.i("znh", "------------on broadcast  receiver-------------");
            Intent toMainActivityIntent = new Intent(context, ActivityLoading.class);
//            Intent toMainActivityIntent = new Intent(context, MainActivity2.class);
            toMainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(toMainActivityIntent);
        }
    }
}