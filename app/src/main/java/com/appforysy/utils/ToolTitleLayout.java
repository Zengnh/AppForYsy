package com.appforysy.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class ToolTitleLayout {

    public ToolTitleLayout(Activity activity) {
        initView(activity);
    }

    public ToolTitleLayout(Fragment activity) {
        initView(activity.getActivity());
    }

    private ImageView tLayoutBack;
    private TextView tLayoutTitle;
    private Activity activityRoot;
    private LinearLayout title_layout_root;

    public void initView(Activity view) {
        activityRoot = view;
        tLayoutBack = view.findViewById(R.id.tLayoutBack);
        title_layout_root = view.findViewById(R.id.title_layout_root);
        tLayoutTitle = view.findViewById(R.id.tLayoutTitle);
        tLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.click(tLayoutBack);
                } else {
                    activityRoot.finish();
                }
            }
        });
    }

    private ComListener<View> listener;

    public void setBackClick(ComListener<View> listener) {
        this.listener = listener;
    }

    public void setTitle(String title) {
        tLayoutTitle.setText(title);
    }

    public void hitBack() {
        tLayoutBack.setVisibility(View.GONE);
    }

    public void setAlphLayouBg() {
        title_layout_root.setBackgroundColor(Color.argb(33, 0, 0, 0));
    }
}
