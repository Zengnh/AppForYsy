package com.zxinglib;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassFile {


    public void setFils(Object bean) {
        Class cls = bean.getClass();
// 取出bean里的所有方法
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            try {
                Log.i("znh_fields", "field======" + field.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Method method : methods) {
            try {
                Log.i("znh_fields", "method======" + method.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkSetMet(Method[] methods, String fieldSetName) {
        return false;
    }

    private String parSetName(String name) {
        return "";
    }


}
