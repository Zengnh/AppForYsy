package com.appforysy.activity.activity_game.fragment.game_different;

import com.appforysy.activity.activity_game.fragment.game_pingtu.ItemPinTu;
import com.toolmvplibrary.activity_root.RootModel;
import com.toolmvplibrary.activity_root.RootPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PresenterDrage extends RootPresenter {
    @Override
    public RootModel createModel() {
        return null;
    }

    public List<ItemPinTu> sourceDataList = new ArrayList<>();
    public int row = 3;

    public void initChangeDef() {
        sourceDataList.clear();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                ItemPinTu item = new ItemPinTu();
                item.postion = i * row + j;
                item.content = comText;
                item.isEmpty = false;
                sourceDataList.add(item);
            }
        }
        for (int i = 0; i < row; i++) {
            sourceDataList.get(i).content = difText;
            sourceDataList.get(i).isEmpty = true;
        }
        Collections.shuffle(sourceDataList);
    }

    public void changeLevel() {
        initChangeDef();
    }

    public String comText = "甲";
    public String difText = "由";
    public int countDiff=3;

//    "汆""氽"
//    "胄""胄"
}
