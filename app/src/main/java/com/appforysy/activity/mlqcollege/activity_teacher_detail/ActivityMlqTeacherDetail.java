package com.appforysy.activity.mlqcollege.activity_teacher_detail;

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
import com.appforysy.activity.mlqcollege.activity_course_detail.ActivityMlqCourseDetail;
import com.appforysy.activity.mlqcollege.activity_mlq.ItemIntro;
import com.cameralib.ActivityCameraResult;
import com.toolmvplibrary.activity_root.ActivityRoot;

public class ActivityMlqTeacherDetail extends ActivityRoot {

    public static void startAct(Context context,String id) {
        Intent intent = new Intent(context, ActivityMlqTeacherDetail.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlq_teacher_detail);
        initView();
        initData();
        initEvent();
    }
    private ImageView imageView;
    private TextView content;
    private void initView() {
        titleLayout = findViewById(R.id.titleLayout);
        titleLayout.setTitle("课程详情");
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageView = findViewById(R.id.imageView);
        content = findViewById(R.id.content);
    }

    private void initData() {
        String id = getIntent().getStringExtra("id");
        ItemIntro bean = ToolDataInfo.getInstance().getCutTeacher(id);
        titleLayout.setTitle(bean.title);
        imageView.setImageResource(bean.imgResource);
        content.setText("简介：" +bean.content);
    }

    private void initEvent() {

    }

    private MLQTitle titleLayout;
}
