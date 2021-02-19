package com.appforysy.activity.activity_time_note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterTimeNote extends RecyclerView.Adapter<AdapterTimeNote.HolderNote> {
    private List<ItemTimeNote> dataList;

    public AdapterTimeNote(List<ItemTimeNote> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public HolderNote onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_note, parent, false);
        return new HolderNote(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNote holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        holder.timeNote.setText(simpleDateFormat.format(new Date(dataList.get(position).sysTime)));
        holder.adapterContentNote.setData(dataList.get(position).content);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class HolderNote extends RecyclerView.ViewHolder {
        private TextView timeNote;
        private ListView listData;
        public AdapterContentNote adapterContentNote;

        public HolderNote(@NonNull View itemView) {
            super(itemView);
            timeNote = itemView.findViewById(R.id.timeNote);
            listData = itemView.findViewById(R.id.listData);
            adapterContentNote = new AdapterContentNote();
            listData.setAdapter(adapterContentNote);
        }

    }

}
