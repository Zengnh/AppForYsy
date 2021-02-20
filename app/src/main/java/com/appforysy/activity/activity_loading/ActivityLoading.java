package com.appforysy.activity.activity_loading;

import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appforysy.activity.activity_login.ActivityLogin;
import com.toolmvplibrary.activity_root.ActivityRootInit;

import com.appforysy.R;
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
        presenter.checkVersion();
    }

    private DialogStyleMy dialog;
    @Override
    public void checkV() {
        TextView txtView = new TextView(this);
        txtView.setText("版本更新");
         dialog = new DialogStyleMy(this, txtView, "确定",
                "取消", new DialogListener() {
            @Override
            public void click(String str) {
                dialog.dismiss();
                Intent intent = new Intent(ActivityLoading.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    @Override
    public void toLogin() {
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }
}
