package com.appforysy.activity.activity_game;

import android.content.Intent;

import com.toolmvplibrary.activity_root.InterCallBack;
import com.toolmvplibrary.activity_root.RootInterUi;
import com.toolmvplibrary.activity_root.RootModel;

//所有的接口都在此类。实现一一对应。方便查看
public interface GameManager {
    interface GameView extends RootInterUi {
        void reflushView(String code);
    }

    interface PresenterGame {

        void setIntentInit(Intent intent);
    }

    interface GameModel extends RootModel {
        public void getData(String name, String pwd, InterCallBack<String> result);
    }
}
