package com.toolmvplibrary.activity_root.logindemo;
import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.activity_root.RootInterUi;
import com.toolmvplibrary.activity_root.RootModel;
//所有的接口都在此类。实现一一对应。方便查看
public interface LoginManager {
    interface LoginView extends RootInterUi {
        void loginResult(String code);
    }
    interface  PresenterLogin{
        public void login(String name,String pwd);
    }
    interface LoginModel extends RootModel {
        public void login(String name, String pwd, InterCallBack<String> result);
    }
}
