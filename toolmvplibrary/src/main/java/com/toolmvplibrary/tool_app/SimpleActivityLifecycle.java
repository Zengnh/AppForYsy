package com.toolmvplibrary.tool_app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

public class SimpleActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private static SimpleActivityLifecycle instance;

    public static SimpleActivityLifecycle getInstance() {
        if (instance == null) {
            instance = new SimpleActivityLifecycle();
        }
        return instance;
    }

    public int count = 0;
    public int destoryCount = 0;

    private SimpleActivityLifecycle() {
    }

    private boolean isForeground = false;//应用是否处于前端

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        destoryCount++;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (count == 0) {
            isForeground = false;

            LogUtil.i( ">>>>>>>>>>>切到前台>>>>>>>>");
            if(interLifeCycle!=null){
                interLifeCycle.startApp();
            }
        }
        count++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        count--;
        if (count == 0) {
            LogUtil.i( ">>>>>>>>>>>切到后台>>>>>>>>");
            isForeground = true;
            if(interLifeCycle!=null){
                interLifeCycle.stopApp();
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        destoryCount--;
        if (destoryCount == 0) {
            Log.i("znh", "app destory");

        }
    }

    public boolean isForeground() {
        return isForeground;
    }

    public InterLifecycle interLifeCycle;

    public void setLifeCycle(InterLifecycle interLifeCycle) {
        this.interLifeCycle = interLifeCycle;
    }
    public interface InterLifecycle{
        void startApp();
        void stopApp();
    }
}