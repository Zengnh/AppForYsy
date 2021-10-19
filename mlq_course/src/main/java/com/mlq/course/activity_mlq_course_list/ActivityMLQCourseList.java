package com.mlq.course.activity_mlq_course_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mlq.course.MLQTitle;
import com.mlq.course.R;
import com.mlq.course.ToolDataInfo;
import com.mlq.course.activity_mlq.ItemIntro;
import com.mlq.course.activity_mlq.MYLTYPE;
import com.toolmvplibrary.activity_root.ActivityRoot;

import java.util.ArrayList;
import java.util.List;

public class ActivityMLQCourseList extends ActivityRoot {
    public static void startAct(Context context) {
        Intent intent = new Intent(context, ActivityMLQCourseList.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlq_course_list);
        initView();
        initData();
    }

    private LinearLayoutManager linearLayoutManager;

    MLQTitle titleLayout;

    private void initView() {
        contentRecycler = findViewById(R.id.contentRecycler);
        titleLayout = findViewById(R.id.titleLayout);

        titleLayout.setTitle("课程简介");
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contentRecycler.setLayoutManager(linearLayoutManager);
    }

    private List<ItemIntro> dataListIntro = new ArrayList<>();

    private void initData() {
        dataListIntro.clear();

        ItemIntro course = new ItemIntro();
        course.type = MYLTYPE.COURSE;
        course.imgResource = R.mipmap.course_01;
        course.id = ToolDataInfo.course01;
        course.content = "形象美学进阶培训课程，*教学老师教学，手把手教学，全方位帮助学员掌握形象美学专";
        course.title = "形象美学进阶培训课程";
        dataListIntro.add(course);

        ItemIntro course2 = new ItemIntro();
        course2.type = MYLTYPE.COURSE;
        course2.id = ToolDataInfo.course02;
        course2.imgResource = R.mipmap.course_02;
        course2.content = "形象美学基础培训课程，业内**授课辅导，理论＋实操活学活用，循序渐进学习，帮";
        course2.title = "形象美学基础培训课程";
        dataListIntro.add(course2);


        ItemIntro course3 = new ItemIntro();
        course3.type = MYLTYPE.COURSE;
        course3.id = ToolDataInfo.course03;
        course3.imgResource = R.mipmap.course_03;
        course3.content = "高级搭配师培训课程，本培训零基础教学，主要针对美学爱好者，化妆师、纹绣师等美业从";
        course3.title = "高级搭配师培训课程";
        dataListIntro.add(course3);

        ItemIntro course4 = new ItemIntro();
        course4.type = MYLTYPE.COURSE;
        course4.id = ToolDataInfo.course04;
        course4.imgResource = R.mipmap.course_04;
        course4.content = "专业形体培训课程，沐灵茜*教学老师一对一教学，理论实践结合培训，致力于打造美业";
        course4.title = "专业形体培训课程";
        dataListIntro.add(course4);


        ItemIntro course5 = new ItemIntro();
        course5.type = MYLTYPE.COURSE;
        course5.id = ToolDataInfo.course05;
        course5.imgResource = R.mipmap.course_05;
        course5.content = "高级*创业培训课程，培训课时2个月，专业老师手把手教学，讲练结合，锻炼提升学员";
        course5.title = "高级搭配师培训课程";
        dataListIntro.add(course5);

        ItemIntro course6 = new ItemIntro();
        course6.type = MYLTYPE.COURSE;
        course6.id = ToolDataInfo.course06;
        course6.imgResource = R.mipmap.course_06;
        course6.content = "皮肤管理培训课程，培训课时7天，教学生动有趣，课堂活跃，从理论讲解到实际操作，均";
        course6.title = "皮肤管理培训课程";
        dataListIntro.add(course6);

        adapter = new AdapterCourseList(dataListIntro);
        contentRecycler.setAdapter(adapter);
    }

    private RecyclerView contentRecycler;
    private AdapterCourseList adapter;
}
