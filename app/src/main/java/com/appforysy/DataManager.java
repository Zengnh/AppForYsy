package com.appforysy;
import android.os.Handler;

import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.activity_root.RootDataManager;

public class DataManager implements RootDataManager {

    public void login(String name, String pwd, InterCallBack<String> callBack){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callBack.result(name+"-----"+pwd);
            }
        },2000);
    }

    public void checkVersion(InterCallBack<String> callBack){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callBack.result("");
            }
        },2000);
    }

    public void getDbData(){

    }

}
