package com.appforysy.activity.draw_image_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appforysy.R;

import java.util.List;

public class AdapterEditGrid extends BaseAdapter {
    private List<ItemEdit> dataList;

    public AdapterEditGrid(List<ItemEdit> dataList) {
        this.dataList = dataList;
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView itemContnt;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_grid, parent, false);
            itemContnt = convertView.findViewById(R.id.itemContnt);
            convertView.setTag(itemContnt);
        } else {
            itemContnt = (TextView) convertView.getTag();
        }
        String content=dataList.get(position).itemName;
        if(content.length()>2){
            content=content.substring(0,2)+"\n"+content.substring(2,content.length());
        }

        itemContnt.setText(content);
        return convertView;
    }
}
