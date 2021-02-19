package com.toolmvplibrary.activity_root;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.toolmvplibrary.tool_app.ToolSys;
import java.util.List;

public class AdapterPopBase extends BaseAdapter {
    private List<String> dataList;

    public AdapterPopBase(List<String> dataList) {
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
    public View getView(int position, View content, ViewGroup parent) {
        TextView tv;
        if (content == null) {
            tv = new TextView(parent.getContext());
            ViewGroup.LayoutParams lp = tv.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ToolSys.dip2px(parent.getContext(), 45);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(ToolSys.sp2px(parent.getContext(),14));
            tv.setTextColor(Color.BLACK);
        } else {
            tv = (TextView) content;
        }
        tv.setText(dataList.get(position));
        return tv;
    }
}
