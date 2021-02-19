package com.toolmvplibrary.view;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.toolmvplibrary.R;
import com.toolmvplibrary.tool_app.ToolSys;

/**
 * @author Z
 *         广告栏引导点
 */
public class LeadPoint extends LinearLayout {
    private Context context;
    private int oldSelec = 0;
    private ImageView[] viewList;

    public LeadPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * 移动第几个点
     *
     * @param position
     */
    public void setPointSelect(int position) {
        if (viewList == null || viewList.length <=position) {
        } else {
            for (int i = 0; i <viewList.length; i++) {
                viewList[i].setImageResource(pointDef);
            }
//            viewList[oldSelec].setImageResource(pointDef);
            viewList[position].setImageResource(pointSel);
            oldSelec = position;
        }
    }

    private int pointDef = R.drawable.pointdefault, pointSel = R.drawable.pointitemselect;

    public void setImagePoint(int def, int sel) {
        pointDef = def;
        pointSel = sel;
    }

    /**
     * 初始化点数个数
     *
     * @param pointSize
     */
    public void initPoint(int pointSize) {
        int size = ToolSys.dip2px(context,8);
        int margin = ToolSys.dip2px(context,8);
        viewList = new ImageView[pointSize];
        this.removeAllViews();
        for (int i = 0; i < pointSize; i++) {
            ImageView view = new ImageView(context);
            viewList[i] = view;
            if (i == 0) {
                view.setImageResource(pointSel);
            } else {
                view.setImageResource(pointDef);
            }
            LayoutParams lp = new LayoutParams(size, size);
            lp.setMargins(margin, 0, margin, 0);
            this.addView(viewList[i], lp);
        }
    }
}
