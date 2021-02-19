package com.toolmvplibrary.activity_root;

import android.content.Context;

public interface RootInterUi {
    void showToast(String str);
    void showLoading();
    void hitLoading();
    void hitKeyBox();
    Context getContext();
}
