package com.photolib.selectpic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
public class ToolToSelectPic {

    private ToolToSelectPic() {
        initDef();
    }

    private void initDef() {
        selectLimit = 1;
        showPic = true;
        showVideo = false;
        requestCode = 114;
        fromType = 0;

    }

    private static ToolToSelectPic instance;

    public static ToolToSelectPic create() {
        instance = new ToolToSelectPic();
        return instance;
    }

    //    从哪个位置跳转到选择图片的。1为聊天----选择图片顶部右侧为发送文案，其他为确定
    private int fromType = 0;

    public ToolToSelectPic setFromType(int fromType) {
        this.fromType = fromType;
        return instance;
    }

    //    限制选择图片个数
    private int selectLimit = 1;

    public ToolToSelectPic setLitmitPic(int limit) {
        selectLimit = limit;
        return instance;
    }

    //    设置请求码，供result使用
    private int requestCode = 110;

    public ToolToSelectPic setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return instance;
    }

    //    是否只选择图片
    private boolean showPic = false;

    public ToolToSelectPic showPic(boolean onlyPic) {
        this.showPic = onlyPic;
        return instance;
    }

    private boolean showVideo = true;

    public ToolToSelectPic showVideo(boolean onlyPic) {
        this.showVideo = onlyPic;
        return instance;
    }

    /**
     * 直接跳转摄像头
     */
    private boolean isToCamera = false;

    public ToolToSelectPic isToCamera(boolean toCamrea) {
        this.isToCamera = toCamrea;
        return instance;
    }

    public final static String LIMIT = "limit_img";
    public final static String SHOWPIC = "show_pic";
    public final static String SHOWVIDEO = "show_video";
    public final static String FROMTYPE = "from_type";

    public void start(Activity act) {
        Intent intent;
//        if (isToCamera) {
//          ToolLocationImage.getInstance().setCameraResult(null);
//            intent = createToCameraIntent(act);
//        } else {
//            intent = createIntent(act);
//        }
        intent = createIntent(act);
        act.startActivityForResult(intent, requestCode);
    }

    public void start(Fragment fra) {
//        Intent intent = createIntent(fra.getContext());
        Intent intent;
        if (isToCamera) {
            ToolLocationImage.getInstance().setCameraResult(null);
//            intent = createToCameraIntent(fra.getContext());
            intent = createIntent(fra.getContext());//去掉拍照。只有选择图片、视频
        } else {
            intent = createIntent(fra.getContext());
        }
        fra.startActivityForResult(intent, requestCode);
    }


//    private Intent createToCameraIntent(Context context) {
//        Intent intent = new Intent(context, ActivityCamera.class);
//        intent.putExtra(LIMIT, selectLimit);
//        intent.putExtra(FROMTYPE, fromType);
//        intent.putExtra(SHOWVIDEO, showVideo);
//        intent.putExtra(SHOWPIC, showPic);
//        return intent;
//    }

    private Intent createIntent(Context context) {
        Intent intent = new Intent(context,ActivityLocalImageList.class);
        intent.putExtra(LIMIT, selectLimit);
        intent.putExtra(FROMTYPE, fromType);
        intent.putExtra(SHOWVIDEO, showVideo);
        intent.putExtra(SHOWPIC, showPic);
        return intent;
    }

    public static ConfigPic getConfig(Intent intent) {
        ConfigPic config = new ConfigPic();
        config.limit = intent.getIntExtra(LIMIT, 1);
        config.showPic = intent.getBooleanExtra(SHOWPIC, true);
        config.fromType = intent.getIntExtra(FROMTYPE, 0);
        config.showVideo = intent.getBooleanExtra(SHOWVIDEO, false);
        return config;
    }

    public ArrayList<ImageBean> resultSelect() {
        ArrayList<ImageBean> select = new ArrayList<ImageBean>();
        select.addAll(ToolLocationImage.getInstance().getPicked());
        return select;
    }
}
