package com.appforysy.activity.activity_perfect;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.appforysy.R;
import com.google.android.material.tabs.TabLayout;
import com.toolmvplibrary.activity_root.ActivityRoot;


public class ActivityPerfect extends ActivityRoot {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect);
        initView();
        initEvent();
        initData();
    }

    private void initData() {

    }

    private void initEvent() {

    }

    private ViewPager viewPager;

    //    private RecyclerView recyclerContentView;
//    private LinearLayoutManager linearLayoutManager;
//    private AdapterRecycler adapter;
    private TabLayout tabayView;
    private String[] title = {"教程", "简介", "我的"};
    private AdapterViewPager adapterViewPager;

    private void initView() {
        tabayView = findViewById(R.id.tabayView);
        viewPager = findViewById(R.id.viewPager);

        for (int i = 0; i < title.length; i++) {
            tabayView.addTab(tabayView.newTab());
            tabayView.getTabAt(i).setText(title[i]);
        }
        tabayView.setupWithViewPager(viewPager, false);
        adapterViewPager = new AdapterViewPager(title);
        viewPager.setAdapter(adapterViewPager);


//        recyclerContentView=findViewById(R.id.recyclerContentView);
//        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        adapter=new AdapterRecycler();
//        recyclerContentView.setLayoutManager(linearLayoutManager);
//        recyclerContentView.setAdapter(adapter);
////        左右滑动会有磁吸效果。上线就无效
//        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
//        linearSnapHelper.attachToRecyclerView(recyclerContentView);

    }
}
