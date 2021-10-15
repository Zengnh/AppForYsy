package com.cameralib;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


/***
 * google player
 * implementation 'com.google.android.exoplayer:exoplayer:2.X.X'
 */
public class ActivityCameraResult extends AppCompatActivity {
    public static void startToResult(Activity act, String path, boolean assets) {
        Intent intent = new Intent(act, ActivityCameraResult.class);
        intent.putExtra("path", path);
        intent.putExtra("assets", assets);
        act.startActivity(intent);
    }

    public static void startToResult(Activity act, String path) {
        startToResult(act, path, false);
    }

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
        setContentView(R.layout.activity_camera_result);
        initView();
        initData();
        initEvent();
    }

    private ImageView takeVideoResult;
    private SurfaceView surfaceView;

    private void initView() {
        takeVideoResult = findViewById(R.id.takeVideoResult);
        surfaceView = findViewById(R.id.surfaceView);
    }

    private String path;

    private void initData() {
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        isAssets = intent.getBooleanExtra("assets", false);
        if (path.endsWith(".jpg") || path.endsWith(".png")) {
            surfaceView.setVisibility(View.GONE);
            takeVideoResult.setVisibility(View.VISIBLE);
            takeVideoResult.setImageBitmap(BitmapFactory.decodeFile(path));
        } else {
            surfaceView.setVisibility(View.VISIBLE);
            takeVideoResult.setVisibility(View.GONE);
            playerVideo();
        }
    }

    private SurfaceHolder surfaceHolder;

    private void initEvent() {

    }

    private void playerVideo() {
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int w, int h) {
                mediaPlayer();
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    private MediaPlayer mediaPlayer;
    private boolean isAssets = false;

    public void mediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            //设置类型
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            /* 得到文件路径 *//* 注：文件存放在SD卡的根目录，一定要进行prepare()方法，使硬件进行准备 */
            try {
                /* 为MediaPlayer 设置数据源 */
//                mediaPlayer.setDataSource(path);
                if (isAssets) {
                    AssetFileDescriptor afd = getResources().getAssets().openFd(path);
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                } else {
                    mediaPlayer.setDataSource(this, Uri.parse(path));
                }

                /* 准备 */
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });
                mediaPlayer.prepareAsync();
//                mediaPlayer.start();
                //将播放器捕捉的画面展示到SurfaceView画面上
                mediaPlayer.setDisplay(surfaceHolder);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // 把图标变为暂停图标,获取音乐的总时长
            int duration = mediaPlayer.getDuration();
            //设置进度条的最大值为音乐总时长
        } else if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            //把图标变为播放图标
        } else {
            try {
                mediaPlayer.reset();
                if (isAssets) {
                    AssetFileDescriptor afd = getResources().getAssets().openFd(path);
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                } else {
                    mediaPlayer.setDataSource(this, Uri.parse(path));
                }
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //把图标变为暂停图标
        }
    }

}
