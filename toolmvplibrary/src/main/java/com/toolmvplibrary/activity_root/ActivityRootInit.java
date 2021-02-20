package com.toolmvplibrary.activity_root;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
public abstract class ActivityRootInit<P extends RootPresenter> extends ActivityRoot<P> {
    /**
     * 设置布局
     *
     * @return
     */
    public abstract int setCutLayout();
    /**
     * 初始化视图
     */
    public abstract void initView();
    public abstract void initData();
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initView();
        initData();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setCutLayout());
        initView();
        initData();
    }
}
