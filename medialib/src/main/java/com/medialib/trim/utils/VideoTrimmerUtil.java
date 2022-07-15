package com.medialib.trim.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.arch.core.executor.TaskExecutor;

import com.medialib.DiskConfig;
import com.medialib.R;
import com.medialib.trim.VideoFrame;
import com.medialib.trim.callback.ILoadFrameCallback;
import com.medialib.trim.callback.VideoTrimListener;
import com.medialib.video_trim.SizeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.microshow.rxffmpeg.RxFFmpegInvoke;


//	maven { url 'https://www.jitpack.io' }
//            implementation 'com.github.microshow:RxFFmpeg:2.2.0'

//-dontwarn io.microshow.rxffmpeg.**
// -keep class io.microshow.rxffmpeg.**{*;}
public class VideoTrimmerUtil {

    private static final String TAG = VideoTrimmerUtil.class.getSimpleName();
    public static final long MIN_SHOOT_DURATION = 5000L;// 最小剪辑时间3s
    public static final int VIDEO_MAX_TIME = 15;// 10秒
    public static final long MAX_SHOOT_DURATION = VIDEO_MAX_TIME * 1000L;//视频最多剪切多长时间10s

    public static final int MAX_COUNT_RANGE = 10;  //seekBar的区域内一共有多少张图片


    private static int SCREEN_WIDTH_FULL = 640;
    public static int RECYCLER_VIEW_PADDING = 70;
    private static int THUMB_HEIGHT = 100;


    //    private static final int SCREEN_WIDTH_FULL = SizeUtils.getScreenWidth();
//    public static final int RECYCLER_VIEW_PADDING = SizeUtils.dip2px(35);
//    private static final int THUMB_HEIGHT = SizeUtils.dip2px(50);
    public static int VIDEO_FRAMES_WIDTH = SCREEN_WIDTH_FULL - RECYCLER_VIEW_PADDING * 2;
    private static int THUMB_WIDTH = (SCREEN_WIDTH_FULL - RECYCLER_VIEW_PADDING * 2) / VIDEO_MAX_TIME;

    public static void initValue(Context context) {
        SCREEN_WIDTH_FULL = SizeUtils.getScreenWidth(context);
        RECYCLER_VIEW_PADDING = SizeUtils.dip2px(context, 35);
        THUMB_HEIGHT = SizeUtils.dip2px(context, 50);
        VIDEO_FRAMES_WIDTH = SCREEN_WIDTH_FULL - RECYCLER_VIEW_PADDING * 2;
        THUMB_WIDTH = (SCREEN_WIDTH_FULL - RECYCLER_VIEW_PADDING * 2) / VIDEO_MAX_TIME;

    }


    public static void trim(Context context, String inputFile,
                            final String outputFile, long startMs, long endMs, final VideoTrimListener callback) {
        String start = convertSecondsToTime(startMs / 1000);
        String duration = convertSecondsToTime((endMs - startMs) / 1000);
        //String start = String.valueOf(startMs);
        //String duration = String.valueOf(endMs - startMs);
        SCREEN_WIDTH_FULL = SizeUtils.getScreenWidth(context);
        if (callback != null) {
            callback.onStartTrim();
        }



        new AsyncTask<String,Integer,String>(){
            @Override
            protected String doInBackground(String... strings) {
                File source = new File(inputFile);
                File target = new File(outputFile);
                File dir = DiskConfig.SaveDir.getCompressDir();
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
                        return null;
                    }
                }

                /** 裁剪视频ffmpeg指令说明：
                 * ffmpeg -ss START -t DURATION -i INPUT -codec copy -avoid_negative_ts 1 OUTPUT
                 -ss 开始时间，如： 00:00:20，表示从20秒开始；
                 -t 时长，如： 00:00:10，表示截取10秒长的视频；
                 -i 输入，后面是空格，紧跟着就是输入视频文件；
                 -codec copy -avoid_negative_ts 1 表示所要使用的视频和音频的编码格式，这里指定为copy表示原样拷贝；
                 INPUT，输入视频文件；
                 OUTPUT，输出视频文件
                 */
                //TODO: Here are some instructions
                //https://trac.ffmpeg.org/wiki/Seeking
                //https://superuser.com/questions/138331/using-ffmpeg-to-cut-up-video

                //String cmd = "-ss " + start + " -t " + duration + " -accurate_seek" + " -i " + inputFile + " -codec copy -avoid_negative_ts 1 " + outputFile;
                //String cmd = "-ss " + start + " -i " + inputFile + " -ss " + start + " -t " + duration + " -vcodec copy " + outputFile;
                //{"ffmpeg", "-ss", "" + startTime, "-y", "-i", inputFile, "-t", "" + induration, "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", outputFile}
                //String cmd = "-ss " + start + " -y " + "-i " + inputFile + " -t " + duration + " -vcodec " + "mpeg4 " + "-b:v " + "2097152 " + "-b:a " + "48000 " + "-ac " + "2 " + "-ar " + "22050 "+ outputFile;

                //String cmd = String.format("ffmpeg -y -ss %s -t %s -i %s %s", start, duration, inputFile, outputFile);
                String cmd = String.format("ffmpeg -y -ss %s -t %s -accurate_seek -i %s -codec copy -avoid_negative_ts 1 %s", start, duration, source.getAbsolutePath(), target.getAbsolutePath());
                //String cmd = String.format("ffmpeg -i %s -vcodec h264 -preset fast -b:v 2000k %s",  inputFile, outputFile);error
                String[] command = cmd.split(" ");

                RxFFmpegInvoke.getInstance().runCommand(command, null);

                if (csource != null) {
                    csource.delete();
                }

                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (outputFile != null && new File(outputFile).exists()) {
                    if (callback != null) {
                        callback.onFinishTrim(endMs - startMs, outputFile);
                    }
                } else {
                    if (callback != null) {
                        callback.onError(context.getString(R.string.tip_trim_error));
                    }
                }
            }
        }.execute();



//        new TaskExecutor() {
//           @Override
//            public Void doThreadWork(Void aVoid) {
//                File source = new File(inputFile);
//                File target = new File(outputFile);
//                File dir = DiskConfig.SaveDir.getCompressDir();
//                File csource = null;
//                if (source.getAbsolutePath().endsWith(".3gp")) {
//                    csource = new File(dir, source.getName().replace(".3gp", "_convert.mp4"));
//                    String cmd = String.format("ffmpeg -y -i %s -f mp4 -preset superfast %s",
//                            source.getAbsolutePath(), csource.getAbsolutePath());
//                    String[] commands = cmd.split(" ");
//                    int res = RxFFmpegInvoke.getInstance().runCommand(commands, null);
//                    Log.i(TAG, "视频格式转换结果:" + res + " / " + csource.exists() + " / " + csource.getAbsolutePath());
//                    if (csource.exists()) {
//                        source = csource;
//                        Log.i(TAG, "格式转换成功，原视频改为:" + source.getAbsolutePath());
//                    } else {
//                        Log.i(TAG, "无法转换格式,忽略压缩>" + source.getAbsolutePath());
//                        return null;
//                    }
//                }
//
//                /** 裁剪视频ffmpeg指令说明：
//                 * ffmpeg -ss START -t DURATION -i INPUT -codec copy -avoid_negative_ts 1 OUTPUT
//                 -ss 开始时间，如： 00:00:20，表示从20秒开始；
//                 -t 时长，如： 00:00:10，表示截取10秒长的视频；
//                 -i 输入，后面是空格，紧跟着就是输入视频文件；
//                 -codec copy -avoid_negative_ts 1 表示所要使用的视频和音频的编码格式，这里指定为copy表示原样拷贝；
//                 INPUT，输入视频文件；
//                 OUTPUT，输出视频文件
//                 */
//                //TODO: Here are some instructions
//                //https://trac.ffmpeg.org/wiki/Seeking
//                //https://superuser.com/questions/138331/using-ffmpeg-to-cut-up-video
//
//                //String cmd = "-ss " + start + " -t " + duration + " -accurate_seek" + " -i " + inputFile + " -codec copy -avoid_negative_ts 1 " + outputFile;
//                //String cmd = "-ss " + start + " -i " + inputFile + " -ss " + start + " -t " + duration + " -vcodec copy " + outputFile;
//                //{"ffmpeg", "-ss", "" + startTime, "-y", "-i", inputFile, "-t", "" + induration, "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", outputFile}
//                //String cmd = "-ss " + start + " -y " + "-i " + inputFile + " -t " + duration + " -vcodec " + "mpeg4 " + "-b:v " + "2097152 " + "-b:a " + "48000 " + "-ac " + "2 " + "-ar " + "22050 "+ outputFile;
//
//                //String cmd = String.format("ffmpeg -y -ss %s -t %s -i %s %s", start, duration, inputFile, outputFile);
//                String cmd = String.format("ffmpeg -y -ss %s -t %s -accurate_seek -i %s -codec copy -avoid_negative_ts 1 %s", start, duration, source.getAbsolutePath(), target.getAbsolutePath());
//                //String cmd = String.format("ffmpeg -i %s -vcodec h264 -preset fast -b:v 2000k %s",  inputFile, outputFile);error
//                String[] command = cmd.split(" ");
//
//                RxFFmpegInvoke.getInstance().runCommand(command, null);
//
//                if (csource != null) {
//                    csource.delete();
//                }
//                return null;
//            }
//
//            @Override
//            public void onCompleteTask(Void aVoid) {
//                super.onCompleteTask(aVoid);
//                if (outputFile != null && new File(outputFile).exists()) {
//                    if (callback != null) {
//                        callback.onFinishTrim(endMs - startMs, outputFile);
//                    }
//                } else {
//                    if (callback != null) {
//                        callback.onError(context.getString(R.string.tip_trim_error));
//                    }
//                }
//            }
//        }.execute();
    }

    public static void loadFrames(Context context, Uri videoUri, List<VideoFrame> fs, ILoadFrameCallback callback) {
        if (fs == null || fs.size() <= 0) return;
        BackgroundExecutor.execute(new BackgroundExecutor.Task("" + fs.get(0).getTimestamp(), 0L, "") {
            @Override
            public void execute() {
                List<VideoFrame> frames = new ArrayList<>(fs);
                try {
                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(context, videoUri);
                    // Retrieve media data use microsecond
                    for (VideoFrame frame : frames) {
                        if (frame.getBitmap() == null && frame.getStatus() != VideoFrame.STATUS_LOADING) {
                            frame.setStatus(VideoFrame.STATUS_LOADING);

                            Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(frame.getTimestamp(),
                                    MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                            if (bitmap == null) {
                                frame.setStatus(VideoFrame.STATUS_ERROR);
                                continue;
                            }
                            try {
                                bitmap = ImageUtils.resizeImage(bitmap, THUMB_WIDTH);//Bitmap.createScaledBitmap(bitmap, THUMB_WIDTH, THUMB_HEIGHT, false);
                                frame.setBitmap(bitmap);
                                frame.setStatus(VideoFrame.STATUS_SUCCESS);
                            } catch (final Throwable t) {
                                t.printStackTrace();
                                frame.setStatus(VideoFrame.STATUS_ERROR);
                            }
                        }
                    }
                    mediaMetadataRetriever.release();
                } catch (final Throwable e) {
                    //Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                    e.printStackTrace();
                } finally {
                    if (callback != null) {
                        callback.onLoadFrames(frames);
                    }
                }
            }
        });
    }


    public static void shootVideoThumbInBackground(final Context context, final Uri videoUri, final int totalThumbsCount, final long startPosition,
                                                   final long endPosition, final SingleCallback<Bitmap, Integer> callback) {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0L, "") {
            @Override
            public void execute() {
                try {
                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(context, videoUri);
                    // Retrieve media data use microsecond
                    long interval = (endPosition - startPosition) / (totalThumbsCount - 1);
                    for (long i = 0; i < totalThumbsCount; ++i) {
                        long frameTime = startPosition + interval * i;
                        Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(frameTime * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                        if (bitmap == null) continue;
                        try {
                            bitmap = Bitmap.createScaledBitmap(bitmap, THUMB_WIDTH, THUMB_HEIGHT, false);
                        } catch (final Throwable t) {
                            t.printStackTrace();
                        }
                        callback.onSingleCallback(bitmap, (int) interval);
                    }
                    mediaMetadataRetriever.release();
                } catch (final Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        });
    }

    public static String getVideoFilePath(String url) {
        if (TextUtils.isEmpty(url) || url.length() < 5) return "";
        if (url.substring(0, 4).equalsIgnoreCase("http")) {

        } else {
            url = "file://" + url;
        }

        return url;
    }

    private static String convertSecondsToTime(long seconds) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (seconds <= 0) {
            return "00:00";
        } else {
            minute = (int) seconds / 60;
            if (minute < 60) {
                second = (int) seconds % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) return "99:59:59";
                minute = minute % 60;
                second = (int) (seconds - hour * 3600 - minute * 60);
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }


    public static void pushLiveVideo(){
       String liveAddr="ffmpeg -re -i {input-source} -vcodec libx264 -vprofile baseline -acodec aac -ar 44100 -strict -2 -ac 1 -f flv -s 1280x720 -q 10 rtmp://localhost:1935/live/test\n";
        String liveAddr2="ffmpeg -i {input-source} -f flv -r 25 -s 1280*720 -an rtmp://localhost:1935/live/test";
        String liveAddr3="ffmpeg -re -i demo.wmv -f flv rtmp://127.0.0.1:1935/live/123";
        String live4="ffmpeg -f dshow -i video='USB2.0 PC CAMERA' -vcodec libx264 -preset:v ultrafast -tune:v zerolatency -f flv rtmp://127.0.0.1:1935/live/123";
    }
    public void camera(){
//        Camera.Parameters parameters = camera.getParameters();
//        //对拍照参数进行设置
//        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
//            LogUtils.d(size.width + "  " + size.height);
//        }
//        注意这段打印出来的宽高，后来设置Camera拍摄的图片大小配置必须是里面的一组，否则无法获取Camera的回调数据，这个很关键
    }

    public static void pushVideo(String locadPath,String tagPath){
//        String cmd=  "ffmpeg -re -i /Users/jack/test.mp4 -vcodec libx264 -acodec aac -f flv rtmp://localhost:1935/rtmplive/home";
        locadPath=Environment.getExternalStorageDirectory().getPath() + File.separator + "cameradir/abcd.mp4";
        tagPath="rtmp://192.168.2.160:1935/live/address";
//        String cmd = String.format("ffmpeg -y -i %s -f mp4 -preset superfast %s",
        String cmd = String.format("ffmpeg -re -i %s -vcodec libx264 -acodec aac -f flv %s",locadPath, tagPath);
        String[] commands = cmd.split(" ");
        int res = RxFFmpegInvoke.getInstance().runCommand(commands, new RxFFmpegInvoke.IFFmpegListener() {
            @Override
            public void onFinish() {
                Log.i("znh","push onFinish onFinish");
            }
            @Override
            public void onProgress(int progress, long progressTime) {
//                Log.i(TAG, "视频格式转换结果:" + res + " / " + csource.exists() + " / " + csource.getAbsolutePath());
                Log.i("znh","push start"+progress);
            }
            @Override
            public void onCancel() {
                Log.i("znh","push onCancel");
            }
            @Override
            public void onError(String message) {
                Log.i("znh","push onCancel"+message);
            }
        });
    }
}