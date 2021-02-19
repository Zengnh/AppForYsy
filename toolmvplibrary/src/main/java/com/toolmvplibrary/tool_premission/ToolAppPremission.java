package com.toolmvplibrary.tool_premission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class ToolAppPremission {

    /**
     * 网络权限
     * @param act
     * @param reqCode
     */
    public static void reqInternet(Activity act,int reqCode){

        try {
            String[] PERMISSIONS_STORAGE = {Manifest.permission.INTERNET};
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(act,Manifest.permission.INTERNET);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(act, PERMISSIONS_STORAGE,reqCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//####################################################################################

    /**
     * 读写权限
     * @param act
     * @param reqCode
     */
    public static void reqWriteDestory(Activity act,int reqCode){
        //先定义
        try {
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET};
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(act,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(act, PERMISSIONS_STORAGE,reqCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static boolean hasWritePremission(Activity act){
        //检测是否有写的权限
        int permission = ActivityCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 没有写的权限，去申请写的权限，会弹出对话框
            return false;
        }else{
            return true;
        }
    }
//    ############################################################################

    /**
     * 摄像头权限
     * @param act
     * @param reqCode
     */
    public static void reqCamer(Activity act,int reqCode){
        //先定义
        try {
            String[] PERMISSIONS_STORAGE = {
                    "android.permission.RECORD_AUDIO",
                    "android.permission.VIBRATE" };
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(act,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(act, PERMISSIONS_STORAGE,reqCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasCamerPremission(Activity act){
        //检测是否有写的权限
        int permission = ActivityCompat.checkSelfPermission(act,
                "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 没有写的权限，去申请写的权限，会弹出对话框
            return false;
        }else{
            return true;
        }
    }
//#######################################################################
    public static void reqLocation(Activity act,int reqCode){
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(act,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    reqCode);
        }
    }
//###########################################################

    // 检测摄像头权限
    public static boolean checkPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    public static void requestCamer( Activity act,int reqCode){
        if (!checkPermission(act)) {
            ActivityCompat.requestPermissions(act, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, reqCode);
        }
    }

    //检测联系人权限
    public  static boolean checkCONTACTS(Activity act,int reqCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
//            联系人权限
            if (PackageManager.PERMISSION_GRANTED !=
                    ActivityCompat.checkSelfPermission(act.getApplicationContext(), Manifest.permission.READ_CONTACTS)) {
                permissions.add(Manifest.permission.READ_CONTACTS);
            }
//            if (PackageManager.PERMISSION_GRANTED !=
//                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CONTACTS)) {
//                permissions.add(Manifest.permission.WRITE_CONTACTS);
//            }
            if (PackageManager.PERMISSION_GRANTED !=
                    ActivityCompat.checkSelfPermission(act.getApplicationContext(), Manifest.permission.CALL_PHONE)) {
                permissions.add(Manifest.permission.CALL_PHONE);
            }
            if (PackageManager.PERMISSION_GRANTED !=
                    ActivityCompat.checkSelfPermission(act.getApplicationContext(), Manifest.permission.READ_SMS)) {
                permissions.add(Manifest.permission.READ_SMS);
            }

//            联系人权限申请
            if (permissions.size() != 0) {
                String[] permissionsArray = permissions.toArray(new String[1]);
                ActivityCompat.requestPermissions(act,
                        permissionsArray, reqCode);
                return false;
            }
        }
        return true;
    }





}
