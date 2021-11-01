package com.zxinglib;

import android.graphics.Bitmap;
import com.google.zxing.WriterException;
import com.zxinglib.encode.CodeCreator;

public class ToolZXing {
    public static Bitmap createQRCode(String url) throws WriterException {
        return CodeCreator.createQRCode(url);
    }
}
