package com.reone.mrthumb.cache;

import android.graphics.Bitmap;

import java.util.Arrays;

/**
 * Created by wangxingsheng on 2018/9/30.
 */
public class ThumbCache {
    private int max = 0;
    private int lastIndex = 0;//最后一个存在的位置
    private Bitmap[] thumbnails;

    private ThumbCache() {
        thumbnails = new Bitmap[max];
    }

    private static ThumbCache mInstance = null;

    public static ThumbCache getInstance() {
        if (mInstance == null) {
            synchronized (ThumbCache.class) {
                if (mInstance == null) {
                    mInstance = new ThumbCache();
                }
            }
        }
        return mInstance;
    }

    public Bitmap get(int index) {
        ensureArray();
        if (index < 0 || index >= max) {
            return thumbnails[lastIndex];
        }
        return thumbnails[index];
    }

    private void ensureArray() {
        if(thumbnails == null){
            thumbnails = new Bitmap[max];
        }
    }

    public boolean hasThumbnail(int index) {
        ensureArray();
        return index > 0 && index < max && thumbnails[index] != null;
    }

    public void set(int index, Bitmap thumbnail) {
        if (index < 0 || index >= max) {
            return;
        }
        lastIndex = Math.max(index, lastIndex);
        ensureArray();
        this.thumbnails[index] = thumbnail;
    }

    public void setCacheMax(int maxSize) {
        max = Math.max(max, maxSize);
        if (thumbnails == null) {
            thumbnails = new Bitmap[max];
        } else {
            thumbnails = Arrays.copyOf(thumbnails, max);
        }
    }

    public void release() {
        if (thumbnails != null) {
            try {
                for (Bitmap bitmap : thumbnails) {
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            thumbnails = null;
        }
    }
}
