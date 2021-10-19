package com.medialib.video_trim;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.medialib.trim.utils.VideoTrimmerUtil.VIDEO_FRAMES_WIDTH;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medialib.AdapterVideoTrim;
import com.medialib.DiskConfig;
import com.medialib.ImageVideoBean;
import com.medialib.R;
import com.medialib.trim.VideoFrame;
import com.medialib.trim.callback.ILoadFrameCallback;
import com.medialib.trim.callback.IVideoTrimmerView;
import com.medialib.trim.callback.VideoTrimListener;
import com.medialib.trim.utils.BackgroundExecutor;
import com.medialib.trim.utils.SingleCallback;
import com.medialib.trim.utils.UiThreadExecutor;
import com.medialib.trim.utils.VideoTrimmerUtil;

import java.io.File;
import java.util.List;
import java.util.UUID;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * 视频预览，裁剪逻辑处理
 */
public class VideoTrimmerView extends FrameLayout implements IVideoTrimmerView {

    private static final String TAG = VideoTrimmerView.class.getSimpleName();
    private static final int MSG_UPDATE = 1;

    private int mMaxWidth = VIDEO_FRAMES_WIDTH;
    private Context mContext;
    private RelativeLayout mLinearVideo;
    //    private ZVideoView mVideoView;
    private IjkPlayerVideoView mVideoView;
    //private ImageView mPlayView;
    private RecyclerView mVideoThumbRecyclerView;
    private RangeSeekBarView mRangeSeekBarView;
    private LinearLayout mSeekBarLayout;
    private ImageView mRedProgressIcon;
    private TextView mVideoShootTipTv;
    private float mAverageMsPx;//每毫秒所占的px
    private float averagePxMs;//每px所占用的ms毫秒
    private Uri mSourceUri;
    private ImageVideoBean mVideo;
    private VideoTrimListener mOnTrimVideoListener;
    private long mDuration = 0;
    private int mRotate;//视频旋转角度
    private AdapterVideoTrim mVideoThumbAdapter;
    private boolean isFromRestore = false;
    private long mLeftProgressPos, mRightProgressPos;
    private long mRedProgressBarPos = 0;
    private long scrollPos = 0;
    private int mScaledTouchSlop;
    private int lastScrollX;
    private boolean isSeeking;
    private boolean isOverScaledTouchSlop;
    private int mThumbsTotalCount;
    private ValueAnimator mRedProgressAnimator;
    //private Handler mAnimationHandler = new Handler();
    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE:
                    if (mVideoView.isPlaying()) {

                        mRedProgressIcon.setVisibility(View.VISIBLE);

                        mRedProgressBarPos = mVideoView.getCurrentPosition();

                        if (mRedProgressBarPos < mLeftProgressPos) {
                            mRedProgressIcon.setVisibility(View.GONE);
                        }

                        final LayoutParams params = (LayoutParams) mRedProgressIcon.getLayoutParams();
                        int start = (int) (VideoTrimmerUtil.RECYCLER_VIEW_PADDING + (mRedProgressBarPos - scrollPos) * averagePxMs);
                        params.leftMargin = start;
                        mRedProgressIcon.setLayoutParams(params);
                        //mRangeSeekBarView.play(mRedProgressIcon);

                        //Log.e(TAG, "正在播放:" + mRedProgressBarPos + " left:" + mLeftProgressPos + " end:" + mRightProgressPos);

                        sendEmptyMessageDelayed(MSG_UPDATE, 5);

                        if (mRedProgressBarPos >= mRightProgressPos) {
                            mRedProgressIcon.setVisibility(View.GONE);
                            mRedProgressBarPos = mLeftProgressPos;
                            pauseRedProgressAnimation();
                            onVideoPause();
                            seekTo(mLeftProgressPos);
                            mVideoView.start();
                            playingRedProgressAnimation();
                        }
                    } else {
                        //mRangeSeekBarView.stop();
                        mRedProgressIcon.setVisibility(View.GONE);
                    }

                    break;
            }
        }
    };
    private LinearLayoutManager mgr;

    public VideoTrimmerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoTrimmerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_video_trim, this, true);

        mLinearVideo = findViewById(R.id.layout_surface_view);
        mVideoView = findViewById(R.id.video_loader);
        //mPlayView = findViewById(R.id.icon_video_play);
        mSeekBarLayout = findViewById(R.id.seekBarLayout);
        mRedProgressIcon = findViewById(R.id.positionIcon);
        mVideoShootTipTv = findViewById(R.id.video_shoot_tip);
        mVideoThumbRecyclerView = findViewById(R.id.video_frames_recyclerView);
        mgr = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mVideoThumbRecyclerView.setLayoutManager(mgr);
        mVideoThumbAdapter = new AdapterVideoTrim(mContext);
        mVideoThumbRecyclerView.setAdapter(mVideoThumbAdapter);
        mVideoThumbRecyclerView.addOnScrollListener(mOnScrollListener);
        setUpListeners();
    }

    private void initRangeSeekBarView() {
        if (mRangeSeekBarView != null) return;
        int rangeWidth;//所有缩略图的占用宽度
        mLeftProgressPos = 0;
        if (mDuration <= VideoTrimmerUtil.MAX_SHOOT_DURATION) {
            mThumbsTotalCount = VideoTrimmerUtil.MAX_COUNT_RANGE;
            rangeWidth = mMaxWidth;
            mRightProgressPos = mDuration;
        } else {
            mThumbsTotalCount = (int) (mDuration * 1.0f / (VideoTrimmerUtil.MAX_SHOOT_DURATION * 1.0f) * VideoTrimmerUtil.MAX_COUNT_RANGE);
            /*if (mThumbsTotalCount > 200) {
                mThumbsTotalCount = 200;
            }*/
            rangeWidth = mMaxWidth / VideoTrimmerUtil.MAX_COUNT_RANGE * mThumbsTotalCount;
            mRightProgressPos = VideoTrimmerUtil.MAX_SHOOT_DURATION;
        }
        mVideoThumbRecyclerView.addItemDecoration(new SpacesItemDecoration2(VideoTrimmerUtil.RECYCLER_VIEW_PADDING, mThumbsTotalCount));
        mRangeSeekBarView = new RangeSeekBarView(mContext, mLeftProgressPos, mRightProgressPos);
        mRangeSeekBarView.setSelectedMinValue(mLeftProgressPos);
        mRangeSeekBarView.setSelectedMaxValue(mRightProgressPos);
        mRangeSeekBarView.setStartEndTime(mLeftProgressPos, mRightProgressPos);
        mRangeSeekBarView.setMinShootTime(VideoTrimmerUtil.MIN_SHOOT_DURATION);
        mRangeSeekBarView.setNotifyWhileDragging(true);
        mRangeSeekBarView.setOnRangeSeekBarChangeListener(mOnRangeSeekBarChangeListener);
        mSeekBarLayout.addView(mRangeSeekBarView);

        mAverageMsPx = mDuration * 1.0f / rangeWidth * 1.0f;
        averagePxMs = (mMaxWidth * 1.0f / (mRightProgressPos - mLeftProgressPos));
    }

    public void initVideoByURI(ImageVideoBean video) {
        mVideo = video;
        mSourceUri = Uri.parse(video.getPath());
        mVideoView.setVideoPath(video);
        mVideoView.requestFocus();

        mVideoShootTipTv.setText(String.format(mContext.getResources().getString(R.string.video_shoot_tip), VideoTrimmerUtil.VIDEO_MAX_TIME));
    }

    private void loadVideoThumbs(Context context, Uri videoUri, List<VideoFrame> frames) {
        VideoTrimmerUtil.loadFrames(context, videoUri, frames, new ILoadFrameCallback() {
            @Override
            public void onLoadFrames(List<VideoFrame> frames) {
                UiThreadExecutor.runTask("", new Runnable() {
                    @Override
                    public void run() {
                        mVideoThumbAdapter.notifyDataSetChanged();
                    }
                }, 0L);
            }
        });
    }

    private void startShootVideoThumbs(final Context context, final Uri videoUri, int totalThumbsCount, long startPosition, long endPosition) {
        Log.e("Video", "获取缩略图：" + totalThumbsCount + "，start:" + startPosition + " end:" + endPosition);
        VideoTrimmerUtil.shootVideoThumbInBackground(context, videoUri, totalThumbsCount, startPosition, endPosition,
                new SingleCallback<Bitmap, Integer>() {
                    @Override
                    public void onSingleCallback(final Bitmap bitmap, final Integer interval) {
                        if (bitmap != null) {
                            UiThreadExecutor.runTask("", new Runnable() {
                                @Override
                                public void run() {
                                    //mVideoThumbAdapter.addBitmaps(bitmap);
                                }
                            }, 0L);
                        }
                    }
                });
    }

    private void onCancelClicked() {
        if (mOnTrimVideoListener != null) {
            mOnTrimVideoListener.onCancel();
        }
    }

    private void videoPrepared(IMediaPlayer mp) {
        ViewGroup.LayoutParams lp = mVideoView.getLayoutParams();
        int videoWidth = mVideo.getWidth();//mp.getVideoWidth();
        int videoHeight = mVideo.getHeight();//mp.getVideoHeight();
        Log.i(TAG, "videoprepared:w:" + videoWidth + " h:" + videoWidth);
        Log.i(TAG, "videoprepared:w2:" + mVideo.getWidth() + " h2:" + mVideo.getHeight());

        /*if (mRotate != 0 && mRotate % 90 == 0) {
            Logger.e(TAG,"视频旋转，进行宽高对换");
            int temp = videoWidth;
            videoWidth = videoHeight;
            videoHeight = temp;
        }*/

        float videoProportion = (float) videoWidth / (float) videoHeight;
        int screenWidth = mLinearVideo.getWidth();
        int screenHeight = mLinearVideo.getHeight();

        if (videoHeight > videoWidth) {
            lp.width = screenWidth;
            lp.height = screenHeight;
        } else {
            lp.width = screenWidth;
            float r = videoHeight / (float) videoWidth;
            lp.height = (int) (lp.width * r);
        }

        /*if (mRotate != 0 && mRotate % 90 == 0) {
            int temp = lp.width;
            lp.width = lp.height;
            lp.height = temp;
        }*/

        // mVideoView.setLayoutParams(lp);
        mDuration = mVideoView.getDuration();
        if (!getRestoreState()) {
            seekTo((int) mRedProgressBarPos);
        } else {
            setRestoreState(false);
            seekTo((int) mRedProgressBarPos);
        }
        initRangeSeekBarView();
        mVideoThumbAdapter.setData(mThumbsTotalCount, 0, mDuration);

        mVideoThumbRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                loadVideoThumbs(mContext, mSourceUri, getVisibleFrames());
            }
        });
    }

    private List<VideoFrame> getVisibleFrames() {
        int from = mgr.findFirstVisibleItemPosition();
        int to = mgr.findLastVisibleItemPosition();

        return mVideoThumbAdapter.getSubFrames(from, to);
    }


    private void videoCompleted() {
        seekTo(mLeftProgressPos);
        //setPlayPauseViewIcon(false);
    }

   private void onVideoReset() {
        mVideoView.pause();
        //setPlayPauseViewIcon(false);
    }

    private void playVideoOrPause() {
        mRedProgressBarPos = mVideoView.getCurrentPosition();
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
            pauseRedProgressAnimation();
        } else {
            mVideoView.start();
            playingRedProgressAnimation();
        }
        //setPlayPauseViewIcon(mVideoView.isPlaying());
    }

    public void onVideoPause() {
        if (mVideoView.isPlaying()) {
            seekTo(mLeftProgressPos);//复位
            mVideoView.pause();
            //setPlayPauseViewIcon(false);
            mRedProgressIcon.setVisibility(GONE);
        }
    }

    public void setOnTrimVideoListener(VideoTrimListener onTrimVideoListener) {
        mOnTrimVideoListener = onTrimVideoListener;
    }

    private void setUpListeners() {
        findViewById(R.id.cancelBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClicked();
            }
        });

        findViewById(R.id.finishBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveClicked();
            }
        });
        mVideoView.setListener(new VideoPlayerListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                super.onPrepared(iMediaPlayer);
                videoPrepared(iMediaPlayer);
                checkPlayAnim();
            }

            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                videoPrepared(iMediaPlayer);
                return super.onError(iMediaPlayer, i, i1);
            }

            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                super.onCompletion(iMediaPlayer);
                videoCompleted();
                checkPlayAnim();
            }

            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
                if (what == IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED) {
                    mRotate = extra;
                }
                return super.onInfo(iMediaPlayer, what, extra);
            }
        });
    }

    private void onSaveClicked() {
        if (mRightProgressPos - mLeftProgressPos < VideoTrimmerUtil.MIN_SHOOT_DURATION) {
            Toast.makeText(mContext, "视频长不足3秒,无法上传", Toast.LENGTH_SHORT).show();
        } else {
            mVideoView.pause();
            File dir = DiskConfig.SaveDir.getCompressDir();
            if (dir == null) {
                return;
            }
            String path = mVideo.getPath();

            File file = new File(path);
            String suffix = ".mp4";
            int idx = file.getName().lastIndexOf(".");
            if (idx > 0) {
                suffix = file.getName().substring(idx);
                Log.i(TAG, "视频后缀:" + suffix);
            }

            dir.mkdirs();

            File outputFile = new File(dir, UUID.randomUUID().toString() + suffix);
            Log.i(TAG, "裁剪输出视频:" + outputFile);

            VideoTrimmerUtil.trim(mContext,
                    mSourceUri.getPath(),
                    outputFile.getAbsolutePath(),
                    mLeftProgressPos,
                    mRightProgressPos,
                    mOnTrimVideoListener);
        }
    }

    private void seekTo(long msec) {
        mVideoView.seekTo((int) msec);
        Log.e(TAG, "seekTo = " + msec + " / video pos:" + mVideoView.getCurrentPosition());
        mRedProgressBarPos = msec;
    }

    private boolean getRestoreState() {
        return isFromRestore;
    }

    public void setRestoreState(boolean fromRestore) {
        isFromRestore = fromRestore;
    }

    private final RangeSeekBarView.OnRangeSeekBarChangeListener mOnRangeSeekBarChangeListener = new RangeSeekBarView.OnRangeSeekBarChangeListener() {
        @Override
        public void onRangeSeekBarValuesChanged(RangeSeekBarView bar, long minValue, long maxValue, int action, boolean isMin,
                                                RangeSeekBarView.Thumb pressedThumb) {
            Log.d(TAG, "-----minValue----->>>>>>" + minValue);
            Log.d(TAG, "-----maxValue----->>>>>>" + maxValue);
            mLeftProgressPos = minValue + scrollPos;
            mRedProgressBarPos = mLeftProgressPos;
            mRightProgressPos = maxValue + scrollPos;
            Log.d(TAG, "-----mLeftProgressPos----->>>>>>" + mLeftProgressPos);
            Log.d(TAG, "-----mRightProgressPos----->>>>>>" + mRightProgressPos);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    isSeeking = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    isSeeking = true;
                    seekTo((int) (pressedThumb == RangeSeekBarView.Thumb.MIN ? mLeftProgressPos : mRightProgressPos));
                    break;
                case MotionEvent.ACTION_UP:
                    isSeeking = false;
                    seekTo((int) mLeftProgressPos);
                    mRedProgressBarPos = mLeftProgressPos;
                    mVideoView.start();
                    playingRedProgressAnimation();
                    break;
                default:
                    break;
            }

            mRangeSeekBarView.setStartEndTime(mLeftProgressPos, mRightProgressPos);
        }
    };

    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.d(TAG, "newState = " + newState);
            if (newState == SCROLL_STATE_IDLE) {
                loadVideoThumbs(mContext, mSourceUri, getVisibleFrames());

                mRedProgressBarPos = mLeftProgressPos;
                mVideoView.start();
                playingRedProgressAnimation();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            isSeeking = false;
            int scrollX = calcScrollXDistance();
            //达不到滑动的距离
            if (Math.abs(lastScrollX - scrollX) < mScaledTouchSlop) {
                isOverScaledTouchSlop = false;
                return;
            }
            isOverScaledTouchSlop = true;
            //初始状态,why ? 因为默认的时候有35dp的空白！
            if (scrollX == -VideoTrimmerUtil.RECYCLER_VIEW_PADDING) {
                scrollPos = 0;
            } else {
                isSeeking = true;
                scrollPos = (long) (mAverageMsPx * (VideoTrimmerUtil.RECYCLER_VIEW_PADDING + scrollX));
                mLeftProgressPos = mRangeSeekBarView.getSelectedMinValue() + scrollPos;
                mRightProgressPos = mRangeSeekBarView.getSelectedMaxValue() + scrollPos;
                Log.d(TAG, "onScrolled >>>> mLeftProgressPos = " + mLeftProgressPos);
                mRedProgressBarPos = mLeftProgressPos;
                if (mVideoView.isPlaying()) {
                    //mVideoView.pause();
                    //setPlayPauseViewIcon(false);
                    onVideoPause();
                }
                mRedProgressIcon.setVisibility(GONE);
                seekTo(mLeftProgressPos);
                mRangeSeekBarView.setStartEndTime(mLeftProgressPos, mRightProgressPos);
                mRangeSeekBarView.invalidate();
            }
            lastScrollX = scrollX;
        }
    };

    /**
     * 水平滑动了多少px
     */
    private int calcScrollXDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mVideoThumbRecyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = layoutManager.findViewByPosition(position);
        if (firstVisibleChildView == null)
            return 0;
        int itemWidth = firstVisibleChildView.getWidth();
        return (position) * itemWidth - firstVisibleChildView.getLeft();
    }

    private void playingRedProgressAnimation() {
        pauseRedProgressAnimation();
        checkPlayAnim();
    }

    private void checkPlayAnim() {
        mUIHandler.removeMessages(MSG_UPDATE);
        mUIHandler.sendEmptyMessage(MSG_UPDATE);
    }

    private void pauseRedProgressAnimation() {
        mRedProgressIcon.clearAnimation();
        if (mRedProgressAnimator != null && mRedProgressAnimator.isRunning()) {
            //mAnimationHandler.removeCallbacks(mAnimationRunnable);
            mRedProgressAnimator.cancel();
        }
        checkPlayAnim();
    }


    /**
     * Cancel trim thread execut action when finish
     */
    @Override
    public void onDestroy() {
        BackgroundExecutor.cancelAll("", true);
        UiThreadExecutor.cancelAll("");
        mVideoThumbAdapter.release();
        mUIHandler.removeCallbacksAndMessages(null);
    }
}
