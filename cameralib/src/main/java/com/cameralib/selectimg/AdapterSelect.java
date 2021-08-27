package com.cameralib.selectimg;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cameralib.R;

import java.util.ArrayList;

public class AdapterSelect extends RecyclerView.Adapter {
    private ArrayList<ImageBean> dataList;
    private InterItemClick interItemClick;

    public AdapterSelect(ArrayList<ImageBean> dataList, InterItemClick interItemClick) {
        this.dataList = dataList;
        this.interItemClick = interItemClick;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_image_ed, parent, false);
        return new HolderSelect(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        HolderSelect holderSelect = (HolderSelect) holder;
//        Glide.with(holder.itemView.getContext()).load( dataList.get(position).getPath()).into();
        holderSelect.imageImgSelect.setImageBitmap(BitmapFactory.decodeFile(dataList.get(position).getPath()));
        holderSelect.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interItemClick != null) {
                    interItemClick.clickPos(position, 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class HolderSelect extends RecyclerView.ViewHolder {
        ImageView imageImgSelect;

        public HolderSelect(View itemView) {
            super(itemView);
            imageImgSelect = itemView.findViewById(R.id.imageImgSelect);
        }
    }
}
