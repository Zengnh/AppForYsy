package com.photolib.selectpic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

public class ToolScreenDensity {

    private ToolScreenDensity() {
    }

    private volatile static ToolScreenDensity instance;

    public static ToolScreenDensity getInstance() {
        if (instance == null) {
            synchronized (ToolScreenDensity.class) {
                if (instance == null) {
                    instance = new ToolScreenDensity();
                }
            }
        }
        return instance;
    }

    private int viewHight;
    private Activity act;

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {


        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;


    }

    public int getRootViewHight(Activity act) {
        if (viewHight == 0) {
            if (act != null) {
                Rect r = new Rect();
                act.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                viewHight = r.bottom - r.top;
            }
        }
        return viewHight;
    }

    public void setRootViewHight(Activity act) {
        if (act != null) {
            Rect r = new Rect();
            act.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            viewHight = r.bottom - r.top;
        }
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}