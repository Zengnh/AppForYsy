package com.toolmvplibrary.activity_root.logindemo;

public class DataModel {
    public static ModelRoot request(String token){
        // 声明一个空的BaseModel
        ModelRoot model = null;
        try {
            //利用反射机制获得对应Model对象的引用
            model = (ModelRoot)Class.forName(token).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return model;
    }
}