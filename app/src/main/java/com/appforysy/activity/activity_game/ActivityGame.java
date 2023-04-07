package com.appforysy.activity.activity_game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appforysy.R;
import com.appforysy.utils.EventRightBtnClick;
import com.appforysy.utils.ToolTitleLayout;
import com.rootlibs.eventbus.ToolEventBus;
import com.toolmvplibrary.activity_root.ActivityRoot;

import org.greenrobot.eventbus.EventBus;

/***
 *
 */
public class ActivityGame extends ActivityRoot<PresenterGame> implements GameManager.GameView {

    /**
     * 1.舒尔特
     * 2、绘本
     * 3、拼图
     *
     * @param act
     * @param type
     * @param title
     */
    public static void startActivityGame(Context act, int type, String title) {
        Intent intent = new Intent(act, ActivityGame.class);
        intent.putExtra("type", type);
        intent.putExtra("title", title);
        act.startActivity(intent);
    }

    @Override
    protected PresenterGame setPresenter() {
        return new PresenterGame();
    }

    private ToolTitleLayout toolTitleLayout;
    private ImageView titleRight;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        toolTitleLayout = findViewById(R.id.titleLayout);
        presenter.setIntentInit(getIntent());
        titleRight=toolTitleLayout.setRightBtn(R.drawable.mine_setting, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventRightBtnClick(1));
            }
        });
        toolTitleLayout.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(presenter.showRightSetBtn()){
            titleRight.setVisibility(View.VISIBLE);
        }else{
            titleRight.setVisibility(View.GONE);}

    }

    @Override
    public void reflushView(String code) {
        if (code.equals("title")) {
            toolTitleLayout.setTitle(presenter.getTitle());
            changeFragment(presenter.getCutFragment());
        }
    }

    public void changeFragment(Fragment fra) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layou_game_content, fra)
                .commit();
    }
}