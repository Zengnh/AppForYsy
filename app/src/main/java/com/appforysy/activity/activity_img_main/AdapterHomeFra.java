package com.appforysy.activity.activity_img_main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.rootlibs.loadimg.ToolGlide;
import com.toolmvplibrary.activity_root.ItemClick;

import java.util.ArrayList;
import java.util.List;

public class AdapterHomeFra extends RecyclerView.Adapter {
    private List<ItemMain> dataList;
    private ItemClick<Integer> listener;
    public AdapterHomeFra(ArrayList<ItemMain> dataList, ItemClick<Integer> itemClick) {
        this.dataList = dataList;
        this.listener=itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_fra, parent, false);
        return new HolderMain(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HolderMain hd = (HolderMain) holder;
        hd.setData(holder.itemView.getContext(), dataList.get(position).icon);
        hd.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.itemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class HolderMain extends RecyclerView.ViewHolder {
        ImageView imageView;

        public HolderMain(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void setData(Context context, int path) {
            ToolGlide.loadImge(context, path, imageView);
        }
    }

}
