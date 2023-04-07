package com.appforysy.activity.activity_game.fragment.game_different;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.activity_game.fragment.game_pingtu.ItemPinTu;
import com.toolmvplibrary.activity_root.FragmenRoot;
import com.toolmvplibrary.activity_root.ItemClick;
import com.toolmvplibrary.tool_app.ToolScreenDensity;
import com.toolmvplibrary.tool_app.ToolSoundPool;

public class FragmentChinaDifferent extends FragmenRoot<PresenterDrage> {

    @Override
    protected PresenterDrage createPresenter() {
        return new PresenterDrage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pingtu_drage, container, false);
        return view;
    }

    private RecyclerView targeImage;
    private AdapterChinaDifferent adapterFraPingTu;
    private SeekBar seekBarPingTu;
    private GridLayoutManager gridLayoutmanaer;

    private TextView textLevel;
    ToolSoundPool toolSoundPool;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolSoundPool = new ToolSoundPool(getActivity());
        textLevel = getActivity().findViewById(R.id.textLevel);
        seekBarPingTu = getActivity().findViewById(R.id.seekBarPingTu);
        initTagerView();
        initEvent();
        textLevel.setText("L" + presenter.row);
        seekBarPingTu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                presenter.row = i + 3;
                presenter.countDiff = 0;
                textLevel.setText("L" + presenter.row);
                changeGroup();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initTagerView() {
        targeImage = getActivity().findViewById(R.id.recyclerViewcutImage);
        gridLayoutmanaer = new GridLayoutManager(getActivity(), presenter.row);
        targeImage.setLayoutManager(gridLayoutmanaer);
        presenter.initChangeDef();
        adapterFraPingTu = new AdapterChinaDifferent(presenter.sourceDataList);
        setSize();
        presenter.countDiff = 0;
        targeImage.setAdapter(adapterFraPingTu);
        adapterFraPingTu.addListenter(new ItemClick<Integer>() {
            @Override
            public void itemClick(Integer rst) {
                ItemPinTu item = presenter.sourceDataList.get(rst);
                if (item.content.equals(presenter.difText)&&!item.isSelect) {
                    toolSoundPool.playDi();
                    item.isSelect = true;
                    presenter.countDiff++;
                    adapterFraPingTu.notifyDataSetChanged();

                    if (presenter.countDiff == presenter.row) {
//                      可以做下一个难度了
                        seekBarPingTu.setProgress(seekBarPingTu.getProgress() + 1);
                    }
                } else {
                    toolSoundPool.playErr();
                }
            }
        });
    }

    public void changeGroup() {
        presenter.changeLevel();
        gridLayoutmanaer.setSpanCount(presenter.row);
        setSize();
        adapterFraPingTu.notifyDataSetChanged();
    }

    public void setSize() {
        float oneWidth = (float) ((ToolScreenDensity.getInstance().getRootViewWidth(getActivity()) - ToolScreenDensity.dp2px(getActivity(), 2)) - presenter.row * ToolScreenDensity.dp2px(getActivity(), 2)) / presenter.row;
        adapterFraPingTu.setOneWidth(oneWidth);
    }

    private void initEvent() {

    }

}