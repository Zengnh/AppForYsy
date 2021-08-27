package com.cameralib.selectimg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cameralib.R;

import java.math.BigDecimal;
import java.util.ArrayList;
/**
 * Created by Z on 2017/5/25.
 */

public class AdapterLocalImageList extends RecyclerView.Adapter<AdapterLocalImageList.ViewHolder> {
    public interface OnPickDelegate {
        /**
         * 点击相机拍照
         */
        void onClickCamera();
        /**
         * 选择、取消选择 图片或者视频
         *
         * @param bean
         * @return
         */
        boolean onPickImage(ImageBean bean);

        /**
         * 预览 图片或 视频
         *
         * @param bean
         */
        void onPreviewImage(ImageBean bean, int pos);

        /**
         * 已选择的项发生改变
         *
         * @param list
         */
        void onPickChanged(ArrayList<ImageBean> list, boolean add, int postion);
        void selectImgToast(String msg);
    }

    private Context context;
    private ArrayList<ImageBean> imageList;
    public OnPickDelegate mDelegate = null;
    private float hight = 0;
    private RecyclerView recyclerView;
    public AdapterLocalImageList(Context context,RecyclerView recy, ArrayList<ImageBean> imageList, OnPickDelegate listener) {
        this.mDelegate = listener;
        this.recyclerView=recy;
        this.imageList = imageList;
        this.context = context;
        hight = (context.getResources().getDisplayMetrics().widthPixels -80 )/ 4;
        if (hight == 0) {
            hight =  170;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_select_image_list, null);
        return new ViewHolder(view, (int) hight);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, @SuppressLint("RecyclerView") final int position) {

        if (position == 0) {
            vh.camera.setVisibility(View.VISIBLE);//拍照页面
            vh.image.setVisibility(View.GONE);
            vh.iv_image_select.setVisibility(View.GONE);
            vh.tv_video_duration.setVisibility(View.GONE);
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDelegate != null) {
                        mDelegate.onClickCamera();
                    }
                }
            });
        } else {
            ImageBean bean = imageList.get(position - 1);
            vh.iv_image_select.setVisibility(View.VISIBLE);
            vh.image.setVisibility(View.VISIBLE);
            vh.camera.setVisibility(View.GONE);//拍照页面
            if (bean.isSelect) {
                vh.iv_image_select.setImageResource(R.mipmap.checkbox_checked);
            } else {
                vh.iv_image_select.setImageResource(R.mipmap.checkbox_unchecked);
            }
            vh.iv_image_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imageList.get(position - 1).isSelect){
//                        选中状态，不管是否满足限制个数，都可以取取消
                        imageList.get(position - 1).isSelect=false;
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                notifyItemChanged(position);
                            }
                        });
                        if (mDelegate != null) {
                            mDelegate.onPickChanged(ToolLocationImage.getInstance().getPicked(),
                                    imageList.get(position - 1).isSelect, position);
                        }
                    }else{
                        if(ToolLocationImage.getInstance().getPicked().size()>=ToolLocationImage.getInstance().limitSelect()){
                            if (mDelegate != null) {
                                mDelegate.selectImgToast("你最多只能选择 "+ToolLocationImage.getInstance().limitSelect()+" 张照片");
                            }
                        }else{
                            imageList.get(position - 1).isSelect=true;
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    notifyItemChanged(position);
                                }

                            });
                            if (mDelegate != null) {
                                mDelegate.onPickChanged(ToolLocationImage.getInstance().getPicked(),
                                        imageList.get(position - 1).isSelect, position);
                            }
                        }
                    }


                }
            });
            vh.image.setImageBitmap(BitmapFactory.decodeFile(bean.getPath()));
//            Glide.with(context).load(bean.getPath()).into(vh.image);
            if (bean.getDuration() > 0) {
                vh.tv_video_duration.setText(formatDuration(bean.getDuration())+"");
                vh.tv_video_duration.setVisibility(View.VISIBLE);
            } else {
                vh.tv_video_duration.setVisibility(View.GONE);
            }
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDelegate != null) {
                        mDelegate.onPreviewImage(imageList.get(position - 1), position);
                    }
                }
            });
        }
    }


    private String formatDuration(long duration) {
        int minute = (int) (duration / 60000);
        float second = ((duration - minute * 60000) / 1000f);
        BigDecimal b = new BigDecimal(second);
        int d = b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        return String.format("%02d:%02d", minute, d);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return imageList.size() + 1;//extra camera count
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image, camera;
        public TextView tv_video_duration;
        private ImageView iv_image_select;
        private RelativeLayout rootViewHight;

        public ViewHolder(@NonNull View itemView, int hight) {
            super(itemView);
            rootViewHight = itemView.findViewById(R.id.rootViewHight);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rootViewHight.getLayoutParams();
            lp.height = hight;
            tv_video_duration = itemView.findViewById(R.id.tv_video_duration);
            iv_image_select = itemView.findViewById(R.id.iv_image_select);

            camera = itemView.findViewById(R.id.iv_camera);
            image = itemView.findViewById(R.id.iv_image);
        }

    }
}
