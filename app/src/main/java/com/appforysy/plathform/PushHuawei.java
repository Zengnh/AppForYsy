package com.appforysy.plathform;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.toolmvplibrary.tool_app.LogUtil;

//https://developer.huawei.com/consumer/cn/doc/HMSCore-References-V5/hmsmessageservice-0000001050173839-V5
public class PushHuawei extends HmsMessageService {

//    onMessageReceived(RemoteMessage message)
//
//    接收透传消息方法。


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        LogUtil.i("znh_push","huawei-push-"+remoteMessage.describeContents());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        LogUtil.i("znh_push","huawei-push-token="+s);
//        PIMManager.getInstance().getClientService()
//                .RegPushAndroid(2,s, PushHuawei.this.getPackageName(),false, 0, 0);
    }
}
