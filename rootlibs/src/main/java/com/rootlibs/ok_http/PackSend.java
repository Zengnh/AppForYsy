package com.rootlibs.ok_http;

import java.util.HashMap;
import java.util.Map;

public class PackSend<T> implements InterFaceOkHttp{
    public boolean isFile = false;
    public String url = "";
    public String token="";

    public Map<String, Object> upMap = new HashMap<>();
    public Map<String, Object> getUpMap() {
        return upMap;
    }


    private OkHttpResultListener listener;
    private PackDown<T> downResult;
    public void startService(OkHttpResultListener listener){
        this.downResult=new PackDown();
        this.listener=listener;
        ToolOkHttp.getInstance().reqStart(this,this);
    }

    @Override
    public void result(int code, String content) {
        downResult.code=code;
        downResult.message=content;
        if(code==0){
            downResult.fitData(content);
        }
        listener.result(downResult.content);
    }
}
