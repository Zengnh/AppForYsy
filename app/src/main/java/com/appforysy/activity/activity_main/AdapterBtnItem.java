package com.appforysy.activity.activity_main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appforysy.R;
import com.appforysy.utils.ItemInfo;

import java.util.List;

public class AdapterBtnItem extends BaseAdapter {
    private List<ItemInfo> dataList;

    public AdapterBtnItem(List<ItemInfo> dataList) {
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
            holder.itemText.setTextColor(convertView.getContext().getResources().getColor(R.color.blue_m_t));
            holder.mainItemImage.setImageResource(dataList.get(position).imgSel);
        } else {
            holder.itemText.setTextColor(convertView.getContext().getResources().getColor(R.color.color_999));
            holder.mainItemImage.setImageResource(dataList.get(position).imgNor);
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
        private ImageView mainItemImage;
        public Holder(View view) {
            itemText = view.findViewById(R.id.itemText);
            mainItemImage=view.findViewById(R.id.mainItemImage);
        }
    }
}
