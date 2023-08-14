package com.appforysy.activity.activity_game.fragment.game_pingtu_drage;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.activity_game.fragment.game_pingtu.ItemPinTu;
import com.toolmvplibrary.activity_root.FragmenRoot;
import com.toolmvplibrary.activity_root.ItemClick;
import com.toolmvplibrary.tool_app.ToolScreenDensity;
import com.toolmvplibrary.tool_app.ToolSoundPool;
import com.toolmvplibrary.view.DialogListener;
import com.toolmvplibrary.view.DialogStyleMy;

public class FragmentPingTuDrage extends FragmenRoot<PresenterDrage> {

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
    private AdapterImageTarge adapterFraPingTu;
    private SeekBar seekBarPingTu;
    private GridLayoutManager gridLayoutmanaer;

    private TextView textLevel;
    ToolSoundPool toolSoundPool;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolSoundPool = new ToolSoundPool(getActivity());

//        imageSmail = getActivity().findViewById(R.id.imageSmail);
        textLevel = getActivity().findViewById(R.id.textLevel);
        seekBarPingTu = getActivity().findViewById(R.id.seekBarPingTu);
        presenter.initBitmap(R.mipmap.ping_tu1);
//        imageSmail.setImageResource(R.mipmap.ping_tu1);
//        initSource();
        initTagerView();
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

//        imageSmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDialog();
//            }
//        });
    }

    DialogStyleMy dialogStyleMy;

    public void showDialog() {
        if (dialogStyleMy == null) {
            ImageView image = new ImageView(getActivity());
            image.setImageResource(R.mipmap.ping_tu1);
            dialogStyleMy = new DialogStyleMy(getActivity(), image, "确定", new DialogListener() {
                @Override
                public void click(String str) {
                    dialogStyleMy.dismiss();
                }
            });
            dialogStyleMy.setTitle("原图");
        }
        dialogStyleMy.show();
    }

//    private RecyclerView randImageView;
//    private AdapterSelectImageResource adapterResource;
//    private void initSource() {
//        randImageView = getActivity().findViewById(R.id.randImageView);
//        adapterResource = new AdapterSelectImageResource(presenter.sourceDataList, listenerSource);
//        randImageView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        randImageView.setAdapter(adapterResource);
//    }

    private void initTagerView() {
        targeImage = getActivity().findViewById(R.id.recyclerViewcutImage);
        gridLayoutmanaer = new GridLayoutManager(getActivity(), presenter.row);
        targeImage.setLayoutManager(gridLayoutmanaer);
        adapterFraPingTu = new AdapterImageTarge(presenter.targeDataList);
        float oneWidth = (float) (ToolScreenDensity.getInstance().getRootViewWidth(getActivity()) - presenter.row * ToolScreenDensity.dp2px(getActivity(), 2)) / presenter.row;
        adapterFraPingTu.setOneWidth(oneWidth);
        targeImage.setAdapter(adapterFraPingTu);
        draTag();
    }

    private int proSource = R.mipmap.ping_tu1;

    public void changeGroup() {
        gridLayoutmanaer.setSpanCount(presenter.row);
        presenter.initBitmap(proSource);
        float oneWidth = (float) (ToolScreenDensity.getInstance().getRootViewWidth(getActivity()) - presenter.row * ToolScreenDensity.dp2px(getActivity(), 2)) / presenter.row;
        adapterFraPingTu.setOneWidth(oneWidth);
        adapterFraPingTu.notifyDataSetChanged();
////        333333333333333333333333333333333333333333333333333333
//        adapterResource.notifyDataSetChanged();
    }

    private void initEvent() {

    }

    private ItemClick<Integer> listenerSource = new ItemClick<Integer>() {
        @Override
        public void itemClick(Integer rst) {
            toolSoundPool.playBeep();
        }
    };
//    private ItemClick<Integer> listenerTrag = new ItemClick<Integer>() {
//        @Override
//        public void itemClick(Integer rst) {
//            toolSoundPool.playBeep();
//            int sourceClick = adapterResource.getPostionSelect();
//            if (sourceClick >= 0) {
//                ItemPinTu targe = presenter.targeDataList.get(rst);
//                if (targe.bm == null) {
//                    presenter.setSourceToTager(sourceClick, rst);
//                    adapterResource.setPostionEmpty();
//                    adapterFraPingTu.notifyDataSetChanged();
//                    adapterResource.notifyDataSetChanged();
//                    checkSucc();
//                }
//            }
//        }
//    };

    private void checkSucc() {
        boolean isTrue = true;
        for (int i = 0; i < (presenter.targeDataList.size() - presenter.row); i++) {
            if (i != presenter.targeDataList.get(i).postion) {
                isTrue = false;
                break;
            }
        }
        if (isTrue) {
            showToast("完成啦");
            toolSoundPool.playRight();
        }
    }

    public void draTag() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallBack());
        itemTouchHelper.attachToRecyclerView(targeImage);
    }

    public class MyItemTouchHelperCallBack extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = 0;
            int swipeFlags = 0;
            if (targeImage.getLayoutManager() instanceof GridLayoutManager) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.DOWN;
                swipeFlags = 0;
            } else {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlags = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
            }

            return makeMovementFlags(dragFlags, swipeFlags);
        }

        /**
         * 拖拽时回调的方法
         *
         * @param recyclerView
         * @param viewHolder
         * @param target
         * @return
         */
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            start = viewHolder.getAdapterPosition();
            endPos = target.getAdapterPosition();
//           Log.i("znh_move","#############"+start+"---->"+endPos);

            return true;
        }

        /**
         * 滑动时回调的方法
         *
         * @param viewHolder
         * @param direction  ItemTouchHelper.ACTION_STATE_IDLE 空闲状态
         */
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Log.i("znh_Swiped", "#############" + direction);
        }

        @Override
        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                ViewPropertyAnimatorCompat anivaite = ViewCompat.animate(viewHolder.itemView);
                anivaite.setDuration(100);
                anivaite.scaleX(1.2f).scaleY(1.2f).start();
//                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);

            ViewPropertyAnimatorCompat anivaite = ViewCompat.animate(viewHolder.itemView);
            anivaite.setDuration(100);
            anivaite.scaleX(1f).scaleY(1f).start();
//            移动结束了。需要变更下内容；  start,endPos;
            if (endPos == presenter.targeDataList.size() - 1) {
//                拖动到删除框了
                if (start < (presenter.targeDataList.size() - presenter.row)) {
                    ItemPinTu item = presenter.targeDataList.get(start);

//                    adapterFraPingTu.notifyItemMoved(start, endPos);
                    if (presenter.targeDataList.get((presenter.targeDataList.size() - presenter.row)).bm == null) {
                        presenter.targeDataList.set((presenter.targeDataList.size() - presenter.row), item);
                    } else {
                        presenter.sourceDataList.add(item);
                    }
                    ItemPinTu empty = new ItemPinTu();
                    presenter.targeDataList.set(start, empty);
                    adapterFraPingTu.notifyDataSetChanged();
                } else {
//                 删除对于的内容
                    return;
                }
            } else {
                if (start == (presenter.targeDataList.size() - presenter.row)) {
                    ItemPinTu itemstart = presenter.targeDataList.get(start);
                    if(itemstart.isEmpty){
                        return;
                    }
                    ItemPinTu itemEnd = presenter.targeDataList.get(endPos);
                    if (itemEnd.bm == null) {
                        ItemPinTu item = presenter.targeDataList.get(start);
                        presenter.targeDataList.set(endPos, item);
                        if (presenter.sourceDataList.size() > 0) {
                            presenter.targeDataList.set(start, presenter.sourceDataList.get(0));
                            presenter.sourceDataList.remove(0);
                        } else {
                            presenter.targeDataList.get(start).bm = null;
                        }

//                        adapterFraPingTu.notifyItemMoved(start, endPos);
                        adapterFraPingTu.notifyDataSetChanged();
                    } else {
//                        目标非空也不管
                        return;
                    }
                } else {
//                    非源头的移动也不管
                    return;
                }
            }

            super.clearView(recyclerView, viewHolder);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

    }

    int start, endPos;
}
