package com.appforysy.activity.activity_login;

import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.activity_root.RootPresenter;
import com.toolmvplibrary.tool_app.LogUtil;

public class PresenterLogin extends RootPresenter<InterViewLogin, ModelLogin> {


    @Override
    public ModelLogin createModel() {
        return new ModelLogin();
    }

    public void clickLogin() {
        showLoading();
//        model.params();
        model.params(getViewInterface().getUserName(), getViewInterface().getUserPwd());
        model.login(new InterCallBack<String>() {
            @Override
            public void result(String res) {
                LogUtil.i("znh_login", "login succ");
                showToast("请求成功");
                hitLoading();
                getViewInterface().loginSuccToNext();
            }

            @Override
            public void err(String msg) {
                LogUtil.i("znh_login", "login err");
                hitLoading();
            }
        });
    }

}
