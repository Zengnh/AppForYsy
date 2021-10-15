package com.appforysy.activity.mlqcollege.activity_mlq_course_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.mlqcollege.activity_course_detail.ActivityMlqCourseDetail;
import com.appforysy.activity.mlqcollege.activity_mlq.ItemIntro;
import com.rootlibs.loadimg.ToolGlide;

import java.util.List;

public class AdapterCourseList extends RecyclerView.Adapter {
    public List<ItemIntro> dataList;

    public AdapterCourseList(List<ItemIntro> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mly_course, parent, false);
        return new HolderCourse(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemIntro item = dataList.get(position);
        HolderCourse hd = (HolderCourse) holder;
        hd.setData(item);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class HolderCourse extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemContent;
        Button toDetail;
        public HolderCourse(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemContent = itemView.findViewById(R.id.itemContent);
            toDetail = itemView.findViewById(R.id.toDetail);
        }

        public void setData(ItemIntro data) {
            ToolGlide.loadImageRindLeft(itemView.getContext(), data.imgResource, itemImage, 0, 6);
            itemTitle.setText(data.title + "");
            itemContent.setText(data.content);
            toDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityMlqCourseDetail.startAct(itemContent.getContext(),data.id);
                }
            });
        }
    }
}
