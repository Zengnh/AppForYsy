package com.mykotlin.activity.activity_music;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mykotlin.R;

import java.util.List;

public class AdapterRecyclerMusic extends RecyclerView.Adapter<AdapterRecyclerMusic.HolderMusic> {
    private List<ItemMusic> dataList;
    private ItemClick listener;

    public AdapterRecyclerMusic(List<ItemMusic> dataList, ItemClick listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    public int itemHight = 0;

    public void setItemHight(int hight) {
        itemHight = hight;
    }

    @NonNull
    @Override
    public HolderMusic onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kotlin_music_value, parent, false);
        return new HolderMusic(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMusic holder, @SuppressLint("RecyclerView") int position) {
        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.drawable.music_itemselect_2);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.music_itemselect_1);
        }
        if (itemHight > 0) {
            holder.itemView.getLayoutParams().height = itemHight;
        }

        if (TextUtils.isEmpty(dataList.get(position).itemRemark)) {
            holder.item_music_remark.setVisibility(View.GONE);
        } else {
            holder.item_music_remark.setVisibility(View.VISIBLE);
            holder.item_music_remark.setText(dataList.get(position).itemRemark + "");
        }
        holder.textView.setText(dataList.get(position).itemName + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.itemClick(dataList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    protected class HolderMusic extends RecyclerView.ViewHolder {
        TextView textView;
        TextView item_music_remark;

        public HolderMusic(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_music_info);
            item_music_remark = itemView.findViewById(R.id.item_music_remark);
        }
    }

}
