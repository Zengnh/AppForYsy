package com.appforysy.activity.draw_line;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.appforysy.R;

import java.util.List;

public class AdapterEditGrid extends BaseAdapter {
    private List<ItemEdit> dataList;
    public AdapterEditGrid(List<ItemEdit> dataList){
        this.dataList=dataList;
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
        ImageView itemImage;
       if(convertView==null){
           convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_grid,parent,false);
           itemImage=convertView.findViewById(R.id.itemImage);
           convertView.setTag(itemImage);
       }else{
           itemImage= (ImageView) convertView.getTag();
       }
        itemImage.setImageResource(dataList.get(position).icon);
        return convertView;
    }
}
