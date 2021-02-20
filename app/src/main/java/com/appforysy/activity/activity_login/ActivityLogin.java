package com.appforysy.activity.activity_login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.toolmvplibrary.activity_root.ActivityRootInit;
import com.rootlibs.loadimg.ToolGlide;
import com.appforysy.R;
import com.appforysy.activity.activity_main.ActivityMain;
import com.toolmvplibrary.tool_app.ToolLanguage;

public class ActivityLogin extends ActivityRootInit<PresenterLogin> implements InterViewLogin, View.OnClickListener {
    private TextView userLogin;
    private EditText userName, userPwd;
    private ImageView imageviewgif;
    private TextView register;

    @Override
    public PresenterLogin setPresenter() {
        return new PresenterLogin();
    }

    @Override
    public int setCutLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        imageviewgif = findViewById(R.id.imageviewgif);
        userLogin = findViewById(R.id.login);
        userLogin.setOnClickListener(this);
        userName = findViewById(R.id.account);
        userPwd = findViewById(R.id.password);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void initData() {
        ToolGlide.loadImge(this, "file:///android_asset/login_top_gif.gif", imageviewgif);
    }

//    @Override
//    protected void befSetContentView() {
//        super.befSetContentView();
////        YsyApplication.changeAppLanguage(getResources(),"zh");
//    }

    @Override
    public void loginSuccToNext() {
        Intent intent=new Intent(this, ActivityMain.class);
        startActivity(intent);
    }

    @Override
    public String getUserName() {
        return userName.getText().toString();
    }

    @Override
    public String getUserPwd() {
        return userPwd.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                ToolLanguage.changeAppLanguage(this,"en");
                break;
            case R.id.login:
                presenter.clickLogin();
                break;
        }
    }
}