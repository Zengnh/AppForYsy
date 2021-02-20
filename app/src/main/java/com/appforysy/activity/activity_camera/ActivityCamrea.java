package com.appforysy.activity.activity_camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.appforysy.R;
import com.appforysy.activity.activity_camera.camera.CameraManager;
import com.toolmvplibrary.activity_root.ActivityRootInit;
import com.toolmvplibrary.activity_root.RootPresenter;


import java.io.IOException;

public class ActivityCamrea extends ActivityRootInit implements SurfaceHolder.Callback {
    @Override
    protected RootPresenter setPresenter() {
        return null;
    }

    @Override
    public int setCutLayout() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.activity_camera;
    }

    private CameraManager cameraManager;
    private SurfaceView preview_view;
    private ImageView changeCamera;

    @Override
    public void initView() {
        preview_view = findViewById(R.id.preview_view);
        changeCamera = findViewById(R.id.changeCamera);
    }

    @Override
    public void initData() {

        changeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraManager.stopPreview();
                cameraManager.closeDriver();
                if (isFontCamrea == cameraManager.getfrontCamera()) {
                    isFontCamrea = cameraManager.getBackCamera();
                } else {
                    isFontCamrea = cameraManager.getfrontCamera();
                }
                initCamera(surfaceHolder);
            }
        });
    }

    private int isFontCamrea = -1;

    private SurfaceHolder surfaceHolder;

    @Override
    protected void onResume() {
        super.onResume();
        cameraManager = new CameraManager(getApplication());
        isFontCamrea = cameraManager.getBackCamera();
        surfaceHolder = preview_view.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initCamera(surfaceHolder);
    }

    private int RC_PERMISSION = 111;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (!(ActivityCompat.checkSelfPermission(ActivityCamrea.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)) {
            //没有权限，申请权限
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            //申请权限，其中RC_PERMISSION是权限申请码，用来标志权限申请的
            ActivityCompat.requestPermissions(ActivityCamrea.this, permissions, RC_PERMISSION);
        } else {
            //拥有权限
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /**
     * 初始化Camera
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            cameraManager.setManualCameraId(isFontCamrea);
            cameraManager.openDriver(surfaceHolder);
            cameraManager.startPreview();
            cameraManager.authFocusStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
