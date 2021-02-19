package com.appforysy.activity.activity_rotation;
import com.appforysy.R;
import com.toolmvplibrary.activity_root.ActivityRoot;
import com.toolmvplibrary.activity_root.RootPresenter;

public class ActivityRotation extends ActivityRoot {

    @Override
    protected RootPresenter setPresenter() {
        return null;
    }

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
