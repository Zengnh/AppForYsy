package com.appforysy.activity.activity_shuerte;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.utils.ItemInfo;
import com.toolmvplibrary.activity_root.ActivityRoot;
import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.tool_app.ToolScreenDensity;
import com.toolmvplibrary.tool_app.ToolSoundPool;

import java.util.List;

public class ActiviyShuErTe extends ActivityRoot<PresenterShuErTe> {

    @Override
    protected PresenterShuErTe setPresenter() {
        return new PresenterShuErTe();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shu_er_te);
        initView();
    }

    private ToolSoundPool toolSoundPool;
    private GridView gridViewSET;
    private AdapterShuErTe adapterShuErTe;
    private List<ItemInfo> dataItemInfo;
    private ImageView tLayoutBack;
    private ImageView reLoadCutLevel;
    private TextView tLayoutTitle;
    private TextView timeCount;
    private CheckBox cbStartTime;

    private RecyclerView levelRecycler;


    private void initView() {
        tLayoutBack = findViewById(R.id.tLayoutBack);
        tLayoutTitle = findViewById(R.id.tLayoutTitle);
        tLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tLayoutTitle.setText("舒尔特方格");
        initRecyclerVIew();//难度
        initGridView();//舒尔特方格
        initEdit();
    }

    private void initEdit() {
        timeCount = findViewById(R.id.timeCount);
        reLoadCutLevel = findViewById(R.id.reLoadCutLevel);
        reLoadCutLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proInt = 1;
                for (ItemInfo item : dataItemInfo) {
                    item.type = 0;
                }
                adapterShuErTe.notifyDataSetChanged();
                coutTime = 0;
                timeCount.setText("");
                if (cbStartTime.isChecked()) {
                    cbStartTime.setChecked(false);
                }
            }
        });
        cbStartTime = findViewById(R.id.cbStartTime);
        cbStartTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    timeCount.setText(coutTime + "s");
                    startTime();
                } else {
                    handler.removeMessages(0);
                }
            }
        });
    }

    int coutTime = 0;

    public void startTime() {
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cbStartTime.setChecked(false);
    }

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            coutTime++;
            timeCount.setText(coutTime + "s");
            startTime();
        }
    };

    private void initGridView() {
        toolSoundPool = new ToolSoundPool(this);
        gridViewSET = findViewById(R.id.gridViewSET);
        gridViewSET.setNumColumns(presenter.rowCell);
        float oneWidth = (float) (ToolScreenDensity.getInstance().getRootViewWidth(this) - ToolScreenDensity.dp2px(this, 32)) / (float) presenter.rowCell;
        dataItemInfo = presenter.getContentInfo();
        adapterShuErTe = new AdapterShuErTe(dataItemInfo);
        adapterShuErTe.setAttribute(oneWidth, presenter.rowCell);
        gridViewSET.setAdapter(adapterShuErTe);
        gridViewSET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemInfo itemInfo = dataItemInfo.get(position);
                if (proInt == Integer.parseInt(itemInfo.text)) {
                    dataItemInfo.get(position).type = 1;
                    adapterShuErTe.notifyDataSetChanged();
                    toolSoundPool.playRight();
                    proInt++;

                    if (proInt > dataItemInfo.size()) {
                        if (cbStartTime.isChecked()) {
                            cbStartTime.setChecked(false);
                        }
                    }

                } else {
                    if (proInt > dataItemInfo.size()) {
                        showToast("以完成本难度挑战");
                    } else {
                        toolSoundPool.playErr();
                    }
                }
            }
        });
    }

    private AdapterLievelSET adapterLievelSET;

    private void initRecyclerVIew() {
        levelRecycler = findViewById(R.id.levelRecycler);
        levelRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterLievelSET = new AdapterLievelSET(presenter.getContentLevel(), new InterCallBack<Integer>() {
            @Override
            public void result(Integer res) {
                reflush(res);
            }

            @Override
            public void err(String msg) {

            }
        });

        levelRecycler.setAdapter(adapterLievelSET);
    }


    private void reflush(Integer res) {
        proInt = 1;
        ItemInfo item = presenter.getContentLevel().get(res);
        presenter.rowCell = item.type;
        gridViewSET.setNumColumns(presenter.rowCell);
        float oneWidth = (float) (ToolScreenDensity.getInstance().getRootViewWidth(this) - ToolScreenDensity.dp2px(this, 32)) / (float) presenter.rowCell;
        dataItemInfo.clear();
        dataItemInfo.addAll(presenter.getContentInfo());
        adapterShuErTe.setAttribute(oneWidth, presenter.rowCell);
        adapterShuErTe.notifyDataSetChanged();

    }

    private int proInt = 1;
}
