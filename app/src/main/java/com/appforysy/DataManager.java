package com.appforysy;
import android.os.Handler;
import android.util.Log;


import com.rootlibs.ok_http.ToolOkHttp;
import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.activity_root.RootModel;
import com.toolmvplibrary.tool_app.ToolThreadPool;

import java.io.IOException;

public class DataManager implements RootModel {

    public void login(String name, String pwd, InterCallBack<String> callBack){

                ToolThreadPool.getInstance().exeRunable(new Runnable() {
                    @Override
                    public void run() {
                        ToolOkHttp okManager=ToolOkHttp.getInstance();
                        try {
                          String result=okManager.post("http://192.168.3.100:10000","110");
//                          String result=okManager.post("http://oies-api.jsyledu.cc:8099/oies/api/v1/student/goods/list","");
                            Log.i("znh","result="+result);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.result(name+"-----"+pwd);
                                }
                            },2000);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });


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
