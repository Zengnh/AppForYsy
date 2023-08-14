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

public class AdapterSelectImageResource extends RecyclerView.Adapter<AdapterSelectImageResource.MainHolder> {
    private List<ItemPinTu> itemData;
    private ItemClick<Integer> listener;

    public AdapterSelectImageResource(List<ItemPinTu> itemData, ItemClick<Integer> listener) {
        this.itemData = itemData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pingtu_image_resource, parent, false);
        return new MainHolder(v);
    }

    private int postionSelect = -1;

    public int getPostionSelect() {
        return postionSelect;
    }

    public void setPostionEmpty() {
        this.postionSelect = -1;
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemPinTu info = itemData.get(position);
        holder.imageView.setImageBitmap(info.bm);
        if (postionSelect == position) {
            holder.imageView.setPadding(3, 3, 3, 3);
        } else {
            holder.imageView.setPadding(0, 0, 0, 0);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.itemClick(position);
                }
                postionSelect = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
