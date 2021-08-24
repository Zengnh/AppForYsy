package com.appforysy.activity.activity_main;

import android.graphics.Color;
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

    public AdapterBtnItem(List<ItemMainBtn> dataList) {
        this.dataList = dataList;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_btn, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.itemText.setText(dataList.get(position).text);
        if (cutSelectPos == position) {
            holder.itemText.setTextColor(Color.RED);
        } else {
            holder.itemText.setTextColor(Color.BLACK);
        }
        return convertView;
    }

    private int cutSelectPos = 0;

    public void setItemClick(int position) {
        this.cutSelectPos = position;
        notifyDataSetChanged();
    }

    class Holder {
        private TextView itemText;

        public Holder(View view) {
            itemText = view.findViewById(R.id.itemText);

        }
    }
}
