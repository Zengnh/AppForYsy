package com.cameralib;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cameralib.camera.CameraManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ActivityCameraDemo extends AppCompatActivity {
    //***************************************************************************************************
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色

            getWindow().setStatusBarColor(Color.TRANSPARENT);//透明

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        } else {
            Toast.makeText(this, "低于4.4的android系统版本不存在沉浸式状态栏", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        setContentView(R.layout.activity_camera_demo);
        rootPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "cameradir";
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        initView();
        cameraManager = new CameraManager(getApplication());
        initEvent();
    }

    private long timeIsCamera = 500;

    private void initEvent() {
//        takePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                takePhoto.setVisibility(View.GONE);
////                takePictu();
//                Toast.makeText(ActivityCameraDemo.this,"开始",Toast.LENGTH_SHORT).show();
//
//                startRecord();
//            }
//        });

        takePhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchTime = System.currentTimeMillis();
                        handlerRecord.removeMessages(0);
                        handlerRecord.sendEmptyMessageDelayed(0, timeIsCamera);
                        Log.i("znh", "#####ActionDown");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("znh", "#####ActionUP");
                        long touchTimeCount = System.currentTimeMillis() - touchTime;
                        if (touchTimeCount < timeIsCamera) {
//                            拍照
                            handlerRecord.removeMessages(0);
                            progressBar.setVisibility(View.VISIBLE);
                            takePictu();
                        } else {
//                         录屏停止
                            handlerRecord.removeMessages(0);
                            handlerRecord.sendEmptyMessage(1);
                        }
                        break;
                }
                return true;
            }
        });

        changeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
                isBalck = !isBalck;
                openCamera();
            }
        });

    }

    private void initCameraSize() {
        cameraSizeList.clear();
        Camera.Parameters parameters = camera.getParameters();
        //对拍照参数进行设置
        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
            cameraSizeList.add(size);
        }
    }

    private List<Camera.Size> cameraSizeList = new ArrayList<>();

    private void openCamera() {
        int cameraid;
        if (isBalck) {
            cameraid = getCameraBId();
        } else {
            cameraid = getCameraPId();
        }
        camera = Camera.open(cameraid);
        initCameraSize();
        if (camera != null) {
            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                camera.setDisplayOrientation(90);
            } else {
                camera.setDisplayOrientation(0);
            }
        }
        if (vX != 0) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewSize(vX, vY);
            List<Camera.Size> vSizeList = parameters.getSupportedPictureSizes();
            for (int num = 0; num < vSizeList.size(); num++) {
                Camera.Size vSize = vSizeList.get(num);
            }
        }
        try {
            camera.setPreviewDisplay(surfaceView.getHolder());
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
            camera.release();
            camera = null;
        }
    }


    private Handler handlerRecord = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                stopRecord();
            } else if (msg.what == 0) {
                Log.i("znh", "#####startrecord");
                startRecord();
            }
        }
    };


    private long touchTime = 0;


    private String rootPath;

    //    #########################
    public void simpleToCamer() {
        String imgPath = "/sdcard/test/img.jpg";
//必须确保文件夹路径存在，否则拍照后无法完成回调
        File vFile = new File(imgPath);
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile(); //new File(vFile.getParent());
            vDirPath.mkdirs();
        }
        Uri uri = Uri.fromFile(vFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
        startActivityForResult(intent, systemCamera);
    }

    private int systemCamera = 123;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == systemCamera) {
            String imgPath = "/sdcard/test/img.jpg";
//必须确保文件夹路径存在，否则拍照后无法完成回调
            File vFile = new File(imgPath);
            Uri uri = Uri.fromFile(vFile);
        }
    }

    private SurfaceView surfaceView;
    private Camera camera;

    private CameraManager cameraManager;

    private ImageView takePhoto;
    private TextView textView;
    private ProgressBar progressBar;
    private ImageView changeCamera;

    private void initView() {
        changeCamera = findViewById(R.id.changeCamera);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);
        surfaceView = findViewById(R.id.surfaceView);
        takePhoto = findViewById(R.id.takePhoto);
//        takePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                takePhoto.setVisibility(View.GONE);
////                takePictu();
////                startRecord();
//            }
//        });

        SurfaceHolder sH = surfaceView.getHolder();
        sH.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
//                    打开摄像头
                openCamera();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int w, int h) {
//已经获得Surface的width和height，设置Camera的参数
                vX = w;
                vY = h;
                if (camera != null) {
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setPreviewSize(vX, vY);
                    List<Camera.Size> vSizeList = parameters.getSupportedPictureSizes();
                    for (int num = 0; num < vSizeList.size(); num++) {
                        Camera.Size vSize = vSizeList.get(num);
                    }
                }

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
//                销毁摄像头
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                }
            }
        });
        sH.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    public int getCameraBId() {
        int index = 0;
        int numCame = Camera.getNumberOfCameras();
        for (int i = 0; i < numCame; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int getCameraPId() {
        int index = 0;
        int numCame = Camera.getNumberOfCameras();
        for (int i = 0; i < numCame; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int vX = 0, vY = 0;
    private boolean isBalck = false;

    public void takePictu() {
        Log.i("znh", "#####takePicture");
        new Thread(new Runnable() {
            @Override
            public void run() {
                camera.takePicture(null, null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Bitmap bitmapTemp;
                        if (isBalck) {
                            bitmapTemp = rotaingImageView(90, bitmap);//后摄像头
                        } else {
                            bitmapTemp = rotaingImageView(-90, bitmap);//后摄像头
                        }
                        String filePath = rootPath + File.separator + System.currentTimeMillis() + ".jpg";
                        File fileImg = new File(filePath);
                        try {
                            if (!fileImg.exists()) {
                                fileImg.createNewFile();
                            }
                            FileOutputStream fos = new FileOutputStream(fileImg);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            bitmapTemp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ActivityCameraResult.startToResult(ActivityCameraDemo.this, filePath);
                                finish();
                            }
                        });


//                int tempx = bitmapTemp.getWidth();
//                int tempy = bitmapTemp.getHeight();
//                imageView.setImageBitmap(bitmapTemp);
                    }
                });
            }
        }).run();


    }

    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        if (!isBalck) {
//            前摄像头需要镜像翻转下
            matrix.postScale(-1, 1); // 镜像水平翻转
        }
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private MediaRecorder mMediaRecorder = new MediaRecorder();

    public void stopRecord() {
        try {
            if (mMediaRecorder == null) {
                return;
            }
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            if (camera != null) {
                camera.lock();
            }
            timerTask.cancel();
            timerTask = null;
            ActivityCameraResult.startToResult(ActivityCameraDemo.this, videoPath);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Timer timerTask;
    private int recoreMaxTime = 1000000;

    public void startRecord() {
//        camera.lock();
//        Camera.Parameters parameters = camera.getParameters();
//        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
//        float tempX=vX,tempY=vY;
//        float proPoint=100;
//        float intP=vY/vX;
//        for (int i = 0; i < supportedPreviewSizes.size(); i++) {
//            float tempInX=supportedPreviewSizes.get(i).width;
//            float tempInY=supportedPreviewSizes.get(i).height;
//            float tempP=tempInY/tempInX;
//            if(Math.abs(intP-tempP)<proPoint){
//                tempX=supportedPreviewSizes.get(i).width;
//                tempY=supportedPreviewSizes.get(i).height;
//            }
//            Log.i("znh", "width="+supportedPreviewSizes.get(i).width+";height="+supportedPreviewSizes.get(i).height);
//        }
//        Log.i("znh",tempX+"   "+tempY);
//        camera.autoFocus(null);
        // 解锁camera
        camera.unlock();
        mMediaRecorder.setCamera(camera);
        // 设置录制视频源为Camera(相机)
//        mediarecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        // 设置录制文件质量，格式，分辨率之类，这个全部包括了
//        mediarecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_480P));  //7.43M  10frame
//        mediarecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_1080P));   //70.94M  10frame
//        mediarecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_CIF));  // 2.6M  5frame/10frame
//        mediarecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_QCIF));  //0.76M   30frame  模糊
//        mediarecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_QVGA));  //2.1M
//        mediarecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_TIME_LAPSE_CIF));  //不支持
//        mediarecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_LOW));  //766KB  还行  比QUALITY_QCIF好
//        mediarecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_TIME_LAPSE_LOW));  //1M 质量类似LOW
//        mediarecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_TIME_LAPSE_480P));  //480p效果
//        mediarecorder.setAudioEncoder(MediaRecorder.AudioEncoder.);
//        mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        boolean isSupQUALITY_TIME_LAPSE_CIF = CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_TIME_LAPSE_CIF);
//        boolean isSupQUALITY_LOW = CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_LOW);
//        Log.v("startRecord", "isSupQUALITY_TIME_LAPSE_CIF="+isSupQUALITY_TIME_LAPSE_CIF+";isSupQUALITY_LOW="+isSupQUALITY_LOW);
//        mediarecorder.setVideoFrameRate(30);
        //start实现录像静音
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        if (isBalck) {
            mMediaRecorder.setOrientationHint(90);
        } else {
            mMediaRecorder.setOrientationHint(270);
        }
//        mediarecorder.setVideoSize(640,480);
//        mMediaRecorder.setVideoSize((int)tempX, (int)tempY);

        for (Camera.Size size:cameraSizeList) {
//            mMediaRecorder.setVideoSize(1920, 1080);//video 大小必须要在此范围内，size。否则会出错。
        }


        mMediaRecorder.setVideoSize(1920, 1080);
        //设置编码比特率,不设置会使视频图像模糊
//        mediarecorder.setVideoEncodingBitRate(5*1024*1024);  //清晰     512*1024(不清楚)
//        mMediaRecorder.setVideoEncodingBitRate(900 * 1024); //较为清晰，且文件大小为3.26M(30秒)
        mMediaRecorder.setVideoEncodingBitRate(1080 * 1920); //较为清晰
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);  //H263的貌似有点不清晰
//        mediarecorder.setVideoFrameRate(10);  //设置无效
        mMediaRecorder.setMaxDuration(recoreMaxTime);
        handlerFinish.sendEmptyMessageDelayed(0, recoreMaxTime);
        //end
        mMediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
        // 设置视频文件输出的路径
        videoPath = rootPath + File.separator + System.currentTimeMillis() + ".mp4";
//        videoPath = "rtmp://192.168.2.160:1935/live/phone";
        mMediaRecorder.setOutputFile(videoPath);
        try {
            // 准备录制
            mMediaRecorder.prepare();
            // 开始录制
            mMediaRecorder.start();
            timerTask = new Timer();
            countTime = 0;
            TimerTask timeRecord = new TimerTask() {
                @Override
                public void run() {
                    countTime++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("znh", "---video---" + countTime);
                            textView.setText(countTime + "");

                        }
                    });
                }
            };
            timerTask.schedule(timeRecord, 0, 1000);
//
        } catch (Exception e) {
            e.printStackTrace();
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }

            Log.i("znh", "#####recordErr:" + e.getMessage());
        }
    }

    private int countTime = 0;
    private String videoPath = "";
    private Handler handlerFinish = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            stopRecord();
        }
    };

}
