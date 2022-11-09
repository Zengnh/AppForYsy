package com.baiduproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;


public class BaiduManMainActivity extends AppCompatActivity {

    Button exeCheck;
    TextView checkResult;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_man_main);

        requestCamer(this, 1111);
        initView();
        initEvent();
        initDate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1111) {
            if (!checkPermission(this)) {
                finish();
            }
        }
    }

    private void initView() {
        exeCheck = findViewById(R.id.exeCheck);
        checkResult = findViewById(R.id.checkResult);
        surfaceView = findViewById(R.id.surfaceView);
        sfholder = surfaceView.getHolder();
    }

    private SurfaceHolder sfholder;

    private void initDate() {
        BaiduSingle.getInstance().setListener(new BaiduInterface() {
            @Override
            public void checkResult(String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(res)) {
                            checkResult.setText(res);
                        } else {
                            checkResult.setText("空数据内容");
                        }
                    }
                });
            }
        });

        sfholder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                CameraUtil.getInstance().openCamera();

                CameraUtil.getInstance().setSurfaceHolder(holder);
//                handelrCamera.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                CameraUtil.getInstance().releaseCamera();
            }
        });
    }

//    Handler handelrCamera = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            Camera.Size cameraSize = CameraUtil.getInstance().getCameraSize();
//            int sH = getWindowManager().getDefaultDisplay().getHeight();
////            int sW = getWindowManager().getDefaultDisplay().getWidth();
//            Log.i("znh_baidu", cameraSize.height + "    " + cameraSize.width + "    " +sH);
//            if (sH > 0) {
//                int lpW = cameraSize.height / cameraSize.width * sH;
//                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(lpW, sH);
//                surfaceView.setLayoutParams(lp);
//            }
//
//        }
//    };


    private void initEvent() {
        exeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictu();
//                BaiduSingle.getInstance().demoCheckImg();

            }
        });
    }

    // 检测摄像头权限
    public static boolean checkPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCamer(Activity act, int reqCode) {
        if (!checkPermission(act)) {
            ActivityCompat.requestPermissions(act, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, reqCode);
        }
    }

    public void takePictu() {
        Log.i("znh", "#####takePicture");
        new Thread(new Runnable() {
            @Override
            public void run() {
                CameraUtil.getInstance().getSingleCamera().takePicture(null, null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Bitmap bitmapTemp = null;
                        if (CameraUtil.getInstance().isBalck) {
                            bitmapTemp = rotaingImageView(90, bitmap);//后摄像头
                        } else {
                            bitmapTemp = rotaingImageView(-90, bitmap);//后摄像头
                        }
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmapTemp.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                        byte[] imageInByte = stream.toByteArray();
                        BaiduSingle.getInstance().checkImage(imageInByte);

                    }
                });
            }
        }).run();
    }

    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        if (!CameraUtil.getInstance().isBalck) {
//            前摄像头需要镜像翻转下
            matrix.postScale(-1, 1); // 镜像水平翻转
        }
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


}