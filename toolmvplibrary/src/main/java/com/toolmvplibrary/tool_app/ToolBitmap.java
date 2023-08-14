package com.toolmvplibrary.tool_app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ToolBitmap {

    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    public static Bitmap getVideoFirstImg(String localPath) {
        Bitmap bitmap = null;
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        try {
//            //根据文件路径获取缩略图
//            retriever.setDataSource(localPath);
//            //获得第一帧图片
//            bitmap = retriever.getFrameAtTime();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } finally {
//            retriever.release();
//        }
        return bitmap;
    }


    public static String saveBitmap(Context context, Bitmap bitmap) {
        String locationFile = "";
        try {
            String filePath = ToolFile.getImgRoot(context);
            File dirFile = new File(filePath);
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }
            String fileName = "vf" + System.currentTimeMillis() + ".jpg";
            locationFile = filePath + fileName;
            File file = new File(filePath, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            locationFile = "";
        } catch (IOException e) {
            e.printStackTrace();
            locationFile = "";
        }
        return locationFile;
    }

    public String saveBitmapPic(Context context, Bitmap bitmap) {
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);

        String displayName = System.currentTimeMillis() + "z.jpg";
        String mimeType = "image/jpeg";
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
        return addBitmapToAlbum(context, bitmap, displayName, mimeType, compressFormat);
    }

    /**
     * api >= 29时调用 保存图片到系统相册
     *
     * @param bitmap
     * @param displayName
     * @param mimeType
     * @param compressFormat
     */

    private static String addBitmapToAlbum(Context context, Bitmap bitmap, String displayName, String mimeType, Bitmap.CompressFormat compressFormat) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
        } else {
            values.put(MediaStore.MediaColumns.DATA, Environment.getExternalStorageDirectory().getPath() + Environment.DIRECTORY_DCIM + displayName);
        }
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (uri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = contentResolver.openOutputStream(uri);
                bitmap.compress(compressFormat, 100, outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (outputStream != null) {
            }
            Log.i("save", "保存成功 " + uri.getPath());
            return uri.getPath();
        }
        return "";
    }
}
