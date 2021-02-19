package com.toolmvplibrary.tool_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class ToolLanguage {

    public static final String LANGUAGE_ENGLISH="en";

    public static void changeAppLanguage(Context context, String lanAtr){
        Resources resources=context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (lanAtr.equals(LANGUAGE_ENGLISH)){
            configuration.locale = Locale.ENGLISH;
        }else {
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        resources.updateConfiguration(configuration,displayMetrics);
        resources.flushLayoutCache();
    }

    public static void changeLanFinish(Context activity,Class cs){


//        //重启app,这一步一定要加上，如果不重启app，可能打开新的页面显示的语言会不正确
        Intent intent = new Intent(activity, cs);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
