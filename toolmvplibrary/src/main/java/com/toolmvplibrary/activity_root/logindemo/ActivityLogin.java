package com.toolmvplibrary.activity_root.logindemo;

import android.view.View;
import android.widget.Button;

import com.toolmvplibrary.R;
import com.toolmvplibrary.activity_root.ActivityRoot;

public class ActivityLogin extends ActivityRoot<PresenterLogin> implements LoginManager.LoginView{
    @Override
    protected PresenterLogin setPresenter() {
        return new PresenterLogin();
    }

    @Override
    public int setCutLayout() {
        return R.layout.activity_login;
    }
    private Button btnLogin;
    @Override
    public void initView() {
        btnLogin=findViewById(R.id.btnLogin);
    }

    @Override
    public void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login("","");
            }
        });
    }

    @Override
    public void loginResult(String code) {

    }
}
