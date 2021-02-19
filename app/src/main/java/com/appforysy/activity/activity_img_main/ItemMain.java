package com.appforysy.activity.activity_img_main;

public class ItemMain {
    public ItemMain(){}
    public ItemMain(int floag,int icon){
        this.floag=floag;
        this.icon=icon;
    }
    public ItemMain(int floag,int icon,Class toNext){
        this.floag=floag;
        this.icon=icon;
        this.toNext=toNext;
    }
    public ItemMain(int icon,Class toNext){
        this.icon=icon;
        this.toNext=toNext;
    }
    int floag=0;
    int icon;
    Class toNext;
}
