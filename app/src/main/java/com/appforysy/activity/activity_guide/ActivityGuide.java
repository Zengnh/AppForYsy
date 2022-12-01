package com.appforysy.activity.activity_guide;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.appforysy.R;
import com.appforysy.activity.activity_img_main.ActivityImageMain;
import com.appforysy.activity.activity_main.ActivityMain;
import com.toolmvplibrary.activity_root.ActivityRootInit;
import com.toolmvplibrary.activity_root.RootPresenter;

public class ActivityGuide extends ActivityRootInit {
    @Override
    protected RootPresenter setPresenter() {
        return null;
    }

    @Override
    public int setCutLayout() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        windowW = getWindowManager().getDefaultDisplay().getWidth();
        windowH = getWindowManager().getDefaultDisplay().getHeight();
        addLengW = windowW * 0.2f;
        addLengH = windowH * 0.2f;

        imageTxtTop = findViewById(R.id.imageTxtTop);
        anim_1 = findViewById(R.id.anim_1);
        anim_2 = findViewById(R.id.anim_2);

        anim_3 = findViewById(R.id.anim_3);
        anim_4 = findViewById(R.id.anim_4);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) anim_1.getLayoutParams();
        lp.width = (int) (windowW + addLengW);
        lp.setMargins(0, 0, (int) -addLengW, 0);
        anim_1.setLayoutParams(lp);
        anim_1.getWidth();

        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) anim_2.getLayoutParams();
        lp2.width = (int) (windowW + addLengW);
        lp2.setMargins((int) -addLengW, 0, 0, 0);
        anim_2.setLayoutParams(lp2);

        RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams) anim_3.getLayoutParams();
        lp3.width = (int) (windowW + addLengW);
        lp3.height = (int) (windowH + addLengH);

        lp3.setMargins(0, -(int) addLengH, -(int) addLengW, 0);
        anim_3.setLayoutParams(lp3);

        imageTxtTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startPos++;
//                playNextAnim();
//                if (startPos <= 7) {
//                    if (animatorSet != null) {
//                        animatorSet.pause();
//                        animatorSet.cancel();
//                    }
//                    playFinish();
//                }
            }
        });
    }

    @Override
    public void initData() {
        initAnimal();
    }

    private AnimatorSet animatorSet;
    private int startPos = 0;
    private Animator.AnimatorListener animListenr1 = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            startPos++;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            playNextAnim();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private void playNextAnim() {
        if (startPos == 1) {
            imageTxtTop.setImageResource(R.mipmap.img_loading_txt_2);
        } else if (startPos == 3) {
            imageTxtTop.setImageResource(R.mipmap.img_loading_txt_3);
        } else if (startPos == 5) {
            imageTxtTop.setImageResource(R.mipmap.img_loading_txt_4);
        }
        if (startPos == 1) {
            animatorSet = new AnimatorSet();
            animatorSet.addListener(animListenr1);
            animatorSet.playTogether(img1alpha, img2alphaShow, imgTxtShow);
            animatorSet.start();
        } else if (startPos == 2) {
            animatorSet = new AnimatorSet();
            animatorSet.addListener(animListenr1);
            animatorSet.play(imageMove2);
            animatorSet.start();
        } else if (startPos == 3) {
            animatorSet = new AnimatorSet();
            animatorSet.addListener(animListenr1);
            animatorSet.playTogether(img2alphaHit, img3alphaShow, imgTxtShow);
            animatorSet.start();
        } else if (startPos == 4) {
            animatorSet = new AnimatorSet();
            animatorSet.addListener(animListenr1);
            animatorSet.playTogether(imageMove3, imageMove32);
            animatorSet.start();
        } else if (startPos == 5) {
            animatorSet = new AnimatorSet();
            animatorSet.addListener(animListenr1);
            animatorSet.playTogether(img3alphaHit, img4alphaShow, imageMoveBig4, imageMoveBig42, imgTxtShow);
            animatorSet.start();
        } else if (startPos == 6) {
            animatorSet = new AnimatorSet();
            animatorSet.addListener(animListenr1);
            animatorSet.playTogether(imageMove4, imageMove42);
            animatorSet.start();

        } else if (startPos == 7) {
//                播放完成
            playFinish();

        }
    }

    private void playFinish() {
//        Intent intent=new Intent(this,MainActivity.class);
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
        finish();
    }


    private ImageView anim_1, anim_2, anim_3, anim_4;

    ObjectAnimator img1alpha;
    ObjectAnimator img2alphaShow;
    ObjectAnimator img2alphaHit;
    ObjectAnimator img3alphaShow;
    ObjectAnimator img3alphaHit;
    ObjectAnimator img4alphaShow;
    ObjectAnimator imgTxtShow;
    ObjectAnimator imageMove1;
    ObjectAnimator imageMove2;

    ObjectAnimator imageMove3;
    ObjectAnimator imageMove32;
    ObjectAnimator imageMove4;
    ObjectAnimator imageMove42;

    ObjectAnimator imageMoveBig4;
    ObjectAnimator imageMoveBig42;
//    private int move = 2000;
//    private int alpht = 1000;

    private int move = 1000;
    private int alpht = 500;


    public void initAnimal() {

        imageMove1 = ObjectAnimator.ofFloat(anim_1, View.TRANSLATION_X, 0f, -addLengW);
        imageMove1.setDuration(move);

        imageMove2 = ObjectAnimator.ofFloat(anim_2, View.TRANSLATION_X, 0f, addLengW);
        imageMove2.setDuration(move);

        imageMove3 = ObjectAnimator.ofFloat(anim_3, View.TRANSLATION_X, 0f, -addLengW);
        imageMove3.setDuration(move);

        imageMove32 = ObjectAnimator.ofFloat(anim_3, View.TRANSLATION_Y, 0f, addLengH);
        imageMove32.setDuration(move);


        imageMoveBig4 = ObjectAnimator.ofFloat(anim_4, View.SCALE_X, 1.0f, 2.0f);
        imageMoveBig4.setDuration(10);

        imageMoveBig42 = ObjectAnimator.ofFloat(anim_4, View.SCALE_Y, 1f, 2.0f);
        imageMoveBig42.setDuration(10);

        imageMove4 = ObjectAnimator.ofFloat(anim_4, View.SCALE_X, 2.0f, 1f);
        imageMove4.setDuration(move);

        imageMove42 = ObjectAnimator.ofFloat(anim_4, View.SCALE_Y, 2.0f, 1f);
        imageMove42.setDuration(move);

        img1alpha = ObjectAnimator.ofFloat(anim_1, View.ALPHA, 1f, 0f);
        img1alpha.setDuration(alpht);
        img2alphaShow = ObjectAnimator.ofFloat(anim_2, View.ALPHA, 0f, 1f);
        img2alphaShow.setDuration(alpht);

        img2alphaHit = ObjectAnimator.ofFloat(anim_2, View.ALPHA, 1f, 0f);
        img2alphaHit.setDuration(alpht);
        img3alphaShow = ObjectAnimator.ofFloat(anim_3, View.ALPHA, 0f, 1f);
        img3alphaShow.setDuration(alpht);
        img3alphaHit = ObjectAnimator.ofFloat(anim_3, View.ALPHA, 1f, 0f);
        img3alphaHit.setDuration(alpht);
        img4alphaShow = ObjectAnimator.ofFloat(anim_4, View.ALPHA, 0f, 1f);
        img4alphaShow.setDuration(alpht);


        imgTxtShow = ObjectAnimator.ofFloat(imageTxtTop, View.ALPHA, 0f, 1f);
        imgTxtShow.setDuration(alpht);


        animatorSet = new AnimatorSet();
        animatorSet.addListener(animListenr1);
        animatorSet.play(imageMove1);
        animatorSet.start();
    }

    private float addLengW;
    private float addLengH;
    private int windowW;
    private int windowH;
    private ImageView imageTxtTop;

}
