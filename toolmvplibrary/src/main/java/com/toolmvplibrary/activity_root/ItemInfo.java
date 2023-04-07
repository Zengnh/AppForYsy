package com.toolmvplibrary.activity_root;

import androidx.fragment.app.Fragment;

public class ItemInfo {
    public ItemInfo() {
    }
    public Fragment fra;
    public ItemInfo(int img, String text, String remark) {
        this(img, text, remark, 0, 0);
    }

    public int img;
    public String text;
    public int type;//1. 为游戏，跳转到gan页面，attribute 为游戏类型 GAMEENUM.PINGTU
    public int attribute;

    public int imgSel;
    public int imgNor;
    public String remark;
    public Class nextClass;

    public ItemInfo(int img, String text, String remark, int type, int attribute) {
        this.img = img;
        this.text = text;
        this.remark = remark;
        this.type = type;
        this.attribute = attribute;
    }

    public void setNextClass(Class nextClass) {
        this.nextClass = nextClass;
    }
}
