package com.medialib.trim.callback;


public interface VideoTrimListener {
    void onStartTrim();

    void onFinishTrim(long duration, String url);

    void onCancel();

    void onError(String message);
}
