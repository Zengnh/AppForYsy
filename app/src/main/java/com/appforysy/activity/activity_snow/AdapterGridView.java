package com.appforysy.activity.activity_snow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;

import java.util.List;

public class AdapterGridView extends RecyclerView.Adapter {
    private List<Integer> imageList;
    public AdapterGridView( List<Integer> imageList){
        this.imageList=imageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_change,parent,false);
        return new HolderChange(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HolderChange hd= (HolderChange) holder;
        hd.imageView.setImageResource(imageList.get(position).intValue());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class HolderChange extends RecyclerView.ViewHolder{
        ImageView imageView;
        public HolderChange(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }

}
