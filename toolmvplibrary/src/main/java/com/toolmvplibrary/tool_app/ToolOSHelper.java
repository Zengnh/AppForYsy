package com.toolmvplibrary.tool_app;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 设备系统辅助类
 */
public final class ToolOSHelper {

    public static boolean isHuawei() {

        Log.i("znh_build","build----------"+getDeviceBrand()+"   "+ getSystemModel());

        String strBuild=getDeviceBrand().toLowerCase();
        if(strBuild.equals("honor")){
            return true;
        }

        if(ToolOSRom.isEmui()){
            return true;
        }

        String str = getSystem();
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.equals(SYS_EMUI)) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isXiaomi() {
        String str = getSystem();
        String strBuild=getDeviceBrand().toLowerCase();

        if(strBuild.equals("xiaomi")){
            return true;
        }

        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.equals(SYS_MIUI)) {
            return true;
        } else {
            return false;
        }
    }

    public static final String SYS_EMUI = "sys_emui";
    public static final String SYS_MIUI = "sys_miui";
    public static final String SYS_FLYME = "sys_flyme";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";


    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }


    public static String getSystem() {
        String SYS = null;
        try {
            Properties prop = new Properties();
            FileInputStream inPutStream=new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
            prop.load(inPutStream);
            if (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                SYS = SYS_MIUI;//小米
            } else if (prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                    || prop.getProperty(KEY_EMUI_VERSION, null) != null
                    || prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null) {
                SYS = SYS_EMUI;//华为
            } else if (getMeizuFlymeOSFlag().toLowerCase().contains("flyme")) {
                SYS = SYS_FLYME;//魅族
            };
        } catch (IOException e) {
            e.printStackTrace();
            return SYS;
        }
        return SYS;
    }

    public static String getMeizuFlymeOSFlag() {
        return getSystemProperty("ro.build.display.id", "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }


}
