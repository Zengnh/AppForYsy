package com.appforysy.activity.activity_loading;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appforysy.activity.activity_guide.ActivityGuide;
import com.appforysy.activity.activity_main.ActivityMain;
import com.toolmvplibrary.activity_root.ActivityRootInit;

import com.appforysy.R;
import com.toolmvplibrary.tool_app.ToolPreferences;
import com.toolmvplibrary.tool_premission.ToolAppPremission;
import com.toolmvplibrary.view.DialogListener;
import com.toolmvplibrary.view.DialogStyleMy;

public class ActivityLoading extends ActivityRootInit<PresenternLoading> implements InterLoading {
    @Override
    protected PresenternLoading setPresenter() {
        return new PresenternLoading();
    }

    @Override
    public int setCutLayout() {
        return R.layout.activity_loading;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        if (ToolAppPremission.hasCamerPremission(this)) {
            initPremissNext();
        } else {
            ToolAppPremission.reqWriteDestory(this, 111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            if (ToolAppPremission.hasCamerPremission(this)) {
                finish();
            } else {
                initPremissNext();
            }
        }
    }

    public void initPremissNext() {
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String toGuide = ToolPreferences.getString(ActivityLoading.this, "loading");
            if (TextUtils.isEmpty(toGuide)) {
                toGuide();
                ToolPreferences.setString(ActivityLoading.this, "loading","1");
            } else {
                toLogin();
            }
        }
    };

    @Override
    public void checkV() {

    }

    @Override
    public void toLogin() {
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
        finish();
    }

    public void toGuide() {
        Intent intent = new Intent(this, ActivityGuide.class);
        startActivity(intent);
        finish();
    }
}
