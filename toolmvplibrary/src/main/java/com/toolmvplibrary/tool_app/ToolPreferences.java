package com.toolmvplibrary.tool_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class ToolPreferences {
    private static final String PREFERENCES_FILE = "app_config";
    /**
     * 删除某个key
     */
    public static void delKey(Context context,String key) {
        if(TextUtils.isEmpty(key)){
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.apply();
    }

    /**
     * 保存String类型
     */
    public static void setString(Context context,String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (value == null) {
            edit.remove(key);
        } else {
            edit.putString(key, value);
        }
        edit.apply();
    }
    public static String getString(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

}
