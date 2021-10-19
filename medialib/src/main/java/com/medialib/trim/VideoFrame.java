package com.medialib.trim;

import android.graphics.Bitmap;

public class VideoFrame {
    public static int STATUS_LOADING = 1;
    public static int STATUS_SUCCESS = 2;
    public static int STATUS_NONE = 0;
    public static int STATUS_ERROR = 3;

    private int status;
    private Bitmap bitmap;
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public synchronized int getStatus() {
        return status;
    }

    public synchronized void setStatus(int status) {
        this.status = status;
    }
}
