package com.appforysy.activity.activity_time_note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appforysy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdapterContentNote extends BaseAdapter {
    private List<String> dataList = new ArrayList<>();
    private Random run = new Random(15);

    public void setData(List<String> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView timeNoteContent;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_note_content, parent, false);
            timeNoteContent = convertView.findViewById(R.id.timeNoteContent);
            convertView.setTag(timeNoteContent);
        } else {
            timeNoteContent = (TextView) convertView.getTag();
        }
        timeNoteContent.setText(dataList.get(position) + "");

        timeNoteContent.setBackgroundColor(
                parent.getResources().getColor(ToolColor.getInstance().getColor(run.nextInt(15))));
        return convertView;
    }
}
