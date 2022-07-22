package com.cameralib.camera;

import android.graphics.ImageFormat;
import android.hardware.Camera;

import java.util.List;

public class CameraUtil {
    private static CameraUtil mInstance;
    /**
     * 相机参数对象
     */
    private Camera.Parameters mParameters;
    /**
     * 闪光灯自动
     */
    public static final int FLASH_AUTO = 0;
    /**
     * 闪光灯关闭
     */
    public static final int FLASH_OFF = 1;
    /**
     * 闪光灯开启
     */
    public static final int FLASH_ON = 2;

    private CameraUtil() {
    }

    private static final Object o = new Object();

    public static CameraUtil getInstance() {
        if (mInstance == null) {
            synchronized (o) {
                if (mInstance == null) {
                    mInstance = new CameraUtil();
                }
            }
        }
        return mInstance;
    }

    private Camera mCamera;

    /**
     * 默认开启后摄像头
     *
     * @return
     */
    public Camera openCamera() {
        // 0 表示开启后置相机
        return openCamera(0);
    }


    /**
     * 开启摄像头
     * Camera.CameraInfo.CAMERA_FACING_BACK
     * Camera.CameraInfo.CAMERA_FACING_FRONT
     *
     * @return
     */
    public Camera openCamera(int id) {
        if (mCamera == null) {
            mCamera = Camera.open(id);
        }
        setProperty();
        return mCamera;
    }

    private Camera.Size cutCameraSize;

    /**
     * 相机属性设置
     */
    private void setProperty() {
        cutCameraSize = mCamera.new Size(1280, 720);
        //设置相机预览页面旋转90°，（默认是横屏）
        mCamera.setDisplayOrientation(90);
        mParameters = mCamera.getParameters();
        //设置将保存的图片旋转90°（竖着拍摄的时候）

        List<Camera.Size> previewSize = getPreviewSizeList();
//        计算最大视频比例
        for (Camera.Size size : previewSize) {
            if (cutCameraSize.width < size.width) {
                cutCameraSize.width = size.width;
                cutCameraSize.height = size.height;
            }
        }
        mParameters.setRotation(90);
        mParameters.setPreviewSize(cutCameraSize.width, cutCameraSize.height);
        mParameters.setPictureSize(cutCameraSize.width, cutCameraSize.height);
//        mParameters.setPreviewSize(1920, 1080);
//        mParameters.setPictureSize(1920, 1080);
//        mParameters.setPictureSize(4608, 3456);
        mParameters.setPictureFormat(ImageFormat.JPEG);
        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//        mParameters.set(ImageFormat.YUV_444_888);
        mCamera.setParameters(mParameters);
    }

    /**
     * 选装图片的角度
     */
    public void setRotateDegree(int degree) {
//        boolean result = degree == 0 || degree == 90
//        mParameters.setRotation(90);
        if (mCamera != null) {
            mParameters = mCamera.getParameters();
            mParameters.setRotation(degree);
            mCamera.setParameters(mParameters);
        }

    }

    /**
     * 获取支持的预览分辨率
     */
    public List<Camera.Size> getPreviewSizeList() {
        if (mCamera == null) {
            throw new NullPointerException("Camera can not be null");
        }
        return mCamera.getParameters().getSupportedPreviewSizes();
    }

    /**
     * 获取保存图片支持的分辨率
     */
    public List<Camera.Size> getPictureSizeList() {
        if (mCamera == null) {
            throw new NullPointerException("Camera can not be null");
        }
        return mCamera.getParameters().getSupportedPictureSizes();
    }

    /**
     * 设置闪光灯模式
     */
    public void setFlashMode(int mode) {
        mParameters = mCamera.getParameters();
        String flashMode = mParameters.getFlashMode();
        switch (mode) {
            case FLASH_AUTO:
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                break;
            case FLASH_OFF:
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                break;
            case FLASH_ON:
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                break;
            default:
                break;
        }
        mCamera.setParameters(mParameters);
    }

    /**
     * 释放相机资源
     */
    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
            isRelease = true;
        }
    }

    /**
     * 是否旋转图片 true 选装
     */
    private boolean isRelease = false;

    public boolean getIsRelease() {
        return isRelease;
    }

    /**
     * 设置保存图片的分辨率
     */
    public void setSaveSize(Camera.Size saveSize) {
        mParameters.setPictureSize(saveSize.width, saveSize.height);
        mCamera.setParameters(mParameters);
    }
}