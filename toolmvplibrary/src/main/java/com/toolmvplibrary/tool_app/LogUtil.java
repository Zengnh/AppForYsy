package com.toolmvplibrary.tool_app;

import android.util.Log;

public class LogUtil {
    private static String tagDef = "TAG";

    public static void isDebug(boolean db, String TAG) {
        isDebug = db;
        tagDef = TAG;
    }

    private static boolean isDebug = false;

    private LogUtil() {
    }

    /***************** 1、error级别的日志管理 *****************/

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (isDebug)
            Log.e(tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (isDebug)
            Log.w(tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (isDebug)
            Log.i(tag, msg, tr);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (isDebug)
            Log.d(tag, msg, tr);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    //默认目标###################################
    public static void e(String msg) {
        if (isDebug)
            Log.e(tagDef, msg);
    }

    public static void w(String msg) {
        if (isDebug)
            Log.w(tagDef, msg);
    }

    public static void i(String msg) {
        if (isDebug)
            Log.i(tagDef, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(tagDef, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(tagDef, msg);
    }
}