package com.mlq.course.activity_mlq_imgintro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mlq.course.R;
import com.mlq.course.activity_mlq.ItemIntro;
import java.util.List;

public class AdapterMLQImg extends RecyclerView.Adapter {
    public List<ItemIntro> dataList;

    public AdapterMLQImg(List<ItemIntro> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mlq_intro_img, parent, false);
        return new HolderInfoImg(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemIntro item = dataList.get(position);
        HolderInfoImg hd = (HolderInfoImg) holder;
        hd.setData(item);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class HolderInfoImg extends RecyclerView.ViewHolder {
        public ImageView imageViewIntroduct;
        public TextView introText;

        public HolderInfoImg(@NonNull View itemView) {
            super(itemView);
            imageViewIntroduct = itemView.findViewById(R.id.imageViewIntroduct);
            introText = itemView.findViewById(R.id.introText);
        }

        public void setData(ItemIntro data) {
            imageViewIntroduct.setImageResource(data.imgResource);
            introText.setText(data.title);
        }
    }
}