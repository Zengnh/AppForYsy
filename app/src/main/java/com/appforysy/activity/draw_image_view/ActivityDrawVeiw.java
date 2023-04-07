package com.appforysy.activity.draw_image_view;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.utils.ToolTitleLayout;
import com.toolmvplibrary.activity_root.ActivityRoot;
import com.toolmvplibrary.tool_app.ToolCutSavePhoto;
import com.toolmvplibrary.view.DialogListener;
import com.toolmvplibrary.view.DialogStyleMy;
import com.toolmvplibrary.view.zoom_img.PhotoView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
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
            listView.setVisibility(View.GONE);
        }
    };

    private ListView listView;
    private ToolTitleLayout toolTitleLayout;
    private CheckBox cbEditStatus;
    private ImageView cleanProDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_drawer);
        initView();
        initEvent();
        initData();
    }

    private AdapterEditGrid leftAdapter;
    private List<ItemEdit> leftList = new ArrayList<>();

    private void initData() {
        toolTitleLayout.setTitle("绘本");
        iamgeList.clear();
        iamgeList.add(R.mipmap.bg_draw_01);
        iamgeList.add(R.mipmap.bg_draw_02);
        iamgeList.add(R.mipmap.bg_draw_03);
        iamgeList.add(R.mipmap.bg_draw_04);
        iamgeList.add(R.mipmap.bg_draw_05);
        iamgeList.add(R.mipmap.bg_draw_06);
        iamgeList.add(R.mipmap.bg_draw_07);
        showImage(true);
//            九宫格
        leftList.add(new ItemEdit(13, R.mipmap.more_icon, "背景"));
        leftList.add(new ItemEdit(11, R.mipmap.more_icon, "颜色"));
        leftList.add(new ItemEdit(12, R.mipmap.more_icon, "大小"));
        leftList.add(new ItemEdit(4, R.mipmap.more_icon, "保存图片"));
        leftList.add(new ItemEdit(1, R.mipmap.icon_team_delete_history, "删除路径"));
        leftList.add(new ItemEdit(5, R.mipmap.icon_contest_more_left, "上一张"));
        leftList.add(new ItemEdit(6, R.mipmap.icon_contest_more_right, "下一张"));

        leftAdapter = new AdapterEditGrid(leftList);
        listView.setAdapter(leftAdapter);
    }

    private void initEvent() {
        cbEditStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
//                    不允许绘制
                    drawView.setVisibility(View.GONE);
                } else {
//                    允许绘制
                    drawView.setVisibility(View.VISIBLE);
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemEdit item = leftList.get(i);
                if (item.flog == 11) {
                    //变更颜色
                    showSelectColor();
                } else if (item.flog == 12) {
                    //变更画笔大小
                    showSelectSize();
                } else if (item.flog == 13) {
                    showCanSelectImage();
                } else if (item.flog == 1) {
                    drawView.cleanView();
                    showToast("清空绘制");
                } else if (item.flog == 4) {
//                    保存备注生成图片
                    ToolCutSavePhoto tool = new ToolCutSavePhoto(ActivityDrawVeiw.this);
                    try {
//                        String path = tool.SaveBitmapFromView(drawableRoot);
                        String path = tool.SaveBitmapFromView(drawView);
                        Log.i("znh_save", "" + path);
                        showSaveSucc(path);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    showToast("保存完成");
                } else if (item.flog == 5) {
//                    上一张
                    showImage(true);
                } else if (item.flog == 6) {
//                    下一张
                    showImage(false);
                }
                listView.setVisibility(View.GONE);
            }
        });


        buttonDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dv.cleanView();
                if (buttonDrawable.getAlpha() == 1f) {
//                     点击了。
                    if (listView.getVisibility() == View.VISIBLE) {
                        listView.setVisibility(View.GONE);
                    } else {
                        listView.setVisibility(View.VISIBLE);
                    }
                } else {
                    handler.removeMessages(0);
                    buttonDrawable.setAlpha(1f);
                    handler.sendEmptyMessageDelayed(0, 5000);
                }
            }
        });

        cleanProDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.cleanProPaintPath();
            }
        });

    }

    private PhotoView zoomImage;

    private void initView() {
        toolTitleLayout =findViewById(R.id.titleLayout);

        toolTitleLayout.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cbEditStatus = findViewById(R.id.cbEditStatus);
        drawView = findViewById(R.id.drawLineView);
        listView = findViewById(R.id.listView);
        buttonDrawable = findViewById(R.id.buttonDrawable);
        zoomImage = findViewById(R.id.zoomImage);
        cleanProDraw = findViewById(R.id.cleanProDraw);
    }

    private DialogStyleMy dialog;

    public void showSaveSucc(String path) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_image_view, null);
        ImageView dialog_image = view.findViewById(R.id.dialog_image);
        TextView dialogText = view.findViewById(R.id.dialogText);
        dialogText.setText("文件位置：" + path);
        dialog_image.setImageBitmap(BitmapFactory.decodeFile(path));
        dialog = new DialogStyleMy(this, view, "取消", "确定", new DialogListener() {
            @Override
            public void click(String str) {
                dialog.dismiss();
                if (str.equals("取消")) {
                    try {
                        File file = new File(path);
                        file.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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


    private DialogStyleMy dialogSelectColor;

    private SeekBar seekBarColorR;
    private SeekBar seekBarColorG;
    private SeekBar seekBarColorB;
    private SeekBar seekBarColorA;

    private TextView colorShowView;

    public void showSelectColor() {
        if (dialogSelectColor == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_paint_color, null);
            seekBarColorR = view.findViewById(R.id.seekBarColorR);
            seekBarColorG = view.findViewById(R.id.seekBarColorG);
            seekBarColorB = view.findViewById(R.id.seekBarColorB);
            seekBarColorA = view.findViewById(R.id.seekBarColorA);
            colorShowView = view.findViewById(R.id.colorShowView);
            seekBarColorB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    colorB = i;
                    colorShowView.setBackgroundColor(Color.argb(colorA, colorR, colorG, colorB));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            seekBarColorR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    colorR = i;
                    colorShowView.setBackgroundColor(Color.argb(colorA, colorR, colorG, colorB));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            seekBarColorG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    colorG = i;
                    colorShowView.setBackgroundColor(Color.argb(colorA, colorR, colorG, colorB));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            seekBarColorA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    colorA = i;
                    colorShowView.setBackgroundColor(Color.argb(colorA, colorR, colorG, colorB));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            dialogSelectColor = new DialogStyleMy(this, view, "确定", "取消", new DialogListener() {
                @Override
                public void click(String str) {
                    dialogSelectColor.dismiss();
                    if (str.equals("确定")) {
                        drawView.setColorR(colorR);
                        drawView.setColorG(colorG);
                        drawView.setColorB(colorB);
                        drawView.setColorA(colorA);
                        drawView.reflushColor();
                    }
                }
            });
            dialogSelectColor.setTitle("绘笔颜色");
        }
        colorA = drawView.getColorA();
        colorR = drawView.getColorR();
        colorG = drawView.getColorG();
        colorB = drawView.getColorB();
        colorShowView.setBackgroundColor(Color.argb(colorA, colorR, colorG, colorB));
        seekBarColorR.setProgress(colorR);
        seekBarColorG.setProgress(colorG);
        seekBarColorB.setProgress(colorB);
        seekBarColorA.setProgress(colorA);

        dialogSelectColor.show();
    }

    private int colorA, colorR, colorG, colorB;
//    ------------------------------------------------------------

    DialogStyleMy dialogSelectImg;
    private RecyclerView showImageView;
    private AdapterDrawImg adapterDrawImg;
    private SeekBar seekBackgroundA;
    private float alphSelect = 1f;

    public void showCanSelectImage() {
        if (dialogSelectImg == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_paint_img, null);
            seekBackgroundA = view.findViewById(R.id.seekBackgroundA);
            showImageView = view.findViewById(R.id.showImageView);
            showImageView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            adapterDrawImg = new AdapterDrawImg(iamgeList);
            showImageView.setAdapter(adapterDrawImg);
            //吸附效果
            PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
            pagerSnapHelper.attachToRecyclerView(showImageView);
            seekBackgroundA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    alphSelect = i / 100f;
                    adapterDrawImg.setImageAlph(alphSelect);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            dialogSelectImg = new DialogStyleMy(this, view, "确定", "取消", new DialogListener() {
                @Override
                public void click(String str) {
                    dialogSelectImg.dismiss();
                    if (str.equals("确定")) {
                        int iClik = adapterDrawImg.getSelectItem();
                        if (cutPosImage != iClik) {
                            cutPosImage = iClik;
                            zoomImage.setImageResource(iamgeList.get(cutPosImage));
                        }
                        if(zoomImage.getAlpha()!=alphSelect){
                            zoomImage.setAlpha(alphSelect);
                        }
                    }
                }
            });
            dialogSelectImg.setTitle("绘笔背景");
        }
        alphSelect = zoomImage.getAlpha();
        adapterDrawImg.setImageAlph(alphSelect);
        adapterDrawImg.setSelectItem(cutPosImage);
        seekBackgroundA.setProgress((int) (alphSelect * 100));
        dialogSelectImg.show();
    }

}