package com.appforysy;

public class ConfigAppInfo {
    private static ConfigAppInfo instance;
    public static ConfigAppInfo getInstance() {
        if(instance==null){
            instance=new ConfigAppInfo();
        }
        return instance;
    }

    public Object getUserInfo() {

        return "";
    }

    public String getUserId() {
        return "";
    }
}
