package com.appforysy.activity.draw_line;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appforysy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.toolmvplibrary.activity_root.ActivityRoot;
import com.toolmvplibrary.tool_app.ToolCutSavePhoto;
import com.toolmvplibrary.view.DialogListener;
import com.toolmvplibrary.view.DialogStyleMy;
import com.toolmvplibrary.view.zoom_img.PhotoView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ActivityDrawVeiw extends ActivityRoot {

    private DrawingView dv;
    private FloatingActionButton buttonDrawable;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            buttonDrawable.setAlpha(0.5f);
            gridViewEidt.setVisibility(View.GONE);
        }
    };

    private GridView gridViewEidt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_drawer);
        initView();
        initEvent();
        initData();
    }

    private AdapterEditGrid adapter;
    private List<ItemEdit> dataList = new ArrayList<>();

    private void initData() {
        iamgeList.clear();
        iamgeList.add(R.mipmap.image_course_1);
        iamgeList.add(R.mipmap.image_course_2);
        iamgeList.add(R.mipmap.image_course_3);
        iamgeList.add(R.mipmap.image_course_4);
        showImage(false);
//            九宫格
        for (int i = 0; i < 9; i++) {
            ItemEdit item = new ItemEdit();
            dataList.add(item);
        }

        dataList.get(6).icon = R.mipmap.icon_red_cut;
        dataList.get(6).flog = 4;
        dataList.get(2).icon = R.mipmap.icon_red_delete;
        dataList.get(2).flog = 1;
        dataList.get(4).icon = R.mipmap.icon_red_distory;


        dataList.get(7).icon = R.mipmap.icon_red_no;
        dataList.get(7).flog = 2;
        dataList.get(7).itemName = "禁止顶层绘制";

        dataList.get(5).icon = R.mipmap.icon_red_write;
        dataList.get(5).flog = 3;
        dataList.get(5).itemName = "可以绘制";


        dataList.get(1).flog = 6;
        dataList.get(1).itemName = "下一张";
        dataList.get(1).icon = R.mipmap.icon_red_toright;
        dataList.get(0).flog = 5;
        dataList.get(0).itemName = "上一张";
        dataList.get(0).icon = R.mipmap.icon_red_toleft;

        adapter = new AdapterEditGrid(dataList);
        gridViewEidt.setAdapter(adapter);
    }

    private void initEvent() {

        gridViewEidt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dataList.get(position).flog == 1) {
                    dv.cleanView();
                    showToast("清空绘制");
                } else if (dataList.get(position).flog == 2) {
//                    禁止绘制
                    showToast("禁止绘制");
                    dv.setVisibility(View.GONE);
                } else if (dataList.get(position).flog == 3) {
//                    可以绘制
                    showToast("可以绘制");
                    dv.setVisibility(View.VISIBLE);
                } else if (dataList.get(position).flog == 5) {
//                    上一张
                    showImage(true);
                } else if (dataList.get(position).flog == 6) {
//                    下一张
                    showImage(false);
                } else if (dataList.get(position).flog == 4) {
//                    保存备注生成图片
                    ToolCutSavePhoto tool = new ToolCutSavePhoto(ActivityDrawVeiw.this);
                    try {
                        String path = tool.SaveBitmapFromView(drawableRoot);
                        Log.i("znh_save", "" + path);
                        showSaveSucc(path);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    showToast("保存完成");
                }
                gridViewEidt.setVisibility(View.GONE);
            }
        });


        buttonDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dv.cleanView();

                if (buttonDrawable.getAlpha() == 1f) {
//                     点击了。
                    if (gridViewEidt.getVisibility() == View.VISIBLE) {
                        gridViewEidt.setVisibility(View.GONE);
                    } else {
                        gridViewEidt.setVisibility(View.VISIBLE);
                    }
                } else {
                    handler.removeMessages(0);
                    buttonDrawable.setAlpha(1f);
                    handler.sendEmptyMessageDelayed(0, 5000);
                }
            }
        });
    }

    private View drawableRoot;
    PhotoView zoomImage;

    private void initView() {
        drawableRoot = findViewById(R.id.drawableRoot);
        dv = findViewById(R.id.drawLineView);
        gridViewEidt = findViewById(R.id.gridViewEidt);
        buttonDrawable = findViewById(R.id.buttonDrawable);
        zoomImage = findViewById(R.id.zoomImage);

    }

    private DialogStyleMy dialog;

    public void showSaveSucc(String path) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_image_view, null);
        ImageView dialog_image = view.findViewById(R.id.dialog_image);
        TextView dialogText = view.findViewById(R.id.dialogText);
        dialogText.setText("文件位置：" + path);
        dialog_image.setImageBitmap(BitmapFactory.decodeFile(path));
        dialog = new DialogStyleMy(this, view, "返回", "继续", new DialogListener() {
            @Override
            public void click(String str) {
                dialog.dismiss();
                if (str.equals("返回")) {
                    finish();
                }
            }
        });
        dialog.show();

    }

    private int cutPosImage = -1;
    private List<Integer> iamgeList = new ArrayList();

    public void showImage(boolean ispro) {


        if (ispro) {
            cutPosImage++;
            if (cutPosImage >= iamgeList.size()) {
                cutPosImage = 0;
            }
        } else {
            cutPosImage--;
            if (cutPosImage < 0) {
                cutPosImage = iamgeList.size() - 1;
            }
        }
        dv.cleanView();
//        zoomImage.setImageResource(R.mipmap.image_course_3);
        zoomImage.setImageResource(iamgeList.get(cutPosImage));
    }

}