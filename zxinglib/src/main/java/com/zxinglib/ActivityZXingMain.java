package com.zxinglib;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.WriterException;
import com.zxinglib.encode.CodeCreator;

public class ActivityZXingMain extends AppCompatActivity {
    private static final int REQUEST_CODE_SCAN = 0x0000;

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";

    TextView qrCoded;
    ImageView qrCodeImage;
    Button creator, scanner;
    EditText qrCodeUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_zxing_main);
        qrCoded =  findViewById(R.id.ECoder_title);
        qrCodeImage =  findViewById(R.id.ECoder_image);
        creator =  findViewById(R.id.ECoder_creator);
        scanner =  findViewById(R.id.ECoder_scaning);
        qrCodeUrl =  findViewById(R.id.ECoder_input);

        creator.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String url = qrCodeUrl.getText().toString();
                try {
                    Bitmap bitmap = CodeCreator.createQRCode(url);
                    qrCodeImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });

        scanner.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivityZXingMain.this, ActivityCapture.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                qrCoded.setText("解码结果： \n" + content);
                qrCodeImage.setImageBitmap(bitmap);
            }
        }
    }
}
