package com.appforysy.activity.activity_rotation;
import com.appforysy.R;
import com.toolmvplibrary.activity_root.ActivityRoot;

public class ActivityRotation extends ActivityRoot {

    @Override
    public int setCutLayout() {
        return R.layout.activity_rotation;
    }
   private RotatView myRotatView;
    @Override
    public void initView() {
        myRotatView=findViewById(R.id.myRotatView);
    }

    @Override
    public void initData() {
        myRotatView.setRotatDrawableResource(R.mipmap.image_rolate);
    }


}
