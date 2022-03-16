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
import com.toolmvplibrary.view.snowview.SnowUtils;
import com.toolmvplibrary.view.snowview.SnowView;

import java.util.ArrayList;
import java.util.List;


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
    private LinearLayoutManager linearLayoutManager;

    private TabLayout tabayView;
    private String[] title = {"App", "Demo", "我的"};
    private AdapterViewPager adapterViewPager;

    List<ItemPagerContent> listData=new ArrayList<>();
    private RecyclerView bannerRecycler;
    private AdapterMainBanner adapter;

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
//####################################################################################
        listData.clear();
        ItemPagerContent bean=new ItemPagerContent();
        bean.icon=R.mipmap.image_banner_heard;
        listData.add(bean);


        ItemPagerContent bean2=new ItemPagerContent();
        bean2.icon=R.mipmap.image_banner_flor;
        listData.add(bean2);

        ItemPagerContent bean3=new ItemPagerContent();
        bean3.icon=R.mipmap.image_banner_user;
        listData.add(bean3);

        bannerRecycler = findViewById(R.id.imageBanner);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        adapter=new AdapterMainBanner(listData);
        bannerRecycler.setLayoutManager(linearLayoutManager);
        bannerRecycler.setAdapter(adapter);
////        左右滑动会有磁吸效果。上线就无效
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(bannerRecycler);

    }
}
