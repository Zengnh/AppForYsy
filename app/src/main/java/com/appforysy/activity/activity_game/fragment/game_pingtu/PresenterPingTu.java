package com.appforysy.activity.activity_game.fragment.game_pingtu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.toolmvplibrary.activity_root.RootModel;
import com.toolmvplibrary.activity_root.RootPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PresenterPingTu extends RootPresenter {
    @Override
    public RootModel createModel() {
        return null;
    }

    public List<ItemPinTu> dataList = new ArrayList<>();
    public int row = 3;

    public void initBitmap(int resource) {
        dataList.clear();
        Bitmap bmRoot = BitmapFactory.decodeResource(getContext().getResources(), resource);
        int imgWidth = bmRoot.getWidth();
        int oneWidth = imgWidth / row;
        Random random = new Random();
        int posE = random.nextInt(row * row);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                ItemPinTu item = new ItemPinTu();
                item.postion = i * row + j;
                if (item.postion == posE) {
                    item.isEmpty = true;
                    Bitmap.Config config = Bitmap.Config.ARGB_8888;
                    item.bm = Bitmap.createBitmap(oneWidth, oneWidth, config);
                    emptyBm = Bitmap.createBitmap(bmRoot, j * oneWidth, i * oneWidth, oneWidth, oneWidth);
                } else {
                    item.bm = Bitmap.createBitmap(bmRoot, j * oneWidth, i * oneWidth, oneWidth, oneWidth);
                    item.isEmpty = false;
                }
                dataList.add(item);
            }
        }
        Collections.shuffle(dataList);
    }

    public Bitmap emptyBm;

}
