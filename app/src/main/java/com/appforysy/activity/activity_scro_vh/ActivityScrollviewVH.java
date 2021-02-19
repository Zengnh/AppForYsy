package com.appforysy.activity.activity_scro_vh;

import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appforysy.R;
import com.appforysy.activity.activity_time_note.ToolColor;
import com.toolmvplibrary.activity_root.ActivityRoot;
import com.toolmvplibrary.activity_root.RootPresenter;


import java.util.Random;

public class ActivityScrollviewVH extends ActivityRoot {
    @Override
    protected RootPresenter setPresenter() {
        return null;
    }

    @Override
    public int setCutLayout() {
        return R.layout.activvity_scrollview_vh;
    }

    ScrollView scrollVert;
    HorizontalScrollView scrollHori;
    LinearLayout contentLayout;

    @Override
    public void initView() {
        ToolColor.getInstance();
        scrollVert = findViewById(R.id.scrollVert);
        scrollHori = findViewById(R.id.scrollHori);

        contentLayout = findViewById(R.id.contentLayout);

        scrollVert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollHori.onTouchEvent(event);
                return false;
            }
        });

        scrollHori.setFocusable(false);
        addView();
    }

    @Override
    public void initData() {

    }

    Random rand=new Random();
    public void addView() {
        for (int i = 0; i < 15; i++) {
            LinearLayout linearlayout = new LinearLayout(this);
            linearlayout.setOrientation(LinearLayout.VERTICAL);
            for (int j = 0; j < 55; j++) {
                TextView tv = new TextView(this);

                tv.setPadding(20, 20, 20, 20);
                tv.setText("第" + j + "行 第" + i + "列内容");
                tv.setBackgroundColor(getResources().getColor(ToolColor.getInstance().getColor(j+rand.nextInt(15))));
                linearlayout.addView(tv);
            }
            contentLayout.addView(linearlayout);
        }
    }

}
