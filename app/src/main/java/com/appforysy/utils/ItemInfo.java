package com.appforysy.utils;

public class ItemInfo {
    public ItemInfo() {
    }

    public ItemInfo(int img, String text, String remark) {
        this.img = img;
        this.text = text;
        this.remark = remark;
    }

    public int img;
    public String text;
    public int type;

    public int imgSel;
    public int imgNor;
    public String remark;
    public Class nextClass;
    public void setNextClass( Class nextClass){
        this.nextClass=nextClass;
    }
}
