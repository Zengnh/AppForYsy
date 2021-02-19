package com.toolmvplibrary.tool_app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

public class ToolInstallApk {
    public static void insertApk(Activity act, String filePath, String config){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri;
        File file = new File(filePath);
        if (Build.VERSION.SDK_INT >= 24) {//android 7.0以上
            uri = FileProvider.getUriForFile(act, config.concat(".provider"), file);
        } else {
            uri = Uri.fromFile(file);
        }
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(uri, type);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        act.startActivityForResult(intent, 10);
    }


//    BuildConfig.APPLICATION_ID
    public static void insertApk(Activity act,File file, String config){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {//android 7.0以上
            uri = FileProvider.getUriForFile(act, config.concat(".provider"), file);
        } else {
            uri = Uri.fromFile(file);
        }
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(uri, type);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        act.startActivityForResult(intent, 10);
    }



//    private static FileProvider.PathStrategy parsePathStrategy(Context context, String authority) throws IOException, XmlPullParserException {
//        FileProvider.SimplePathStrategy strat = new FileProvider.SimplePathStrategy(authority);
//        ProviderInfo info = context.getPackageManager().resolveContentProvider(authority, 128);
//        XmlResourceParser in = info.loadXmlMetaData(context.getPackageManager(), "android.support.FILE_PROVIDER_PATHS");
//        if (in == null) {
//            throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
//        } else {
//            int type;
//            while((type = in.next()) != 1) {
//                if (type == 2) {
//                    String tag = in.getName();
//                    String name = in.getAttributeValue((String)null, "name");
//                    String path = in.getAttributeValue((String)null, "path");
//                    File target = null;
//                    if ("root-path".equals(tag)) {
//                        target = DEVICE_ROOT;
//                    } else if ("files-path".equals(tag)) {
//                        target = context.getFilesDir();
//                    } else if ("cache-path".equals(tag)) {
//                        target = context.getCacheDir();
//                    } else if ("external-path".equals(tag)) {
//                        target = Environment.getExternalStorageDirectory();
//                    } else {
//                        File[] externalMediaDirs;
//                        if ("external-files-path".equals(tag)) {
//                            externalMediaDirs = ContextCompat.getExternalFilesDirs(context, (String)null);
//                            if (externalMediaDirs.length > 0) {
//                                target = externalMediaDirs[0];
//                            }
//                        } else if ("external-cache-path".equals(tag)) {
//                            externalMediaDirs = ContextCompat.getExternalCacheDirs(context);
//                            if (externalMediaDirs.length > 0) {
//                                target = externalMediaDirs[0];
//                            }
//                        } else if (Build.VERSION.SDK_INT >= 21 && "external-media-path".equals(tag)) {
//                            externalMediaDirs = context.getExternalMediaDirs();
//                            if (externalMediaDirs.length > 0) {
//                                target = externalMediaDirs[0];
//                            }
//                        }
//                    }
//
//                    if (target != null) {
//                        strat.addRoot(name, buildPath(target, path));
//                    }
//                }
//            }
//
//            return strat;
//        }
//    }

}
