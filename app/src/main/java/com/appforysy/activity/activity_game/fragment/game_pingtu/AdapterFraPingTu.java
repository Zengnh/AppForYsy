package com.appforysy.activity.activity_game.fragment.game_pingtu;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.activity_game.ActivityGame;
import com.rootlibs.loadimg.ToolGlide;
import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.activity_root.ItemClick;
import com.toolmvplibrary.activity_root.ItemInfo;

import java.util.List;

public class AdapterFraPingTu extends RecyclerView.Adapter<AdapterFraPingTu.MainHolder> {
    private List<ItemPinTu> itemData;
    private ItemClick<Integer> listener;

    public AdapterFraPingTu(List<ItemPinTu> itemData, ItemClick<Integer> listener) {
        this.itemData = itemData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pingtu_fra_adapter, parent, false);
        return new MainHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        ItemPinTu info = itemData.get(position);
        ViewGroup.LayoutParams parm = holder.itemView.getLayoutParams();
        parm.height = (int) oneWidth;

        holder.imageView.setImageBitmap(info.bm);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.itemClick(position);
                }
            }
        });
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
