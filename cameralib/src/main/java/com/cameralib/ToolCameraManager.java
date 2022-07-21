package com.cameralib;

import android.graphics.Bitmap;

//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.ffmpeg.global.avutil;
//import org.bytedeco.javacv.CanvasFrame;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.FrameGrabber;
//import org.bytedeco.javacv.OpenCVFrameGrabber;

public class ToolCameraManager {
    private ToolCameraManager() {
    }

    private static volatile ToolCameraManager instance;

    public static ToolCameraManager getInstance() {
        if (instance == null) {
            synchronized (ToolCameraManager.class) {
                if (instance == null) {
                    instance = new ToolCameraManager();
                }
            }
        }
        return instance;
    }

    private Bitmap takeBm;

    public void setCameraBitmap(Bitmap bm) {
        this.takeBm = bm;
    }

    public Bitmap getTakeBm() {
        return takeBm;
    }


//    ###############################################################################

//    int captureWidth = 1280;
//    int captureHeight = 720;
//    String format = "flv";
//    final private static int GOP_LENGTH_IN_FRAMES = 60;
//    final private static int FRAME_RATE = 30;
//    int videoBitrate = 2000000;
//    int audioChannels = 2;
//    int simpleRate = 44100;
//    double videoQuality = -1;
//    int WEBCAM_DEVICE_INDEX = 1;
//    long startTime=0;
//    long videoTS=0;
//    public void pushVideo(String rtmpVideo) throws FFmpegFrameRecorder.Exception, FrameGrabber.Exception {
//        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(rtmpVideo, captureWidth, captureHeight, 0);
//        recorder.setVideoCodecName("H264");//优先级高于videoCodec
//        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);//只有在videoCodecName没有设置或者没有找到videoCodecName的情况下才会使用videoCodec
//        recorder.setFormat(format);//只支持flv，mp4，3gp和avi四种格式，flv:AV_CODEC_ID_FLV1;mp4:AV_CODEC_ID_MPEG4;3gp:AV_CODEC_ID_H263;avi:AV_CODEC_ID_HUFFYUV;
////        recorder.setPixelFormat(pixelFormat);// 只有pixelFormat，width，height三个参数任意一个不为空才会进行像素格式转换
////        recorder.setImageScalingFlags(imageScalingFlags);//缩放，像素格式转换时使用，但是并不是像素格式转换的判断参数之一
//        recorder.setGopSize(GOP_LENGTH_IN_FRAMES);//gop间隔
//        recorder.setFrameRate(FRAME_RATE);//帧率
//        recorder.setVideoBitrate(videoBitrate);
//        recorder.setVideoQuality(videoQuality);
////下面这三个参数任意一个会触发音频编码
//        recorder.setSampleFormat(avutil.AV_SAMPLE_FMT_NONE);//音频采样格式,使用avutil中的像素格式常量，例如：avutil.AV_SAMPLE_FMT_NONE
//        recorder.setAudioChannels(audioChannels);//声道
//        recorder.setSampleRate(simpleRate);
//        recorder.start();
//
//        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(WEBCAM_DEVICE_INDEX);
//        grabber.setImageWidth(captureWidth);
//        grabber.setImageHeight(captureHeight);
//        grabber.start();
//
////      CanvasFrame cFrame = new CanvasFrame("Capture Preview", CanvasFrame.getDefaultGamma() / grabber.getGamma());
//        Frame capturedFrame = null;
//        // While we are capturing...
//        while ((capturedFrame = grabber.grab()) != null) {
////            if (cFrame.isVisible()) {
////                // Show our frame in the preview
////                cFrame.showImage(capturedFrame);
////            }
//            // Let's define our start time...
//            // This needs to be initialized as close to when we'll use it as
//            // possible,
//            // as the delta from assignment to computed time could be too high
//            if (startTime == 0)
//                startTime = System.currentTimeMillis();
//
//            // Create timestamp for this frame
//            videoTS = 1000 * (System.currentTimeMillis() - startTime);
//
//            // Check for AV drift
//            if (videoTS > recorder.getTimestamp()) {
//                System.out.println(
//                        "Lip-flap correction:"
//                                + videoTS + " :"
//                                + recorder.getTimestamp() + " -> "
//                                + (videoTS - recorder.getTimestamp()));
//                // We tell the recorder to write this frame at this timestamp
//                recorder.setTimestamp(videoTS);
//            }
//            // Send the frame to the org.bytedeco.javacv.FFmpegFrameRecorder
//            recorder.record(capturedFrame);
//        }
////        cFrame.dispose();
//        recorder.stop();
//    }

}
