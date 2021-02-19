package com.appforysy.activity.activity_main.imgcard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.appforysy.R;
import com.rootlibs.loadimg.ToolGlide;
import java.util.List;

public class MyAdapterV2 extends RecyclerView.Adapter {
    private static final String TAG = "PlayerAdapter";
    List<Integer> mItemLists;
    private Context context;
    private OnUserPickDelegate listener;

    public interface OnUserPickDelegate {
        void onClickPick(ItemUserDetail item);
    }

    private OnUserPickDelegate mDelegate;

    public MyAdapterV2(List<Integer> myDataset, Context mContext, OnUserPickDelegate listener) {
        mItemLists = myDataset;
        this.context = mContext;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fligirl_v2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderr, int position) {

        MyViewHolder holder = (MyViewHolder) holderr;
        ToolGlide.loadImge(context,mItemLists.get(position),holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mItemLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}