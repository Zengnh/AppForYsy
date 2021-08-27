package com.cameralib.selectimg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    /**
     * 获取本地图片的宽高
     * @param path
     * @return
     */
    public static int[] getImageWidthHeight(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return new int[]{options.outWidth,options.outHeight};
    }

    public static void saveImageToSDCard(String filePath, Bitmap bitmap, int quality) throws IOException {
        File file = new File(filePath.substring(0,
                filePath.lastIndexOf(File.separator)));
        if (!file.exists()) {
            file.mkdirs();
        }
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(filePath));
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        bos.flush();
        bos.close();
    }
}