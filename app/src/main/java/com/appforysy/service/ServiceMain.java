package com.appforysy.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.toolmvplibrary.tool_app.LogUtil;


public class ServiceMain extends Service {
    private MyBinder myBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i("onBind");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("onCreate");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public void testFunct() {
        LogUtil.i("exe func test");
    }


    public class MyBinder extends Binder {
        public ServiceMain getService() {
            return ServiceMain.this;
        }
    }
}
