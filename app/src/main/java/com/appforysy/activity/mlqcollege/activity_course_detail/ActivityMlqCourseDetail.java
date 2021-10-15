package com.appforysy.activity.mlqcollege.activity_course_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appforysy.R;
import com.appforysy.activity.mlqcollege.MLQTitle;
import com.appforysy.activity.mlqcollege.ToolDataInfo;
import com.appforysy.activity.mlqcollege.activity_mlq.ItemIntro;
import com.cameralib.ActivityCameraResult;
import com.toolmvplibrary.activity_root.ActivityRoot;

public class ActivityMlqCourseDetail extends ActivityRoot {

    public static void startAct(Context context, String id) {
        Intent intent = new Intent(context, ActivityMlqCourseDetail.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlq_course_detail);

        initView();
        initData();
        initEvent();
    }

    private ImageView imageView;
    private TextView price, courseClass, location, organization, content;

    private void initView() {

        titleLayout = findViewById(R.id.titleLayout);
        titleLayout.setTitle("课程详情");
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toVideo = findViewById(R.id.toVideo);
        imageView = findViewById(R.id.imageView);
        price = findViewById(R.id.price);
        courseClass = findViewById(R.id.courseClass);
        location = findViewById(R.id.location);
        organization = findViewById(R.id.organization);
        content = findViewById(R.id.content);

    }

    private Button toVideo;

    private void initData() {
        String id = getIntent().getStringExtra("id");
        ItemIntro bean = ToolDataInfo.getInstance().getCutCourse(id);

        titleLayout.setTitle(bean.title);
        imageView.setImageResource(bean.imgResource);
        price.setText("费用：" + bean.price);
        courseClass.setText("开设班级：" + bean.courseClass);
        location.setText("上课地点：" + bean.location);
        organization.setText("授权机构：" + bean.organization);
        content.setText(bean.content);
    }

    private void initEvent() {
        toVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                file:///android_asset/
                ActivityCameraResult.startToResult(ActivityMlqCourseDetail.this, "video01.mp4",true);
            }
        });
    }

    private MLQTitle titleLayout;
}
