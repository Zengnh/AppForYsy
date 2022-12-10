package com.appforysy.activity.activity_cut_image;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.wheel.SimpleWheelAdapter;
import com.appforysy.wheel.WheelView;
import com.google.android.material.tabs.TabLayout;
import com.toolmvplibrary.activity_root.ActivityRoot;

/***
 *
 */
public class ActivityCutImage extends ActivityRoot {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intruct);
    }
}