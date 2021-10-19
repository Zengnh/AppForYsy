package com.photolib.selectpic;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.photolib.ToolThreadPool;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ToolLocationImage {
    private ToolLocationImage() {
    }
    private static ToolLocationImage instance;

    public static ToolLocationImage getInstance() {
        if (instance == null) {
            instance = new ToolLocationImage();
        }
        return instance;
    }

    private static final String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT,};

    private static final String[] VIDEO_PROJECTION = new String[]{
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.DATE_TAKEN,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT,
    };

    private Activity loadAct;
    private boolean checkImg = true;
    private boolean checkVideo = false;
    private int selectCount = 1;

    public int limitSelect() {
        return selectCount;
    }

    public void setFloag(boolean checkImg, boolean checkVideo, int selectCount) {
        this.checkImg = checkImg;
        this.checkVideo = checkVideo;
        this.selectCount = selectCount;
        this.isFromChag = false;
    }

    private InterLoadImg interLoadImg;

    private ArrayList<ImageBean> mAllVideos = new ArrayList<>();//所有视频
    private ArrayList<ImageBean> mAllImages = new ArrayList<>();//所有图片
    private Handler handlerLoad = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (interLoadImg != null) {
                interLoadImg.loadResult();
            }
        }
    };

    public void load(Activity act) {
        this.loadAct = act;
        ToolThreadPool.getInstance().exeRunable(new Runnable() {
            @Override
            public void run() {
                mAll.clear();
                mAllImages.clear();
                mAllVideos.clear();
                if (checkImg) {
                    // 过滤掉小于10K的图片
                    ContentResolver cr = loadAct.getContentResolver();
                    Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            , IMAGE_PROJECTION, IMAGE_PROJECTION[4] + ">10240 AND " + IMAGE_PROJECTION[1] + "=? OR " + IMAGE_PROJECTION[1] + "=? ",
                            new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[5] + " DESC");
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            ImageBean t = parseImage(cursor);
                            mAllImages.add(t);
                        }
                        cursor.close();
                    }
                }
                if (checkVideo) {
                    ContentResolver cr = loadAct.getContentResolver();
                    Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            , VIDEO_PROJECTION, VIDEO_PROJECTION[4] + ">0", null, VIDEO_PROJECTION[5] + " DESC");
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            ImageBean t = parseVideo(cursor);
                            File file = new File(t.getPath());
//                            Log.i("znh_video","@@@@@"+file.length());
                            if (file.exists() && file.length() < 50 * 1024 * 1024) {
                                mAllVideos.add(t);
                            }

                        }
                        cursor.close();
                    }
                }


                mAll.addAll(mAllImages);
                mAll.addAll(mAllVideos);
                if (mAll.size() > 0) {
                    Collections.sort(mAll, new Comparator<ImageBean>() {
                        @Override
                        public int compare(ImageBean o1, ImageBean o2) {
                            if (o1.getDateToken() > o2.getDateToken()) {
                                return -1;
                            } else if (o1.getDateToken() < o2.getDateToken()) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }


                //需要重新进行排序
                if (mAllImages.size() > 0)
                    Collections.sort(mAllImages, new Comparator<ImageBean>() {
                        @Override
                        public int compare(ImageBean o1, ImageBean o2) {
                            if (o1.getDateToken() > o2.getDateToken()) {
                                return -1;
                            } else if (o1.getDateToken() < o2.getDateToken()) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
                //需要重新进行排序
                if (mAllVideos.size() > 0)
                    Collections.sort(mAllVideos, new Comparator<ImageBean>() {
                        @Override
                        public int compare(ImageBean o1, ImageBean o2) {
                            if (o1.getDateToken() > o2.getDateToken()) {
                                return -1;
                            } else if (o1.getDateToken() < o2.getDateToken()) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
                handlerLoad.sendEmptyMessage(0);
            }
        });
    }


    public ArrayList<ImageBean> getAllImg() {
        return mAllImages;
    }

    public ArrayList<ImageBean> getAllVideo() {
        return mAllVideos;
    }

    private ArrayList<ImageBean> mAll = new ArrayList<>();//所有视频

    public ArrayList<ImageBean> getAllImgVideo() {
        return mAll;
    }

    public ArrayList<ImageBean> getPicked() {
        ArrayList<ImageBean> select = new ArrayList<ImageBean>();
        for (int i = 0; i < mAll.size(); i++) {
            if (mAll.get(i).isSelect) {
                mAll.get(i).postion = i;
                select.add(mAll.get(i));
            }
        }
        return select;
    }


    private ImageBean parseVideo(Cursor cursor) {
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        String mime = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
        Integer folderId = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));
        String folderName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
        long dateToken = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN));

        ImageBean video = new ImageBean();
        video.setPath(path);
        video.setMime(mime);
        video.setFolderId(folderId);
        video.setFolderName(folderName);
        video.setDuration(duration);
        video.setDateToken(dateToken);
        video.setWidth(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.WIDTH)));
        video.setHeight(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.HEIGHT)));
        return video;
    }

    private ImageBean parseImage(Cursor cursor) {
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        String mime = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
        Integer folderId = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
        String folderName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
        long dateToken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));

        ImageBean image = new ImageBean();
        image.setPath(path);
        image.setMime(mime);
        image.setFolderId(folderId);
        image.setFolderName(folderName);
        image.setSize(size);
        image.setDateToken(dateToken);
        image.setWidth(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH)));
        image.setHeight(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT)));
        return image;
    }

    public void setLoadListener(InterLoadImg interLoadImg) {
        this.interLoadImg = interLoadImg;
    }

    public void setTakePhoto(ImageBean bean) {
        mAll.add(0, bean);
        mAllImages.add(0, bean);
    }

    public void setIsFromChat(boolean t) {
        isFromChag = t;
    }

    private boolean isFromChag = false;

    public boolean getFromChat() {
        return isFromChag;
    }

    public ImageBean getCamreaResult() {
        return imageview;
    }

    private ImageBean imageview;

    public void setCameraResult(ImageBean imageview) {
        this.imageview = imageview;
    }

}
