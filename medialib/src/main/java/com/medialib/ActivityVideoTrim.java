package com.medialib;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import com.medialib.mvp.VideoTrimView;
import com.medialib.trim.callback.VideoTrimListener;
import com.medialib.trim.utils.ImageUtils;
import com.medialib.trim.utils.VideoTrimmerUtil;
import com.medialib.video_trim.VideoTrimmerView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.microshow.rxffmpeg.RxFFmpegInvoke;

/**
 * 视频裁剪
 */
public class ActivityVideoTrim extends AppCompatActivity implements VideoTrimView, VideoTrimListener {

    public static final String KEY_VIDEO = "key_video";
    private static final String TAG = "VideoTrim";
    private VideoTrimmerView mTrimmerView;
    private ImageVideoBean mVideo;

    public static void goTrim(Activity activity, ImageVideoBean video, int requestCode) {
        Intent intent = new Intent(activity, ActivityVideoTrim.class);
        intent.putExtra(KEY_VIDEO, video);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_trim);
        initViews(savedInstanceState);
        getFirstVideo();
    }

//    @Override
//    protected VideoTrimPresenter createPresenter() {
//        return new VideoTrimPresenterImpl();
//    }


    private void getFirstVideo() {
        ImageView imageView = findViewById(R.id.imageView);
        String str = "file:///android_asset/abc.mp4";
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "abc.mp4");
        mmr.setDataSource(file.getPath());
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
        Log.i("aaa", duration);
        Bitmap mBitmap = mmr.getFrameAtTime();
        imageView.setImageBitmap(mBitmap);
    }

    public ImageVideoBean getVideo() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "abc.mp4";
        ImageVideoBean mv = new ImageVideoBean();
        //            mVideo.setPath("file:///android_asset/abc.mp4");
        mv.setPath(path);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒

        mv.setDuration(Long.parseLong(duration));
        return mv;
    }

    protected void initViews(Bundle savedInstanceState) {
        VideoTrimmerUtil.initValue(this);
        mVideo = getIntent().getParcelableExtra(KEY_VIDEO);
        if (mVideo == null) {
            mVideo = getVideo();
        }
        if (mVideo == null || mVideo.getDuration() <= 0) {
            Log.i("znh", "视频数据异常");
            finish();
            return;
        }

        mTrimmerView = findViewById(R.id.trimmer_view);
        mTrimmerView.setOnTrimVideoListener(this);
        mTrimmerView.initVideoByURI(mVideo);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTrimmerView != null) {
            mTrimmerView.onVideoPause();
            mTrimmerView.setRestoreState(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTrimmerView != null) {
            mTrimmerView.onDestroy();
        }
    }

    @Override
    public void onStartTrim() {
        Log.i("znh", "裁剪中...");
    }

    @Override
    public void onFinishTrim(long duration, String url) {
        Log.i("znh", "裁剪成功:" + url + " /duration:" + duration);
        //hideWaitDialog();
        mVideo.setPath(mVideo.getPath());
        mVideo.setVideoThumbnail(mVideo.getVideoThumbnail());
        mVideo.isTrimmedVideo = true;
        mVideo.setDuration(duration);
        mVideo.setPath(url);
//        压缩适配等动作
        generateVideoThumbnailAndPick(mVideo);
//        presenter.generateVideoThumbnailAndPick(mVideo);
    }

    @Override
    public void onCancel() {
        finish();
        if (mTrimmerView != null) {
            mTrimmerView.onDestroy();
        }
    }

    @Override
    public void onError(String message) {
        Log.i("znh", getString(R.string.tip_video_trim_error));
    }

    @Override
    public void pickVideoAndFinish(ImageVideoBean bean) {
        Intent intent = new Intent();
        intent.putExtra(KEY_VIDEO, bean);
        setResult(RESULT_OK, intent);
        finish();
    }


    public ImageVideoBean generateVideoThumbnailAndPick(ImageVideoBean bean) {
        if (bean != null) {
            String path = VideoUtils.getVideoThumbnailByPath(getBaseContext(), bean.getPath());
            Log.i(TAG, "生成缩略图:" + path);
            bean.setVideoThumbnail(path);
            VideoUtils.getVideoMetadata(bean.getPath());
            if (!TextUtils.isEmpty(path)) {
                // 修正video 宽高倒置
                int[] wh = ImageUtils.getImageWidthHeight(path);
                bean.setWidth(wh[0]);
                bean.setHeight(wh[1]);
            }

            // 判断是否需要压缩视频
            File dir = DiskConfig.SaveDir.getCompressDir();
            File source = new File(bean.getPath());
            if (dir != null) {
                File csource = null;
                if (source.getAbsolutePath().endsWith(".3gp")) {
                    csource = new File(dir, source.getName().replace(".3gp", "_convert.mp4"));
                    String cmd = String.format("ffmpeg -y -i %s -f mp4 -preset superfast %s",
                            source.getAbsolutePath(), csource.getAbsolutePath());
                    String[] commands = cmd.split(" ");
                    int res = RxFFmpegInvoke.getInstance().runCommand(commands, null);
                    Log.i(TAG, "视频格式转换结果:" + res + " / " + csource.exists() + " / " + csource.getAbsolutePath());
                    if (csource.exists()) {
                        source = csource;
                        Log.i(TAG, "格式转换成功，原视频改为:" + source.getAbsolutePath());
                    } else {
                        Log.i(TAG, "无法转换格式,忽略压缩>" + source.getAbsolutePath());
                        return bean;
                    }
                }

                File target = new File(dir, source.getName().replace(".", "_compress."));//-b 500k

                String cmd = String.format("ffmpeg -y -i %s -r 30 -vcodec libx264 -crf 20 -vf scale=%s -preset superfast %s",
                        source.getAbsolutePath(),
                        bean.getHeight() > bean.getWidth() ? "720:-2" : "-2:720",
                        target.getAbsolutePath());
                String[] commands = cmd.split(" ");
                int res = RxFFmpegInvoke.getInstance().runCommand(commands, null);
                if (csource != null) {
                    csource.delete();
                }
                Log.i(TAG, "2视频压缩结果:" + res + " / " + target.exists() + " / " + target.getAbsolutePath());
                Log.i(TAG, "2原视频:" + source.getAbsolutePath());
                if (target.exists()) {
                    bean.compressPath = target.getAbsolutePath();
                    bean.hasCompressed = true;
                }
            }
        }
        return bean;
    }

}
