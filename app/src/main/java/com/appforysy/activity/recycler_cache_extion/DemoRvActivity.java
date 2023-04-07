package com.appforysy.activity.recycler_cache_extion;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.appforysy.R;

public class DemoRvActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_rv);
        recyclerView = findViewById(R.id.rv_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new DemoAdapter();
        recyclerView.setAdapter(adapter);

        //viewType类型为TYPE_SPECIAL时，设置四级缓存池RecyclerPool不存储对应类型的数据 因为需要开发者自行缓存
        recyclerView.getRecycledViewPool().setMaxRecycledViews(DemoAdapter.TYPE_SPECIAL, 0);
        //设置ViewCacheExtension缓存
        recyclerView.setViewCacheExtension(new MyViewCacheExtension());
    }

    //实现自定义缓存ViewCacheExtension
    class MyViewCacheExtension extends RecyclerView.ViewCacheExtension {
        @Nullable
        @Override
        public View getViewForPositionAndType(@NonNull RecyclerView.Recycler recycler, int position, int viewType) {
            //如果viewType为TYPE_SPECIAL,使用自己缓存的View去构建ViewHolder
            // 否则返回null，会使用系统RecyclerPool缓存或者从新通过onCreateViewHolder构建View及ViewHolder
            return viewType == DemoAdapter.TYPE_SPECIAL ? adapter.caches.get(position) : null;
        }
    }
 }