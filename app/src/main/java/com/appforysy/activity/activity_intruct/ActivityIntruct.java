package com.appforysy.activity.activity_intruct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.wheel.SimpleWheelAdapter;
import com.appforysy.wheel.WheelView;
import com.google.android.material.tabs.TabLayout;
import com.toolmvplibrary.activity_root.ActivityRoot;

/***
 *
 */
public class ActivityIntruct extends ActivityRoot {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intruct);
        initView();
        initEvent();
        initData();
    }

    private String[] titles = new String[]{"最新", "热门", "我的"};

    private void initData() {
        for (int i = 0; i < titles.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.getTabAt(i).setText(titles[i]);
//            tabLayout.getTabAt(i).setCustomView(titles[i]);
        }

//      tabLayout.setupWithViewPager(viewPager,false);
//        simpleWheelAdapter=new SimpleWheelAdapter();
//        wheelView.setAdapter(simpleWheelAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("热门")) {
                    initPopWindow();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initEvent() {

    }

    private RecyclerView recyclerContentView;
    private LinearLayoutManager linearLayoutManager;
    private AdapterIntructRecycler adapter;
    private TabLayout tabLayout;

    private void initView() {
        recyclerContentView = findViewById(R.id.recyclerContentView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new AdapterIntructRecycler();
        recyclerContentView.setLayoutManager(linearLayoutManager);
        recyclerContentView.setAdapter(adapter);
        tabLayout = findViewById(R.id.tableBar);
    }

    //    ###########################
    SimpleWheelAdapter simpleWheelAdapter;
    WheelView wheelView;
    PopupWindow popWindown;

    private void initPopWindow() {
        if (popWindown == null) {
            popWindown = new PopupWindow(this);
            View view = LayoutInflater.from(this).inflate(R.layout.pop_window_select, null);
            wheelView = view.findViewById(R.id.wheelView);
            popWindown.setContentView(view);
            popWindown.setWidth(RecyclerView.LayoutParams.MATCH_PARENT);
            popWindown.setHeight(RecyclerView.LayoutParams.WRAP_CONTENT);
            WheelView.WheelAdapter adapter = new WheelView.WheelAdapter() {
                @Override
                public int getItemCount() {
                    return 15;
                }

                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater inflater, int viewType) {
                    View view = inflater.inflate(R.layout.item_text_view, null);
                    return new ItemHolder(view);
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    ItemHolder hd = (ItemHolder) holder;
                    hd.tv.setText(position + "");
                }

                class ItemHolder extends RecyclerView.ViewHolder {
                    public TextView tv;

                    public ItemHolder(@NonNull View itemView) {
                        super(itemView);
                        tv = itemView.findViewById(R.id.textView);
                    }
                }
            };
            wheelView.setAdapter(adapter);
            popWindown.setOutsideTouchable(true);
        }
        popWindown.showAsDropDown(tabLayout);
    }
}