package com.medialib;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 本地相册图片/视频实体类
 **/
public class ImageVideoBean implements Parcelable {
    public boolean isSelect = false;
    public boolean isplay = false;
    public boolean isTrimmedVideo;//是否是裁剪处理后的图片
    public boolean hasCompressed=false;
    public String compressPath;//被压缩的图片或视频本地路径
    private String path;
    private String mime;
    private String videoThumbnail;
    private Integer folderId;
    private String folderName;
    private long duration;
    private long size;
    private long dateToken;

    private int width;
    private int height;

    // ---------以下3参数用于配合上传 -------------- //
    private String uploadKey;
    private int status = 0;
    private String serverId;

    public int postion = 0;

    public ImageVideoBean() {
    }

    protected ImageVideoBean(Parcel in) {
        path = in.readString();
        mime = in.readString();
        if (in.readByte() == 0) {
            folderId = null;
        } else {
            folderId = in.readInt();
        }
        folderName = in.readString();
        duration = in.readLong();
        size = in.readLong();
        dateToken = in.readLong();
        width = in.readInt();
        height = in.readInt();
        serverId = in.readString();
        videoThumbnail = in.readString();
//        isSelect = in.re();
    }

    public static final Creator<ImageVideoBean> CREATOR = new Creator<ImageVideoBean>() {
        @Override
        public ImageVideoBean createFromParcel(Parcel in) {
            return new ImageVideoBean(in);
        }

        @Override
        public ImageVideoBean[] newArray(int size) {
            return new ImageVideoBean[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDateToken() {
        return dateToken;
    }

    public void setDateToken(long dateToken) {
        this.dateToken = dateToken;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null) return false;
        if (!(obj instanceof ImageVideoBean)) {
            return false;
        }
        String p = ((ImageVideoBean) obj).path;
        if (p != null && p.equals(path))
            return true;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : super.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(mime);
        if (folderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(folderId);
        }
        dest.writeString(folderName);
        dest.writeLong(duration);
        dest.writeLong(size);
        dest.writeLong(dateToken);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(serverId);
        dest.writeString(videoThumbnail);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUploadKey() {
        return uploadKey;
    }

    public void setUploadKey(String uploadKey) {
        this.uploadKey = uploadKey;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }


    public void copy(ImageVideoBean bean) {
        path = bean.path;
        mime = bean.mime;
        videoThumbnail = bean.videoThumbnail;
        folderId = bean.folderId;
        folderName = bean.folderName;
        duration = bean.duration;
        size = bean.size;
        dateToken = bean.dateToken;

        width = bean.width;
        height = bean.height;

        // ---------以下3参数用于配合上传 -------------- //
        uploadKey = bean.uploadKey;
        status = bean.status;
        serverId = bean.serverId;
        isSelect = bean.isSelect;
    }

}
