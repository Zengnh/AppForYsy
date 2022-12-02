package com.appforysy.activity.activity_shuerte;

import com.appforysy.utils.ItemInfo;
import com.toolmvplibrary.activity_root.RootModel;
import com.toolmvplibrary.activity_root.RootPresenter;

import java.util.ArrayList;
import java.util.List;

public class PresenterShuErTe extends RootPresenter {
    @Override
    public RootModel createModel() {
        return new ModelShuErTe();
    }

    public List<ItemInfo> getContentInfo() {
        List<ItemInfo> data = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            ItemInfo item = new ItemInfo();
            item.type = 0;
            item.text = i + "";
            data.add(item);
        }
        return data;
    }

}
