package com.appforysy.activity.activity_snow;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.toolmvplibrary.activity_root.ActivityRoot;
import com.toolmvplibrary.view.snowview.SnowUtils;
import com.toolmvplibrary.view.snowview.SnowView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivitySnow extends ActivityRoot {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snowview);
        snowView = findViewById(R.id.snowView);
        snowView.startSnowAnim(SnowUtils.SNOW_LEVEL_MIDDLE);
        recyclerView = findViewById(R.id.recyclerView);

        imgList.add(R.mipmap.image_course_1);
        imgList.add(R.mipmap.image_course_2);
        imgList.add(R.mipmap.image_course_3);
        imgList.add(R.mipmap.image_course_4);
        imgList.add(R.mipmap.img_guide_1);
        imgList.add(R.mipmap.img_guide_2);
        imgList.add(R.mipmap.img_guide_3);
        imgList.add(R.mipmap.img_guide_4);
        imgList.add(com.toolmvplibrary.R.mipmap.icon_red_save);
        imgList.add(com.toolmvplibrary.R.mipmap.icon_red_delete);
        imgList.add(com.toolmvplibrary.R.mipmap.icon_red_phone);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        adapterGridView = new AdapterGridView(imgList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterGridView);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchHolder());
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private AdapterGridView adapterGridView;
    private List<Integer> imgList = new ArrayList<>();
    private SnowView snowView;
    RecyclerView recyclerView;

    private class MyItemTouchHolder extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            final int dragFlags = ItemTouchHelper.UP
                    | ItemTouchHelper.DOWN
                    | ItemTouchHelper.LEFT
                    | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(imgList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(imgList, i, i - 1);
                }
            }
//            end = target.getAdapterPosition();
            adapterGridView.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setScaleX(1.2f);
                viewHolder.itemView.setScaleY(1.2f);
                viewHolder.itemView.setBackgroundColor(Color.RED);
                start = viewHolder.getAdapterPosition();
            }

        }

        private int start = 0, end = 0;

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);

            if (!recyclerView.isComputingLayout()) {
                //当需要清除之前在onSelectedChanged或者
                // onChildDraw,onChildDrawOver设置的状态或者动画时通过接口返回该ViewHolder
                viewHolder.itemView.setScaleX(1.0f);
                viewHolder.itemView.setScaleY(1.0f);
                viewHolder.itemView.setBackgroundColor(Color.BLUE);
            }

//            int startImg = imgList.get(end);
//            imgList.set(end, imgList.get(start));
//            imgList.set(start, startImg);
//            adapterGridView.notifyDataSetChanged();
//            adapterGridView.notifyItemRangeChanged(end,start);
//            adapterGridView.notifyItemRangeInserted(start, end);
//            adapterGridView.notifyItemChanged(start, end);
//            adapterGridView.notifyItemMoved(start, end);
        }

        //        @Override
//        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            //viewHolder.itemView.setBackground(getDrawable(R.drawable.card));
////            Collections.swap(imgList, start, end);

//        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
    }

}
