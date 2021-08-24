package com.rootlibs.mysurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public MySurfaceView(Context context) {
        super(context);
        initView();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private SurfaceHolder mSurfaceHolder;
    //绘图的Canvas
    private Canvas mCanvas;
    //子线程标志位
    private boolean mIsDrawing;

    /**
     * 初始化View
     */
    private void initView() {

        mSurfaceHolder = getHolder();
        //注册回调方法
        mSurfaceHolder.addCallback(this);
        //设置一些参数方便后面绘图
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
        initPaint();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        页面创建
        mIsDrawing = true;
        //开启子线程
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        变更
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//    页面销毁
        mIsDrawing = false;
        //开启子线程
    }

    @Override
    public void run() {
//        子线程
//        绘制逻辑
        while (mIsDrawing) {
            long start = System.currentTimeMillis();
            drawSomething();
            long end = System.currentTimeMillis();
            if (end - start < 88) {
                try {
                    Thread.sleep(88 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
//        while (mIsDrawing) {
//            drawSomething();
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


    //绘图逻辑
    private void drawSomething() {
        try {
            //获得canvas对象
            mCanvas = mSurfaceHolder.lockCanvas();
            //绘制背景
            mCanvas.drawColor(Color.WHITE);
            //绘制路径
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {

        } finally {
            if (mCanvas != null) {
                //释放canvas对象并提交画布
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }

//        try {
//            //获得canvas对象
//            mCanvas = mSurfaceHolder.lockCanvas();
//            //绘制背景
//            mCanvas.drawColor(Color.WHITE);
//            //绘图
//
//
//        } catch (Exception e) {
//
//        } finally {
//            if (mCanvas != null) {
//                //释放canvas对象并提交画布
//                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
//            }
//        }
    }

    //    #######################################################
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);

        mPath = new Path();
        mPath.moveTo(0, 100);
    }

    //画笔
    private Paint mPaint;
    //路径
    private Path mPath;
}
