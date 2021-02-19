package com.appforysy.activity.activity_login;

import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.activity_root.PresenterRoot;
import com.toolmvplibrary.tool_app.LogUtil;

public class PresenterLogin extends PresenterRoot<InterViewLogin, ModelLogin> {

    public PresenterLogin(){
        model=new ModelLogin();
    }


    public void clickLogin() {
        interfaceView.showLoading();
//        model.params();
        model.params(interfaceView.getUserName(), interfaceView.getUserPwd());
        model.login(new InterCallBack<String>() {
            @Override
            public void result(String res) {
                LogUtil.i("znh_login", "login succ");
                interfaceView.showToast("请求成功");
                interfaceView.hitLoading();
                interfaceView.loginSuccToNext();
            }

            @Override
            public void err(String msg) {
                LogUtil.i("znh_login", "login err");
                interfaceView.hitLoading();
            }

        });
    }
}
