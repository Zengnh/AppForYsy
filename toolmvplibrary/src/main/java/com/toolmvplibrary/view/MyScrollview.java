package com.toolmvplibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollview extends ScrollView {

	private KeepPaceWithcScrollView scrollViewListener = null;

	public MyScrollview(Context context) {
		super(context);
			
	}

	public MyScrollview(Context context, AttributeSet attrs,
						int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyScrollview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public void setScrollViewListener(KeepPaceWithcScrollView scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}
	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(ev);
	}
}