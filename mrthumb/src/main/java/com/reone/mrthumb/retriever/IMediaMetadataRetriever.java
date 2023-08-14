package com.reone.mrthumb.retriever;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Map;

/**
 * author  dengyuhan
 * created 2017/5/26 14:49
 */
public interface IMediaMetadataRetriever {

    void setDataSource(String path);

    void setDateSource(String uri, Map<String, String> headers);

    void setDataSource(Context context, Uri uri);

    Bitmap getFrameAtTime();

    Bitmap getFrameAtTime(long timeUs, int option);

    Bitmap getScaledFrameAtTime(long timeUs, int width, int height);

    Bitmap getScaledFrameAtTime(long timeUs, int option, int width, int height);

    byte[] getEmbeddedPicture();

    String extractMetadata(String keyCode);

    void release();
}
