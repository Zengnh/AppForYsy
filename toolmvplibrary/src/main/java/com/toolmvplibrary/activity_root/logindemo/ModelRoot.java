package com.toolmvplibrary.activity_root.logindemo;

import com.toolmvplibrary.activity_root.RootModel;

public class ModelRoot<M extends RootModel> implements RootModel {
    //数据请求参数
    protected String[] mParams;
    /**
     * 设置数据请求参数
     * @param args 参数数组
     */
    public  ModelRoot params(String... args){
        mParams = args;
        return this;
    }

    public M dataManager;
}
