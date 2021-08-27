package com.cameralib;

import android.graphics.Bitmap;

public class ToolCameraManager {
    private ToolCameraManager(){}
    private static volatile  ToolCameraManager instance;
    public static ToolCameraManager getInstance(){
        if(instance==null){
            synchronized (ToolCameraManager.class){
                if(instance==null){
                    instance=new ToolCameraManager();
                }
            }
        }
        return instance;
    }
    private Bitmap takeBm;
    public void setCameraBitmap(Bitmap bm){
        this.takeBm=bm;
    }
    public Bitmap getTakeBm(){
        return takeBm;
    }


}
