package com.rootlibs.google_gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

//https://github.com/google/gson
//文档：https://github.com/google/gson/blob/master/UserGuide.md
//  implementation 'com.google.code.gson:gson:2.8.6'
public class ToolGson {

    class BagOfPrimitives {
        private int value1 = 1;
        private String value2 = "abc";
        private transient int value3 = 3;
        BagOfPrimitives() {
            // no-args constructor
        }
    }
    public void toJson(){
        // Serialization---生成
        BagOfPrimitives obj = new BagOfPrimitives();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
//##########################################################
        // Deserialization-----解析
        BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);
// ==> obj2 is just like obj

    }


    public static String toJson(Object object) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(object);
        return gsonString;
    }

    public static <T> T fromJson(String gsonString, Type type) {
        Gson gson = new Gson();
        T t = gson.fromJson(gsonString, type);
        return t;
    }

    public static <T> T fromJson(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        T t = gson.fromJson(gsonString, cls);
        return t;
    }

    public static <T> List<T> fromJsonToList(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

//    Type userListType = new TypeToken<ArrayList<User>>(){}.getType();

    public static <T> List<Map<String, T>> fromJsonToListMap(
            String gsonString) {
        List<Map<String, T>> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
        }.getType());
        return list;
    }

    public static <T> Map<String, T> fromJsonToMap(String gsonString) {
        Map<String, T> map = null;
        Gson gson = new Gson();
        map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }



}
