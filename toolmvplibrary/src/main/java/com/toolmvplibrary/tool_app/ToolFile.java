package com.toolmvplibrary.tool_app;

import android.content.Context;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ToolFile {
    public static String getAppRoot(Context context) {
//        方法一
////        String strRoot=Environment.getExternalStorageDirectory().getPath() + "/yousucloud" ;
//        File file=new File(strRoot);
//        if(!file.exists()){
//            file.mkdirs();
//        }

//        方法二 Android/data/包名/files
//        getExternalFilesDir(String type)	外部存储	无需权限	Android/data/包名/files	如果传入非空的String 会在其目录下创建相应文件夹比如传入“test”就是Android/data/包名/files/test；卸载应用后会自动删除
        String strRoot = context.getExternalFilesDir("alldataroot").getPath();

//        String strRoot = null;
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//                || !Environment.isExternalStorageRemovable()) {
//            strRoot = context.getExternalCacheDir().getPath();
//        } else {
//            strRoot = context.getCacheDir().getPath();
//        }

        return strRoot;
    }

    public static String getVoiceRoot(Context context) {
        String strRoot = getAppRoot(context) + "/voice/";
        File file = new File(strRoot);
        if (!file.exists()) {
            file.mkdirs();
        }
        return strRoot;
    }

    public static String getFileRoot(Context context) {
        String strRoot = getAppRoot(context) + "/file/";
        File file = new File(strRoot);
        if (!file.exists()) {
            file.mkdirs();
        }
        return strRoot;
    }

    public static String getImgRoot(Context context) {
        String strRoot = getAppRoot(context) + "/img/";
        File file = new File(strRoot);
        if (!file.exists()) {
            file.mkdirs();
        }
        return strRoot;
    }

    public static String exitDistory() {
        String strRoot = Environment.getExternalStorageDirectory().getPath() + "/DCIM/zfile/";
        File file = new File(strRoot);
        if (!file.exists()) {
            file.mkdirs();
        }
        return strRoot;
    }


    //#################################读取文本内容#############################################
    public static void readFormSDcardGBK(String path) {
        if (!new File(path).exists()) {
            LogUtil.i("tag", "没有这个文件");
        } else {
            try {
                FileInputStream fis = new FileInputStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis, "GBK"));
                StringBuilder sb = new StringBuilder("");
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                LogUtil.i("znh_fileinput", sb.toString());
            } catch (Exception e) {
                LogUtil.i("znh_fileinput", "读取失败！");
            }
        }
    }
//########################################################################################

//    #########################文本写入########################

    /**
     * 保存动作数据
     * data 保存的内容
     * time 时间作为txt文件名
     */
    public static void saveAction(String data, String fileInfo) {
        //新建文件夹
        String folderName = "infoText";
        File sdCardDir = new File(Environment.getExternalStorageDirectory(), folderName);
        if (!sdCardDir.exists()) {
            sdCardDir.mkdirs();
        }
        String fileName = fileInfo + ".txt";
        //新建文件
        File saveFile = new File(sdCardDir, fileName);
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            writeData(data, false, saveFile);
        } catch (Exception e) {
            LogUtil.e("znh_write", "saveAll: " + e.toString());
        }

    }


    /**
     * @param content        写入内容
     * @param isClearContent 是否清楚原来内容 true 覆盖数据 false 累加内容
     */
//每次都重新写入
    public static void writeData(String content, Boolean isClearContent, File saveFile) {
        try {
            if (isClearContent) {
                final FileOutputStream outStream = new FileOutputStream(saveFile);
                try {
                    //内容写入文件
                    outStream.write(content.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.e("znh_write_err", "writeTxtToFile: --------------" + e.toString());
                } finally {
                    try {
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //内容追加
                BufferedWriter out = null;
                try {
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile, true)));
                    out.write(content + "\r\n");
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            LogUtil.i("znh_write", "写入成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static int getFileImg(String path) {
//        int source = R.mipmap.img_chat_unknow;
//        if (path.toLowerCase().endsWith(".txt")) {
//            source = R.mipmap.img_chat_txt;
//        } else if (path.toLowerCase().endsWith(".doc") || path.toLowerCase().endsWith(".docx")) {
////            doc
//            source = R.mipmap.img_chat_word;
//        } else if (path.toLowerCase().endsWith(".xlsx") || path.toLowerCase().endsWith(".xlsm")) {
////            Excel
//            source = R.mipmap.img_chat_excel;
//        } else if (path.toLowerCase().endsWith(".pptx")) {
////            ppt
//            source = R.mipmap.img_chat_ppt;
//        } else if (path.toLowerCase().endsWith(".pdf")) {
////            ppt
//            source = R.mipmap.img_chat_pdf;
//        } else if (path.toLowerCase().endsWith(".rar") || path.toLowerCase().endsWith(".zip")) {
//            source = R.mipmap.img_chat_rar;
//        }
//        return source;
//    }

//    ###############################################################

    /**
     *    * 压缩文件和文件夹
     *    *
     *    * @param srcFileString 要压缩的文件或文件夹
     *    * @param zipFileString 压缩完成的Zip路径
     *    * @throws Exception
     *    
     */
    public static void ZipFolder(String srcFileString, String zipFileString) throws Exception {
//创建ZIP
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
//创建文件
        File file = new File(srcFileString);
//压缩
//LogUtils.i("---->"+file.getParent()+"==="+file.getAbsolutePath());
        ZipFiles(file.getParent() + File.separator, file.getName(), outZip);
//完成和关闭
        outZip.finish();
        outZip.close();
    }

    /**
     * 压缩文件
     *
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     * @throws Exception
     */
    private static void ZipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam) throws Exception {
//    LogUtils.LOGE("folderString:" + folderString + "\n" +
//                        "fileString:" + fileString + "\n==========================");
        if (zipOutputSteam == null)
            return;
        File file = new File(folderString + fileString);
        if (file.isFile()) {
            ZipEntry zipEntry = new ZipEntry(fileString);
            FileInputStream inputStream = new FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[4096];
            while ((len = inputStream.read(buffer)) != -1) {
                zipOutputSteam.write(buffer, 0, len);
            }
            zipOutputSteam.closeEntry();
        } else {
//文件夹
            String fileList[] = file.list();
//没有子文件和压缩
            if (fileList.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
                zipOutputSteam.putNextEntry(zipEntry);
                zipOutputSteam.closeEntry();
            }
//子文件和递归
            for (int i = 0; i < fileList.length; i++) {
                ZipFiles(folderString + fileString + "/", fileList[i], zipOutputSteam);
            }
        }
    }


}
