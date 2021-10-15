package com.appforysy.activity.mlqcollege.activity_mlq;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.mlqcollege.activity_course_detail.ActivityMlqCourseDetail;
import com.appforysy.activity.mlqcollege.activity_mlq_course_list.ActivityMLQCourseList;
import com.appforysy.activity.mlqcollege.activity_mlq_imgintro.ActivityMLQImagerList;
import com.appforysy.activity.mlqcollege.activity_mlq_teacher_list.ActivityMLQTeacherList;
import com.appforysy.activity.mlqcollege.activity_teacher_detail.ActivityMlqTeacherDetail;
import com.rootlibs.loadimg.ToolGlide;

import java.util.List;

public class AdapterCourse extends RecyclerView.Adapter {
    public List<ItemIntro> dataList;

    public AdapterCourse(List<ItemIntro> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MYLTYPE.COURSE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mly_course, parent, false);
            return new HolderCourse(view);
        } else if (viewType == MYLTYPE.INFOIMG) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mlq_intro, parent, false);
            return new HolderInfoImg(view);
        } else if (viewType == MYLTYPE.TEACHER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mly_teacher, parent, false);
            return new HolderTeacher(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mly_title, parent, false);
            return new HolderTitle(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemIntro item = dataList.get(position);
        if (item.type == MYLTYPE.COURSE) {
            HolderCourse hd = (HolderCourse) holder;
            hd.setData(item);
        } else if (item.type == MYLTYPE.INFOIMG) {
            HolderInfoImg hd = (HolderInfoImg) holder;
            hd.setData(item);
        } else if (item.type == MYLTYPE.TEACHER) {
            HolderTeacher hd = (HolderTeacher) holder;
            hd.setData(item);
        } else if (item.type == MYLTYPE.TITLE) {
            HolderTitle hd = (HolderTitle) holder;
            hd.setData(item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
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
//            itemImage.setImageResource(data.imgResource);
            ToolGlide.loadImageRindLeft(itemView.getContext(), data.imgResource, itemImage, 0, 10);
            itemTitle.setText(data.title + "");
            itemContent.setText(data.content);
            toDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityMlqCourseDetail.startAct(itemContent.getContext(), data.id);
                }
            });
        }
    }

    class HolderTeacher extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemContent;


        ImageView itemImage2;
        TextView itemTitle2;
        TextView itemContent2;
        LinearLayout teacerhView1;
        LinearLayout teacerhView2;
        public HolderTeacher(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemContent = itemView.findViewById(R.id.itemContent);

            itemImage2 = itemView.findViewById(R.id.itemImage2);
            itemTitle2 = itemView.findViewById(R.id.itemTitle2);
            itemContent2 = itemView.findViewById(R.id.itemContent2);
            teacerhView1 = itemView.findViewById(R.id.teacerhView1);
            teacerhView2 = itemView.findViewById(R.id.teacerhView2);
        }

        public void setData(ItemIntro data) {
            itemImage.setImageResource(data.imgResource);
            itemTitle.setText(data.title + "");
            itemContent.setText(data.content);

            itemImage2.setImageResource(data.imgResource2);
            itemTitle2.setText(data.title2 + "");
            itemContent2.setText(data.content2);

            teacerhView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityMlqTeacherDetail.startAct(itemContent.getContext(), data.id);
                }
            });
            teacerhView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityMlqTeacherDetail.startAct(itemContent.getContext(), data.id2);
                }
            });
        }
    }

    class HolderTitle extends RecyclerView.ViewHolder {
        TextView title;
        TextView moreInfo;

        public HolderTitle(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            moreInfo = itemView.findViewById(R.id.moreInfo);
        }

        public void setData(ItemIntro data) {
            title.setText(data.title);
            moreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (data.titleType == MYLTYPE.COURSE) {
                        ActivityMLQCourseList.startAct(itemView.getContext());
                    } else if (data.titleType == MYLTYPE.TEACHER) {
                        ActivityMLQTeacherList.startAct(itemView.getContext());
                    }
                }
            });
        }
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityMLQImagerList.startAct(itemView.getContext());
                }
            });
        }
    }
}
