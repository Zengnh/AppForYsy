package com.appforysy.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.rootlibs.ToolSys;

/**
 * author:znh
 * time:2023/8/9
 * desc:
 */
public class TriangleView extends View {
    public Context context;

    public TriangleView(Context context) {
        super(context);
        this.context = context;
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint1.setARGB(255, 25, 25, 25);
        paint1.setStrokeWidth(2);
        paint1.setTextSize(dip2px(14));
        paint2.setARGB(255, 255, 33, 25);
        paint2.setTextSize(dip2px(14));
        paint3.setARGB(255, 25, 255, 25);
        paint3.setTextSize(dip2px(14));
        paint4.setARGB(255, 25, 25, 255);
        paint4.setTextSize(dip2px(14));
    }

    private Paint paint1 = new Paint();
    private Paint paint2 = new Paint();
    private Paint paint3 = new Paint();
    private Paint paint4 = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawText("画三角形：", 10, 200, paint);
// 绘制这个三角形,你可以绘制任意多边形

        int viewHight = 200;

        int hight1 = 200;
        int hight2 = 70;
        int hight3 = 30;

//        double tan=Math.tan(7);
//        double tan2=Math.tan(15);
        double tanValue = 0.4;
//        int width1 = Math.abs((int) (Math.tan(15) * hight1) * 2);
//        int width2 = Math.abs((int) (Math.tan(15) * hight2) * 2);
//        int width3 = Math.abs((int) (Math.tan(15) * hight3) * 2);
        int width1 = Math.abs((int) (tanValue * hight1) * 2);
        int width2 = Math.abs((int) (tanValue * hight2) * 2);
        int width3 = Math.abs((int) (tanValue * hight3) * 2);

        Path path = new Path();
        path.moveTo(dip2px(0), dip2px(viewHight - hight1));// 此点为多边形的起点
        path.lineTo(dip2px(width1), dip2px(viewHight - hight1));
        path.lineTo(dip2px(width1 / 2), dip2px(viewHight));
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint1);


        Path path2 = new Path();
        path2.moveTo(dip2px(width1 / 2 - width2 / 2), dip2px(viewHight - hight2));// 此点为多边形的起点
        path2.lineTo(dip2px(width1 / 2 + width2 / 2), dip2px(viewHight - hight2));
        path2.lineTo(dip2px(width1 / 2), dip2px(viewHight));
        path2.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path2, paint2);


        Path path3 = new Path();
        path3.moveTo(dip2px(width1 / 2 - width3 / 2), dip2px(viewHight - hight3));// 此点为多边形的起点
        path3.lineTo(dip2px(width1 / 2 + width3 / 2), dip2px(viewHight - hight3));
        path3.lineTo(dip2px(width1 / 2), dip2px(viewHight));
        path3.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path3, paint3);


        canvas.drawLine(dip2px(width1), dip2px(viewHight - hight1),
                dip2px(width1 + 20), dip2px(viewHight - hight1) + dip2px(20), paint1);
        canvas.drawLine(dip2px(width1 / 2 + width2 / 2), dip2px(viewHight - hight2),
                dip2px(width1 + 20), dip2px(viewHight - hight2), paint2);
        canvas.drawLine(dip2px(width1 / 2 + width3 / 2), dip2px(viewHight - hight3),
                dip2px(width1 + 20), dip2px(viewHight - hight3), paint3);
        canvas.drawLine(dip2px(width1 / 2), dip2px(viewHight),
                dip2px(width1 + 20), dip2px(viewHight - 5), paint4);

        canvas.drawText(text1, dip2px(width1 + 20), dip2px(viewHight - hight1) + dip2px(20), paint1);
        canvas.drawText(text2, dip2px(width1 + 20), dip2px(viewHight - hight2), paint2);
        canvas.drawText(text3, dip2px(width1 + 20), dip2px(viewHight - hight3), paint3);
        canvas.drawText(text4, dip2px(width1 + 20), dip2px(viewHight - 5), paint4);

    }

    public String text1 = "text1";
    public String text2 = "text2";
    public String text3 = "text3";
    public String text4 = "text4";


    public void setText(String t1, String t2, String t3, String t4) {
        this.text1 = t1;
        this.text2 = t2;
        this.text3 = t3;
        this.text4 = t4;
        this.invalidate();
    }

    public int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
