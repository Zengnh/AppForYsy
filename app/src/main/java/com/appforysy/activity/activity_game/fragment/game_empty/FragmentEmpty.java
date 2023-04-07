package com.appforysy.activity.activity_game.fragment.game_empty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.appforysy.R;
import com.toolmvplibrary.activity_root.FragmenRoot;

public class FragmentEmpty extends FragmenRoot<PresenterEmpty> {

    @Override
    protected PresenterEmpty createPresenter() {
        return new PresenterEmpty();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}