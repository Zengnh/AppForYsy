package com.toolmvplibrary.activity_root;

import android.content.Context;

public  class PresenterRoot<U extends InterUiRoot,M extends ModelRoot> {

    public void attUIView(U interfaceView){
        this.interfaceView=interfaceView;
    }
    public void detachView(){
        this.interfaceView=null;
    }
    public boolean isViewAttached(){
        return this.interfaceView!=null;
    }

    protected Context context;
    public void setContent(Context context){
        this.context=context;
    }
    public U interfaceView;
    public M model;
}
