package com.rootlibs.luban;


import android.content.Context;
import android.text.TextUtils;


import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

//    implementation 'top.zibin:Luban:1.1.8'
public class ToolLuban {
    private OnCompressListener listener = new OnCompressListener() {
        @Override
        public void onStart() {
//            showDialog();
        }

        @Override
        public void onSuccess(File file) {
//            hitDialog();
//            onSelectImgResult(file.getPath());
        }

        @Override
        public void onError(Throwable e) {
//            hitDialog();
//            onSelectImgResult(finalSelectPic1);
        }
    };

    public void scImage(Context context,String saveDir, String file,OnCompressListener listener) {
        File fileImage = new File(saveDir);
        if (!fileImage.exists()) {
            fileImage.mkdirs();
        }
        Luban.with(context)
                .load(file)
                .ignoreBy(512)
                .setTargetDir(saveDir)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(listener)
                .launch();

    }
}
