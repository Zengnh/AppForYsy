package com.mykotlin.activity.activity_music;

import android.content.Context;
import android.media.SoundPool;

import com.mykotlin.R;

import java.util.HashMap;

public class ToolSourcePool {

//    private static final int KEY_A = 1;
//    private static final int KEY_B = 2;
//    private static final int KEY_C = 3;
//    private static final int KEY_D = 4;
//    private static final int KEY_E = 5;
//    private static final int KEY_F = 6;
//    private static final int KEY_G = 7;
    private static volatile ToolSourcePool sSoundPoolUtil;
    private final Context mContext;
    private final SoundPool mSoundPool;
    private final HashMap<Integer, Integer> mSoundMap;
    private int mCurrentId;
    //声音资源加载完成的标识位
    private boolean mHasLoaded;

    private ToolSourcePool(Context context) {
        mContext = context.getApplicationContext();
        mSoundPool = new SoundPool.Builder().setMaxStreams(21).build();
//        mSoundPool = new SoundPool(2, AudioManager.STREAM_VOICE_CALL,0);
        mSoundMap = new HashMap<>();
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mHasLoaded = true;
            }
        });
        initAllMusic();
    }

    public static ToolSourcePool getInstance(Context context) {
        if (sSoundPoolUtil == null) {
            synchronized (ToolSourcePool.class) {
                if (sSoundPoolUtil == null) {
                    sSoundPoolUtil = new ToolSourcePool(context);
                }
            }
        }
        return sSoundPoolUtil;
    }
    private int playKey = KEY_A;
    public void playSound(int key) {
        playKey = key;
        if (!mHasLoaded) {
            mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    if (sampleId == mSoundMap.get(playKey)) {
                        playSoundId(mSoundMap.get(playKey), false);
                    }
                    mHasLoaded = true;
                }
            });
        } else {
            playSoundId(mSoundMap.get(playKey), false);
        }
    }

    public void stopPlay() {
        if (mCurrentId != 0) {
            mSoundPool.stop(mCurrentId);
        }
    }

    private int playSoundId(int sampleId, boolean isLoop) {
        mCurrentId = mSoundPool.play(sampleId, 1f, 1f, 1, isLoop ? -1 : 0, 1);
        return mCurrentId;
    }

    public  void initAllMusic(){

//        mSoundMap.put(KEY_A, mSoundPool.load(mContext, R.raw.c, 1));
//        mSoundMap.put(KEY_B, mSoundPool.load(mContext, R.raw.d, 1));
//        mSoundMap.put(KEY_C, mSoundPool.load(mContext, R.raw.e, 1));
//        mSoundMap.put(KEY_D, mSoundPool.load(mContext, R.raw.f, 1));
//        mSoundMap.put(KEY_E, mSoundPool.load(mContext, R.raw.g, 1));
//        mSoundMap.put(KEY_F, mSoundPool.load(mContext, R.raw.a, 1));
//        mSoundMap.put(KEY_G, mSoundPool.load(mContext, R.raw.b, 1));




        mSoundMap.put(KEY_A, mSoundPool.load(mContext, R.raw.music_do_com, 1));
        mSoundMap.put(KEY_A_Down, mSoundPool.load(mContext, R.raw.music_do_down, 1));
        mSoundMap.put(KEY_A_up, mSoundPool.load(mContext, R.raw.music_do_up, 1));
        mSoundMap.put(KEY_B, mSoundPool.load(mContext, R.raw.music_re_com, 1));
        mSoundMap.put(KEY_B_Down, mSoundPool.load(mContext, R.raw.music_re_down, 1));
        mSoundMap.put(KEY_B_Up, mSoundPool.load(mContext, R.raw.music_re_up, 1));
        mSoundMap.put(KEY_C, mSoundPool.load(mContext, R.raw.music_mi_com, 1));
        mSoundMap.put(KEY_C_Down, mSoundPool.load(mContext, R.raw.music_mi_down, 1));
        mSoundMap.put(KEY_C_Up, mSoundPool.load(mContext, R.raw.music_mi_up, 1));
        mSoundMap.put(KEY_D, mSoundPool.load(mContext, R.raw.music_fa_com, 1));
        mSoundMap.put(KEY_D_Down, mSoundPool.load(mContext, R.raw.music_fa_down, 1));
        mSoundMap.put(KEY_D_Up, mSoundPool.load(mContext, R.raw.music_fa_up, 1));
        mSoundMap.put(KEY_E, mSoundPool.load(mContext, R.raw.music_so_com, 1));
        mSoundMap.put(KEY_E_Down, mSoundPool.load(mContext, R.raw.music_so_down, 1));
        mSoundMap.put(KEY_E_Up, mSoundPool.load(mContext, R.raw.music_so_up, 1));
        mSoundMap.put(KEY_F, mSoundPool.load(mContext, R.raw.music_la_com, 1));
        mSoundMap.put(KEY_F_Down, mSoundPool.load(mContext, R.raw.music_la_down, 1));
        mSoundMap.put(KEY_F_Up, mSoundPool.load(mContext, R.raw.music_la_up, 1));
        mSoundMap.put(KEY_G, mSoundPool.load(mContext, R.raw.music_xi_com, 1));
        mSoundMap.put(KEY_G_Down, mSoundPool.load(mContext, R.raw.music_xi_down, 1));
        mSoundMap.put(KEY_G_Up, mSoundPool.load(mContext, R.raw.music_xi_up, 1));
    }


    public static final int KEY_A = 110;
    public static final int KEY_A_Down = 111;
    public static final int KEY_A_up = 112;
    public static final int KEY_B = 113;
    public static final int KEY_B_Down = 114;
    public static final int KEY_B_Up = 115;
    public static final int KEY_C = 116;
    public static final int KEY_C_Down = 117;
    public static final int KEY_C_Up = 118;
    public static final int KEY_D = 119;
    public static final int KEY_D_Down = 120;
    public static final int KEY_D_Up = 121;
    public static final int KEY_E = 122;
    public static final int KEY_E_Down = 123;
    public static final int KEY_E_Up = 124;
    public static final int KEY_F = 125;
    public static final int KEY_F_Down = 126;
    public static final int KEY_F_Up = 127;
    public static final int KEY_G = 128;
    public static final int KEY_G_Down = 129;
    public static final int KEY_G_Up = 130;

}