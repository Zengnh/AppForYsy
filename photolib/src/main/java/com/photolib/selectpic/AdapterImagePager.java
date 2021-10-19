package com.photolib.selectpic;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.photolib.R;
import com.photolib.imageviw_sc.OnMatrixChangedListener;
import com.photolib.imageviw_sc.OnPhotoTapListener;
import com.photolib.imageviw_sc.OnSingleFlingListener;
import com.photolib.imageviw_sc.PhotoView;

import java.util.ArrayList;

public class AdapterImagePager extends PagerAdapter {

    private ArrayList<ImageBean> imageList;
    private Context context;
    private InterItemClick listerClick;
    public AdapterImagePager(Context context,ArrayList<ImageBean> dl,InterItemClick listerClick) {
        this.imageList = dl;
        this.context = context;
        this.listerClick=listerClick;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_select_img_pager, null);
        final Holder holder = new Holder(view);
//        Glide.with(context).load(imageList.get(position).getPath()).into(holder.imageView);
        ImageBean bean=imageList.get(position);
        if(bean.getDuration()>0){
            holder.layoutVideo.setVisibility(View.VISIBLE);
            holder.mPhotoView.setVisibility(View.GONE);
            Glide.with(context)
                    .load(imageList.get(position).getPath())
                    .error(R.mipmap.img_loading_err)
                    .into(holder.videoView);
            holder.playVido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listerClick.clickPos(position,1);
                }
            });
        }else{
            holder.layoutVideo.setVisibility(View.GONE);
            holder.mPhotoView.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(imageList.get(position).getPath())
                    .error(R.mipmap.img_loading_err)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            holder.mPhotoView.setImageDrawable(resource);
                        }
                    });

        }
        container.addView(view);
        return view;
    }

    private class Holder {
        public ImageView videoView;
        public ImageView playVido;
        public PhotoView mPhotoView;
        RelativeLayout layoutVideo;
        private Matrix mCurrentDisplayMatrix = null;
        public Holder(View view) {
            playVido = view.findViewById(R.id.playVido);
            layoutVideo = view.findViewById(R.id.layoutVideo);
            videoView = view.findViewById(R.id.videoView);
            mPhotoView=view.findViewById(R.id.photo_view);
            mCurrentDisplayMatrix = new Matrix();
            mPhotoView.setDisplayMatrix(mCurrentDisplayMatrix);
            mPhotoView.setOnMatrixChangeListener(new MatrixChangeListener());
            mPhotoView.setOnPhotoTapListener(new PhotoTapListener());
            mPhotoView.setOnSingleFlingListener(new SingleFlingListener());
        }
        private class PhotoTapListener implements OnPhotoTapListener {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                float xPercentage = x * 100f;
                float yPercentage = y * 100f;
            }
        }
        private class MatrixChangeListener implements OnMatrixChangedListener {

            @Override
            public void onMatrixChanged(RectF rect) {

            }
        }

        private class SingleFlingListener implements OnSingleFlingListener {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return true;
            }
        }
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
