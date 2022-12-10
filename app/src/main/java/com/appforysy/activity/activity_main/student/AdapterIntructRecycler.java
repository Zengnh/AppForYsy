package com.appforysy.activity.activity_main.student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterIntructRecycler extends RecyclerView.Adapter<AdapterIntructRecycler.HolderView> {
    private List<String> listData;

    public AdapterIntructRecycler() {
        listData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listData.add("说明文档：" + i);
        }
    }

    @NonNull
    @NotNull
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_intruct, parent, false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        holder.textcontent.setText(listData.get(position));
        if (position % 2 == 0) {
            holder.view.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.alph30_blue));
        } else {
            holder.view.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.alph30_while));
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        private TextView textcontent;
        private View view;
        public HolderView(@NonNull @NotNull View itemView) {
            super(itemView);
            view=itemView.findViewById(R.id.contentRoot);
            textcontent = itemView.findViewById(R.id.textcontent);
        }
    }

}
