package com.toolmvplibrary.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.toolmvplibrary.R;
import com.toolmvplibrary.tool_app.ToolSys;

public class ToolBarComView extends LinearLayout implements IToolbar {

    private TextView mTvTitle, mTvRightText;
    private ImageView iv_toolbar_back;
    private View mStatus;
    private LinearLayout rightLyout,bg;
    public ToolBarComView(Context context) {
        super(context);
        init(context, null);
    }

    public ToolBarComView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ToolBarComView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ToolBarComView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.basic_toolbar_view, this);

        mStatus = findViewById(R.id.status);
        ViewGroup.LayoutParams lp = mStatus.getLayoutParams();
        lp.height = ToolSys.getStatusBarHeight(context);
        mStatus.setLayoutParams(lp);

        mTvTitle = findViewById(R.id.tv_toolbar_title);

        iv_toolbar_back = findViewById(R.id.iv_toolbar_back);
        rightLyout = findViewById(R.id.rightLyout);
        bg = findViewById(R.id.bg);
    }

    public void setTitle(String title){
        mTvTitle.setText(title);
    }
    public void addRightView(View view){
        rightLyout.addView(view);
    }
    public void setBlackOnclick(OnClickListener vc){
        iv_toolbar_back.setOnClickListener(vc);
    }
    public void showHitBlack(boolean isShow){
        if(isShow){
            iv_toolbar_back.setVisibility(View.VISIBLE);
        }else{
            iv_toolbar_back.setVisibility(View.GONE);
        }
    }

    public void setStatusAlpha(float alpha) {
        mStatus.setAlpha(alpha);
    }
    public void setBgAlpha(float alpha) {
        bg.setAlpha(alpha);
    }
    public void setTitleAlpha(float alpha) {
        mTvTitle.setAlpha(alpha);
    }

    public void setStatusBgColor(int color) {
        mStatus.setBackgroundColor(color);
    }
    public void setBgBgColor(int color) {
        bg.setBackgroundColor(color);
    }
    public void setTitleBgColor(int color) {
        mTvTitle.setBackgroundColor(color);
    }
}

