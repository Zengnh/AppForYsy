package com.appforysy.activity.activity_perfect;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.HolderView> {
    private List<ItemPagerContent> listData;
    public AdapterRecycler(List<ItemPagerContent> listData) {
        this.listData = listData;
    }

    @NonNull
    @NotNull
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_perfect, parent, false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        ItemPagerContent item=listData.get(position);
        holder.textcontent.setText(item.itemName);
        holder.iconImage.setImageResource(item.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.nextClass!=null){
                    Intent intent=new Intent(holder.itemView.getContext(),item.nextClass);
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        private TextView textcontent;
        private ImageView iconImage;

        public HolderView(@NonNull @NotNull View itemView) {
            super(itemView);
            textcontent = itemView.findViewById(R.id.textcontent);
            iconImage = itemView.findViewById(R.id.iconImage);
        }
    }

}
