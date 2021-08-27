package com.cameralib.selectimg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.cameralib.R;

import java.util.ArrayList;

public class ActivityLocalImagePager extends AppCompatActivity {
    public static void startPager(Activity act, int pos, int request) {
        Intent intent = new Intent(act, ActivityLocalImagePager.class);
        intent.putExtra("pos", pos);
        act.startActivityForResult(intent, request);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localimage_pager);
        initView();
        initData();
        initEvent();
    }


    @Override
    public void onBackPressed() {
        if (playCenter.getVisibility() == View.VISIBLE) {
            playLayoutGone();
        } else {
//            finish();
            super.onBackPressed();
        }
    }

    private void initEvent() {
        playCenter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                playLayoutGone();
                return true;
            }
        });

        viewPlay.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                playCenter.setVisibility(View.GONE);
                return false;
            }
        });
        viewPlay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playCenter.setVisibility(View.GONE);
            }
        });

        viewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playLayoutGone();
//                videoPlayClick();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            viewPager.getCurrentItem();
//                VideoView vp_Btn = viewPager.findViewById(R.id.videoView);
//                if(vp_Btn.isPlaying()){
//                    vp_Btn.stopPlayback();
//                }
            }

            @Override
            public void onPageSelected(int position) {

                posState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataListSelect.size() > 0) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });
        viewPlay.setPlayPauseListener(new CustomVideoView.PlayPauseListener() {
            @Override
            public void onPlay() {
            }

            @Override
            public void onPause() {
                playCenter.setVisibility(View.GONE);
            }
        });
    }

    private void playLayoutGone() {
        if (viewPlay.isPlaying()) {
            viewPlay.stopPlayback();
        }
        playCenter.setVisibility(View.GONE);
    }


    private int cutItemPos = 0;
    private int selectImg = 0;

    private void posState(int position) {
        cutItemPos = position;
        if (dataList.get(position).isSelect) {
            right.setText(selectImg + "");
//            right.setBackgroundResource(R.drawable.shape_20dp_blue);
        } else {
            right.setText("");
//            right.setBackgroundResource(R.mipmap.multi_select_switch_normal);
        }
    }

    private AdapterImagePager adapter;
    private ArrayList<ImageBean> dataList;

    public void initData() {
        ArrayList<ImageBean> select = ToolLocationImage.getInstance().getPicked();
        selectImg = select.size();
        dataList = ToolLocationImage.getInstance().getAllImgVideo();
        int pos = getIntent().getIntExtra("pos", 0);
        adapter = new AdapterImagePager(this, dataList, new InterItemClick() {
            @Override
            public void clickPos(int pos, int type) {
                playCenter.setVisibility(View.VISIBLE);
                ImageBean bean = dataList.get(pos);
                viewPlay.setVideoPath(bean.getPath());
//                viewPlay.setMediaController(mc);
                viewPlay.requestFocus();
                viewPlay.start();
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
        posState(pos);
    }

    private ViewPager viewPager;
    private TextView right;
    private RecyclerView recyclerView;
    private Button sendImg;

    private AdapterSelect adapterSelect;
    private ArrayList<ImageBean> dataListSelect = new ArrayList<>();

    private RelativeLayout playCenter;
    private CustomVideoView viewPlay;

    public void initView() {
        right=findViewById(R.id.right);
        playCenter = findViewById(R.id.playCenter);
        viewPlay = findViewById(R.id.viewPlay);
        viewPager = findViewById(R.id.viewPager);
        recyclerView = findViewById(R.id.recyclerView);
        sendImg = findViewById(R.id.sendImg);
        if (ToolLocationImage.getInstance().getFromChat()) {
            sendImg.setText("发送");
        } else {
            sendImg.setText("完成");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dataListSelect.clear();
        dataListSelect.addAll(ToolLocationImage.getInstance().getPicked());
        adapterSelect = new AdapterSelect(dataListSelect, new InterItemClick() {
            @Override
            public void clickPos(int pos, int type) {
                viewPager.setCurrentItem(dataListSelect.get(pos).postion);
            }
        });
        recyclerView.setAdapter(adapterSelect);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dataList.get(cutItemPos).isSelect) {
                    dataList.get(cutItemPos).isSelect = false;
                } else {
                    if (selectImg < ToolLocationImage.getInstance().limitSelect()) {
                        dataList.get(cutItemPos).isSelect = true;
                    } else {
//                        showToast(getString(R.string.maxSelectPicture,
//                                String.valueOf(ToolLocationImage.getInstance().limitSelect())));
                        return;
                    }
                }
                ArrayList<ImageBean> select = ToolLocationImage.getInstance().getPicked();
                selectImg = select.size();

                posState(cutItemPos);
                dataListSelect.clear();
                dataListSelect.addAll(select);
                adapterSelect.notifyDataSetChanged();

            }
        });
        right.setTextColor(Color.WHITE);
        ViewGroup.LayoutParams lp = right.getLayoutParams();
        lp.height = 50;
        lp.width = 50;
        right.setPadding(0, 0, 0, 0);
        right.setGravity(Gravity.CENTER);
    }
}
