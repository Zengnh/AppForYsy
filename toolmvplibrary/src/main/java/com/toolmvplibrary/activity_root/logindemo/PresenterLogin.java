package com.toolmvplibrary.activity_root.logindemo;

import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.activity_root.RootPresenter;

public class PresenterLogin extends RootPresenter<LoginManager.LoginView, LoginManager.LoginModel> implements LoginManager.PresenterLogin {
    @Override
    public void login(String name, String pwd) {
        model.login(name, pwd, new InterCallBack<String>() {
            @Override
            public void result(String res) {

            }

            @Override
            public void err(String msg) {

            }
        });
    }

    @Override
    public LoginManager.LoginModel createModel() {
        return new LoginModel();
    }
}
