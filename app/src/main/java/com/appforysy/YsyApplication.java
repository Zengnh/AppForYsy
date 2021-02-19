package com.appforysy;

import android.app.Application;
import com.rootlibs.database.AppDBConfig;
import com.rootlibs.database.ToolGoogleRoom;
import com.rootlibs.downloader.ToolFileDownLoader;
import com.toolmvplibrary.tool_app.LogUtil;
import com.toolmvplibrary.tool_app.ToolThreadPool;

import java.util.List;

public class YsyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToolFileDownLoader.with().init(this);
        LogUtil.isDebug(true,"znh_debug");
//        Locale locale = getResources().getConfiguration().locale;
//        String language = locale.getLanguage();
//        LogUtil.i("@@@@@@@"+language+"@@@@@@@@@@@@");
//        if (language.contains("en")){
//
//        }else{
//
//        }
        ToolGoogleRoom.getInstance().initContent(this);
        ToolThreadPool.getInstance().exeRunable(new Runnable() {
            @Override
            public void run() {
                ToolGoogleRoom.getInstance().initAllInfo();
//                ToolGoogleRoom.getInstance().saveValue("aaa","保存内容");
//                List<AppDBConfig> list=ToolGoogleRoom.getInstance().searchKey("aaa");
//                LogUtil.i("@@"+list.size());
            }
        });


    }


}
