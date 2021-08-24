package com.mykotlin.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mykotlin.R
import com.mykotlin.activity_demo.ActivityCoroutines

class ToolKtNotiManager private constructor() {
    companion object {
        val instance: ToolKtNotiManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ToolKtNotiManager() }
    }

    val CHANNEL_ID = "testchannelid";

    //    创建普通notification
    fun createSimple(context: Context) {
        val intent = Intent(context, ActivityCoroutines::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("textTitle")
            .setContentText("textContent")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
//            .setStyle(NotificationCompat.BigTextStyle()
//                    .bigText("Much longer text that cannot fit one line...")
//            )//设置大窗口
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

//        createNotificationChannel(context);
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(110, builder.build())
        }

    }

    @SuppressLint("WrongConstant")
    fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val descriptionText = "channel_description"
            val importance = NotificationManager.IMPORTANCE_MAX
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            channel.enableLights(true)
            channel.setLightColor(Color.RED)
            // 设置通知出现时的震动（如果 android 设备支持的话）
            channel.enableVibration(true)
            channel.setVibrationPattern(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))

            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createDownloadNoti(context: Context) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setContentTitle("Picture Download")
            setContentText("Download in progress")
            setSmallIcon(R.mipmap.ic_launcher)
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setCategory(NotificationCompat.CATEGORY_MESSAGE)
        }
        val PROGRESS_MAX = 100
        val PROGRESS_CURRENT = 0
        NotificationManagerCompat.from(context).apply {
            // Issue the initial notification with zero progress
            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            notify(notificationId, builder.build())
            // Do the job here that tracks the progress.
            // Usually, this should be in a
            // worker thread
            // To show progress, update PROGRESS_CURRENT and update the notification with:
            // builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
            // notificationManager.notify(notificationId, builder.build());

            // When done, update the notification one more time to remove the progress bar
            builder.setContentText("Download complete")
                .setProgress(0, 0, false)
            notify(notificationId, builder.build())
        }
    }

    private val notificationId = 1234


    fun toSetNoti(context: Context) {
        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, CHANNEL_ID)
        context.startActivity(intent)
    }



}