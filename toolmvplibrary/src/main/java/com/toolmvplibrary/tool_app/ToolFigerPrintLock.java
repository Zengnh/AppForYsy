package com.toolmvplibrary.tool_app;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class ToolFigerPrintLock {

    private static ToolFigerPrintLock instance;
    private ToolFigerPrintLock(){

    }
    public static ToolFigerPrintLock getInstance(){
        if(instance==null){
            instance=new ToolFigerPrintLock();
        }
        return instance;
    }


    private FingerprintManager manager;
    private KeyguardManager mKeyManager;

    public void init(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = (FingerprintManager) application.getSystemService(Context.FINGERPRINT_SERVICE);
        }
        mKeyManager = (KeyguardManager) application.getSystemService(Context.KEYGUARD_SERVICE);
    }

    public boolean hasFigerPrint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!manager.isHardwareDetected()||manager==null) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean hasFigerPrintUser() {
        //判断是否有指纹录入
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!manager.hasEnrolledFingerprints()||manager==null) {
                return false;
            }
            return true;
        }
        return false;
    }


    //    是否有密码锁
    public boolean hasPwdLock() {
        if (!mKeyManager.isKeyguardSecure()||mKeyManager==null) {
//            Toast.makeText(this, "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void startToPwdLock(Activity act, int requestCode) {
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent = mKeyManager.createConfirmDeviceCredentialIntent("finger", "密码解锁");
        }
        if (intent != null) {
            act.startActivityForResult(intent, requestCode);
        }
    }

    private CancellationSignal mCancellationSignal = new CancellationSignal();
    public void cancel() {
//        取消指纹识别
        mCancellationSignal.cancel();
    }

    public void unlockPwd(Context context) {
//        WindowManagerGlobal.getWindowManagerService( ).dismissKeyguard( );
//        try {
//            // Dismiss the lock screen when activity starts.
//            ActivityManagerNative.getDefault().dismissKeyguardOnNextActivity();
//        } catch (RemoteException e) {
//        }
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivityAsUser(intent, new UserHandle(UserHandle.USER_CURRENT));
    }


    private FingerprintManager.AuthenticationCallback mSelfCancelled;
    public void startFigerprit(Activity act){
        initListener();
        FingerprintManager.CryptoObject cryptoObject=null;
        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(act, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            String[] figerPin = new String[]{Manifest.permission.USE_FINGERPRINT};
            ActivityCompat.requestPermissions(act, figerPin, 110);
            return;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            manager.authenticate(cryptoObject, mCancellationSignal, 0, mSelfCancelled, null);
        }

    }
    public void initListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mSelfCancelled = new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    //但多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
    //                showTaost(errString.toString());
    //                showAuthenticationScreen();
                }

                @Override
                public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                    LogUtil.i("znh_figerprint",helpCode+"  ###  "+helpString);
    //                1001  ###  等待手指按下
    //                1002  ###  手指按下
    //                1003  ###  手指抬起
                    if (helpCode==1002) {
    //                    imgFiger.setImageResource(R.mipmap.icon_figer_press);
                    }else if(helpCode==1003){
    //                    imgFiger.setImageResource(R.mipmap.icon_figer_press_nor);
                    }
    //                showTaost(helpCode+"  ###  "+helpString);
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
    //                Toast.makeText(ActiityFigerPrintLock.this, "指纹识别成功", Toast.LENGTH_SHORT).show();
    //                setResult(Activity.RESULT_OK);
    //                finish();
                }

                @Override
                public void onAuthenticationFailed() {
    //                Toast.makeText(ActiityFigerPrintLock.this, "指纹识别失败", Toast.LENGTH_SHORT).show();
                }
            };
        }
    }

    /**
     * 锁屏密码
     */
    private void showAuthenticationScreen(Activity act) {
//      KeyguardManager mKeyManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        Intent intent = mKeyManager.createConfirmDeviceCredentialIntent("finger", "测试指纹识别");
        if (intent != null) {
            act.startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }
    }

    private int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 123;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            // Challenge completed, proceed with using cipher
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "识别成功", Toast.LENGTH_SHORT).show();
//                setResult(Activity.RESULT_OK);
//                finish();
//            } else {
//                Toast.makeText(this, "识别失败", Toast.LENGTH_SHORT).show();
//            }
        }
    }


}
