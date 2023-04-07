package com.appforysy.activity.activity_game.fragment.game_pingtu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.utils.EventRightBtnClick;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.toolmvplibrary.activity_root.FragmenRoot;
import com.toolmvplibrary.activity_root.ItemClick;
import com.toolmvplibrary.tool_app.ToolScreenDensity;
import com.toolmvplibrary.tool_app.ToolSoundPool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class FragmentPingTu extends FragmenRoot<PresenterPingTu> {

    @Override
    protected PresenterPingTu createPresenter() {
        return new PresenterPingTu();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pingtu, container, false);
        EventBus.getDefault().register(this);
        return view;
    }

    private ImageView resource, resource2;
    private RecyclerView recyclerViewcutImage;
    private AdapterFraPingTu adapterFraPingTu;

    private SeekBar seekBarPingTu;
    private GridLayoutManager gridLayoutmanaer;


    private TextView textLevel;
    ToolSoundPool toolSoundPool;
    private Button sureBtn, sureBtn1;
    private View setLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolSoundPool = new ToolSoundPool(getActivity());
        textLevel = getActivity().findViewById(R.id.textLevel);
        seekBarPingTu = getActivity().findViewById(R.id.seekBarPingTu);
        resource = getActivity().findViewById(R.id.resource);
        resource2 = getActivity().findViewById(R.id.resource2);
        sureBtn = getActivity().findViewById(R.id.sureBtn);
        sureBtn1 = getActivity().findViewById(R.id.sureBtn1);
        setLayout = getActivity().findViewById(R.id.setLayout);
        recyclerViewcutImage = getActivity().findViewById(R.id.recyclerViewcutImage);

        gridLayoutmanaer = new GridLayoutManager(getActivity(), presenter.row);
        recyclerViewcutImage.setLayoutManager(gridLayoutmanaer);
        presenter.initBitmap(R.mipmap.ping_tu1);
        adapterFraPingTu = new AdapterFraPingTu(presenter.dataList, listener);
        float oneWidth = (float) (ToolScreenDensity.getInstance().getRootViewWidth(getActivity()) - presenter.row * ToolScreenDensity.dp2px(getActivity(), 2)) / presenter.row;
        adapterFraPingTu.setOneWidth(oneWidth);
        recyclerViewcutImage.setAdapter(adapterFraPingTu);
        initEvent();
        textLevel.setText("L" + presenter.row);
        seekBarPingTu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                presenter.row = i + 3;
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
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout.setVisibility(View.GONE);
            }
        });
        sureBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout.setVisibility(View.GONE);
            }
        });
    }

    @Subscribe
    public void rightClick(EventRightBtnClick event) {
        setLayout.setVisibility(View.VISIBLE);
    }

    private int proSource = R.mipmap.ping_tu1;

    public void changeGroup() {
        gridLayoutmanaer.setSpanCount(presenter.row);
        presenter.initBitmap(proSource);
        float oneWidth = (float) (ToolScreenDensity.getInstance().getRootViewWidth(getActivity()) - presenter.row * ToolScreenDensity.dp2px(getActivity(), 2)) / presenter.row;
        adapterFraPingTu.setOneWidth(oneWidth);
        adapterFraPingTu.notifyDataSetChanged();
    }

    private int imgSelect = 1;

    private void initEvent() {
        resource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgSelect != 1) {
                    imgSelect = 1;
                    proSource = R.mipmap.ping_tu1;
                    presenter.initBitmap(R.mipmap.ping_tu1);
                    adapterFraPingTu.notifyDataSetChanged();
                }
            }
        });
        resource2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgSelect != 2) {
                    imgSelect = 2;
                    presenter.initBitmap(R.mipmap.ping_tu2);
                    proSource = R.mipmap.ping_tu2;
                    adapterFraPingTu.notifyDataSetChanged();
                }
            }
        });
    }

    private int proClickEmpty = -1;
    private int type = 2;
    private ItemClick<Integer> listener = new ItemClick<Integer>() {
        @Override
        public void itemClick(Integer rst) {

            toolSoundPool.playBeep();
//          判断上下左右四个有没有空的
//          有则交换值。并判断所有顺序是否是对的；
            ItemPinTu item = presenter.dataList.get(rst);
            if (type == 1) {
                if (item.isEmpty) {
                    if (proClickEmpty != -1) {
                        changTwoImg(rst, proClickEmpty);
                    }
                    proClickEmpty = -1;
                } else {
                    proClickEmpty = rst;
                }
            } else if (type == 2) {

                if (item.isEmpty) {
                    return;
                }
                int top = rst - presenter.row;
                if (top >= 0) {
                    ItemPinTu iTop = presenter.dataList.get(top);
                    if (iTop.isEmpty) {
                        changTwoImg(rst, top);
                        return;
                    }
                }
                int bottom = rst + presenter.row;
                if (bottom < presenter.dataList.size()) {
                    ItemPinTu iBottom = presenter.dataList.get(bottom);
                    if (iBottom.isEmpty) {
                        changTwoImg(rst, bottom);
                        return;
                    }
                }
                int left = -1;
                int right = -1;
                if ((rst) % presenter.row == 0) {
//                说明是最左边一列。只要判断右边
                    right = rst + 1;
                    ItemPinTu iRight = presenter.dataList.get(right);
                    if (iRight.isEmpty) {
                        changTwoImg(rst, right);
                        return;
                    }
                } else if ((rst + 1) % presenter.row == 0) {
                    //                说明是最右边的值

                    left = rst - 1;
                    ItemPinTu ileft = presenter.dataList.get(left);
                    if (ileft.isEmpty) {
                        changTwoImg(rst, left);
                        return;
                    }
                } else {
                    left = rst - 1;
                    ItemPinTu ileft = presenter.dataList.get(left);
                    if (ileft.isEmpty) {
                        changTwoImg(rst, left);
                        return;
                    }

                    right = rst + 1;
                    ItemPinTu iRight = presenter.dataList.get(right);
                    if (iRight.isEmpty) {
                        changTwoImg(rst, right);
                        return;
                    }
                }
            }
        }
    };

    private void changTwoImg(Integer rst, int oth) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ItemPinTu item = presenter.dataList.get(rst);
                presenter.dataList.set(rst, presenter.dataList.get(oth));
                presenter.dataList.set(oth, item);

                boolean isTrue = true;
                for (int i = 0; i < presenter.dataList.size(); i++) {
                    if (i != presenter.dataList.get(i).postion) {
                        isTrue = false;
                        break;
                    }
                }
                if (isTrue) {
                    showToast("完成啦");
                    toolSoundPool.playRight();
                    for (int i = 0; i < presenter.dataList.size(); i++) {
                        if (presenter.dataList.get(i).isEmpty) {
                            presenter.dataList.get(i).bm = presenter.emptyBm;
                            presenter.dataList.get(i).isEmpty = false;
                            break;
                        }
                    }
                }
                adapterFraPingTu.notifyDataSetChanged();
            }
        });
    }
}
