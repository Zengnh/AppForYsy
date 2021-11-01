package com.zxinglib.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;

import com.zxinglib.R;


public class ScreenView extends RelativeLayout {
    private ImageView imageView;

    public ScreenView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        imageView = new ImageView(context);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, viewHight);
        imageView.setLayoutParams(lp);
        imageView.setBackgroundResource(R.mipmap.scan_light);
//        imageView.setImageResource(R.drawable.emoji_1f30d);
        addView(imageView);
//        startAni();
    }

    public ScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public ScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startAni();
    }

    @SuppressLint("WrongConstant")
    public void startAni() {
        ObjectAnimator mAnimator1 = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, -viewHight, getHeight() + viewHight);
        mAnimator1.setDuration(800);
        mAnimator1.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        mAnimator1.setRepeatMode(ValueAnimator.INFINITE);//
        mAnimator1.start();
    }

    private int viewHight = 150;
}
