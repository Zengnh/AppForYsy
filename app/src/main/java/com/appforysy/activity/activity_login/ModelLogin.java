package com.appforysy.activity.activity_login;

import com.appforysy.DataManager;
import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.activity_root.ModelRoot;


public class ModelLogin extends ModelRoot<DataManager> {
    public ModelLogin() {
        dataManager = new DataManager();
    }

    public void login(InterCallBack<String> listener) {
        dataManager.login(mParams[0], mParams[1], listener);
    }
}
