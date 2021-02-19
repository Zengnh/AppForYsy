package com.appforysy.plathform;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.appforysy.ConfigAppInfo;
import com.appforysy.activity.PermissionActivity;
import com.toolmvplibrary.tool_app.LogUtil;
import com.appforysy.R;
import com.appforysy.activity.activity_loading.ActivityLoading;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**

 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 *
 * @author mayixiang
 */
public class XiaomiMessageReceiver extends PushMessageReceiver {

    private String mRegId;
    private String mTopic;
    private String mAlias;
    private String mAccount;
    private String mStartTime;
    private String mEndTime;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        Log.i(logTag,
                "onReceivePassThroughMessage is called. " + message.toString());
        String log = context.getString(R.string.recv_passthrough_message, message.getContent());
//        MainActivity.logList.add(0, getSimpleDate() + " " + log);
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        Message msg = Message.obtain();
        msg.obj = log;

        try {
            MNotification notification = new MNotification(context);
            notification.showDefaultNotify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        Log.i(logTag, "onNotificationMessageClicked is called. " + message.toString());
        String log = context.getString(R.string.click_notification_message, message.getContent());
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        Message msg = Message.obtain();
        if (message.isNotified()) {
            msg.obj = log;
        }
        try {
            Intent intent = new Intent(context, ActivityLoading.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        Log.i(logTag, "onNotificationMessageArrived is called. " + message.toString());
        String log = context.getString(R.string.arrive_notification_message, message.getContent());
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        Message msg = Message.obtain();
        msg.obj = log;

//        try {
//            String str = PreferencesUtil.getStringAttr("noti");
//            JSONArray jsonArraySave = new JSONArray(str);
//            jsonArraySave.put(message.getNotifyId());
//
//            Log.i(logTag, "cut=" + message.getNotifyId() + "    all=" + jsonArraySave.length());
//            PreferencesUtil.saveObject("noti", jsonArraySave.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    public static String pushTokenXiaoMi="";

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        Log.i(logTag, "onCommandResult is called. " + message.toString());
        LogUtil.i("push","onCommandResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                Log.i(logTag, "onCommandResult  COMMAND_REGISTER is token---" + mRegId + "   ----" + message.getReason());
                log = context.getString(R.string.register_success);
                LogUtil.i("push","xiaomi "+ log);
                pushTokenXiaoMi = mRegId;
                if (!TextUtils.isEmpty(ConfigAppInfo.getInstance().getUserId())) {
                    MiPushClient.setAlias(context, ConfigAppInfo.getInstance().getUserId(), mRegId);
                }
            } else {
                log = context.getString(R.string.register_fail);
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {

            Log.i(logTag, "@@@@@@@@@@@@ COMMAND_SET_ALIAS @@@@@@@@@@@@");
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
                LogUtil.i("push","xiaomi  设置别名完成-- up service");
                if (!TextUtils.isEmpty(ConfigAppInfo.getInstance().getUserId())) {
//                    if (ToolOSHelper.isXiaomi()) {
//                        PIMManager.getInstance().getClientService()
//                                .RegPushAndroid(3,
//                                        ConfigAppInfo.getInstance().getUserId()
//                                        , context.getPackageName(),
//                                        false, 0, 0);
//                    } else{
//                        PIMManager.getInstance().getClientService()
//                                .RegPushAndroid(4,
//                                        ConfigAppInfo.getInstance().getUserId()
//                                        , context.getPackageName(),
//                                        false, 0, 0);
//                    }
                }
                Log.i(logTag, "COMMAND_SET_ALIAS is token---" + mAlias + "   ----" + message.getReason());
                log = context.getString(R.string.set_alias_success, mAlias);
            } else {
                log = context.getString(R.string.set_alias_fail, message.getReason());
                LogUtil.i("push","xiaomi  "+log+"  设置失败，提交token");
                if (!TextUtils.isEmpty(ConfigAppInfo.getInstance().getUserId())) {
//                    if (ToolOSHelper.isXiaomi()) {
//                        PIMManager.getInstance().getClientService()
//                                .RegPushAndroid(3,
//                                        pushTokenXiaoMi+""
//                                        , context.getPackageName(),
//                                        false, 0, 0);
//                    } else{
//                        PIMManager.getInstance().getClientService()
//                                .RegPushAndroid(4,
//                                        pushTokenXiaoMi+""
//                                        , context.getPackageName(),
//                                        false, 0, 0);
//                    }
                }
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                Log.i(logTag, "COMMAND_UNSET_ALIAS is token---" + mAlias + "   ----" + message.getReason());
                mAlias = cmdArg1;
                log = context.getString(R.string.unset_alias_success, mAlias);
            } else {
                log = context.getString(R.string.unset_alias_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_SET_ACCOUNT.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAccount = cmdArg1;
                Log.i(logTag, "COMMAND_SET_ACCOUNT is token---" + mAccount + "   ----" + message.getReason());
                log = context.getString(R.string.set_account_success, mAccount);
            } else {
                log = context.getString(R.string.set_account_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSET_ACCOUNT.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAccount = cmdArg1;
                Log.i(logTag, "COMMAND_UNSET_ACCOUNT is token---" + mAccount + "   ----" + message.getReason());
                log = context.getString(R.string.unset_account_success, mAccount);
            } else {
                log = context.getString(R.string.unset_account_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
                log = context.getString(R.string.subscribe_topic_success, mTopic);
            } else {
                log = context.getString(R.string.subscribe_topic_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
                log = context.getString(R.string.unsubscribe_topic_success, mTopic);
            } else {
                log = context.getString(R.string.unsubscribe_topic_fail, message.getReason());
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
                log = context.getString(R.string.set_accept_time_success, mStartTime, mEndTime);
            } else {
                log = context.getString(R.string.set_accept_time_fail, message.getReason());
            }
        } else {
            log = message.getReason();
        }
//        MainActivity.logList.add(0, getSimpleDate() + "    " + log);
        Log.i(logTag, "onCommandResult is called. " + log);
        Message msg = Message.obtain();
        msg.obj = log;
        LogUtil.i("push","xiaomi receiver log=" + log);
//        DemoApplication.getHandler().sendMessage(msg);
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        Log.i(logTag, "onReceiveRegisterResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
//               注册成功：
                log = context.getString(R.string.register_success);
            } else {
                log = context.getString(R.string.register_fail);
            }
        } else {
            log = message.getReason();
        }
        Log.i(logTag, "onCommandResult is called. " + log);
        Message msg = Message.obtain();
        msg.obj = log;
//        DemoApplication.getHandler().sendMessage(msg);
    }

    @Override
    public void onRequirePermissions(Context context, String[] permissions) {
        super.onRequirePermissions(context, permissions);
        Log.i(logTag, "onRequirePermissions is called. need permission" + arrayToString(permissions));

        if (Build.VERSION.SDK_INT >= 23 && context.getApplicationInfo().targetSdkVersion >= 23) {
            Intent intent = new Intent();
            intent.putExtra("permissions", permissions);
            intent.setComponent(new ComponentName(context.getPackageName(), PermissionActivity.class.getCanonicalName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static String getSimpleDate() {
        return new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date());
    }

    public String arrayToString(String[] strings) {
        String result = " ";
        for (String str : strings) {
            result = result + str + " ";
        }
        return result;
    }
    private String logTag = "znh_xiaomi";
}

