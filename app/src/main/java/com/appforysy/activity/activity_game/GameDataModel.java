package com.appforysy.activity.activity_game;

public class GameDataModel  {
    public static ModelGame request(String token){
        // 声明一个空的BaseModel
        ModelGame model = null;
        try {
            //利用反射机制获得对应Model对象的引用
            model = (ModelGame)Class.forName(token).newInstance();
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