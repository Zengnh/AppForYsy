package com.appforysy.activity.activity_main.book;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.appforysy.R;
import com.appforysy.utils.TextView4Size;
import com.libraryassets.ActivityShowBook;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class AdapterIntructRecycler extends RecyclerView.Adapter<AdapterIntructRecycler.HolderView> {
    private List<ItemBookTxt> listData;

    public AdapterIntructRecycler(List<ItemBookTxt> listData) {
       this.listData=listData;
    }

    @NonNull
    @NotNull
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_intruct, parent, false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(HolderView holder, @SuppressLint("RecyclerView") int position) {
        holder.textTitle.setData(listData.get(position).bookName);
        holder.textcontent.setText(listData.get(position).bookRemark);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(listData.get(position).bookPath)){
                    Intent intent=new Intent(holder.itemView.getContext(), ActivityShowBook.class);
                    intent.putExtra("path",listData.get(position).bookPath);
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
        TextView textcontent;
        TextView4Size textTitle;

        public HolderView(@NonNull @NotNull View itemView) {
            super(itemView);
            textcontent = itemView.findViewById(R.id.textcontent);
            textTitle = itemView.findViewById(R.id.textTitle);
        }
    }

}
