package com.mlq.course.activity_mlq;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mlq.course.R;
import com.mlq.course.activity_mlq_location.ActivityMlqLocation;
import com.toolmvplibrary.activity_root.ActivityRoot;
import com.toolmvplibrary.tool_premission.ToolAppPremission;

public class ActivityMLQCollege extends ActivityRoot<PresenterCollege> {
    @Override
    protected PresenterCollege setPresenter() {
        return new PresenterCollege();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlq_college);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        layoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityMlqLocation.startAct(ActivityMLQCollege.this);
            }
        });
        callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CALL_PHONE
                if(ToolAppPremission.requestCall(ActivityMLQCollege.this,111)){
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4008826911"));
                    startActivity(intent);
                }

            }
        });
    }

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AdapterCourse adapterTeacher;
    private View layoutLocation;
    private View callPhone;
    private void initView() {
        layoutLocation = findViewById(R.id.layoutLocation);
        callPhone = findViewById(R.id.callPhone);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterTeacher = new AdapterCourse(presenter.getDataList());
        recyclerView.setAdapter(adapterTeacher);
    }

    private void initData() {
        presenter.initData();
        adapterTeacher.notifyDataSetChanged();
    }

//    private Timer timerIntro;
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (timerIntro != null) {
//            timerIntro.cancel();
//            timerIntro = null;
//        }
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
////        startTime();
//    }

//    public void startTime() {
//        if (timerIntro == null) {
//            timerIntro = new Timer();
//            timerIntro.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            loadNextImageIntro();
//                        }
//                    });
//                }
//            }, 0, 4500);
//        }
//    }

//    public void loadNextImageIntro() {
//        imageViewIntroduct.setImageResource(presenter.getNextImg());
//        ToolGlide.loadImageRind(ActivityMLQCollege.this, presenter.getNextImg(), imageViewIntroduct, 0, 8);
//    }

//    public void exeStartAnim(ImageView img, ImageView image2) {
//        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
//        alphaAnimation.setDuration(2000);
//        //动画停留在结束的位置
//        alphaAnimation.setFillAfter(true);
//        //开启动画
//        img.startAnimation(alphaAnimation);
//        AlphaAnimation alphaAnimation2 = new AlphaAnimation(0, 1);
//        alphaAnimation2.setDuration(2000);
//        //动画停留在结束的位置
//        alphaAnimation2.setFillAfter(true);
//        //开启动画
//        image2.startAnimation(alphaAnimation);
//    }

}
