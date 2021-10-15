package com.appforysy.activity.mlqcollege.activity_mlq_imgintro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.mlqcollege.MLQTitle;
import com.appforysy.activity.mlqcollege.ToolDataInfo;
import com.appforysy.activity.mlqcollege.activity_mlq.ItemIntro;
import com.appforysy.activity.mlqcollege.activity_mlq.MYLTYPE;
import com.appforysy.activity.mlqcollege.activity_mlq_teacher_list.AdapterTeacherList;
import com.toolmvplibrary.activity_root.ActivityRoot;

import java.util.ArrayList;
import java.util.List;

public class ActivityMLQImagerList extends ActivityRoot {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, ActivityMLQImagerList.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlq_teacher_list);
        initView();
        initData();
    }

    private LinearLayoutManager linearLayoutManager;
    private MLQTitle titleLayout;

    private void initView() {

        contentRecycler = findViewById(R.id.contentRecycler);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contentRecycler.setLayoutManager(linearLayoutManager);

        titleLayout = findViewById(R.id.titleLayout);
        titleLayout.setTitle("学院简介");
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private List<ItemIntro> dataListIntro = new ArrayList<>();

    private void initData() {
        dataListIntro.clear();

        dataListIntro.clear();

        ItemIntro teacher8 = new ItemIntro();
        teacher8.imgResource = R.mipmap.introduct_08;
        teacher8.title = "学校前台";
        dataListIntro.add(teacher8);


        ItemIntro teacher7 = new ItemIntro();
        teacher7.imgResource = R.mipmap.introduct_07;
        teacher7.title = "学院接待区";
        dataListIntro.add(teacher7);


        ItemIntro teacher2 = new ItemIntro();
        teacher2.imgResource = R.mipmap.introduct_02;
        teacher2.title = "走廊环境";
        dataListIntro.add(teacher2);

        ItemIntro teacher = new ItemIntro();
        teacher.imgResource = R.mipmap.introduct_01;
        teacher.title = "学院简介";
        dataListIntro.add(teacher);

        ItemIntro teacher4 = new ItemIntro();
        teacher4.imgResource = R.mipmap.introduct_04;
        teacher4.title = "走廊环境";
        dataListIntro.add(teacher4);

        ItemIntro teacher3 = new ItemIntro();
        teacher3.imgResource = R.mipmap.introduct_03;
        teacher3.title = "教学环境";
        dataListIntro.add(teacher3);

        ItemIntro teacher5 = new ItemIntro();
        teacher5.imgResource = R.mipmap.introduct_05;
        teacher5.title = "教学环境";
        dataListIntro.add(teacher5);

        ItemIntro teacher6 = new ItemIntro();
        teacher6.imgResource = R.mipmap.introduct_06;
        teacher6.title = "教室";
        dataListIntro.add(teacher6);


        ItemIntro teacher9 = new ItemIntro();
        teacher9.imgResource = R.mipmap.introduct_09;
        teacher9.title = "彩妆教室";
        dataListIntro.add(teacher9);
        ItemIntro teacher10 = new ItemIntro();
        teacher10.imgResource = R.mipmap.introduct_10;
        teacher10.title = "教学环境";
        dataListIntro.add(teacher10);


        adapter = new AdapterMLQImg(dataListIntro);
        contentRecycler.setAdapter(adapter);
    }

    private RecyclerView contentRecycler;
    private AdapterMLQImg adapter;
}
