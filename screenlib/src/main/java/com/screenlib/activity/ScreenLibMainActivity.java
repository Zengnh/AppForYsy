package com.screenlib.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.screenlib.MainService;
import com.screenlib.R;
import com.screenlib.notification.MyNotification;
import com.screenlib.utils.SharedMemory;
import com.screenlib.utils.UtilsService;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has the controls to set the color of the filter
 *
 * @author Hathibelagal
 */
public class ScreenLibMainActivity extends Activity implements OnSeekBarChangeListener {
    private TextView openNotification;
    private SharedMemory shared;

    private SeekBar alphaControl;
    private SeekBar redControl;
    private SeekBar greenControl;
    private SeekBar blueControl;
    private SeekBar blackControl;

    private int alpha, red, green, blue, black;
    private CheckBox cb_open;
    private CheckBox cbOpenTop;//通知栏目


    private TextView color_value;//色温值
    private TextView alph_value;//色温值
    private TextView black_value;//色温值

    private RadioGroup radioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_lib);
        initView();
        initData();
        initEvent();

        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 1057);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1057) {
//            if (Build.VERSION.SDK_INT >= 23) {
//                if (!Settings.canDrawOverlays(this)) {
//                    finish();
//                }
//            }
        }
    }

    private void initView() {
        cb_open = findViewById(R.id.cb_open);
        cbOpenTop = findViewById(R.id.cbOpenTop);
        alphaControl = (SeekBar) findViewById(R.id.alphaControl);
        redControl = (SeekBar) findViewById(R.id.redControl);
        greenControl = (SeekBar) findViewById(R.id.greenControl);
        blueControl = (SeekBar) findViewById(R.id.blueControl);
        blackControl = (SeekBar) findViewById(R.id.blackControl);

        color_value = findViewById(R.id.color_value);//色温值
        alph_value = findViewById(R.id.alph_value);//色温值
        black_value = findViewById(R.id.black_value);//色温值

        radioGroup = findViewById(R.id.radioGroup);//色温值
        openNotification = findViewById(R.id.openNotification);//色温值

//        adReflush();
    }

    private void initData() {
        shared = new SharedMemory(this);
        black = shared.getBlack();
        alpha = shared.getAlpha();
        red = shared.getRed();
        green = shared.getGreen();
        blue = shared.getBlue();

        alph_value.setText(alpha / 2 + "%");//色温值
        black_value.setText(black / 2 + "%");//色温值


        blackControl.setProgress(black / 2);
        alphaControl.setProgress(alpha / 2);

        redControl.setProgress(red);
        greenControl.setProgress(green);
        blueControl.setProgress(blue);

        if (UtilsService.isServiceWork(this, "com.zeng.screentool.MainService")) {
            MyNotification.getInstance(this).setPosition(shared.getItem());
            if (!cbOpenTop.isChecked()) {
                cbOpenTop.setChecked(true);
            }
            if (!cb_open.isChecked()) {
                cb_open.setChecked(true);
            }
        } else {
            if (cb_open.isChecked()) {
                cb_open.setChecked(false);
            }
        }
    }

    private void initEvent() {
        blackControl.setOnSeekBarChangeListener(this);
        alphaControl.setOnSeekBarChangeListener(this);
        redControl.setOnSeekBarChangeListener(this);
        greenControl.setOnSeekBarChangeListener(this);
        blueControl.setOnSeekBarChangeListener(this);
        cb_open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    applyClick();
                } else {
                    showPopAd();
                    offClick();
                }
            }
        });
        cbOpenTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    MyNotification.getInstance(ScreenLibMainActivity.this).setPosition(shared.getItem());
                    openNotification.setText(getResources().getString(R.string.notificationbarOn));
                } else {
                    cancelNotification();
                    openNotification.setText(getResources().getString(R.string.notificationbarOff));
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb0) {
                    color_value.setText("3200k");
                    changeGGB(255, 187, 120);
                } else if (i == R.id.rb1) {
                    color_value.setText("1300k");
                    changeGGB(255, 56, 0);
                } else if (i == R.id.rb2) {
                    color_value.setText("2000k");
                    changeGGB(255, 137, 18);
                } else if (i == R.id.rb3) {
                    color_value.setText("3000k");
                    changeGGB(255, 180, 107
                    );
                } else if (i == R.id.rb4) {
                    color_value.setText("3500k");
                    changeGGB(255, 198, 130);
                } else if (i == R.id.rb5) {
                    color_value.setText("4500k");
                    changeGGB(255, 219, 186);
                }
            }
        });
    }

    private void changeGGB(int red, int green, int blue) {
        redControl.setProgress(red);
        greenControl.setProgress(green);
        blueControl.setProgress(blue);
    }


//    private boolean getPermissionFunction(String[] writeP, int resultCode) {
//        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            // 检查该权限是否已经获取
//            int i = ContextCompat.checkSelfPermission(this, writeP[0]);
//            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
//            if (i != PackageManager.PERMISSION_GRANTED) {
//                // 如果没有授予该权限，就去提示用户请求
//                ActivityCompat.requestPermissions(this, writeP, resultCode);
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return true;
//        }
//    }


    private void stopServiceIfActive() {
        if (MainService.STATE == MainService.ACTIVE) {
            Intent i = new Intent(ScreenLibMainActivity.this, MainService.class);
            stopService(i);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        int id = seekBar.getId();
        if (id == R.id.alphaControl) {
            alpha = progress * 2;
            alph_value.setText(progress + "%");//色温值
            shared.setAlpha(alpha);
        } else if (id == R.id.redControl) {
            red = progress;
            shared.setRed(red);
        } else if (id == R.id.greenControl) {
            green = progress;
            shared.setGreen(green);
        } else if (id == R.id.blueControl) {
            blue = progress;
            shared.setBlue(blue);
        } else if (id == R.id.blackControl) {
            black = progress * 2;
            shared.setBlack(black);
            black_value.setText(progress + "%");//暗度值
        }
        if (cb_open.isChecked()) {
            applyClick();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar sb) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar sb) {
    }

    public void offClick() {
        stopServiceIfActive();
    }

    public void applyClick() {
        Intent i = new Intent(ScreenLibMainActivity.this, MainService.class);
        startService(i);
//        makeNotification();
    }

    private void cancelNotification() {
        MyNotification.getInstance(this).setCancelNotification();
    }

    private Toast toast;

    private void showToast(String message) {
//        if (toast == null) {
//            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
//        }
//        toast.setText(message);
//        toast.show();
    }

    private void adReflush() {
//        AdView mAdView = (AdView) findViewById(R.id.listener_av_main);
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                showToast("Ad loaded.");
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                showToast(String.format("Ad failed to load with error code %d.", errorCode));
//            }
//
//            @Override
//            public void onAdOpened() {
//                showToast("Ad opened.");
//            }
//
//            @Override
//            public void onAdClosed() {
//                showToast("Ad closed.");
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                showToast("Ad left application.");
//            }
//        });
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
    }

    private PopupWindow pop;

    private void showPopAd() {
//        View view = LayoutInflater.from(this).inflate(R.layout.poplayout, null);
//        pop = new PopupWindow(this);
//        pop.setContentView(view);
//        pop.setOutsideTouchable(true);
//        PublisherAdView adView = view.findViewById(R.id.adsizes_pav_main);
//        adView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                View viewRoot = findViewById(R.id.root_layout);
//                if (!pop.isShowing()) {
//                    pop.showAtLocation(viewRoot, Gravity.CENTER, 0, 0);
//                }
//            }
//        });
//        List<AdSize> sizeList = new ArrayList<AdSize>();
//        sizeList.add(AdSize.MEDIUM_RECTANGLE);
//        adView.setAdSizes(sizeList.toArray(new AdSize[sizeList.size()]));
//        adView.loadAd(new PublisherAdRequest.Builder().build());
    }


}
