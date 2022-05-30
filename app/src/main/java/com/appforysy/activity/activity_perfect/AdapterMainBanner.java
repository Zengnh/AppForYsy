package com.appforysy.activity.activity_perfect;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.rootlibs.downloader.InterListener;
import com.rootlibs.downloader.ResultDownLoader;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterMainBanner extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemPagerContent> listData;
    private InterListener listener;
    public AdapterMainBanner(List<ItemPagerContent> listData, InterListener listener) {
        this.listData = listData;
        this.listener=listener;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_main_banner, parent, false);
            return new HolderView(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_main_banner_btn, parent, false);
            return new HolderViewExe(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position)==1) {
            HolderViewExe holderContent= (HolderViewExe) holder;
            holderContent.btnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.resultFinish(new ResultDownLoader());
                }
            });
        } else {
            ItemPagerContent item = listData.get(position);
            HolderView holderContent= (HolderView) holder;
            holderContent.iconImage.setImageResource(item.icon);
        }

    }



    @Override
    public int getItemCount() {
        return listData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == listData.size()) {
            return 1;
        } else {
            return 0;
        }
    }

    public class HolderView extends RecyclerView.ViewHolder {
        private ImageView iconImage;

        public HolderView(@NonNull @NotNull View itemView) {
            super(itemView);

            iconImage = itemView.findViewById(R.id.iconImage);
        }
    }

    public class HolderViewExe extends RecyclerView.ViewHolder {
        private CheckBox btnOpen;

        public HolderViewExe(@NonNull @NotNull View itemView) {
            super(itemView);

            btnOpen = itemView.findViewById(R.id.btnOpen);
        }
    }
}
