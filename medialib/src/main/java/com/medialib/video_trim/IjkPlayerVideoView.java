package com.medialib.video_trim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.medialib.ImageVideoBean;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IjkPlayerVideoView extends FrameLayout implements TextureView.SurfaceTextureListener {

    private static final String TAG = "IJKPlayer";
    private IMediaPlayer mMediaPlayer = null;
    private Uri mPath;
    private GSYTextureView textureView;
    private VideoPlayerListener listener;
    private Context mContext;
    private Surface surface;
    private int mRotate;
    private ImageVideoBean mVideo;

    public IjkPlayerVideoView(@NonNull Context context) {
        super(context);
        initVideoView(context);
    }

    public IjkPlayerVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVideoView(context);
    }

    public IjkPlayerVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);
    }

    @SuppressLint("NewApi")
    public IjkPlayerVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initVideoView(context);
    }

    private void initVideoView(Context context) {
        mContext = context;
        //获取焦点
//        setFocusable(true);
    }

    /**
     * 设置视频地址。
     * 根据是否第一次播放视频，做不同的操作。
     *
     *
     */
    public void setVideoPath(ImageVideoBean video) {
        mVideo = video;
        if (mPath == null) {
            //如果是第一次播放视频，那就创建一个新的TextureView
            mPath = Uri.parse(video.getPath());
            createTextureView();
        } else {
            //否则就直接load
            mPath = Uri.parse(video.getPath());
            load();
        }
    }

    /**
     * 新建一个TextureView
     */
    private void createTextureView() {
        textureView = null;
        textureView = new GSYTextureView(getContext());
        textureView.setSurfaceTextureListener(this);
        textureView.setVideoParamsListener(new MeasureHelper.MeasureFormVideoParamsListener() {
            @Override
            public int getCurrentVideoWidth() {
                if (mVideo != null)
                    return (mRotate != 0 && mRotate % 90 == 0) ? mVideo.getHeight() : mVideo.getWidth();
                return 0;
            }

            @Override
            public int getCurrentVideoHeight() {
                if (mVideo != null)
                    return (mRotate != 0 && mRotate % 90 == 0) ? mVideo.getWidth() : mVideo.getHeight();
                return 0;
            }

            @Override
            public int getVideoSarNum() {
                return 0;
            }

            @Override
            public int getVideoSarDen() {
                return 0;
            }
        });
        textureView.setRotation(mRotate);
        Log.i(TAG, "createTextureView获取到旋转角度:" + mRotate);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        textureView.setLayoutParams(layoutParams);
        addView(textureView);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int i, int i1) {
        this.surface = new Surface(surface);
        // surfaceTexture数据通道准备就绪，打开播放器
        load();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        surface.release();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    /**
     * 加载视频
     */
    private void load() {
        //每次都要重新创建IMediaPlayer
        createPlayer();
        try {
            mMediaPlayer.setDataSource(mContext, mPath);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.setVolume(0f, 0f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //给mediaPlayer设置视图
        mMediaPlayer.setSurface(surface);
        mMediaPlayer.prepareAsync();
    }

    /**
     * 创建一个新的player
     */
    private void createPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.setDisplay(null);
            mMediaPlayer.release();
        }
        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);

        //开启硬解码
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);

        mMediaPlayer = ijkMediaPlayer;

        if (listener != null) {
            mMediaPlayer.setOnPreparedListener(listener);
            mMediaPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
                    if (what == IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED) {
                        mRotate = extra;
                        if (textureView != null) {
                            Log.i(TAG, "获取到旋转角度:" + extra);
                            textureView.setRotation(mRotate);
                        }
                    }
                    if (listener != null) {
                        return listener.onInfo(iMediaPlayer, what, extra);
                    }
                    return false;
                }
            });
            mMediaPlayer.setOnSeekCompleteListener(listener);
            mMediaPlayer.setOnBufferingUpdateListener(listener);
            mMediaPlayer.setOnErrorListener(listener);
        }
    }

    public void setListener(VideoPlayerListener listener) {
        this.listener = listener;
        if (mMediaPlayer != null) {
            mMediaPlayer.setOnPreparedListener(listener);
        }
    }

    /**
     * -------======--------- 下面封装了一下控制视频的方法
     */

    public void setVolume(float v1, float v2) {
        //关闭声音
        if (mMediaPlayer != null) {
            mMediaPlayer.setVolume(v1, v2);
        }
    }

    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    public void reset() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
        }
    }

    public void reCreate() {
        if (mMediaPlayer == null) {
            mMediaPlayer.reset();
        }
    }

    public long getDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    public long getCurrentPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    public void seekTo(long l) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(l);
        }
    }

    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }
}
