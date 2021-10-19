package com.medialib;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.medialib.trim.utils.ImageUtils;
import com.medialib.video_trim.UriUtil;

import java.io.File;
import java.io.IOException;

/**
 * 视频工具类
 *
 * @author lincun
 * @date 2019/6/30 21:54
 **/
public class VideoUtils {

    private static final String TAG = VideoUtils.class.getSimpleName();

    public static class VideoMetadata {
    }

    public static String getVideoMetadata(String videoPath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            mmr.setDataSource(videoPath);
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            String width = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//宽
            String height = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//高
            String bitrate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);//高
            Log.i(TAG, "d:" + duration + " w:" + width + " h:" + height +" bitrate:"+bitrate);
            return bitrate;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mmr.release();
        }
        return null;
    }

    /**
     * 获取本地视频封面
     *
     * @param context
     * @param videoPath 本地视频地址
     * @return
     */
    public static String getVideoThumbnailByPath(Context context, String videoPath) {
        if (TextUtils.isEmpty(videoPath))
            return null;
        String[] thumbColumns = {
                MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID};
        // 视频其他信息的查询条件
        String[] mediaColumns = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DURATION
        };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, mediaColumns[1] + "= ?", new String[]{videoPath}, null);

        if (cursor == null) {
            return null;
        }

        String thumbnail = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Video.Media._ID));
            cursor.close();

            Cursor thumbCursor = context.getContentResolver().query(
                    MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                    thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + "= ?", new String[]{id + ""}, null);
            if (thumbCursor.moveToFirst()) {
                do {
                    thumbnail = thumbCursor.getString(thumbCursor
                            .getColumnIndex(MediaStore.Video.Thumbnails.DATA));

                } while (thumbCursor.moveToNext());
            }
            thumbCursor.close();
        }

        if (TextUtils.isEmpty(thumbnail)) {
            // 找不到封面则手动生成
            MediaMetadataRetriever metaDataSource = new MediaMetadataRetriever();
            metaDataSource.setDataSource(context, Uri.fromFile(new File(videoPath)));
            Bitmap thumbBitmap = metaDataSource.getFrameAtTime(MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            metaDataSource.release();
           /* Bitmap thumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MICRO_KIND);*/
            if (thumbBitmap != null) {
                File dir = DiskConfig.SaveDir.getCacheDir();
                File file = new File(dir, UriUtil.getNameFromUrl(videoPath) + ".jpg");
                if (dir != null && dir.exists()) {
                    try {
                        ImageUtils.saveImageToSDCard(file.getAbsolutePath(), thumbBitmap, 100);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (file.exists() && file.isFile()) {
                    thumbnail = file.getAbsolutePath();
                }
            }
        }

        return thumbnail;
    }
}
