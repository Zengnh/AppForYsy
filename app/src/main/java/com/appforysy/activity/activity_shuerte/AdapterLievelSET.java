package com.appforysy.activity.activity_shuerte;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.utils.ItemInfo;
import com.toolmvplibrary.activity_root.InterCallBack;

import java.util.List;

public class AdapterLievelSET extends RecyclerView.Adapter {
    List<ItemInfo> dataList;
    InterCallBack<Integer> listener;

    public AdapterLievelSET(List<ItemInfo> dataList, InterCallBack<Integer> listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_level_set_game, parent, false);
        return new HolderLevel(view);
    }

    public int postionSel = 1;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HolderLevel levelH = (HolderLevel) holder;
        ItemInfo intemInfo = dataList.get(position);
        levelH.itemLevel.setText(intemInfo.text);
        if (postionSel == position) {
            levelH.itemLevel.setTextColor(holder.itemView.getResources().getColor(R.color.myl_red, null));
        } else {
            levelH.itemLevel.setTextColor(holder.itemView.getResources().getColor(R.color.color_1e, null));
        }
        levelH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                postionSel = position;
                if (listener != null) {
                    listener.result(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class HolderLevel extends RecyclerView.ViewHolder {
        TextView itemLevel;

        public HolderLevel(@NonNull View itemView) {
            super(itemView);
            itemLevel = itemView.findViewById(R.id.itemLevel);
        }
    }
}
