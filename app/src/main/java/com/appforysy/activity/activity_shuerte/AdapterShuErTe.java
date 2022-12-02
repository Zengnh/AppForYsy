package com.appforysy.activity.activity_shuerte;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appforysy.R;
import com.appforysy.activity.activity_main.AdapterBtnItem;
import com.appforysy.utils.ItemInfo;

import java.util.List;

public class AdapterShuErTe extends BaseAdapter {
    private List<ItemInfo> data;

    public AdapterShuErTe(List<ItemInfo> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shu_er_te_content, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        ItemInfo info=data.get(position);
        if(info.type==1){
            holder.itemSETContent.setTextColor(convertView.getContext().getResources().getColor(R.color.blue));
        }else{
            holder.itemSETContent.setTextColor(convertView.getContext().getResources().getColor(R.color.color_1e));
        }
        holder.itemSETContent.setText(info.text);
        return convertView;
    }

    class Holder {
        private TextView itemSETContent;
        public Holder(View view) {
            itemSETContent = view.findViewById(R.id.itemSETContent);
        }
    }
}
