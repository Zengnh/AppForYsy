package com.toolmvplibrary.tool_app;

import android.app.Service;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

/**
 * 默认铃声及震动
 */
public class ToolRecMsgVoiceVir {

    //<uses-permission android:name="android.permission.WRITE_SETTINGS" />
    //开始播放
    public static void playRing(Context context) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }

//<uses-permission android:name="android.permission.VIBRATE" />

    /**
     * 让手机振动milliseconds毫秒
     */
    public static void vibrate(Context context, long milliseconds) {
        try{

            Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
            if (vib.hasVibrator()) {  //判断手机硬件是否有振动器
                vib.vibrate(milliseconds);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 让手机以我们自己设定的pattern[]模式振动
     * long pattern[] = {1000, 20000, 10000, 10000, 30000};
     */
    public static void vibrate(Context context, long[] pattern, int repeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if (vib.hasVibrator()) {
            vib.vibrate(pattern, repeat);
        }
    }

    /**
     * 取消震动
     */
    public static void virateCancle(Context context) {
        //关闭震动
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }
}
