package com.appforysy.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appforysy.R;
import com.toolmvplibrary.activity_root.ComListener;

//--------------------- xml 布局
//<?xml version="1.0" encoding="utf-8"?>
//<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
//        android:layout_width="match_parent"
//        android:layout_height="61dp"
//        android:background="#22000000"
//        android:paddingTop="26dp">
//
//<LinearLayout
//        android:layout_width="match_parent"
//                android:layout_height="match_parent"
//                android:orientation="horizontal"
//                android:paddingLeft="16dp"
//                android:paddingRight="16dp">
//
//<ImageView
//            android:id="@+id/tLayoutBack"
//                    android:layout_width="wrap_content"
//                    android:layout_height="wrap_content"
//                    android:layout_marginTop="3.5dp"
//                    android:src="@mipmap/icon_arrow" />
//
//<TextView
//            android:id="@+id/tLayoutTitle"
//                    android:layout_width="wrap_content"
//                    android:layout_height="wrap_content"
//                    android:gravity="center"
//                    android:textColor="#1e1e1e"
//                    android:textSize="16dp" />
//</LinearLayout>
//
//</LinearLayout>
//-------------------------

//    layout_title.xml 为准
public class ToolTitleLayout extends LinearLayout{
    private ImageView tLayoutBack;
    private ImageView imgRightImg;
    private TextView tLayoutTitle;

    private LinearLayout title_layout_root;

    public ToolTitleLayout(Context context) {
        super(context,null);
    }

    public ToolTitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context,R.layout.layout_title,this);
        initView();
    }

    public void initView() {
        imgRightImg = findViewById(R.id.imgRightImg);
        tLayoutBack = findViewById(R.id.tLayoutBack);
        title_layout_root = findViewById(R.id.title_layout_root);
        tLayoutTitle = findViewById(R.id.tLayoutTitle);
    }
    public ImageView setBackClick(OnClickListener clickListener) {
        tLayoutBack.setVisibility(View.VISIBLE);
        tLayoutBack.setOnClickListener(clickListener);
        return tLayoutBack;
    }

    public TextView setTitle(String title) {
        tLayoutTitle.setVisibility(View.VISIBLE);
        tLayoutTitle.setText(title);
        return tLayoutTitle;
    }
    public ImageView setRightBtn(int imgRes, View.OnClickListener clickk){
        imgRightImg.setVisibility(View.VISIBLE);
        imgRightImg.setImageResource(imgRes);
        imgRightImg.setOnClickListener(clickk);
        return imgRightImg;
    }

    public void hitBack() {
        tLayoutBack.setVisibility(View.GONE);
    }

    public void setAlphLayouBg() {
        title_layout_root.setBackgroundColor(Color.argb(33, 0, 0, 0));
    }
}
