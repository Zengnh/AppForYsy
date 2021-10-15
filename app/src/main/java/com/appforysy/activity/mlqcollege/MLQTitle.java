package com.appforysy.activity.mlqcollege;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appforysy.R;
import com.rootlibs.ToolSys;

public class MLQTitle extends RelativeLayout {
    public MLQTitle(Context context) {
        super(context);
        initView();
    }

    public MLQTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MLQTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private ImageView imageView;
    private TextView textTitle;

    private void initView() {
        setPadding( ToolSys.dip2px(getContext(), 16), ToolSys.dip2px(getContext(), 34), 0, ToolSys.dip2px(getContext(), 8));
        imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.icon_arrow);
        addView(imageView);

        textTitle = new TextView(getContext());
        textTitle.setTextColor(getContext().getResources().getColor(R.color.topTitle));
        textTitle.setTextSize(16);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textTitle.setLayoutParams(lp);
        addView(textTitle);
    }

    public void setTitle(String title){
        textTitle.setText(title);
    }
    public void setLeftIcon(OnClickListener listener){
        imageView.setOnClickListener(listener);
    }
}
