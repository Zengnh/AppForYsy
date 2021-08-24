package com.appforysy.activity.draw_line;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.appforysy.R;

public class ActivityDrawVeiw extends Activity {

    private DrawingView dv;
    private Button buttonDrawable;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_drawer);
        dv = findViewById(R.id.drawLineView);
        imageView = findViewById(R.id.iamgeView);
        buttonDrawable = findViewById(R.id.buttonDrawable);
        buttonDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dv.cleanView();
            }
        });

//        dv.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View arg0, MotionEvent arg1) {
//                return false;
//            }
//        });
    }
}