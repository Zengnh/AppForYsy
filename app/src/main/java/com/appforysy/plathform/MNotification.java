package com.appforysy.plathform;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.appforysy.R;
import com.appforysy.activity.activity_loading.ActivityLoading;


/**
 * 通知栏管理
 */
public class MNotification {
    // 通知管理器
    private NotificationManager notificationManager;
    private Notification.Builder mBuilder;
    private Context context;
    private int Notification_ID_BASE = 11110;

    public MNotification(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        Notification_ID_BASE= (int) System.currentTimeMillis();
    }

    /**
     * 默认自定义通知栏
     */
    public void showDefaultNotify()throws Exception {
        if(context==null){
            return;
        }

        // 先设定RemoteViews
//        RemoteViews view_warn = new RemoteViews(context.getPackageName(), R.layout.notification_warn_layout);
        RemoteViews view_warn = new RemoteViews("com.workysy", R.layout.notification_layout);
        view_warn.setImageViewResource(R.id.warn_icon, R.mipmap.ic_launcher_mlq);
        mBuilder = new Notification.Builder(context);
        mBuilder.setContent(view_warn)
                .setWhen(System.currentTimeMillis())
                // 通知产生的时间，会在通知信息里显示
//                .setTicker(notiContext.TITLE)
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(false)// 不是正在进行的 true为正在进行 效果和.flag一样
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher_mlq);
        Notification notify = mBuilder.build();
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_mlq);
        notify.contentView = view_warn;
        // 通知被点击后，自动消失
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notify.contentIntent = getPendIntent();
        notificationManager.notify(Notification_ID_BASE, notify);
    }

    public PendingIntent getPendIntent() {
        Intent intent = new Intent(context, ActivityLoading.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        PendingIntent pendingInt = PendingIntent.getActivity(context, 0, intent, 0);
        return pendingInt;
    }
}
