package com.cameralib.camera_result;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cameralib.R;
import com.cameralib.ToolCameraManager;

/**
 * 摄像头结果页面
 */
public class ActivityTakePhotoView extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cameralib_activity_take_photo_view);
        initView();
    }
    ImageView takeImage;
    private void initView() {
        takeImage=findViewById(R.id.takeImage);
        takeImage.setImageBitmap(ToolCameraManager.getInstance().getTakeBm());
    }
}
