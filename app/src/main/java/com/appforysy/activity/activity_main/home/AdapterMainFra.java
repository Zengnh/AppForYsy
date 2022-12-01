package com.appforysy.activity.activity_main.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.utils.ItemInfo;
import com.rootlibs.loadimg.ToolGlide;

import java.util.List;

public class AdapterMainFra extends RecyclerView.Adapter<AdapterMainFra.MainHolder> {
    private List<ItemInfo> itemData;

    public AdapterMainFra(List<ItemInfo> itemData) {
        this.itemData = itemData;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_fra_adapter, parent, false);
        return new MainHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        ItemInfo info = itemData.get(position);
        holder.itemContent.setText(info.text);
        ToolGlide.loadImageRind(holder.itemView.getContext(), info.img, holder.itemImage, R.mipmap.ic_launcher, 5);
        holder.itemRemark.setText(info.remark);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.nextClass != null) {
                    Intent itnent = new Intent(holder.itemContent.getContext(), info.nextClass);
                    holder.itemContent.getContext().startActivity(itnent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemContent;
        TextView itemRemark;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemContent = itemView.findViewById(R.id.itemContent);
            itemRemark = itemView.findViewById(R.id.itemRemark);
        }
    }
}
