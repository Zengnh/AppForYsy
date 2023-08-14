package com.appforysy.activity.activity_game.fragment.game_pingtu_drage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.appforysy.activity.activity_game.fragment.game_pingtu.ItemPinTu;
import com.toolmvplibrary.activity_root.RootModel;
import com.toolmvplibrary.activity_root.RootPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PresenterDrage extends RootPresenter {
    @Override
    public RootModel createModel() {
        return null;
    }

    public List<ItemPinTu> sourceDataList = new ArrayList<>();
    public int row = 3;

    public void initBitmap(int resource) {
        sourceDataList.clear();
        targeDataList.clear();
        Bitmap bmRoot = BitmapFactory.decodeResource(getContext().getResources(), resource);
        int imgWidth = bmRoot.getWidth();
        int oneWidth = imgWidth / row;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                ItemPinTu itemTart = new ItemPinTu();
                targeDataList.add(itemTart);
                ItemPinTu item = new ItemPinTu();
                item.postion = i * row + j;
                item.bm = Bitmap.createBitmap(bmRoot, j * oneWidth, i * oneWidth, oneWidth, oneWidth);
                item.isEmpty = false;
                sourceDataList.add(item);
            }
        }

        Collections.shuffle(sourceDataList);
//        需要多一行。用于回收跟发牌
        for (int j = 0; j < row; j++) {
            ItemPinTu itemTart = new ItemPinTu();
            if (j == 0) {
                itemTart.bm = sourceDataList.get(0).bm;
                itemTart.postion = sourceDataList.get(0).postion;
            }
            targeDataList.add(itemTart);
        }
        sourceDataList.remove(0);
    }

    //    ##############################################################
    public List<ItemPinTu> targeDataList = new ArrayList<>();

    //    ################################################################
    public void setSourceToTager(int s, int t) {
        ItemPinTu itemTag = targeDataList.get(t);
        if (itemTag.bm == null) {
            ItemPinTu source = sourceDataList.get(s);
            targeDataList.set(t, source);
            sourceDataList.remove(s);
        }
    }
}
