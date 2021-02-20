package com.appforysy.activity.activity_edt_note;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.appforysy.R;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.rootlibs.chosefile.InterFaceResult;
import com.rootlibs.chosefile.ToolSelectFile;
import com.rootlibs.database.ToolGoogleRoom;
import com.toolmvplibrary.activity_root.ActivityRootInit;
import com.toolmvplibrary.activity_root.RootPresenter;
import com.toolmvplibrary.tool_app.ToolThreadPool;

import java.util.List;

public class ActivityNotiEdit extends ActivityRootInit {
    @Override
    public int setCutLayout() {
        return R.layout.activity_noti_edit;
    }

    private Button addImage;

    @Override
    public void initView() {
        et_detail = findViewById(R.id.editInput);
        addImage = findViewById(R.id.addImage);
    }

    @Override
    protected RootPresenter setPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        String str = et_detail.getText().toString().trim();
        if (TextUtils.isEmpty(str)) {

        } else {
            ToolThreadPool.getInstance().exeRunable(new Runnable() {
                @Override
                public void run() {
                    long time = System.currentTimeMillis();
                    ToolGoogleRoom.getInstance().saveValue("note" + time, str);
                }
            });

        }
        super.onDestroy();
    }

    private ToolSelectFile getImage;

    @Override
    public void initData() {
        getImage = new ToolSelectFile(this);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getImage.setResult(data);
    }

    private EditText et_detail;

    public void inputImage() {
        getImage.selectImage(new InterFaceResult<List<ChosenImage>>() {
            @Override
            public void result(List<ChosenImage> result) {
                inputImage(result);
            }

            @Override
            public void err(String err) {
                showToast(err);
            }
        });
    }

    private void inputImage(List<ChosenImage> result) {
        // 依据Bitmap对象创建ImageSpan对象
        String netUrl = "";
        for (int i = 0; i < result.size(); i++) {
            ChosenImage choseimage = result.get(i);
            netUrl = choseimage.getThumbnailPath();
            Bitmap bitmap = BitmapFactory.decodeFile(netUrl);
            ImageSpan imageSpan = new ImageSpan(ActivityNotiEdit.this, bitmap);
            String tempUrl = "<img src=\"" + netUrl + "\" />";
            SpannableString spannableString = new SpannableString(tempUrl);
            // 用ImageSpan对象替换你指定的字符串
            spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // 将选择的图片追加到EditText中光标所在位置
            int index = et_detail.getSelectionStart(); // 获取光标所在位置
            Editable edit_text = et_detail.getEditableText();
            if (index < 0 || index >= edit_text.length()) {
                edit_text.append(spannableString);
            } else {
                edit_text.insert(index, spannableString);
            }
        }

//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.image_noti_head);
//        ImageSpan imageSpan = new ImageSpan(ActivityNotiEdit.this, bitmap);
//        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
//        String tempUrl = "<img src=\"" + netUrl + "\" />";
//        SpannableString spannableString = new SpannableString(tempUrl);
//        // 用ImageSpan对象替换你指定的字符串
//        spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        // 将选择的图片追加到EditText中光标所在位置
//        int index = et_detail.getSelectionStart(); // 获取光标所在位置
//        Editable edit_text = et_detail.getEditableText();
//        if (index < 0 || index >= edit_text.length()) {
//            edit_text.append(spannableString);
//        } else {
//            edit_text.insert(index, spannableString);
//        }
//        System.out.println("插入的图片：" + spannableString.toString());
    }

}
