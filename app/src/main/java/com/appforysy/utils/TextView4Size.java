package com.appforysy.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author:znh
 * time:2023/4/11
 * desc:
 */
public class TextView4Size extends LinearLayout {

    public TextView4Size(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.layout_text, this);
        initView(context, view);
    }

    public TextView4Size(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private AdapterTxt adapterTxt;
    private List<String> txtList = new ArrayList<>();

    private void initView(Context context, View view) {
        adapterTxt = new AdapterTxt(txtList);
        layoutManager = new GridLayoutManager(context, 2);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterTxt);
    }

    public void setData(String text) {
        if (!TextUtils.isEmpty(text)) {
            txtList.clear();
            for (int i = 0; i < text.length(); i++) {
                if (i < 9) {
                    try {
                        txtList.add(text.substring(i, i + 1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (txtList.size() <= 2) {
                layoutManager.setSpanCount(2);
                adapterTxt.setTextSize(25);
            } else if (txtList.size() > 2 && txtList.size() <= 4) {
                layoutManager.setSpanCount(2);
                adapterTxt.setTextSize(25);
            } else if (txtList.size() > 4 && txtList.size() <= 6) {
                layoutManager.setSpanCount(3);
                adapterTxt.setTextSize(20);
            } else if (txtList.size() > 6 && txtList.size() <= 9) {
                layoutManager.setSpanCount(3);
                adapterTxt.setTextSize(20);
            }
            adapterTxt.notifyDataSetChanged();
        }
    }

    private class AdapterTxt extends RecyclerView.Adapter<AdapterTxt.TxtHolder> {
        private List<String> data;

        private int size = 16;

        public void setTextSize(int size) {
            this.size = size;
        }

        public AdapterTxt(List<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public TxtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_view_like_img, null);
            return new TxtHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TxtHolder holder, int position) {
            holder.itemText.setTextSize(size);
            holder.itemText.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class TxtHolder extends RecyclerView.ViewHolder {
            TextView itemText;

            public TxtHolder(@NonNull View itemView) {
                super(itemView);
                itemText = itemView.findViewById(R.id.itemText);
            }
        }
    }

}
