package com.zxinglib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ZxingActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private ZXingView mzxingView;
    private String scanningResult = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        mzxingView = findViewById(R.id.mzxingView);
        mzxingView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mzxingView.startCamera();
        mzxingView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mzxingView.stopCamera();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {

        Log.i("znh", "扫描结果" + result);
        Intent intent = new Intent();
        intent.putExtra("result", result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, "摄像头打开失败", Toast.LENGTH_SHORT).show();
        finish();
    }
}
