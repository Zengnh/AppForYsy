package com.appforysy.activity.activity_main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appforysy.R;

import java.util.List;

public class AdapterBtnItem extends BaseAdapter {
    private List<ItemMainBtn> dataList;
    public AdapterBtnItem(List<ItemMainBtn> dataList){
        this.dataList=dataList;
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
        Holder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_btn,null);
            holder=new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }

        holder.itemText.setText(dataList.get(position).text);
        holder.itemImage.setImageResource(dataList.get(position).img);
        return convertView;
    }
    class Holder{
        private TextView itemText;
        public ImageView itemImage;
        public Holder(View view){
            itemText=view.findViewById(R.id.itemText);
            itemImage=view.findViewById(R.id.itemImage);
        }
    }
}
