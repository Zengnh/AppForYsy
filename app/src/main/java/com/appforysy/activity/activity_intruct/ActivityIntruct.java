package com.appforysy.activity.activity_intruct;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.activity_perfect.AdapterRecycler;
import com.google.android.material.tabs.TabLayout;
import com.toolmvplibrary.activity_root.ActivityRoot;

public class ActivityIntruct extends ActivityRoot {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intruct);
        initView();
        initEvent();
        initData();
    }
    private String[] titles = new String[]{"最新","热门","我的"};
    private void initData() {
        for(int i=0;i<titles.length;i++){
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.getTabAt(i).setText(titles[i]);
//            tabLayout.getTabAt(i).setCustomView(titles[i]);
        }

//      tabLayout.setupWithViewPager(viewPager,false);
    }

    private void initEvent() {

    }
    private RecyclerView recyclerContentView;
    private LinearLayoutManager linearLayoutManager;
    AdapterIntructRecycler adapter;
    private TabLayout tabLayout;
    private void initView() {
        recyclerContentView=findViewById(R.id.recyclerContentView);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter=new AdapterIntructRecycler();
        recyclerContentView.setLayoutManager(linearLayoutManager);
        recyclerContentView.setAdapter(adapter);
        tabLayout=findViewById(R.id.tableBar);
    }

}
