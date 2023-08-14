package com.libraryassets;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * author:znh
 * time:2023/4/26
 * desc:
 */
public class PresenterBookRead {
    private AppCompatActivity act;

    public PresenterBookRead(AppCompatActivity act) {
        this.act = act;
    }

    private File fileTxt;

    public void copyTxtToLocation() {
        try {
            InputStream inputStream = act.getApplicationContext().getAssets().open("textbook/bcgm.txt");
            File file = act.getExternalFilesDir("textbook");
            if (!file.exists()) {
                file.mkdirs();
            }
            fileTxt = new File(file.getPath() + "/bcgm.txt");
            if (!fileTxt.exists()) {
                fileTxt.createNewFile();
            }
            if (fileTxt.length() == 0) {
                try {
                    OutputStream output = new FileOutputStream(fileTxt);
                    try {
                        byte[] buffer = new byte[4 * 1024]; // or other buffer size
                        int read;
                        while ((read = inputStream.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }
                        output.flush();
                    } finally {
                        output.close();
                    }
                } finally {
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void destory() {
        try {
            if (lineNumberReader != null) {
                lineNumberReader.close();
            }
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//--------------------LineNumberReader------------------------------------------------

    private LineNumberReader lineNumberReader;

    public void initLineNumber() {
        try {
            cutReadPostion = 0;
            dataSize = 10;
            lineNumberReader = new LineNumberReader(new FileReader(fileTxt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String readLine() {
        lineNumberReader.setLineNumber(cutReadPostion);
        String lineText = "";
        for (int i = 0; i < dataSize; i++) {
            String line = null;
            try {
                line = lineNumberReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {
                break;
            }
            if (i == 0) {
                lineText += line;
            } else {
                lineText += "\n\r" + line;
            }
        }
        return lineText;
    }
    //--------------------------RandomAccessFile------------------------------------------


    private RandomAccessFile randomAccessFile;

    public void initRandomAccess() {
        try {
//“r”：以只读方式打开指定文件。如果试图对该RandomAccessFile执行写入方法，都将抛出IOException异常。
//“rw”：以读、写方式打开指定文件。如果该文件尚不存在，则尝试创建该文件。
//“rws”：以读、写方式打开指定文件。相对于"rw"模式，还要求对文件的内容或元数据的每个更新都同步写入到底层存储设备。
//“rwd”：以读、写方式打开指定文件。相对于"rw"模式，还要求对文件内容的每个更新都同步写入到底层存储设备。
            Log.i("znh", "## " + fileTxt.length());
            cutReadPostion = 0;
            dataSize = 512;
            randomAccessFile = new RandomAccessFile(fileTxt, "r");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] buf = new byte[512];
    private int cutReadPostion = 0;
    private int dataSize = 512;

    public String readrandom() {
        if (randomAccessFile == null) {
            return "";
        }
        try {
            randomAccessFile.seek(cutReadPostion);
            randomAccessFile.read(buf, 0, dataSize);
//            String dataHex = bytesToHexString(buf);//转16进制显示
            String dataHex = new String(buf);
            return dataHex;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String bytesToHexString(byte... src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    //    -------------------------------
    public void initNext() {
        cutReadPostion += dataSize;
    }

    public void initPro() {
        cutReadPostion -= dataSize;
        if (cutReadPostion < 0) {
            cutReadPostion = 0;
        }
    }
}
