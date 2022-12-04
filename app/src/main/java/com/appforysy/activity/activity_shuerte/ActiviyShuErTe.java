package com.appforysy.activity.activity_shuerte;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.appforysy.R;
import com.appforysy.utils.ItemInfo;
import com.toolmvplibrary.activity_root.ActivityRoot;
import com.toolmvplibrary.tool_app.ToolSoundPool;

import java.util.List;

public class ActiviyShuErTe extends ActivityRoot<PresenterShuErTe> {

    @Override
    protected PresenterShuErTe setPresenter() {
        return new PresenterShuErTe();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shu_er_te);
        initView();
    }

    ToolSoundPool toolSoundPool;
    private GridView gridViewSET;
    private AdapterShuErTe adapterShuErTe;
    private List<ItemInfo> dataItemInfo;

    private void initView() {
        toolSoundPool = new ToolSoundPool(this);
        gridViewSET = findViewById(R.id.gridViewSET);
        gridViewSET.setNumColumns(3);
        dataItemInfo = presenter.getContentInfo();
        adapterShuErTe = new AdapterShuErTe(dataItemInfo);
        gridViewSET.setAdapter(adapterShuErTe);
        gridViewSET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemInfo itemInfo=dataItemInfo.get(position);
                if(proInt==Integer.parseInt(itemInfo.text)){
                    dataItemInfo.get(position).type = 1;
                    adapterShuErTe.notifyDataSetChanged();
                    toolSoundPool.play();
                    proInt++;
                }else{
                    showToast("选错咯");
                }
            }
        });
    }
    private int proInt=1;
}
