package com.screenlib.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.screenlib.MainService;
import com.screenlib.activity.ActivityOpenState;
import com.screenlib.notification.MyNotification;
import com.screenlib.utils.SharedMemory;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * （通知栏中的点击事件是通过广播来通知的，所以在需要处理点击事件的地方注册广播即可）
 * 广播监听按钮点击事件
 */
public class ButtonBroadcastReceiver extends BroadcastReceiver {
    public static final String actionName = "com.zeng.receiver";
    public static final String actionKey = "btn_item";
    public static final int reqID = 110;
    public static final String Key1 = "key1";
    public static final String Key2 = "key2";
    public static final String Key3 = "key3";
    public static final String Key4 = "key4";
    public static final String Key5 = "key5";
    public static final String Launcher = "launcher";

    public static final int reqId0 = 110;
    public static final int reqId1 = 111;
    public static final int reqId2 = 112;
    public static final int reqId3 = 113;
    public static final int reqId4 = 114;
    public static final int reqId5 = 115;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (shared == null) {
            shared = new SharedMemory(context);
        }
        String action = intent.getAction();
        if (action.equals(actionName)) {
            //通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
            String buttonId = intent.getStringExtra(actionKey);
            Intent i = new Intent(context, MainService.class);
            switch (buttonId) {
                case Launcher:
                    Intent intentTo = new Intent(context, ActivityOpenState.class);
                    intentTo.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentTo);
                    break;
                case Key1:
                    shared.setItem(1);
                    MyNotification.getInstance(context).setPosition(1);
                    changeGGB(255, 187, 120);
                    context.startService(i);
                    break;
                case Key2:
                    shared.setItem(2);
                    MyNotification.getInstance(context).setPosition(2);
                    changeGGB(255, 56, 0);
                    context.startService(i);
                    break;
                case Key3:
                    shared.setItem(3);
                    MyNotification.getInstance(context).setPosition(3);
                    changeGGB(255, 137, 18);
                    context.startService(i);
                    break;
                case Key4:
                    shared.setItem(4);
                    MyNotification.getInstance(context).setPosition(4);
                    changeGGB(255, 180, 107);
                    context.startService(i);
                    break;
                case Key5:
                    shared.setItem(5);
                    MyNotification.getInstance(context).setPosition(5);
                    changeGGB(255, 198, 130);
                    context.startService(i);
                    break;
            }
        }

    }

    private SharedMemory shared;

    private void changeGGB(int red, int green, int blue) {
        shared.setRed(red);
        shared.setGreen(green);
        shared.setBlue(blue);
    }
}