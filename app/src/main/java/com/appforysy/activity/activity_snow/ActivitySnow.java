package com.appforysy.activity.activity_snow;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.appforysy.R;
import com.toolmvplibrary.activity_root.ActivityRoot;
import com.toolmvplibrary.view.snowview.SnowUtils;
import com.toolmvplibrary.view.snowview.SnowView;

public class ActivitySnow extends ActivityRoot {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snowview);
        snowView = findViewById(R.id.snowView);
        snowView.startSnowAnim(SnowUtils.SNOW_LEVEL_MIDDLE);
    }

    private SnowView snowView;
}
