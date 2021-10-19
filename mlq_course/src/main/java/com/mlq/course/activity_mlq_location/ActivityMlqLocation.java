package com.mlq.course.activity_mlq_location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.mlq.course.MLQTitle;
import com.mlq.course.R;
import com.toolmvplibrary.activity_root.ActivityRoot;

public class ActivityMlqLocation extends ActivityRoot {
    public static void startAct(Context context) {
        Intent intent = new Intent(context, ActivityMlqLocation.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlq_local_detail);
        titleLayout = findViewById(R.id.titleLayout);
        titleLayout.setTitle("教学地址");
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private MLQTitle titleLayout;
}
