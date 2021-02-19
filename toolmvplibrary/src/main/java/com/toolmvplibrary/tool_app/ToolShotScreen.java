package com.toolmvplibrary.tool_app;

import android.app.Activity;
import android.view.WindowManager;

public class ToolShotScreen
{
//    开启禁止截屏
    public static void enableScreen(Activity act){
        act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);//页面是保密的。禁止截屏
    }

}
