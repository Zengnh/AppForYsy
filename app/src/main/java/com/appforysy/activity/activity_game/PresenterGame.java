package com.appforysy.activity.activity_game;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.appforysy.activity.activity_game.fragment.game_empty.FragmentEmpty;
import com.appforysy.activity.activity_game.fragment.game_pingtu.FragmentPingTu;
import com.appforysy.activity.activity_game.fragment.game_different.FragmentChinaDifferent;
import com.appforysy.activity.activity_game.fragment.game_shuerte.FragmentShuErTe;
import com.toolmvplibrary.activity_root.RootPresenter;

public class PresenterGame extends RootPresenter<GameManager.GameView, GameManager.GameModel> implements GameManager.PresenterGame {

    private Intent intent;
    private GameInfo cutGameInfo = new GameInfo();

    @Override
    public void setIntentInit(Intent intent) {
        this.intent = intent;
        cutGameInfo.type = intent.getIntExtra("type", 1);
        cutGameInfo.title = intent.getStringExtra("title");
        getViewInterface().reflushView("title");
    }

    @Override
    public GameManager.GameModel createModel() {
        return new GameModel();
    }

    public String getTitle() {
        return cutGameInfo.title;
    }

    public Fragment getCutFragment() {
        if (cutGameInfo.type == GAMEENUM.SHUERTE.getAttibute()) {
            return new FragmentShuErTe();
        } else if (cutGameInfo.type == GAMEENUM.DIFFERENT.getAttibute()) {
            return new FragmentChinaDifferent();
        } else if (cutGameInfo.type == GAMEENUM.PINGTU.getAttibute()) {
            return new FragmentPingTu();
        } else {
            return new FragmentEmpty();
        }
    }

    public boolean showRightSetBtn() {
        if (cutGameInfo.type == GAMEENUM.SHUERTE.getAttibute()) {
            return false;
        } else if (cutGameInfo.type == GAMEENUM.DIFFERENT.getAttibute()) {
            return false;
        } else if (cutGameInfo.type == GAMEENUM.PINGTU.getAttibute()) {
            return true;
        } else {
            return false;
        }

    }
}
