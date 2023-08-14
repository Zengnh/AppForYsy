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
import java.io.RandomAccessFile;

/**
 * author:znh
 * time:2023/4/25
 * desc:
 */
public class ActivityShowBook2 extends AppCompatActivity {
    private TextView boolContent;
    private Button btnPro, btnNext;
    private PresenterBookRead presenterBookRead;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_book2);
        boolContent = findViewById(R.id.boolContent);

        btnPro = findViewById(R.id.btnPro);
        btnNext = findViewById(R.id.btnNext);
        presenterBookRead = new PresenterBookRead(this);
        presenterBookRead.copyTxtToLocation();
        presenterBookRead.initLineNumber();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterBookRead.initNext();
                boolContent.setText(presenterBookRead.readLine());
            }
        });
        btnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterBookRead.initPro();
                boolContent.setText(presenterBookRead.readLine());
            }
        });

        boolContent.setText(presenterBookRead.readLine());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterBookRead.destory();
    }








}
