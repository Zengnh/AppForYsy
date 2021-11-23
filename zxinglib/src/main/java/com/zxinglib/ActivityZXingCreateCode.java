package com.zxinglib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zxinglib.encode.CodeCreator;

import java.util.Hashtable;

public class ActivityZXingCreateCode extends AppCompatActivity {
    public static void startCreate(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            Intent intent = new Intent(context, ActivityZXingCreateCode.class);
            intent.putExtra("context", str);
            context.startActivity(intent);
        }
    }

    TextView qrCoded;
    ImageView qrCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_zxing_create_code);
        qrCoded = findViewById(R.id.ECoder_title);
        qrCodeImage = findViewById(R.id.ECoder_image);
        try {
            String content = getIntent().getStringExtra("context");
//            Bitmap bitmap = CodeCreator.createQRCode(content);
            Bitmap bitmap = creatQRCode(content, 720);
            qrCodeImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap creatQRCode(String var1, int var2) {
        try {
            Hashtable var3 = new Hashtable();
            var3.put(EncodeHintType.CHARACTER_SET, "utf-8");
            var3.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            var3.put(EncodeHintType.MARGIN, 1);
            BitMatrix var4 = (new QRCodeWriter()).encode(var1, BarcodeFormat.QR_CODE, var2, var2, var3);
            var4 = this.deleteWhite(var4);
            int var5 = var4.getWidth();
            int var6 = var4.getHeight();
            int[] var7 = new int[var5 * var6];

            for (int var8 = 0; var8 < var6; ++var8) {
                for (int var9 = 0; var9 < var5; ++var9) {
                    if (var4.get(var9, var8)) {
                        var7[var8 * var5 + var9] = -16777216;
                    } else {
                        var7[var8 * var5 + var9] = -1;
                    }
                }
            }

            Bitmap var11 = Bitmap.createBitmap(var5, var6, Bitmap.Config.ARGB_8888);
            var11.setPixels(var7, 0, var5, 0, 0, var5, var6);
            return var11;
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }

    private BitMatrix deleteWhite(BitMatrix var1) {
        int[] var2 = var1.getEnclosingRectangle();
        int var3 = var2[2] + 1;
        int var4 = var2[3] + 1;
        BitMatrix var5 = new BitMatrix(var3, var4);
        var5.clear();

        for (int var6 = 0; var6 < var3; ++var6) {
            for (int var7 = 0; var7 < var4; ++var7) {
                if (var1.get(var6 + var2[0], var7 + var2[1])) {
                    var5.set(var6, var7);
                }
            }
        }

        return var5;
    }


}
