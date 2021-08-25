package com.appforysy.activity.activity_banner;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.activity_perfect.AdapterRecycler;
import com.toolmvplibrary.activity_root.ActivityRoot;

public class ActivityBanner extends ActivityRoot {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initView();
    }

    private RecyclerView recyclerContentView;
    private LinearLayoutManager linearLayoutManager;
    AdapterBannerRecycler adapter;

    private void initView() {
        recyclerContentView = findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapter = new AdapterBannerRecycler();
        recyclerContentView.setLayoutManager(linearLayoutManager);
        recyclerContentView.setAdapter(adapter);

        adapter.attachToRecyclerView(recyclerContentView, 60);
//        左右滑动会有磁吸效果。上线就无效

//        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
//        linearSnapHelper.attachToRecyclerView(recyclerContentView);

    }

}
