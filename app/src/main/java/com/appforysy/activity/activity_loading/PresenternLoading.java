package com.appforysy.activity.activity_loading;

import com.appforysy.ModelCom;
import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.activity_root.RootInterUi;
import com.toolmvplibrary.activity_root.RootPresenter;


public class PresenternLoading extends RootPresenter<InterLoading, ModelCom> implements RootInterUi {


    public void checkVersion() {
        showLoading();
        model.dataManager.checkVersion(new InterCallBack<String>() {
            @Override
            public void result(String res) {
                hitLoading();
                getViewInterface().checkV();
            }

            @Override
            public void err(String msg) {
                hitLoading();
                getViewInterface().toLogin();
            }
        });
    }

    @Override
    public ModelCom createModel() {
        return new ModelCom();
    }
}
