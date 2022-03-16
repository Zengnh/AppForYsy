package com.appforysy.activity.activity_anim;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.appforysy.R;
import com.appforysy.activity.activity_anim.AnimatorPath.AnimatorPath;
import com.appforysy.activity.activity_anim.AnimatorPath.PathEvaluator;
import com.appforysy.activity.activity_anim.AnimatorPath.PathPoint;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ActivityMainAnim extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fab;
    private AnimatorPath path;//声明动画集合
    ImageView imageAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_anim);
        this.fab = findViewById(R.id.fad_anim);
        imageAnim= findViewById(R.id.imageAnim);
        setPath();
        setPathImg();
        fab.setOnClickListener(this);
    }

    /*设置动画路径*/
    public void setPath() {
        path = new AnimatorPath();
        path.moveTo(0, 0);
        path.lineTo(400, 400);
        path.secondBesselCurveTo(600, 200, 800, 400); //订单
        path.thirdBesselCurveTo(100, 600, 900, 1000, 200, 1200);
    }
    private Path pathImg;//声明动画集合
    /*设置动画路径*/
    public void setPathImg() {
        pathImg = new Path();
        pathImg.moveTo(300, 300);
        pathImg.lineTo(400, 600);
        pathImg.quadTo(200, 300, 800, 400); //订单
        pathImg.cubicTo(100, 600, 900, 1000, 200, 1200);
    }

    /**
     * 设置动画
     *
     * @param view         使用动画的View
     * @param propertyName 属性名字
     * @param path         动画路径集合
     */
    private void startAnimatorPath(View view, String propertyName, AnimatorPath path) {
        ObjectAnimator anim = ObjectAnimator.ofObject(this, propertyName, new PathEvaluator(), path.getPoints().toArray());
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(3000);
        anim.start();
    }

    /**
     * 设置View的属性通过ObjectAnimator.ofObject()的反射机制来调用
     *
     * @param newLoc
     */
    public void setFab(PathPoint newLoc) {
        fab.setTranslationX(newLoc.mX);
        fab.setTranslationY(newLoc.mY);
    }


    private void startAnimatorImgPath(View view, AnimatorPath path) {
        ObjectAnimator anim = ObjectAnimator.ofObject(view,"translationX", new PathEvaluator(), path.getPoints().toArray());
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(3000);
        anim.start();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fad_anim:
                startAnimatorPath(fab, "fab", path);
                startAnimator(imageAnim,pathImg);
                break;
        }
    }

    @SuppressLint("WrongConstant")
    public void animatoerDemo(View tagView){
//        缩放（scale）  alpha,rotationX,translationX
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tagView, "scaleX", 1.0f, 1.5f);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(Animation.INFINITE);
        objectAnimator.setRepeatMode(Animation.RESTART);
        objectAnimator.start();
//        旋转（rotate）
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tagView, "rotationX", 0.0f, 90.0f,0.0F);
//        objectAnimator.setDuration(2000);
//        objectAnimator.setRepeatCount(Animation.INFINITE);
//        objectAnimator.setRepeatMode(Animation.RESTART);
//        objectAnimator.start();
//        ########################
//        setInterpolator()：设置动画插值
//        控制动画的变化速率，系统中定义了好几种Interpolator:
//        LinearInterpolator--均匀的速率改变
//        AccelerateDecelerateInterpolator--先加速后减速
//        AccelerateInterpolator--加速
//        DecelerateInterpolator--减速
//        CycleInterpolator--动画循环播放特定的次数，速率改变沿着正弦曲线
//        setDuration()：设置动画执行时间，动画时间以毫秒为单位（ms）
//        setRepeatCount()：设置动画重复次数
//        大于0的值就代表重复几次，如果需要无限循环，设为-1，上面的Animation.INFINITE是系统给的常量，值为-1，代表无限循环，我们建议使用这个常量，如果设为0呢？也是执行一次。
//        setRepeatMode()：设置动画重复模式
//        setStartDelay():设置动画延时操作，也是以毫秒为单位（ms）
//        setTarget():设置动画的对象


    }
    private void viewPropertyAnimator(View head) {
//        多属性动画
        ViewPropertyAnimator animator = head.animate();
        animator.translationX(200)
                .scaleX(2)
                .scaleY(2)
                .setDuration(2000)
                .start();
    }
    public void togler(View head){
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 2.5f, 1.0f);
        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 2.5f, 1.0f);
        PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("rotationX", 0.0f, 90.0f,0.0F);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(head, valuesHolder, valuesHolder1, valuesHolder2);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

//    路径
    private void startAnimator(View mView,Path path) {
        //mView 用来执行动画的View
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mView, mView.X, mView.Y, path);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(1500);
        objectAnimator.start();
    }

 PathMeasure  mPathMeasure;
    public void valueAnimator(View view){
        Path mPath=new Path();
        long duration=2000;
          mPathMeasure = new PathMeasure(mPath, true);
//设置运动的路径点
       float[] mCurrentPosition = new float[2];
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
//                绘制或者一党位置。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
//                得到当前时段的xy,绘制xy对应的图像，并更新ui
//                postInvalidate();
            }
        });
        valueAnimator.start();
    }


}
