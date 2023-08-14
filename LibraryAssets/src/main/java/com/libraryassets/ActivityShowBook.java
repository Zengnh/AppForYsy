package com.libraryassets;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import kotlin.jvm.internal.SpreadBuilder;

/**
 * author:znh
 * time:2023/4/25
 * desc:
 */
public class ActivityShowBook extends AppCompatActivity {
    private TextView boolContent;
    private String path = "";
    Button btnStart;
    Button btnPause;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_book);
        boolContent = findViewById(R.id.boolContent);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        path = getIntent().getStringExtra("path");
        txtColor = new ForegroundColorSpan(getResources().getColor(R.color.color_book_read));

        initTouch();
        initReader();
        initAssetsBook();
        initPermission();
        initBaiDuTts();
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSpeeking = false;
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSpeeking = true;
                int readstart = readPostion * 59;
                if (allText.length() > readstart) {
                    String cutSpeek = allText.substring(readstart, readstart + 59);
                    mSpeechSynthesizer.speak(cutSpeek);
                }
                reflushTextView();
            }
        });
    }

    private SpeechSynthesizer mSpeechSynthesizer;

    @Override
    protected void onPause() {
        super.onPause();
        mSpeechSynthesizer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTouch() {
        boolContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    private InputStream inputStream = null;
    private InputStreamReader inputStreamReader = null;

    //    private RandomAccessFile randomAccessFile=null;
    public void initReader() {
        try {
            AssetManager assetManager = getApplicationContext().getAssets();
            //ACCESS_UNKNOW：无模式。其代表的值是 0。
//            ACCESS_RANDOM：无序访问模式。这种模式下文件的访问只会打开其中一段内容，然后再根据需要向流的前方或后方移动读取指针。其代表的值是1。
//            ACCESS_STREAMING：顺序读取模式。文件将会被从头部打开，然后按顺序向后面移动读取数据。其代表的值是2。
//            ACCESS_BUFFER：缓存读取模式。读取时会将整个文件直接读取到内存中，这种模式适合小文件的读取。其代表的值是3。

            inputStream = assetManager.open("textbook/" + path);
            inputStreamReader = new InputStreamReader(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initAssetsBook() {
        try {
            StringBuffer sb = new StringBuffer();
            char[] by = new char[1024];
            int size = 1024;
            int readResult;
            while ((readResult = inputStreamReader.read(by, 0, size)) != -1) {
                sb.append(new String(by));
            }
            allText = sb.toString();
            sprint = new SpannableString(allText);
            boolContent.setText(sprint);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reflushTextView() {


        // 在使用SpannableString对象时要注意
        // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE等的作用：
        // 用来标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果。分别有
        // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)、
        // Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)、
        // Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)、
        // Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)。
        //        设置字体颜色，设置前景色
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int readstart = readPostion * 59;
                sprint.removeSpan(txtColor);
                sprint.setSpan(txtColor,
                        readstart, readstart + 59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                boolContent.setText(sprint);
            }
        });
    }

    private ForegroundColorSpan txtColor;
    private SpannableString sprint;

    private String allText = "";
    private int readPostion = 0;
    private boolean isSpeeking = false;

    /**
     * 初始化百度语音合成
     */
    private void initBaiDuTts() {
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(this);
        mSpeechSynthesizer.setAppId(Constants.BAI_DU_APP_ID);
        mSpeechSynthesizer.setApiKey(Constants.BAI_DU_APP_KEY, Constants.BAI_DU_SECRET_KEY);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);

        mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {
                Log.i("znh", "start" + s);
            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i, int i1) {
                Log.i("znh", "onSynthesizeDataArrived" + s);
            }

            @Override
            public void onSynthesizeFinish(String s) {
                Log.i("znh", "onSynthesizeFinish" + s);
            }

            @Override
            public void onSpeechStart(String s) {

                Log.i("znh", "onSpeechStart" + s);
            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {
                Log.i("znh", "onSpeechProgressChanged" + s);

            }

            @Override
            public void onSpeechFinish(String s) {
                Log.i("znh", "onSpeechFinish" + s);
                if (!isSpeeking) {
                    return;
                }
                readPostion++;
                int readstart = readPostion * 59;
                if (allText.length() > readstart) {
                    String cutSpeek = allText.substring(readstart, readstart + 59);
                    mSpeechSynthesizer.speak(cutSpeek);

                    reflushTextView();
                }
            }

            @Override
            public void onError(String s, SpeechError speechError) {
                Log.i("znh", "error:" + s + "   " + speechError.code + ":" + speechError.description);
                if (!isSpeeking) {
                    return;
                }
                int readstart = readPostion * 59;
                if (allText.length() > readstart) {
                    String cutSpeek = allText.substring(readstart, readstart + 59);
                    mSpeechSynthesizer.speak(cutSpeek);

                    reflushTextView();
                }
            }
        });
        int speek = mSpeechSynthesizer.speak("开始读书咯");
        Log.i("znh", "start play" + speek);
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE};

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
