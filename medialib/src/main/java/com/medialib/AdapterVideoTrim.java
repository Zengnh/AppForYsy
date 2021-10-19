package com.medialib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medialib.trim.VideoFrame;
import com.medialib.trim.utils.VideoTrimmerUtil;

import java.util.ArrayList;
import java.util.List;


public class AdapterVideoTrim extends RecyclerView.Adapter {
    private List<VideoFrame> mFrames = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public AdapterVideoTrim(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrimmerViewHolder(mInflater.inflate(R.layout.item_video_trim_thumb_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TrimmerViewHolder) holder).thumbImageView.setImageBitmap(mFrames.get(position).getBitmap());
    }

    @Override
    public int getItemCount() {
        return mFrames.size();
    }

    public void addBitmaps(VideoFrame bitmap) {
        mFrames.add(bitmap);
        notifyDataSetChanged();
    }

    public void setData(int totalThumbsCount, int startPosition, long endPosition) {
        mFrames.clear();
        long interval = (endPosition - startPosition) / (totalThumbsCount - 1);
        for (long i = 0; i < totalThumbsCount; ++i) {
            long frameTime = startPosition + interval * i;
            VideoFrame frame = new VideoFrame();
            frame.setTimestamp(frameTime * 1000);
            mFrames.add(frame);
        }
        notifyDataSetChanged();
    }

    public List<VideoFrame> getSubFrames(int from, int to) {
        if(from <=-1|| to<=-1)
            return new ArrayList<>();
        int size = mFrames.size();
        if (to >= size) {
            to = size;
        }
        if (from >= size) {
            from = size;
        }
        if (from <= 0) {
            from = 0;
        }
        return mFrames.subList(from, to);
    }

    public void release() {
        mFrames.clear();
        notifyDataSetChanged();
    }

    private final class TrimmerViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbImageView;

        TrimmerViewHolder(View itemView) {
            super(itemView);
            thumbImageView = itemView.findViewById(R.id.thumb);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) thumbImageView.getLayoutParams();
            layoutParams.width = VideoTrimmerUtil.VIDEO_FRAMES_WIDTH / VideoTrimmerUtil.MAX_COUNT_RANGE;
            thumbImageView.setLayoutParams(layoutParams);
            thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
}
