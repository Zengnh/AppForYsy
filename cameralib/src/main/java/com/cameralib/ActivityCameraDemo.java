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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        initView();
    }

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
    Button takePhoto;
    ImageView imageView;

    private void initView() {
        surfaceView = findViewById(R.id.surfaceView);
        takePhoto = findViewById(R.id.takePhoto);
        imageView = findViewById(R.id.imageView);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto.setVisibility(View.GONE);
//                takePictu();
                startRecord();
            }
        });

        SurfaceHolder sH = surfaceView.getHolder();
        sH.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
//                    打开摄像头
                int cameraid;
                isBalck = false;
                if (isBalck) {
                    cameraid = getCameraBId();
                } else {
                    cameraid = getCameraPId();
                }
                camera = Camera.open(cameraid);


            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int w, int h) {
//已经获得Surface的width和height，设置Camera的参数
                vX = w;
                vY = h;
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPreviewSize(w, h);
                List<Camera.Size> vSizeList = parameters.getSupportedPictureSizes();

                for (int num = 0; num < vSizeList.size(); num++) {
                    Camera.Size vSize = vSizeList.get(num);
                }
                if (camera != null) {
                    if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                        camera.setDisplayOrientation(90);
                    } else {
                        camera.setDisplayOrientation(0);
                    }
                }
                try {
                    camera.setPreviewDisplay(surfaceHolder);
//                    Camera.Parameters paramets=camera.getParameters();
//                    paramets.
//                    camera.setDisplayOrientation(90);
//                    camera.setParameters();

                    if (isBalck) {

                    } else {
                        //前置摄像头 设置左右旋转
                        Camera.Parameters mParameters = camera.getParameters();
                        mParameters.setRotation(90);
                        camera.setParameters(mParameters);
                    }

                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                    camera.release();
                    camera = null;
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

    private int vX, vY;

    private boolean isBalck = true;

    public void takePictu() {
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
                int tempx = bitmapTemp.getWidth();
                int tempy = bitmapTemp.getHeight();

//                float xPoint = vX / tempx;
//                float yPoint = vY / tempy;
//                if (xPoint < yPoint) {
//                    Bitmap bitmapcut = Bitmap.createBitmap(bitmapTemp, 0, 0, tempx, (int) (vY / xPoint));
//                    imageView.setImageBitmap(bitmapcut);
//                } else {
//                    Bitmap bitmapcut = Bitmap.createBitmap(bitmapTemp, 0, 0, (int) (vX / yPoint), tempy);
//                    imageView.setImageBitmap(bitmapcut);
//                }

//                if (vX > 0 && tempx > 0 && vY > 0 && tempy > 0) {
//                    Bitmap bitmapcut = Bitmap.createBitmap(bitmapTemp, 0, 0, vX, vY);
//                    imageView.setImageBitmap(bitmapcut);
//                } else {
                imageView.setImageBitmap(bitmapTemp);
//                }


            }
        });
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

    MediaRecorder mMediaRecorder = new MediaRecorder();

    public void recordVideo() {
        CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);
// Unlock the camera object before passing it to media recorder.
        camera.unlock();
        mMediaRecorder.setCamera(camera);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setProfile(mProfile);
        mMediaRecorder.setMaxDuration(100000);//ms为单位
        long dateTaken = System.currentTimeMillis();
        Date date = new Date(dateTaken);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String title = dateFormat.format(date);
        String filename = title + ".3gp"; // Used when emailing.
        String cameraDirPath = Environment.getExternalStorageDirectory().getPath();
        String filePath = cameraDirPath + "/" + filename;
        File cameraDir = new File(cameraDirPath);
        cameraDir.mkdirs();
        mMediaRecorder.setOutputFile(filePath);
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start(); // Recording is now started
        } catch (RuntimeException | IOException e) {
        }
    }

    public void stopRecord() {
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;
        if (camera != null) {
            camera.lock();
        }
    }


    public void startRecord() {
        Camera.Parameters parameters = camera.getParameters();
        camera.autoFocus(null);
        // 解锁camera
        camera.unlock();
        mMediaRecorder.setCamera(camera);
//        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
//        for(int i=0;i<supportedPreviewSizes.size();i++)
//        {
////       Log.v("startRecord", "width="+supportedPreviewSizes.get(i).width+";height="+supportedPreviewSizes.get(i).height);
//        }
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
        mMediaRecorder.setVideoSize(1280, 720);
        //设置编码比特率,不设置会使视频图像模糊
//        mediarecorder.setVideoEncodingBitRate(5*1024*1024);  //清晰     512*1024(不清楚)
        mMediaRecorder.setVideoEncodingBitRate(900 * 1024); //较为清晰，且文件大小为3.26M(30秒)
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);  //H263的貌似有点不清晰
//        mediarecorder.setVideoFrameRate(10);  //设置无效
        mMediaRecorder.setMaxDuration(10000);
        handlerFinish.sendEmptyMessageDelayed(0, 10000);
        //end
        mMediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
        // 设置视频文件输出的路径
        mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp4");
        try {
            // 准备录制
            mMediaRecorder.prepare();
            // 开始录制
            mMediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler handlerFinish = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            stopRecord();
            finish();
        }
    };
}
