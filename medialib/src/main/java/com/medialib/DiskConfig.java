package com.medialib;

import android.os.Environment;

import java.io.File;

/**
 * SD卡存储配置
 * @author lincun
 * @date 2019/6/30 20:03
 **/
public class DiskConfig {

    public static class SaveDir {

        /**
         * APP本地存储根路径
         * @return
         */
        public static File getRoot() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/roof");
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file;
            }
            return null;
        }

        /**
         * 图片保存路径
         * @return
         */
        public static File getImageDir(){
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/roof/save_images");
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file;
            }
            return null;
        }

        /**
         * 缓存路径
         * @return
         */
        public static File getCacheDir() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/roof/cache");
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file;
            }
            return null;
        }

        /**
         * 压缩路径
         * @return
         */
        public static File getCompressDir() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/roof/compress");
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file;
            }
            return null;
        }
    }
}