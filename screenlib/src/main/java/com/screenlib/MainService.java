package com.screenlib;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.screenlib.utils.SharedMemory;


/**
 * This is the class that is responsible for adding the filter on the screen: It
 * works as a service, so that the view persists across all activities.
 *
 * @author Hathibelagal
 */
public class MainService extends Service {
    private ImageView mView;
    private SharedMemory shared;
    public static int STATE;
    public static final int INACTIVE = 0;
    public static final int ACTIVE = 0;

    static {
        STATE = INACTIVE;
    }

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        reflushView();
    }

    private WindowManager.LayoutParams params;
    private RelativeLayout.LayoutParams lp;

    private void reflushView() {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (shared == null) {
            lp = new RelativeLayout.LayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MEMORY_TYPE_CHANGED));
            shared = new SharedMemory(this);

            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                            | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);


//            params = new WindowManager.LayoutParams(
//                    WindowManager.LayoutParams.MATCH_PARENT,
//                    getScreenHeight(wm),
//                    0,
//                    -getStatusBarHeight(),
//                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//                            | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//                            | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
//                            | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
//                    PixelFormat.TRANSLUCENT);


        }


        mView = new ImageView(this);
        reflushImageView();
        try {
            wm.addView(mView, params);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("znh", "####################" + e.getMessage());
        }

    }

    private void reflushImageView() {
        mView.setBackgroundColor(shared.getColor());
        mView.setImageDrawable(new ColorDrawable(shared.getColorBlack()));
    }

    private int getScreenHeight(WindowManager mWindowManager) {
        int screenH = 0;
        Display display = mWindowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            screenH = dm.heightPixels + 2 * getStatusBarHeight() + 2 * getNavigationBarHeightPx();
        } else {
            screenH = dm.heightPixels + getStatusBarHeight();
        }
        return screenH;
    }

    private int mStatusBarHeight = -1;
    private int mNavigationBarHeight = -1;
    private float DEFAULT_STATUS_BAR_HEIGHT_DP = 25f;
    private float DEFAULT_NAV_BAR_HEIGHT_DP = 48f;

    private int getStatusBarHeight() {
        if (mStatusBarHeight == -1) {
            int statusBarHeightId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (statusBarHeightId > 0) {
                mStatusBarHeight = getResources().getDimensionPixelSize(statusBarHeightId);
            } else {
                mStatusBarHeight = dpToPx(DEFAULT_STATUS_BAR_HEIGHT_DP);
            }
        }
        return mStatusBarHeight;
    }

    private int getNavigationBarHeightPx() {
        if (mNavigationBarHeight == -1) {
            int navBarHeightId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (navBarHeightId > 0) {
                mNavigationBarHeight = getResources().getDimensionPixelSize(navBarHeightId);
            } else {
                mNavigationBarHeight = dpToPx(DEFAULT_NAV_BAR_HEIGHT_DP);
            }
        }
        return mNavigationBarHeight;
    }

    private int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mView != null) {
            try {
                WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
                wm.removeView(mView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        reflushImageView();

        return super.onStartCommand(intent, flags, startId);
    }


    private int addColors(int color1, int color2) {
        int alpha1 = colorBitsToFloat(Color.alpha(color1));
        int alpha2 = colorBitsToFloat(Color.alpha(color2));
        int red1 = colorBitsToFloat(Color.red(color1));
        int red2 = colorBitsToFloat(Color.red(color2));
        int green1 = colorBitsToFloat(Color.green(color1));
        int green2 = colorBitsToFloat(Color.green(color2));
        int blue1 = colorBitsToFloat(Color.blue(color1));
        int blue2 = colorBitsToFloat(Color.blue(color2));

        // Alpha changed to allow more control
        float fAlpha = alpha2 * INTENSITY_MAX_ALPHA + (DIM_MAX_ALPHA - alpha2 * INTENSITY_MAX_ALPHA) * alpha1;
        alpha1 *= ALPHA_ADD_MULTIPLIER;
        alpha2 *= ALPHA_ADD_MULTIPLIER;

        int alpha = floatToColorBits(fAlpha);
        int red = floatToColorBits((red1 * alpha1 + red2 * alpha2 * (1.0f - alpha1)) / fAlpha);
        int green = floatToColorBits((green1 * alpha1 + green2 * alpha2 * (1.0f - alpha1)) / fAlpha);
        int blue = floatToColorBits((blue1 * alpha1 + blue2 * alpha2 * (1.0f - alpha1)) / fAlpha);

        return Color.argb(alpha, red, green, blue);
    }

    private float DIM_MAX_ALPHA = 0.9f;
    private float INTENSITY_MAX_ALPHA = 0.75f;
    private float ALPHA_ADD_MULTIPLIER = 0.75f;

    private int colorBitsToFloat(int bits) {
        return (int) (bits / 255.0f);
    }

    private int floatToColorBits(float color) {
        return (int) (color * 255.0f);
    }


}
