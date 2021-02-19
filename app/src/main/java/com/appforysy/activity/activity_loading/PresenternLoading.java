package com.appforysy.activity.activity_loading;

import com.appforysy.ModelCom;
import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.activity_root.PresenterRoot;


public class PresenternLoading extends PresenterRoot<InterLoading, ModelCom> {

    public PresenternLoading(){
        model=new ModelCom();
    }

    public void checkVersion() {
        interfaceView.showLoading();
        model.dataManager.checkVersion(new InterCallBack<String>() {
            @Override
            public void result(String res) {
                interfaceView.hitLoading();
                interfaceView.checkV();
            }

            @Override
            public void err(String msg) {
                interfaceView.hitLoading();
                interfaceView.toLogin();
            }
        });
    }
}
