package com.appforysy.activity.activity_game.fragment.game_shuerte;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.toolmvplibrary.activity_root.FragmenRoot;
import com.toolmvplibrary.activity_root.ItemInfo;
import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.tool_app.ToolScreenDensity;
import com.toolmvplibrary.tool_app.ToolSoundPool;

import java.util.List;

public class FragmentShuErTe extends FragmenRoot<PresenterShuErTe> {

    @Override
    protected PresenterShuErTe createPresenter() {
        return new PresenterShuErTe();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shu_er_te, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private ToolSoundPool toolSoundPool;
    private GridView gridViewSET;
    private AdapterShuErTe adapterShuErTe;
    private List<ItemInfo> dataItemInfo;

    private ImageView reLoadCutLevel;
    private TextView timeCount;
    private CheckBox cbStartTime;

    private RecyclerView levelRecycler;

    private SeekBar seekBarPingTu;
    private TextView textLevel;

    private void initView() {
        initGridView();//舒尔特方格
        initEdit();
    }

    private void initEdit() {
        textLevel = getActivity().findViewById(R.id.textLevel);
        seekBarPingTu = getActivity().findViewById(R.id.seekBarPingTu);
        timeCount = getActivity().findViewById(R.id.timeCount);
        reLoadCutLevel = getActivity().findViewById(R.id.reLoadCutLevel);
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
        cbStartTime = getActivity().findViewById(R.id.cbStartTime);
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

        seekBarPingTu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int cutLevel = progress + 2;
                reflush(cutLevel);
                textLevel.setText(cutLevel + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    int coutTime = 0;

    public void startTime() {
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 1000);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
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
        toolSoundPool = new ToolSoundPool(getContext());
        gridViewSET = getActivity().findViewById(R.id.gridViewSET);
        gridViewSET.setNumColumns(presenter.rowCell);
        float oneWidth = (float) (ToolScreenDensity.getInstance().getRootViewWidth(getActivity()) - ToolScreenDensity.dp2px(getContext(), 32)) / (float) presenter.rowCell;
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


    private void reflush(Integer res) {
        proInt = 1;
        presenter.rowCell = res;
        gridViewSET.setNumColumns(presenter.rowCell);
        float oneWidth = (float) (ToolScreenDensity.getInstance().getRootViewWidth(getActivity()) - ToolScreenDensity.dp2px(getContext(), 32)) / (float) presenter.rowCell;
        dataItemInfo.clear();
        dataItemInfo.addAll(presenter.getContentInfo());
        adapterShuErTe.setAttribute(oneWidth, presenter.rowCell);
        adapterShuErTe.notifyDataSetChanged();

    }

    private int proInt = 1;


}
