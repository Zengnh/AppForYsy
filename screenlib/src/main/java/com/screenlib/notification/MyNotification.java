package com.screenlib.notification;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.screenlib.R;
import com.screenlib.activity.ScreenLibMainActivity;
import com.screenlib.broadcast.ButtonBroadcastReceiver;

/**
 * Created by Z on 2017-7-4.
 */

public class MyNotification {
    private static Context context;
    private int chickItem = 1;
    private NotificationManager mNotificationManager;

    private MyNotification(Context context) {
        this.context = context;
    }

    public static MyNotification instance;

    public static MyNotification getInstance(Context context) {
        if (instance == null) {
            instance = new MyNotification(context);
        }
        return instance;
    }

    public void setPosition(int chickitem) {
        this.chickItem = chickitem;
        makeNotification();
    }

    public void makeNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        // 此处设置的图标仅用于显示新提醒时候出现在设备的通知栏
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        // 1、创建一个自定义的消息布局 notification.xml
        // 2、在程序代码中使用RemoteViews的方法来定义image和text。然后把RemoteViews对象传到contentView字段
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.notifyed);
        setImageViewCheck(remoteView);
        //设置点击的事件
//        Intent buttonIntent = new Intent(ButtonBroadcastReceiver.actionName);
//        buttonIntent.putExtra(ButtonBroadcastReceiver.actionKey, ButtonBroadcastReceiver.Key0);
//        PendingIntent intent_paly = PendingIntent.getBroadcast(this, ButtonBroadcastReceiver.reqID, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteView.setOnClickPendingIntent(R.id.icon_lunacher, intent_paly);
        setButtonClicl(remoteView, ButtonBroadcastReceiver.Launcher, R.id.icon_lunacher, ButtonBroadcastReceiver.reqId0);
        setButtonClicl(remoteView, ButtonBroadcastReceiver.Key1, R.id.key_item1, ButtonBroadcastReceiver.reqId1);
        setButtonClicl(remoteView, ButtonBroadcastReceiver.Key2, R.id.key_item2, ButtonBroadcastReceiver.reqId2);
        setButtonClicl(remoteView, ButtonBroadcastReceiver.Key3, R.id.key_item3, ButtonBroadcastReceiver.reqId3);
        setButtonClicl(remoteView, ButtonBroadcastReceiver.Key4, R.id.key_item4, ButtonBroadcastReceiver.reqId4);
        setButtonClicl(remoteView, ButtonBroadcastReceiver.Key5, R.id.key_item5, ButtonBroadcastReceiver.reqId5);
        mBuilder.setContent(remoteView);
        // 3、为Notification的contentIntent字段定义一个Intent(注意，使用自定义View不需要setLatestEventInfo()方法)
        //这儿点击后简答启动Settings模块
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, ScreenLibMainActivity.class), 0);
        mBuilder.setAutoCancel(false);
        Notification noti = mBuilder.build();
//        noti.flags |= Notification.FLAG_ONGOING_EVENT;
//        noti.flags |= Notification.FLAG_ONGOING_EVENT;
        noti.contentIntent = contentIntent;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(noId, noti);
    }


    private void setImageViewCheck(RemoteViews remoteView) {
        remoteView.setImageViewResource(R.id.key_item1, R.mipmap.ic_color_0_normal);
        remoteView.setImageViewResource(R.id.key_item2, R.mipmap.ic_color_1_normal);
        remoteView.setImageViewResource(R.id.key_item3, R.mipmap.ic_color_2_normal);
        remoteView.setImageViewResource(R.id.key_item4, R.mipmap.ic_color_3_normal);
        remoteView.setImageViewResource(R.id.key_item5, R.mipmap.ic_color_4_normal);
        switch (chickItem) {
            case 1:
                remoteView.setImageViewResource(R.id.key_item1, R.mipmap.ic_color_0_pressed);
                break;
            case 2:
                remoteView.setImageViewResource(R.id.key_item2, R.mipmap.ic_color_1_pressed);
                break;
            case 3:
                remoteView.setImageViewResource(R.id.key_item3, R.mipmap.ic_color_2_pressed);
                break;
            case 4:
                remoteView.setImageViewResource(R.id.key_item4, R.mipmap.ic_color_3_pressed);
                break;
            case 5:
                remoteView.setImageViewResource(R.id.key_item5, R.mipmap.ic_color_4_pressed);
                break;
        }
    }


    private void setButtonClicl(RemoteViews remoteView, String key, int btnId, int reqId) {
        Intent buttonIntent = new Intent(ButtonBroadcastReceiver.actionName);
        buttonIntent.putExtra(ButtonBroadcastReceiver.actionKey, key);
//        buttonIntent.putExtra(ButtonBroadcastReceiver.actionKey, ButtonBroadcastReceiver.Key0);
        PendingIntent intent_paly = PendingIntent.getBroadcast(context, reqId, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteView.setOnClickPendingIntent(R.id.icon_lunacher, intent_paly);
        remoteView.setOnClickPendingIntent(btnId, intent_paly);
    }

    private final int noId = 1111;

    public void setCancelNotification() {
        mNotificationManager.cancel(noId);
    }

}
