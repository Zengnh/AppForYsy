package com.appforysy.activity.activity_time_note;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.appforysy.activity.activity_scro_vh.ActivityScrollviewVH;
import com.rootlibs.database.AppDBConfig;
import com.rootlibs.database.ToolGoogleRoom;
import com.toolmvplibrary.activity_root.ActivityRootInit;
import com.toolmvplibrary.activity_root.RootPresenter;

import java.util.ArrayList;
import java.util.List;

public class ActivityTimeNote extends ActivityRootInit {
    @Override
    protected RootPresenter setPresenter() {
        return null;
    }

    @Override
    public int setCutLayout() {
        return R.layout.activity_time_note;
    }

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<ItemTimeNote> dataList = new ArrayList<>();
    private Button showSecond;

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        showSecond = findViewById(R.id.showSecond);
        showSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inTime = new Intent(ActivityTimeNote.this, ActivityScrollviewVH.class);
                startActivity(inTime);
            }
        });
    }

    private AdapterTimeNote adapterTimeNote;

    @Override
    public void initData() {
        ToolColor.getInstance();

        List<AppDBConfig> alll = ToolGoogleRoom.getInstance().getAllConfig();
        for (int i = 0; i < 30; i++) {
            ItemTimeNote item = new ItemTimeNote();
            item.sysTime = System.currentTimeMillis() - i * 1000 * 60 * 60 * 24;

            for (int j = 0; j < 15; j++) {
                int all=(i)*15+j;
                if (all < alll.size()) {
                    item.content.add(alll.get(all).configValue);
                } else {
                    item.content.add("第" + i + "行" + j + "列消息" + j * i);
                }
            }
            dataList.add(item);
        }
        adapterTimeNote = new AdapterTimeNote(dataList);
        recyclerView.setAdapter(adapterTimeNote);
    }
}
