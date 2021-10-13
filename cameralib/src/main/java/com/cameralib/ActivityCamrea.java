package com.cameralib;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.cameralib.camera.CameraManager;
import com.cameralib.camera_result.ActivityTakePhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ActivityCamrea extends AppCompatActivity implements SurfaceHolder.Callback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar(R.color.design_default_color_background);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.cameralib_activity_camera);
        initView();
        initEvent();
    }

    private void initCreateCamreaObject() {
        cameraManager = new CameraManager(getApplication());
        isFontCamrea = cameraManager.getBackCamera();
        surfaceHolder = preview_view.getHolder();
        surfaceHolder.addCallback(this);
    }

    private CameraManager cameraManager;
    private SurfaceView preview_view;
    private ImageView changeCamera;
    private ImageView cameraClick;
    private TextView cameraReSet, cameraSure;

    public void initView() {
        preview_view = findViewById(R.id.preview_view);
        changeCamera = findViewById(R.id.changeCamera);
        cameraClick = findViewById(R.id.cameraClick);
        cameraReSet = findViewById(R.id.cameraReSet);
        cameraSure = findViewById(R.id.cameraSure);
    }

    private boolean isblackCamera = true;

    /**
     *
     */
    public void initEvent() {
        changeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                修改前后摄像头
                cameraManager.stopPreview();
                cameraManager.closeDriver();
                if (isFontCamrea == cameraManager.getfrontCamera()) {
                    isFontCamrea = cameraManager.getBackCamera();
                    isblackCamera = true;
                } else {
                    isblackCamera = false;
                    isFontCamrea = cameraManager.getfrontCamera();
                }
                initCamera(surfaceHolder);
            }
        });
        cameraClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraClick.setEnabled(false);
                Camera camera = cameraManager.getCamera();
                try {
                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] bytes, Camera camera) {
                            Bitmap bipmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            if (isblackCamera) {
                                ToolCameraManager.getInstance().setCameraBitmap(rotaingImageView(90, bipmap));
                            } else {
                                ToolCameraManager.getInstance().setCameraBitmap(rotaingImageView(-90, bipmap));
                            }
                            cameraManager.stopPreview();
                            cameraReSet.setVisibility(View.VISIBLE);
                            cameraSure.setVisibility(View.VISIBLE);
                            changeCamera.setVisibility(View.GONE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    cameraClick.setEnabled(true);
                    Toast.makeText(ActivityCamrea.this, "拍照失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cameraReSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraReSet.setVisibility(View.GONE);
                cameraSure.setVisibility(View.GONE);
                changeCamera.setVisibility(View.VISIBLE);
                cameraClick.setEnabled(true);

                cameraManager.startPreview();
            }
        });

        cameraSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraReSet.setVisibility(View.GONE);
                cameraSure.setVisibility(View.GONE);
                changeCamera.setVisibility(View.VISIBLE);
                cameraClick.setEnabled(true);
                Intent intent = new Intent(ActivityCamrea.this, ActivityTakePhotoView.class);
                startActivity(intent);
            }
        });
    }

    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    private int isFontCamrea = -1;

    private SurfaceHolder surfaceHolder;


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

    @Override
    protected void onResume() {
        super.onResume();
        initCreateCamreaObject();
    }

    //***************************************************************************************************
    protected void setStatusBar(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(colorId));//设置状态栏背景色
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        } else {
            Toast.makeText(this, "低于4.4的android系统版本不存在沉浸式状态栏", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && useStatusBarColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    //是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useThemestatusBarColor = false;
    //是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
    protected boolean useStatusBarColor = true;

    //####################################################################
    public static String saveBitmap(Context context, Bitmap bitmap) {
        String locationFile = "";
        try {
            String filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/zcamrea/";
            File dirFile = new File(filePath);
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }
            String fileName = "vf" + System.currentTimeMillis() + ".jpg";
            locationFile = filePath + fileName;
            File file = new File(filePath, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            locationFile = "";
        } catch (IOException e) {
            e.printStackTrace();
            locationFile = "";
        }
        return locationFile;
    }



}
