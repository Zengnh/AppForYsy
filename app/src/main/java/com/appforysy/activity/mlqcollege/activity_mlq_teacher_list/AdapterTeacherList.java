package com.appforysy.activity.mlqcollege.activity_mlq_teacher_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.mlqcollege.activity_mlq.ItemIntro;
import com.appforysy.activity.mlqcollege.activity_teacher_detail.ActivityMlqTeacherDetail;

import java.util.List;

public class AdapterTeacherList extends RecyclerView.Adapter {
    public List<ItemIntro> dataList;

    public AdapterTeacherList(List<ItemIntro> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mlydetail_teacher, parent, false);
        return new HolderTeacher(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemIntro item = dataList.get(position);

            HolderTeacher hd = (HolderTeacher) holder;
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



    class HolderTeacher extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemContent;

        public HolderTeacher(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemContent = itemView.findViewById(R.id.itemContent);


        }

        public void setData(ItemIntro data) {
            itemImage.setImageResource(data.imgResource);
            itemTitle.setText(data.title + "");
            itemContent.setText(data.content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityMlqTeacherDetail.startAct(itemContent.getContext(),data.id);
                }
            });

        }
    }


}
