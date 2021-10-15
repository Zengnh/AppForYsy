package com.rootlibs.loadimg;

import android.content.Context;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.rootlibs.ToolSys;

//https://link.jianshu.com/?t=https://github.com/bumptech/glide
// implementation 'com.github.bumptech.glide:glide:4.11.0'
public class ToolGlide {

    public static void loadImge(Context context, String url, ImageView img){
        Glide.with(context)
                .load(url)
                .into(img);
    }
    public static void loadImge(Context context, int url, ImageView img){
        Glide.with(context)
                .load(url)
                .into(img);
    }

    public static void loadImgeDef(Context context, String url, ImageView img,int def){
        Glide.with(context)
                .load(url)
                .placeholder(def)
                .error(def)
                .into(img);
    }


    public static void loadImgeCircleDef(Context context, String url, ImageView img,int def){
        Glide.with(context)
                .load(url)
                .placeholder(def)
                .error(def)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(img);
    }
    public static void loadImgeCircleDef(Context context, int url, ImageView img,int def){
        Glide.with(context)
                .load(url)
                .placeholder(def)
                .error(def)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(img);
    }

    public static void loadImageRind(Context context, int url, ImageView img,int def,int r){
        RoundedCornersTransform transform = new RoundedCornersTransform(context, ToolSys.dip2px(context, r));
        transform.setNeedCorner(true, true, true, true);
        RequestOptions optionsRoundImg = new RequestOptions().transform(transform);
        Glide.with(context)
                .load(url)
                .placeholder(def)
                .error(def)
                .apply(optionsRoundImg)
                .into(img);
    }

    public static void loadImageRindLeft(Context context, int url, ImageView img,int def,int r){
        RoundedCornersTransform transform = new RoundedCornersTransform(context, ToolSys.dip2px(context, r));
        transform.setNeedCorner(true, false, true, false);
        RequestOptions optionsRoundImg = new RequestOptions().transform(transform);
        Glide.with(context)
                .load(url)
                .placeholder(def)
                .error(def)
                .apply(optionsRoundImg)
                .into(img);
    }
}
