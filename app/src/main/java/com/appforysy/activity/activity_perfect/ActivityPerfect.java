package com.appforysy.activity.activity_perfect;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.appforysy.R;
import com.google.android.material.tabs.TabLayout;
import com.medialib.trim.utils.VideoTrimmerUtil;
import com.rootlibs.downloader.InterListener;
import com.rootlibs.downloader.ResultDownLoader;
import com.screenlib.MainServiceBook;
import com.toolmvplibrary.activity_root.ActivityRoot;
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
    protected Typeface tfRegular;//定义字体
    {
        tfRegular = Typeface.createFromAsset(getAssets(), "fonts/NEUTRALGROTESK-BOLD.ttf");//初始化字体
//        textView.setTypeface(tfRegular);
    }

    private void initData() {
//        ItemEdit obj=new ItemEdit();
//
//        ClassFile bean=new ClassFile();
//        bean.setFils(obj);
    }

    private void initEvent() {
        pushVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoTrimmerUtil.pushVideo("","");
            }
        });
    }

    private ViewPager viewPager;
    private LinearLayoutManager linearLayoutManager;

    private TabLayout tabayView;
    private String[] title = {"App", "Demo", "我的"};
    private AdapterViewPager adapterViewPager;

    List<ItemPagerContent> listData = new ArrayList<>();
    private RecyclerView bannerRecycler;
    private AdapterMainBanner adapter;


    private InterListener listener = new InterListener() {
        @Override
        public void resultFinish(ResultDownLoader succ) {
            if (MainServiceBook.STATE == MainServiceBook.ACTIVE) {
                Intent i = new Intent(ActivityPerfect.this, MainServiceBook.class);
                stopService(i);
            } else {
                Intent i = new Intent(ActivityPerfect.this, MainServiceBook.class);
                startService(i);
            }

        }
    };
    private ImageView pushVideo;
    private void initView() {

        pushVideo = findViewById(R.id.pushVideo);
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
        ItemPagerContent bean = new ItemPagerContent();
        bean.icon = R.mipmap.image_banner_heard;
        listData.add(bean);


        ItemPagerContent bean2 = new ItemPagerContent();
        bean2.icon = R.mipmap.image_banner_flor;
        listData.add(bean2);

        ItemPagerContent bean3 = new ItemPagerContent();
        bean3.icon = R.mipmap.image_banner_user;
        listData.add(bean3);

        bannerRecycler = findViewById(R.id.imageBanner);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapter = new AdapterMainBanner(listData, listener);
        bannerRecycler.setLayoutManager(linearLayoutManager);
        bannerRecycler.setAdapter(adapter);
////        左右滑动会有磁吸效果。上线就无效
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(bannerRecycler);

    }
}
