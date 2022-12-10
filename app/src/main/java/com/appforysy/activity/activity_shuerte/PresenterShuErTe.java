package com.appforysy.activity.activity_shuerte;

import com.toolmvplibrary.activity_root.ItemInfo;
import com.toolmvplibrary.activity_root.RootModel;
import com.toolmvplibrary.activity_root.RootPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PresenterShuErTe extends RootPresenter {
    @Override
    public RootModel createModel() {
        return new ModelShuErTe();
    }

    public int rowCell = 3;

    public List<ItemInfo> getContentInfo() {
        List<ItemInfo> data = new ArrayList<>();
        for (int i = 0; i < rowCell * rowCell; i++) {
            ItemInfo item = new ItemInfo();
            item.type = 0;
            item.text = String.valueOf(i + 1);
            data.add(item);
        }
        Collections.shuffle(data);
        return data;
    }

    public List<ItemInfo> getContentLevel() {
        List<ItemInfo> data = new ArrayList<>();
        for (int i = 2; i < 9; i++) {
            ItemInfo item = new ItemInfo();
            item.type = i;
            item.text = "L " + i;
            data.add(item);
        }
        return data;
    }

}
