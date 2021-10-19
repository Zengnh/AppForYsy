package com.medialib.trim.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * 图片工具类
 * @author lincun
 * @date 2019/6/27 10:58
 */
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


    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap decodeFile(File f, int WIDTH, int HIGHT) {
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            //Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }


    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    public static Bitmap resizeImage(Bitmap bitmap, int targetSize) {
        int[] size = computeImageSide(bitmap.getWidth(), bitmap.getHeight(), targetSize);
        return Bitmap.createScaledBitmap(bitmap, size[0], size[1], false);
    }

    public static int[] computeImageSide(int width, int height, int targetSize) {
        int[] size = new int[2];
        if (width > targetSize) {
            double d = (double) width / (double) targetSize;
            //计算的时候采用中国(或者美国)的方式,不影响系统设置，仅在方法中有效，
            // //阿拉伯的方式计算DecimalFormat format会报错，temp为一个“阿语的字符”
            Locale.setDefault(Locale.CHINESE);
            DecimalFormat df = new DecimalFormat("##.000");
            String temp = df.format(d);
            double d1 = Double.parseDouble(temp);
            int l = (int) Math.round((double) height / d1);
            size[0] = targetSize;
            size[1] = l;
        } else {
            size[0] = width;
            size[1] = height;
        }
        return size;
    }

}
