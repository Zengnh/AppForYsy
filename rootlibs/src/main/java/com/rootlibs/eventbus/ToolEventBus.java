package com.rootlibs.eventbus;

import org.greenrobot.eventbus.EventBus;

//https://github.com/greenrobot/EventBus
// implementation 'org.greenrobot:eventbus:3.2.0'
public class ToolEventBus {
//    发送
//    EventBus.getDefault().post(new MessageEvent());
//    接收
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(MessageEvent event) {/* Do something */};


//    配置
//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }

    public static void onStart(Object subscriber){
        EventBus.getDefault().register(subscriber);
    }
    public static void onStop(Object subscriber){
        EventBus.getDefault().unregister(subscriber);
    }
    public static void postEvent(Object event){
        EventBus.getDefault().post(event);
    }
}
