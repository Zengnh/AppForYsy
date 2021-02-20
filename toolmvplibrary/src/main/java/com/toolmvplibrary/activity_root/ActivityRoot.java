package com.toolmvplibrary.activity_root;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.toolmvplibrary.R;
import com.toolmvplibrary.tool_app.ToolKeyBoard;
import com.toolmvplibrary.tool_app.ToolShotScreen;
import com.toolmvplibrary.tool_app.ToolSys;
import java.util.ArrayList;
import java.util.List;


public abstract class ActivityRoot<P extends RootPresenter> extends AppCompatActivity implements RootInterUi {
    protected P presenter;

    protected abstract P setPresenter();
    /***
     * 是否全屏。
     * @return
     */
    protected boolean isFullWindow() {
        return true;
    }

    /**
     * 状态栏颜色
     * 默认黑色
     * 关联isFullWindow 开次属性才有用，默认打开，
     *
     * @return
     */
    protected boolean isStateItemBack() {
        return true;
    }

    /**
     * 禁止截屏
     *
     * @return
     */
    protected boolean enableScreen() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

//
//    /**
//     * 设置布局
//     *
//     * @return
//     */
//    public abstract int setCutLayout();
//
//    /**
//     * 初始化视图
//     */
//    public abstract void initView();
//
//    public abstract void initData();

    protected void befSetContentView() {
    }
    protected void befSuperCreate() {
    }

    private int actionBarHight=0;

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (enableScreen()) {
            ToolShotScreen.enableScreen(this);
        }
        befSuperCreate();
        super.onCreate(savedInstanceState);
        getActionBarHighth();
        befSetContentView();
//        setContentView(setCutLayout());
        if (isFullWindow()) {
            setBlackFull();
            setAndroidNativeLightStatusBar(this, isStateItemBack());
        }
        presenter = setPresenter();
        if (presenter != null) {
            presenter.attUIView(this);
        }
    }


    private void getActionBarHighth() {
        actionBarHight=getStatusBarHeight(this);
        if (actionBarHight <= 0) {
            actionBarHight = ToolSys.dip2px(this, 25);
        }
    }


    private Toast toast;

    @Override
    public void showToast(final String str) {
        Log.i("znh_root", "@=" + str);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(ActivityRoot.this, str, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                } else {
                    toast.setText(str);
                }
                toast.show();
            }
        });
    }


    private Dialog progressDialog;

    @Override
    public void showLoading() {
        Log.i("znh_root", "@=show_progress");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = new Dialog(ActivityRoot.this);
                    progressDialog.setContentView(R.layout.layout_top_loadng);
                    progressDialog.getWindow().setBackgroundDrawableResource(R.drawable.share_loading_black70);
                    progressDialog.setCanceledOnTouchOutside(false);
                }
                progressDialog.show();
            }
        });
    }

    @Override
    public void hitLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void hitKeyBox() {
        ToolKeyBoard.hitKeyBox(this);
    }

    @Override
    public Context getContext() {
        return this;
    }


    //    设置满屏状态
    protected void setBlackFull() {
//透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT); //也可以设置成灰色透明的，比较符合Material Design的风格
        }

//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }


    }

    //    设置状态栏文字是黑色还是白色
    protected void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    //    点击edit外关闭软件盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    hitSoftInputTouchOutEdt();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    //    点击Editext外隐藏软键盘事件，子类复写此方法即可得到该监听
    protected void hitSoftInputTouchOutEdt() {

    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1] - 30;
            int bottom = top + v.getHeight() + 60;
            int right = left + v.getWidth();
//            if (event.getX() > left && event.getX() < right
//                    && event.getY() > top && event.getY() < bottom) {
            return !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight(Context context) {
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


    //###########################################################################################

    private PopupWindow popupWindowBase;
    private ListView listContentBase;
    private TextView toCancelBase;
    private View rootView;
    private AdapterPopBase adapterPopBase;
    private List<String> dataListBase = new ArrayList<>();
    private ItemClick interListener;
    public void showPopWindow(View view, List<String> dl, ItemClick listener) {
        this.interListener = listener;
        if (popupWindowBase == null) {
            popupWindowBase = new PopupWindow(this);
            View pView = LayoutInflater.from(this).inflate(R.layout.popupwindow_base, null);
            rootView = pView.findViewById(R.id.rootView);
            listContentBase = pView.findViewById(R.id.listContent);
            toCancelBase = pView.findViewById(R.id.toCancel);
            popupWindowBase.setContentView(pView);
            popupWindowBase.setOutsideTouchable(true);
            popupWindowBase.setBackgroundDrawable(new BitmapDrawable());
            popupWindowBase.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindowBase.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindowBase.setOutsideTouchable(true);
            adapterPopBase = new AdapterPopBase(dataListBase);
            listContentBase.setAdapter(adapterPopBase);
            listContentBase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popupWindowBase.dismiss();
                    if (interListener != null) {
                        interListener.itemClick(position);
                    }
                }
            });
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindowBase.dismiss();
                }
            });
            toCancelBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindowBase.dismiss();
                }
            });
        }
        this.dataListBase.clear();
        this.dataListBase.addAll(dl);
        adapterPopBase.notifyDataSetChanged();
        popupWindowBase.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

}
