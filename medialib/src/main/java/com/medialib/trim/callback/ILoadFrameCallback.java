package com.medialib.trim.callback;

import com.medialib.trim.VideoFrame;

import java.util.List;

public interface ILoadFrameCallback {

    void onLoadFrames(List<VideoFrame> frames);
}
