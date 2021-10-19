package com.photolib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.photolib.selectpic.ToolScreenDensity;

public class ActTitle extends RelativeLayout {
    public ActTitle(Context context) {
        super(context);
        initView();
    }

    public ActTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ActTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private ImageView imageView;
    private TextView textTitle;
    private TextView textRight;

    private void initView() {
        setPadding( ToolScreenDensity.dp2px(getContext(), 16), ToolScreenDensity.dp2px(getContext(), 34), 0, ToolScreenDensity.dp2px(getContext(), 8));
        imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.icon_myl_title_back);
        addView(imageView);

        textTitle = new TextView(getContext());
        textTitle.setTextColor(getContext().getResources().getColor(R.color.topTitle));
        textTitle.setTextSize(16);
        LayoutParams lp = new LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textTitle.setLayoutParams(lp);
        addView(textTitle);
    }


    public TextView setRight(String txt,OnClickListener lt){
        textRight = new TextView(getContext());
        textRight.setTextColor(getContext().getResources().getColor(R.color.t_while));
        textRight.setTextSize(16);
        textRight.setText(txt);
        textRight.setWidth(ToolScreenDensity.dp2px(getContext(),60));
        textRight.setHeight(ToolScreenDensity.dp2px(getContext(),35));
        textRight.setBackgroundResource(R.drawable.shape_3dp_blue);
        LayoutParams lp = new LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.rightMargin=ToolScreenDensity.dp2px(getContext(),16);
        textRight.setLayoutParams(lp);
        addView(textRight);

        textRight.setOnClickListener(lt);

        return textRight;
    }

    public void setTitle(String title){
        textTitle.setText(title);
    }
    public void setLeftIcon(OnClickListener listener){
        imageView.setOnClickListener(listener);
    }
}
