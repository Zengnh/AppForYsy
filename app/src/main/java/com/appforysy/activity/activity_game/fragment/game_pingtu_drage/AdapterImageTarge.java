package com.appforysy.activity.activity_game.fragment.game_pingtu_drage;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.activity_game.fragment.game_pingtu.ItemPinTu;
import com.toolmvplibrary.activity_root.ItemClick;

import java.util.List;

public class AdapterImageTarge extends RecyclerView.Adapter<AdapterImageTarge.MainHolder> {
    private List<ItemPinTu> itemData;
    private int row = 0;

    public void setRow(int row) {
        this.row = row;
    }

    public AdapterImageTarge(List<ItemPinTu> itemData) {
        this.itemData = itemData;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pingtu_image_tager, parent, false);
        return new MainHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemPinTu info = itemData.get(position);
        ViewGroup.LayoutParams parm = holder.itemView.getLayoutParams();
        parm.height = (int) oneWidth;

        if (position == itemData.size() - 1) {
            holder.imageView.setImageResource(R.mipmap.icon_team_delete_history);
        } else {
            holder.imageView.setImageBitmap(info.bm);
        }
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    private float oneWidth;

    public void setOneWidth(float oneWidth) {
        this.oneWidth = oneWidth;
    }

    public class MainHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
