package com.libraryassets;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * author:znh
 * time:2023/4/25
 * desc:
 */
public class ActivityShowBookLinearRead extends AppCompatActivity {
    private TextView boolContent;
    private Button btnPro, btnNext;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_book2);
        boolContent = findViewById(R.id.boolContent);

        btnPro = findViewById(R.id.btnPro);
        btnNext = findViewById(R.id.btnNext);

        try {
            InputStream inputStream = getApplicationContext().getAssets().open("textbook/bcgm.txt");
            File file = getExternalFilesDir("textbook");
            if (!file.exists()) {
                file.mkdirs();
            }
            fileTxt = new File(file.getPath() + "/bcgm.txt");
            if (!fileTxt.exists()) {
                fileTxt.createNewFile();
            }
            if (fileTxt.length() == 0) {
                try {
                    OutputStream output = new FileOutputStream(fileTxt);
                    try {
                        byte[] buffer = new byte[4 * 1024]; // or other buffer size
                        int read;
                        while ((read = inputStream.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }
                        output.flush();
                    } finally {
                        output.close();
                    }
                } finally {
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        initTouch();
        initReader();
        initAssetsBook();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init += 1;
//                init += dataSize;
                initAssetsBook();
            }
        });
        btnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (init == 0) {
                    return;
                }
                init -= 1;
//                init -= dataSize;
                if (init < 0) {
                    init = 0;
                }
                initAssetsBook();
            }
        });
    }

    private File fileTxt;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (randomAccessFile != null) {
                randomAccessFile.close();
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


    private RandomAccessFile randomAccessFile ;
    private LineNumberReader lineNumberReader;
    public void initReader() {
        try {
//“r”：以只读方式打开指定文件。如果试图对该RandomAccessFile执行写入方法，都将抛出IOException异常。
//“rw”：以读、写方式打开指定文件。如果该文件尚不存在，则尝试创建该文件。
//“rws”：以读、写方式打开指定文件。相对于"rw"模式，还要求对文件的内容或元数据的每个更新都同步写入到底层存储设备。
//“rwd”：以读、写方式打开指定文件。相对于"rw"模式，还要求对文件内容的每个更新都同步写入到底层存储设备。
            Log.i("znh", "## " + fileTxt.length());
            randomAccessFile = new RandomAccessFile(fileTxt, "r");

            lineNumberReader=new LineNumberReader(new FileReader(fileTxt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] buf = new byte[512];
    private int init = 0;
    private int dataSize = 512;

    private void initAssetsBook() {
//        if (randomAccessFile == null) {
//            return;
//        }
//        try {
//
//            randomAccessFile.seek(init);
//            randomAccessFile.read(buf, 0, dataSize);
////            String dataHex = bytesToHexString(buf);//转16进制显示
//            String dataHex = new String(buf);
//            boolContent.setText(dataHex);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        lineNumberReader.setLineNumber(init);
        try {
            String lineText=lineNumberReader.readLine();
            boolContent.setText(lineText);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String bytesToHexString(byte... src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


}
