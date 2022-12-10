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
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appforysy.R;
import com.appforysy.utils.ToolTitleLayout;
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

    private DrawingView drawView;
    private ImageView buttonDrawable;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            buttonDrawable.setAlpha(0.5f);
            gridViewEidt.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }
    };

    private GridView gridViewEidt;
    private ListView listView;
    private ToolTitleLayout toolTitleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_drawer);
        initView();
        initEvent();
        initData();
    }

    private AdapterEditGrid adapterButton;
    private AdapterEditGrid leftAdapter;
    private List<ItemEdit> dataListButton = new ArrayList<>();
    private List<ItemEdit> leftList = new ArrayList<>();

    private void initData() {
        toolTitleLayout.setTitle("绘本");
        iamgeList.clear();
        iamgeList.add(R.mipmap.image_course_1);
        iamgeList.add(R.mipmap.image_course_2);
        iamgeList.add(R.mipmap.image_course_3);
        iamgeList.add(R.mipmap.image_course_4);
        showImage(false);
//            九宫格
        dataListButton.add(new ItemEdit(4, R.mipmap.more_icon, "保存图片"));
        dataListButton.add(new ItemEdit(1, R.mipmap.icon_team_delete_history, "删除路径"));
        dataListButton.add(new ItemEdit(2, R.mipmap.icon_nav_reset, "禁止绘制"));
        dataListButton.add(new ItemEdit(3, R.mipmap.icon_can_edit, "可以绘制"));
        dataListButton.add(new ItemEdit(5, R.mipmap.icon_contest_more_left, "上一张"));
        dataListButton.add(new ItemEdit(6, R.mipmap.icon_contest_more_right, "下一张"));
        adapterButton = new AdapterEditGrid(dataListButton);
        gridViewEidt.setNumColumns(dataListButton.size());
        gridViewEidt.setAdapter(adapterButton);


        leftList.add(new ItemEdit(11, R.mipmap.more_icon, "颜色"));
        leftList.add(new ItemEdit(12, R.mipmap.more_icon, "大小"));
        leftAdapter = new AdapterEditGrid(leftList);
        listView.setAdapter(leftAdapter);
    }

    private void initEvent() {

        gridViewEidt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dataListButton.get(position).flog == 1) {
                    drawView.cleanView();
                    showToast("清空绘制");
                } else if (dataListButton.get(position).flog == 2) {
//                    禁止绘制
                    showToast("禁止绘制");
                    drawView.setVisibility(View.GONE);
                } else if (dataListButton.get(position).flog == 3) {
//                    可以绘制
                    showToast("可以绘制");
                    drawView.setVisibility(View.VISIBLE);
                } else if (dataListButton.get(position).flog == 4) {
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
                } else if (dataListButton.get(position).flog == 5) {
//                    上一张
                    showImage(true);
                } else if (dataListButton.get(position).flog == 6) {
//                    下一张
                    showImage(false);

                }
                gridViewEidt.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemEdit item = leftList.get(i);
                if (item.flog == 11) {
//                            变更颜色
                } else if (item.flog == 12) {
//                    变更画笔大小

                    showSelectSize();
                }
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
                        listView.setVisibility(View.GONE);
                    } else {
                        gridViewEidt.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.VISIBLE);
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
        toolTitleLayout = new ToolTitleLayout(this);
        drawableRoot = findViewById(R.id.drawableRoot);
        drawView = findViewById(R.id.drawLineView);
        gridViewEidt = findViewById(R.id.gridViewEidt);
        listView = findViewById(R.id.listView);
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
        drawView.cleanView();
//        zoomImage.setImageResource(R.mipmap.image_course_3);
        zoomImage.setImageResource(iamgeList.get(cutPosImage));
    }


    private DialogStyleMy dialogSelectSize;
    private DrawingView drawLineShow;
    private SeekBar seekBar;

    public void showSelectSize() {
        if (dialogSelectSize == null) {

            View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_paint_size, null);
            seekBar = view.findViewById(R.id.seekBar);
            drawLineShow = view.findViewById(R.id.drawLineShow);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    drawLineShow.setPaintSize(i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            dialogSelectSize = new DialogStyleMy(this, view, "确定", "取消", new DialogListener() {
                @Override
                public void click(String str) {
                    dialogSelectSize.dismiss();
                    if (str.equals("确定")) {
                        drawView.setPaintSize(drawLineShow.getPaintSize());
                    }
                }
            });
            dialogSelectSize.setTitle("绘笔大小");
        }
        seekBar.setProgress(drawLineShow.getPaintSize());
        dialogSelectSize.show();
    }

}