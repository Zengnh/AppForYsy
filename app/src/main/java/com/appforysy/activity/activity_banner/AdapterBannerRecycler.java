package com.appforysy.activity.activity_banner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.toolmvplibrary.tool_app.ToolShotScreen;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterBannerRecycler extends RecyclerView.Adapter<AdapterBannerRecycler.HolderView> {
    private List<Integer> listData;

    public AdapterBannerRecycler() {
        listData = new ArrayList<>();
        listData.add(R.mipmap.image_rolate);
        listData.add(R.mipmap.image_all_image);
        listData.add(R.mipmap.img_guide_1);
        listData.add(R.mipmap.img_guide_2);
        listData.add(R.mipmap.img_guide_3);
        listData.add(R.mipmap.image_time_info);
        listData.add(R.mipmap.image_rolate);
    }

    @NonNull
    @NotNull
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_banner, parent, false);
        myCreateViewHolder(parent, view, 60);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        myBindViewHolder(holder.itemView, position, getItemCount(), 60);
        holder.textcontent.setImageResource(listData.get(position));

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        private ImageView textcontent;

        public HolderView(@NonNull @NotNull View itemView) {
            super(itemView);
            textcontent = itemView.findViewById(R.id.textcontent);
        }
    }

    public int mDefaultMargin = 150;

    public void myCreateViewHolder(ViewGroup parent, View itemView, int margin) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (margin <= 0) {
            margin = mDefaultMargin;
        }
        lp.width = parent.getWidth() - 2 * margin;
        itemView.setLayoutParams(lp);
    }

    //这里是处理最左边和最右边的宽度
    public void myBindViewHolder(View itemView, final int position, int itemCount, int margin) {
        int leftMarin = 0;
        int rightMarin = 0;
        int topMarin = 0;
        int bottomMarin = 0;
        if (position == 0) {
            leftMarin = margin;
            rightMarin = 0;
        } else if (position == (itemCount - 1)) {
            leftMarin = 0;
            rightMarin = margin;
        }
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
        if (lp.leftMargin != leftMarin || lp.topMargin != topMarin || lp.rightMargin != rightMarin || lp.bottomMargin != bottomMarin) {
            lp.setMargins(leftMarin, topMarin, rightMarin, bottomMarin);
            itemView.setLayoutParams(lp);
        }
    }


    // #######################################################
    RecyclerView mRecycler;
    public int mMargin;
    public int mCurrentPosition;

    public void attachToRecyclerView(RecyclerView recyclerView, int margin) {
        if (recyclerView == null) {
            return;
        }
        mRecycler = recyclerView;
        if (margin <= 0) {
            mMargin = mDefaultMargin;
        } else {
            mMargin = margin;
        }
        initView();
        final CustomLinearSnapHelper helper = new CustomLinearSnapHelper();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mDistances == 0 || mDistances == (mItemCount * mItemwidth)) {
                        helper.mStateIdle = true;
                    } else {
                        helper.mStateIdle = false;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getLayoutManager().getLayoutDirection() == LinearLayoutManager.HORIZONTAL) {
                    mDistances += dx;
                    getCurrentPosition();
                    setItemScale();
                }

            }
        });

        helper.attachToRecyclerView(recyclerView);
    }

    //这里获取item的宽与countitem数
    protected void initView() {
        mRecycler.post(new Runnable() {
            @Override
            public void run() {
                mItemCount = mRecycler.getAdapter().getItemCount();
                mItemwidth = mRecycler.getWidth() - 2 * mMargin;
                mRecycler.smoothScrollToPosition(mCurrentPosition);
                setItemScale();
            }
        });
    }

    //item在滑动时，进行动画的缩放
    public void setItemScale() {
        View leftView = null;
        View rightView = null;
        if (mCurrentPosition > 0) {
            leftView = mRecycler.getLayoutManager().findViewByPosition(mCurrentPosition - 1);
        }
        View currentView = mRecycler.getLayoutManager().findViewByPosition(mCurrentPosition);
        if (mCurrentPosition < (mItemCount - 1)) {
            rightView = mRecycler.getLayoutManager().findViewByPosition(mCurrentPosition + 1);
        }
        if (mItemwidth == 0) {
//            分母不能为0;
            return;
        }
        //滑动百分比，左右的都是放大，中间缩小
        float percent = Math.abs((mDistances - mCurrentPosition * mItemwidth * 1.0f) / mItemwidth);

        if (leftView != null) {
            //这里是缩小原来大小的0.8-1.0 左右0.8，中间1.0   0.8+(percent*0.2)
            leftView.setScaleY(0.8f + (percent * 0.2f));
        }
        if (currentView != null) {
            currentView.setScaleY(1f - 0.2f * percent);
        }
        if (rightView != null) {
            rightView.setScaleY(0.8f + (percent * 0.2f));
        }
    }

    protected void getCurrentPosition() {
        if (mItemwidth <= 0) return;
        boolean change = false;

        if (Math.abs(mDistances - mCurrentPosition * mItemwidth) >= mItemwidth) {
            change = true;
        }
        if (change) { //这里是从0开始的
            mCurrentPosition = mDistances / mItemwidth;
        }

    }

    public int mDistances, mItemwidth, mItemCount;

    public void setItemPosition(int position) {
        this.mCurrentPosition = position;
        this.mDistances = position * mItemwidth;
    }

}
