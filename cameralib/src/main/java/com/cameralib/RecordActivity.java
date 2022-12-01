/*
 * Copyright (C) 2012,2013 Qianliang Zhang, Shawn Van Every, Samuel Audet
 *
 * IMPORTANT - Make sure the AndroidManifest.xml file looks like this:
 *
 * <?xml version="1.0" encoding="utf-8"?>
 * <manifest xmlns:android="http://schemas.android.com/apk/res/android"
 *     package="org.bytedeco.javacv.recordactivity"
 *     android:versionCode="1"
 *     android:versionName="1.0" >
 *     <uses-sdk android:minSdkVersion="4" />
 *     <uses-permission android:name="android.permission.CAMERA" />
 *     <uses-permission android:name="android.permission.INTERNET"/>
 *     <uses-permission android:name="android.permission.RECORD_AUDIO"/>
 *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 *     <uses-feature android:name="android.hardware.camera" />
 *     <application android:label="@string/app_name">
 *         <activity
 *             android:name="RecordActivity"
 *             android:label="@string/app_name"
 *             android:screenOrientation="landscape">
 *             <intent-filter>
 *                 <action android:name="android.intent.action.MAIN" />
 *                 <category android:name="android.intent.category.LAUNCHER" />
 *             </intent-filter>
 *         </activity>
 *     </application>
 * </manifest>
 *
 * And the res/layout/main.xml file like this:
 *
 * <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
 *     xmlns:tools="http://schemas.android.com/tools"
 *     android:id="@+id/record_layout"
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent"
 *     android:keepScreenOn="true">
 *
 *     <TextView
 *         android:id="@+id/textView1"
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         android:layout_centerHorizontal="true"
 *         android:layout_centerVertical="true"
 *         android:padding="8dp"
 *         android:text="@string/app_name"
 *         tools:context=".RecordActivity" />
 *
 *     <Button
 *         android:id="@+id/recorder_control"
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         android:layout_above="@+id/textView1"
 *         android:layout_alignRight="@+id/textView1"
 *         android:layout_marginRight="70dp"
 *         android:text="Button" />
 *
 * </LinearLayout>
 */

package com.cameralib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameFilter;

public class RecordActivity extends Activity implements OnClickListener {

    private final static String CLASS_LABEL = "RecordActivity";
    private final static String LOG_TAG = CLASS_LABEL + "Log";

        private String ffmpeg_link = "/mnt/sdcard/streamVideo.flv";
//    private String ffmpeg_link = "rtmp://192.168.2.160:1935/live/address";

    long startTime = 0;
    boolean recording = false;

    private FFmpegFrameRecorder recorder;

    private boolean isPreviewOn = false;

    /*Filter information, change boolean to true if adding a fitler*/
    private boolean addFilter = true;
    private String filterString = "";
    FFmpegFrameFilter filter;
    private int sampleAudioRateInHz = 44100;
    private int imageWidth = 1280;
    private int imageHeight = 720;
    private int frameRate = 30;
    /* audio data getting thread */
    private AudioRecord audioRecord;
    private AudioRecordRunnable audioRecordRunnable;
    private Thread audioThread;
    volatile boolean runAudioThread = true;
    /* video data getting thread */
    private Camera cameraDevice;
    private CameraView cameraView;
    private Frame yuvImage = null;
    private int screenWidth, screenHeight;
    private Button btnRecorderControl, btnBack;

    /* The number of seconds in the continuous record loop (or 0 to disable loop). */
    final int RECORD_LENGTH = 0;
    Frame[] images;
    long[] timestamps;
    ShortBuffer[] samples;
    int imagesIndex, samplesIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.layout_push_record_activity);
        initLayout();
        requestPermission();
    }


    //   java写法：
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            Log.i(TAG,"用户没用此权限");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
//                Log.i(TAG,"用户申请过权限，但是被拒绝了（不是彻底决绝）");
//               申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            } else {
//                Log.i(TAG,"申请过权限，但是被用户彻底决绝了或是手机不允许有此权限（依然可以在此再申请权限）");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        recording = false;
        if (cameraView != null) {
            cameraView.stopPreview();
        }
        if (cameraDevice != null) {
            cameraDevice.stopPreview();
            cameraDevice.release();
            cameraDevice = null;
        }
    }

    LinearLayout camreaRootLayout;

    private void initLayout() {

        /* get size of screen */
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();

        /* add control button: start and stop */
        btnRecorderControl = findViewById(R.id.recorder_control);
        camreaRootLayout = findViewById(R.id.camreaRootLayout);
        btnBack = findViewById(R.id.btnBack);
        btnRecorderControl.setText("Start");
        btnRecorderControl.setOnClickListener(this);
        btnBack.setOnClickListener(this);


        int prev_rh = screenWidth / (imageWidth / imageHeight);
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(screenWidth, prev_rh);
        layoutParam.topMargin = 100;

//        cameraDevice = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        cameraDevice = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);

        cameraDevice.setDisplayOrientation(90);



        Log.i(LOG_TAG, "cameara open");
        cameraView = new CameraView(this, cameraDevice);
        camreaRootLayout.addView(cameraView, layoutParam);
        Log.i(LOG_TAG, "cameara preview start: OK");
    }

    //---------------------------------------
    // initialize ffmpeg_recorder
    //---------------------------------------
    private void initRecorder() {

        Log.i(LOG_TAG, "ffmpeg_url: " + ffmpeg_link + "   w=" + imageWidth + "   h=" + imageHeight);
        recorder = new FFmpegFrameRecorder(ffmpeg_link, imageWidth, imageHeight, 1);
        recorder.setFormat("flv");
        recorder.setSampleRate(sampleAudioRateInHz);
        // Set in the surface changed method
        recorder.setFrameRate(frameRate);

        // The filterString  is any ffmpeg filter.
        // Here is the link for a list: https://ffmpeg.org/ffmpeg-filters.html
        filterString = "transpose=2,crop=w=200:h=200:x=0:y=0";
        filter = new FFmpegFrameFilter(filterString, imageWidth, imageHeight);

        //default format on android
        filter.setPixelFormat(avutil.AV_PIX_FMT_NV21);

        if (RECORD_LENGTH > 0) {
            imagesIndex = 0;
            images = new Frame[RECORD_LENGTH * frameRate];
            timestamps = new long[images.length];
            for (int i = 0; i < images.length; i++) {
                images[i] = new Frame(imageWidth, imageHeight, Frame.DEPTH_UBYTE, 2);
                timestamps[i] = -1;
            }
        } else if (yuvImage == null) {
            yuvImage = new Frame(imageWidth, imageHeight, Frame.DEPTH_UBYTE, 2);
            Log.i(LOG_TAG, "create yuvImage");
        }
        Log.i(LOG_TAG, "recorder initialize success");
        audioRecordRunnable = new AudioRecordRunnable();
        audioThread = new Thread(audioRecordRunnable);
        runAudioThread = true;
    }

    public void startRecording() {
        initRecorder();
        try {
            recorder.start();
            startTime = System.currentTimeMillis();
            recording = true;
            audioThread.start();

            if (addFilter) {
                filter.start();
            }

        } catch (FFmpegFrameRecorder.Exception | FrameFilter.Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {

        runAudioThread = false;
        try {
            audioThread.join();
        } catch (InterruptedException e) {
            // reset interrupt to be nice
            Thread.currentThread().interrupt();
            return;
        }
        audioRecordRunnable = null;
        audioThread = null;

        if (recorder != null && recording) {
            if (RECORD_LENGTH > 0) {
                Log.v(LOG_TAG, "Writing frames");
                try {
                    int firstIndex = imagesIndex % samples.length;
                    int lastIndex = (imagesIndex - 1) % images.length;
                    if (imagesIndex <= images.length) {
                        firstIndex = 0;
                        lastIndex = imagesIndex - 1;
                    }
                    if ((startTime = timestamps[lastIndex] - RECORD_LENGTH * 1000000L) < 0) {
                        startTime = 0;
                    }
                    if (lastIndex < firstIndex) {
                        lastIndex += images.length;
                    }
                    for (int i = firstIndex; i <= lastIndex; i++) {
                        long t = timestamps[i % timestamps.length] - startTime;
                        if (t >= 0) {
                            if (t > recorder.getTimestamp()) {
                                recorder.setTimestamp(t);
                            }
                            recorder.record(images[i % images.length]);
                        }
                    }

                    firstIndex = samplesIndex % samples.length;
                    lastIndex = (samplesIndex - 1) % samples.length;
                    if (samplesIndex <= samples.length) {
                        firstIndex = 0;
                        lastIndex = samplesIndex - 1;
                    }
                    if (lastIndex < firstIndex) {
                        lastIndex += samples.length;
                    }
                    for (int i = firstIndex; i <= lastIndex; i++) {
                        recorder.recordSamples(samples[i % samples.length]);
                    }
                } catch (FFmpegFrameRecorder.Exception e) {
                    Log.v(LOG_TAG, e.getMessage());
                    e.printStackTrace();
                }
            }

            recording = false;
            Log.v(LOG_TAG, "Finishing recording, calling stop and release on recorder");
            try {
                recorder.stop();
                recorder.release();
                filter.stop();
                filter.release();
            } catch (FFmpegFrameRecorder.Exception | FrameFilter.Exception e) {
                e.printStackTrace();
            }
            recorder = null;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (recording) {
                stopRecording();
            }

            finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    //---------------------------------------------
    // audio thread, gets and encodes audio data
    //---------------------------------------------
    class AudioRecordRunnable implements Runnable {

        @Override
        public void run() {

            try {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

                // Audio
                int bufferSize;
                ShortBuffer audioData;
                int bufferReadResult;

                bufferSize = AudioRecord.getMinBufferSize(sampleAudioRateInHz, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

                if (audioRecord == null) {
//                    audioRecord = new audiorecord(MediaRecorder.AudioSource.mic, msamplerate,
//                            mchannelconfig, maudioencoding, mbuffersize * 5);
                    audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleAudioRateInHz,
                            AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
                }


                if (RECORD_LENGTH > 0) {
                    samplesIndex = 0;
                    samples = new ShortBuffer[RECORD_LENGTH * sampleAudioRateInHz * 2 / bufferSize + 1];
                    for (int i = 0; i < samples.length; i++) {
                        samples[i] = ShortBuffer.allocate(bufferSize);
                    }
                } else {
                    audioData = ShortBuffer.allocate(bufferSize);
                }


                Log.d(LOG_TAG, "audioRecord.startRecording()");
                audioRecord.startRecording();

                /* ffmpeg_audio encoding loop */
                while (runAudioThread) {
                    if (RECORD_LENGTH > 0) {
                        audioData = samples[samplesIndex++ % samples.length];
                        audioData.position(0).limit(0);
                    }
                    //Log.v(LOG_TAG,"recording? " + recording);
                    bufferReadResult = audioRecord.read(audioData.array(), 0, audioData.capacity());
                    audioData.limit(bufferReadResult);
                    if (bufferReadResult > 0) {
                        Log.v(LOG_TAG, "bufferReadResult: " + bufferReadResult);
                        // If "recording" isn't true when start this thread, it never get's set according to this if statement...!!!
                        // Why?  Good question...
                        if (recording) {
                            if (RECORD_LENGTH <= 0) try {
                                recorder.recordSamples(audioData);
                                //Log.v(LOG_TAG,"recording " + 1024*i + " to " + 1024*i+1024);
                            } catch (FFmpegFrameRecorder.Exception e) {
                                Log.v(LOG_TAG, e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                }
                Log.v(LOG_TAG, "AudioThread Finished, release audioRecord");

                /* encoding finish, release recorder */
                if (audioRecord != null) {
                    audioRecord.stop();
                    audioRecord.release();
                    audioRecord = null;
                    Log.v(LOG_TAG, "audioRecord released");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //---------------------------------------------
    // camera thread, gets and encodes video data
    //---------------------------------------------
    class CameraView extends SurfaceView implements SurfaceHolder.Callback, PreviewCallback {

        private SurfaceHolder mHolder;
        private Camera mCamera;

        public CameraView(Context context, Camera camera) {
            super(context);
            Log.w("camera", "camera view");
            mCamera = camera;
            mHolder = getHolder();
            mHolder.addCallback(CameraView.this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            mCamera.setPreviewCallback(CameraView.this);

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                stopPreview();
                mCamera.setPreviewDisplay(holder);
            } catch (IOException exception) {
                mCamera.release();
                mCamera = null;
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            stopPreview();

            Camera.Parameters camParams = mCamera.getParameters();
            List<Camera.Size> sizes = camParams.getSupportedPreviewSizes();
            // Sort the list in ascending order
            Collections.sort(sizes, new Comparator<Camera.Size>() {

                public int compare(final Camera.Size a, final Camera.Size b) {
                    return a.width * a.height - b.width * b.height;
                }
            });

            // Pick the first preview size that is equal or bigger, or pick the last (biggest) option if we cannot
            // reach the initial settings of imageWidth/imageHeight.

            for (Camera.Size item : sizes) {
                Log.v(LOG_TAG, "camera size list resolution: " + item.width + " x " + item.height);
            }


            for (int i = 0; i < sizes.size(); i++) {
                if ((sizes.get(i).width >= imageWidth && sizes.get(i).height >= imageHeight) || i == sizes.size() - 1) {
                    imageWidth = sizes.get(i).width;
                    imageHeight = sizes.get(i).height;
                    Log.v(LOG_TAG, "Changed to supported resolution: " + imageWidth + "x" + imageHeight);
                    break;
                }
            }



            //设置将保存的图片旋转90°（竖着拍摄的时候）
            camParams.setRotation(90);
            camParams.setPreviewSize(imageWidth, imageHeight);
            camParams.setPictureSize(imageWidth, imageHeight);
//        mParameters.setPictureSize(4608, 3456);
            camParams.setPictureFormat(ImageFormat.JPEG);
            camParams.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//        mParameters.set(ImageFormat.YUV_444_888);


            Log.v(LOG_TAG, "Setting imageWidth: " + imageWidth + " imageHeight: " + imageHeight + " frameRate: " + frameRate);

            camParams.setPreviewFrameRate(frameRate);
            Log.v(LOG_TAG, "Preview Framerate: " + camParams.getPreviewFrameRate());

            mCamera.setParameters(camParams);

            // Set the holder (which might have changed) again
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.setPreviewCallback(CameraView.this);
                startPreview();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Could not set preview display in surfaceChanged");
            }

            LinearLayout.LayoutParams layoutParam = (LinearLayout.LayoutParams) cameraView.getLayoutParams();
            layoutParam.height = screenWidth / (imageWidth / imageHeight);
            cameraView.setLayoutParams(layoutParam);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            try {
                mHolder.addCallback(null);
                mCamera.setPreviewCallback(null);
            } catch (RuntimeException e) {
                // The camera has probably just been released, ignore.
            }
        }

        public void startPreview() {
            if (!isPreviewOn && mCamera != null) {
                isPreviewOn = true;
                mCamera.startPreview();
            }
        }

        public void stopPreview() {
            if (isPreviewOn && mCamera != null) {
                isPreviewOn = false;
                mCamera.stopPreview();
            }
        }

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {

            if (audioRecord == null || audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
//            if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                startTime = System.currentTimeMillis();
                Log.v(LOG_TAG, audioRecord == null ? "1" : "2" + "onPreviewFrame  Writing Frame" + audioRecord.getRecordingState());
                return;
            }
            if (RECORD_LENGTH > 0) {
                int i = imagesIndex++ % images.length;
                yuvImage = images[i];
                timestamps[i] = 1000 * (System.currentTimeMillis() - startTime);
            }


            /* get video data */
            if (yuvImage != null && recording) {
                ((ByteBuffer) yuvImage.image[0].position(0)).put(data);

                if (RECORD_LENGTH <= 0) try {
                    Log.v(LOG_TAG, "Writing Frame");
                    long t = 1000 * (System.currentTimeMillis() - startTime);
                    if (t > recorder.getTimestamp()) {
                        recorder.setTimestamp(t);
                    }

                    if (addFilter) {
                        filter.push(yuvImage);
                        Frame frame2;
                        while ((frame2 = filter.pull()) != null) {
                            Log.v(LOG_TAG, "while  Writing Frame");
                            recorder.record(frame2, filter.getPixelFormat());
                        }
                    } else {
                        Log.v(LOG_TAG, "while  Writing Frame");
                        recorder.record(yuvImage);
                    }
                } catch (FFmpegFrameRecorder.Exception | FrameFilter.Exception e) {
                    Log.v(LOG_TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBack) {
            stopRecording();
            Log.w(LOG_TAG, "back Button Pushed");
            finish();
        } else if (v.getId() == R.id.recorder_control) {
            if (!recording) {
                startRecording();
                Log.w(LOG_TAG, "Start Button Pushed");
                btnRecorderControl.setText("Stop");
            } else {
                // This will trigger the audio recording loop to stop and then set isRecorderStart = false;
                stopRecording();
                Log.w(LOG_TAG, "Stop Button Pushed");
                btnRecorderControl.setText("Start");
            }
        }


    }
}
