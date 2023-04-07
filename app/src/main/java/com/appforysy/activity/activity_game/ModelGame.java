package com.appforysy.activity.activity_game;

import com.toolmvplibrary.activity_root.RootModel;

public class ModelGame<M extends RootModel> implements RootModel {
    //数据请求参数
    protected String[] mParams;
    /**
     * 设置数据请求参数
     * @param args 参数数组
     */
    public ModelGame params(String... args){
        mParams = args;
        return this;
    }

    public M dataManager;
}
