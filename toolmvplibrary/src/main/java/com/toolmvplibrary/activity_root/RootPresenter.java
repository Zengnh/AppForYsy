package com.toolmvplibrary.activity_root;

import android.content.Context;

import java.lang.ref.WeakReference;

public abstract class RootPresenter<U extends RootInterUi, M extends RootModel> implements RootInterUi {

    public abstract M createModel();

    private WeakReference<U> weakReference;

    public void attUIView(U interfaceView) {
//        this.interfaceView=interfaceView;
        weakReference = new WeakReference<>(interfaceView);
        if(model==null){
            model=createModel();
        }
    }

    public void detachView() {
        if (weakReference != null && isViewAttached()) {
            weakReference.clear();
            weakReference = null;
        }
//        this.interfaceView=null;
    }

    public boolean isViewAttached() {
        return weakReference != null && weakReference.get() != null;
//        return this.interfaceView!=null;
    }

    protected U getViewInterface() {
        return weakReference.get();
    }

    //    public U interfaceView;
    public M model;

    @Override
    public void showToast(String str) {
        if (isViewAttached()) {
            getViewInterface().showToast(str);
        }
    }

    @Override
    public void showLoading() {
        if (isViewAttached()) {
            getViewInterface().showLoading();
        }
    }


    @Override
    public void hitLoading() {
        if (isViewAttached()) {
            getViewInterface().hitLoading();
        }
    }

    @Override
    public void hitKeyBox() {
        if (isViewAttached()) {
            getViewInterface().hitKeyBox();
        }
    }
    @Override
    public Context getContext() {
        if (isViewAttached()) {
            return getViewInterface().getContext();
        } else {
            return null;
        }
    }
}
