package com.toolmvplibrary.activity_root;

public class ModelRoot<M extends RootDataManager> {
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
