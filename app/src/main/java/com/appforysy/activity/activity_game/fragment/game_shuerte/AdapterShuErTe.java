package com.appforysy.activity.activity_game.fragment.game_shuerte;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appforysy.R;
import com.toolmvplibrary.activity_root.ItemInfo;

import java.util.List;

public class AdapterShuErTe extends BaseAdapter {
    private List<ItemInfo> data;
    private float oneWidth = 3;
    private int rowCell;

    public AdapterShuErTe(List<ItemInfo> data) {
        this.data = data;
    }

    public void setAttribute(float oneWidth, int rowCell) {
        this.oneWidth = oneWidth;
        this.rowCell = rowCell;
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
        ViewGroup.LayoutParams lp = holder.itemSETContent.getLayoutParams();
        lp.height = (int) oneWidth;
        lp.width = (int) oneWidth;
        holder.itemSETContent.setLayoutParams(lp);
        int textSize = 48;
        if (rowCell == 2) {
            textSize = 48;
        } else if (rowCell == 3) {
            textSize = 40;
        } else if (rowCell == 4) {
            textSize = 35;
        } else if (rowCell == 5) {
            textSize = 28;
        } else if (rowCell == 6) {
            textSize = 20;
        } else if (rowCell == 7) {
            textSize = 16;
        } else if (rowCell == 8) {
            textSize = 14;
        } else if (rowCell == 9) {
            textSize = 10;
        }
        holder.itemSETContent.setTextSize(textSize);

        ItemInfo info = data.get(position);
        if (info.type == 1) {
            holder.itemSETContent.setTextColor(convertView.getContext().getResources().getColor(R.color.color_blue,null));
            holder.itemSETContent.setBackgroundColor(convertView.getContext().getResources().getColor(R.color.myl_red2,null));
        } else {
            holder.itemSETContent.setTextColor(convertView.getContext().getResources().getColor(R.color.color_1e,null));
            holder.itemSETContent.setBackgroundColor(convertView.getContext().getResources().getColor(R.color.color_fff,null));
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
