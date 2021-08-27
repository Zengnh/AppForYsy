package com.cameralib.selectimg;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cameralib.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片、视频选择器
 * 规则：视频和图片不能同选，视频最多只能选择1项，图片选择数量可配置
 **/
public class ActivityLocalImageList extends AppCompatActivity {
    // 检查权限并开始加载数据
    private void startLoadData() {
        if (!checkPermission(this)) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CODE_PERMISSION);
        } else {
           ToolLocationImage.getInstance().load(this);
        }
    }

    // 权限检查
    public static boolean checkPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length >= 3) {
                int cameraResult = grantResults[0];//相机权限
                int sdResult = grantResults[1];//sd卡权限
                int sdResult2 = grantResults[2];//sd卡权限
                boolean cameraGranted = cameraResult == PackageManager.PERMISSION_GRANTED;//拍照权限
                boolean sdGranted = sdResult == PackageManager.PERMISSION_GRANTED;//拍照权限
                boolean sdGranted2 = sdResult2 == PackageManager.PERMISSION_GRANTED;//拍照权限
                if (cameraGranted && sdGranted && sdGranted2) {
                    //具有拍照权限，sd卡权限，开始扫描任务
                    ToolLocationImage.getInstance().load(this);
                } else {
                    //没有权限
//                    showToast(getString(R.string.emptyNet));
                    setResult(RESULT_CANCELED);
                    finish();
                }
            }
        }
    }

    private ConfigPic configSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_location_image);
        configSelect = ToolToSelectPic.getConfig(getIntent());
        ToolLocationImage.getInstance()
                .setFloag(configSelect.showPic,
                        configSelect.showVideo,
                        configSelect.limit);
        initView();
        startLoadData();
        ToolLocationImage.getInstance().setLoadListener(new InterLoadImg() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void loadResult() {
                if (isFinishing() || isDestroyed())
                    return;
                mImageAdapter.notifyDataSetChanged();
//                (ToolLocationImage.getInstance().getAllImgVideo());
            }
        });
    }

    private TextView tvRight;
    private RecyclerView mRvImages;
    private AdapterLocalImageList mImageAdapter;

    public void initView() {
        tvRight=findViewById(R.id.textFinish);
//        发送/完成
        if (configSelect.fromType == 1) {
            ToolLocationImage.getInstance().setIsFromChat(true);
            tvRight.setText("发送");
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectFinish();
                }
            });
        } else {
            tvRight.setText("完成");
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectFinish();
                }
            });
        }
        tvRight.setAlpha(0.5f);
        tvRight.setEnabled(false);
        mRvImages = findViewById(R.id.rv_images);
        GridLayoutManager sMgr = new GridLayoutManager(this, 4);
        mRvImages.setLayoutManager(sMgr);
        mRvImages.setItemAnimator(null);
        mImageAdapter = new AdapterLocalImageList(this,
                mRvImages, ToolLocationImage.getInstance().getAllImgVideo(),
                new AdapterLocalImageList.OnPickDelegate() {
                    @Override
                    public void onClickCamera() {
                        toClickCamera();
                    }

                    @Override
                    public boolean onPickImage(ImageBean bean) {
                        return false;
                    }

                    @Override
                    public void onPreviewImage(ImageBean bean, int pos) {
                        ActivityLocalImagePager.startPager(ActivityLocalImageList.this,
                                pos - 1, toPagerImg);
                    }

                    @Override
                    public void onPickChanged(ArrayList<ImageBean> list, boolean add, int postion) {
                        changeState(list);
                    }

                    @Override
                    public void selectImgToast(String msg) {
//                        showToast(msg);
                    }
                });
        mRvImages.setAdapter(mImageAdapter);
    }

    public void changeState(ArrayList<ImageBean> list) {
        if (configSelect.fromType == 1) {
            tvRight.setText(list.size() + "/" + configSelect.limit + "发送");
        } else {
            tvRight.setText(list.size() + "/" + configSelect.limit +  "完成");
        }
        if (list.size() > 0) {
            tvRight.setAlpha(1f);
            tvRight.setEnabled(true);
        } else {
            tvRight.setAlpha(0.5f);
            tvRight.setEnabled(false);
        }
    }


    private int toPagerImg = 111;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == toPagerImg) {
//            跳转到单页展示的图片
            if (resultCode == RESULT_OK) {
                selectFinish();
            } else {
                mImageAdapter.notifyDataSetChanged();
                changeState(ToolLocationImage.getInstance().getPicked());
            }
        } else if (resultCode == RESULT_OK
                && requestCode == REQUEST_CODE_CAMERA
                && mCameraTmpFile != null
                && mCameraTmpFile.exists()) {
            int[] wh = ImageUtils.getImageWidthHeight(mCameraTmpFile.getAbsolutePath());
//            ArrayList<ImageBean> list = ToolLocationImage.getInstance().getPicked();
            ImageBean bean = new ImageBean();
            bean.isSelect = true;
            bean.setPath(mCameraTmpFile.getAbsolutePath());
            bean.setMime("image/jpeg");
            bean.setHeight(wh[1]);
            bean.setWidth(wh[0]);
            ToolLocationImage.getInstance().setTakePhoto(bean);
            mImageAdapter.notifyDataSetChanged();
            changeState(ToolLocationImage.getInstance().getPicked());
//            selectFinish();
        }
    }

    public void selectFinish() {
//        选择完成了
//     返回吧
        setResult(Activity.RESULT_OK);
        finish();
    }

    private static final int REQUEST_CODE_PERMISSION = 100;//权限请求
    private static final int REQUEST_CODE_CAMERA = 101;//拍照
    private File mCameraTmpFile; //相机拍照临时存储

    public void toClickCamera() {
        // 打开相机拍照
//        ArrayList<ImageBean> imgs = ToolLocationImage.getInstance().getPicked();
        if (checkPermission(this)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(this.getPackageManager()) != null) {
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                mCameraTmpFile = new File(dir, +System.currentTimeMillis() + ".jpg");
                try {
                    mCameraTmpFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", mCameraTmpFile);
                    List<ResolveInfo> resInfoList = this.getPackageManager()
                            .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        this.grantUriPermission(packageName, photoURI,
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                }
            }
        }
    }
}