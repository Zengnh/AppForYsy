package com.toolmvplibrary.tool_app;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class ToolDisk {
    public static String getAppTempRoot(Context context) {
        // getExternalFilesDir(String type)	外部存储	无需权限	Android/data/包名/files
        // 如果传入非空的String 会在其目录下创建相应文件夹比如传入“test”就是Android/data/包名/files/test；卸载应用后会自动删除
        String strRoot = context.getExternalFilesDir("alldataroot").getPath();
        return strRoot;
    }

    public static String fileRoot="rootLibs";
    public static class SaveDir {

        /**
         * APP本地存储根路径
         *
         * @return
         */
        public static File getRoot() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/"+fileRoot);
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file;
            }
            return null;
        }

        /**
         * 图片保存路径
         *
         * @return
         */
        public static File getImageDir() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(getRoot().getPath() + "/save_images");
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file;
            }
            return null;
        }

        /**
         * 缓存路径
         *
         * @return
         */
        public static File getCacheDir() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(getRoot().getPath() + "/.cache");
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file;
            }
            return null;
        }

        /**
         * 压缩路径
         *
         * @return
         */
        public static File getCompressDir() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(getRoot().getPath() + "/.compress");
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file;
            }
            return null;
        }
    }
}