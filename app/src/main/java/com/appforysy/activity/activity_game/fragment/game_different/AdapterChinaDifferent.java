package com.appforysy.activity.activity_game.fragment.game_different;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.activity_game.fragment.game_pingtu.ItemPinTu;
import com.toolmvplibrary.activity_root.ItemClick;

import java.util.List;

public class AdapterChinaDifferent extends RecyclerView.Adapter<AdapterChinaDifferent.MainHolder> {
    private List<ItemPinTu> itemData;


    public AdapterChinaDifferent(List<ItemPinTu> itemData) {
        this.itemData = itemData;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pingtu_china_tager, parent, false);
        return new MainHolder(v);
    }
    private float oneWidth;
    public void setOneWidth(float oneWidth) {
        this.oneWidth = oneWidth;
    }
    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, @SuppressLint("RecyclerView") int position) {

        ItemPinTu info = itemData.get(position);
//        holder.textContent.setText(info.content);
        ViewGroup.LayoutParams parm = holder.itemView.getLayoutParams();
        parm.height = (int) oneWidth;
        if(info.isSelect){
            holder.textContent.setBackgroundResource(R.drawable.fill_shape_organ_3);
        }else{
            holder.textContent.setBackgroundResource(R.drawable.fill_shape_line_3);
        }

        if(info.isEmpty){
            holder.itemView.setBackgroundResource(R.mipmap.img_def_true);
        }else{
            holder.itemView.setBackgroundResource(R.mipmap.img_def_false);
        }

        holder.textContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.itemClick(position);
                }
            }
        });
    }
    private ItemClick<Integer> listener;
    public void addListenter(ItemClick<Integer> listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }
    public class MainHolder extends RecyclerView.ViewHolder {
        TextView textContent;
        public MainHolder(@NonNull View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.textContent);
        }
    }
}
