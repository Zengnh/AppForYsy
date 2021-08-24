package com.mykotlin.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.mykotlin.R;
import com.mykotlin.activity.activity_bubble.BubbleActivity;

public class ToolJavaNotiManager {
    private String channel_id = "channel_id";
    private String channel_name = "channel_name";

    public NotificationCompat.Builder getNotificationBuilder(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name,
                    NotificationManager.IMPORTANCE_DEFAULT);
            //是否绕过请勿打扰模式
            channel.canBypassDnd();
            //闪光灯
            channel.enableLights(true);
            //锁屏显示通知
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            //闪关灯的灯光颜色
            channel.setLightColor(Color.RED);
            //桌面launcher的消息角标
            channel.canShowBadge();
            //是否允许震动
            channel.enableVibration(true);
            //获取系统通知响铃声音的配置
            channel.getAudioAttributes();
            //获取通知取到组
            channel.getGroup();
            //设置可绕过  请勿打扰模式
            channel.setBypassDnd(true);
            //设置震动模式
            channel.setVibrationPattern(new long[]{100, 100, 200});
            //是否会有灯光
            channel.shouldShowLights();
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, channel_id);
        notification.setContentTitle("新消息来了");
        notification.setContentText("周末到了，不用上班了");
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setAutoCancel(true);
        return notification;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createPop(Context mContext){
        // Create bubble intent
        Intent target = new Intent(mContext, BubbleActivity.class);
        PendingIntent bubbleIntent =PendingIntent.getActivity(mContext, 0, target, 0 /* flags */);

// Create bubble metadata
//        Notification.BubbleMetadata bubbleData =
//                new Notification.BubbleMetadata.Builder()
//                        .setDesiredHeight(600)
//                        .build();
////        bubbleIntent,Icon.createWithResource(mContext, R.drawable.image_rolate))

        Notification.BubbleMetadata bubbleData =
                new Notification.BubbleMetadata.Builder()
                        .setDesiredHeight(600)
                        .setIntent(bubbleIntent)
                        .setAutoExpandBubble(true)
                        .setSuppressNotification(true)
                        .build();

// Create notification

        Person chatPartner = new Person.Builder()
                .setName("Chat partner")
                .setImportant(true)
                .build();

        Notification.Builder builder =
                new Notification.Builder(mContext, ToolKtNotiManager.Companion.getInstance().getCHANNEL_ID())
                        .setContentIntent(bubbleIntent)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setBubbleMetadata(bubbleData)
                        .addPerson(chatPartner);



    }

}
