package com.appforysy.activity.draw_image_view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.rootlibs.loadimg.ToolGlide;
import com.toolmvplibrary.AppComIntener;

import java.util.List;

public class AdapterDrawImg extends RecyclerView.Adapter {
    List<Integer> mItemLists;

    public AdapterDrawImg(List<Integer> myDataset) {
        mItemLists = myDataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imageview_info, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderr, @SuppressLint("RecyclerView") int position) {
        MyViewHolder holder = (MyViewHolder) holderr;
        if (itemClick == position) {
            holder.checkImage.setChecked(true);
        } else {
            holder.checkImage.setChecked(false);
        }
        ToolGlide.loadImge(holderr.itemView.getContext(), mItemLists.get(position), holder.imageView);
        ((MyViewHolder) holderr).imageView.setAlpha(alphImage);
        holder.checkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkImage.isChecked()) {

                    setSelectItem(position);
                }
            }
        });
    }

    private int itemClick = 0;

    public int getSelectItem() {
        return itemClick;
    }

    public void setSelectItem(int postion) {
        itemClick = postion;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItemLists.size();
    }

    private float alphImage = 1;

    public void setImageAlph(float v) {
        alphImage = v;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        private CheckBox checkImage;

        public MyViewHolder(View v) {
            super(v);
            imageView = itemView.findViewById(R.id.imageView);
            checkImage = itemView.findViewById(R.id.checkImage);
        }
    }
}