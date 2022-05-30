package com.screenlib.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class is used to store preferences and acts as shared memory
 * between the activity and the service
 *
 * @author Hathibelagal
 */
public class SharedMemory {
    SharedPreferences prefs;

    public SharedMemory(Context ctx) {
        prefs = ctx.getSharedPreferences("SCREENTOOL_SETTINGS", Context.MODE_PRIVATE);
    }


    public void setItem(int value) {
        prefs.edit().putInt("item", value).commit();
    }

    public int getItem() {
        return prefs.getInt("item", 1);
    }

    public void setBlack(int value) {
        prefs.edit().putInt("black", value).commit();
    }

    public int getBlack() {
        return prefs.getInt("black", 0x00);
    }

    public void setAlpha(int value) {
        prefs.edit().putInt("alpha", value).commit();
    }

    public int getAlpha() {
        return prefs.getInt("alpha", 0x40);
    }

    public void setGreen(int value) {
        prefs.edit().putInt("green", value).commit();
    }

    public int getGreen() {
        return prefs.getInt("green", 0x00);
    }

    public void setBlue(int value) {
        prefs.edit().putInt("blue", value).commit();
    }

    public int getBlue() {
        return prefs.getInt("blue", 0x00);
    }


    public void setRed(int value) {
        prefs.edit().putInt("red", value).commit();
    }

    public int getRed() {
        return prefs.getInt("red", 0x00);
    }


    public static int getColor(int alpha, int red, int green, int blue) {
        String hex = String.format("%02x%02x%02x%02x", alpha, red, green, blue);
        int color = (int) Long.parseLong(hex, 16);
        return color;
    }

    public int getColor() {
        String hex = String.format("%02x%02x%02x%02x", getAlpha(), getRed(), getGreen(), getBlue());
        int color = (int) Long.parseLong(hex, 16);
        return color;
    }

    public int getColorBlack() {
        String hex = String.format("%02x%02x%02x%02x", getBlack(), getBlackC(), getBlackC(), getBlackC());
        int color = (int) Long.parseLong(hex, 16);
        return color;
    }

    private int getBlackC() {
        return 0x7;
    }
}
