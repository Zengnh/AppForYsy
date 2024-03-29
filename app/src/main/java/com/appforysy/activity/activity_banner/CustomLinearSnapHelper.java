package com.appforysy.activity.activity_banner;

import android.util.Log;

import androidx.recyclerview.widget.LinearSnapHelper;

public class CustomLinearSnapHelper extends LinearSnapHelper {

    public static boolean mStateIdle = false;

    /**
     //                 *  int[] snapDistance = calculateDistanceToFinalSnap(layoutManager, snapView);
     //                 if (snapDistance[0] != 0 || snapDistance[1] != 0) {
     //                 mRecyclerView.smoothScrollBy(snapDistance[0], snapDistance[1]);
     //                 }
     //                 这个方法返回的数组值，是让recyclerview移动并居中显示的,如果是第一个或者最后一个位置，
     //                 因无法居中而调用recyclerview的OnScrollListener监听
     //                 */
    @Override
    public int[] calculateScrollDistance(int velocityX, int velocityY) {
        if(mStateIdle){
            return new int[2];
        }else{
            int[] ints = super.calculateScrollDistance(velocityX, velocityY);
            for (int i:ints){
                Log.e("tests","==i=="+i);
            }
            return super.calculateScrollDistance(velocityX, velocityY);
        }
    }

}