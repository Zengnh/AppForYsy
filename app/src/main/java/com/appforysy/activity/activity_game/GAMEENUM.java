package com.appforysy.activity.activity_game;

public enum GAMEENUM {
    SHUERTE(1),PINGTU(2), DIFFERENT(3);
    private int attibute;
    private GAMEENUM(int attibute){
        this.attibute=attibute;
    }

    public int getAttibute() {
        return attibute;
    }
}
