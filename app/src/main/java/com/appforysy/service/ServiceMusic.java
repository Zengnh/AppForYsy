package com.appforysy.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.appforysy.R;

/**
 * 主要是：声音类型，音量大小或者静音
 * AudioManager的常用方法：
 * adjustStreamVolume(int streamType,int direction,int flags)调整手机指定类型声音
 * 第一个参数： streamType：声音类型
 * AudioManager.STREAM_ALARM 手机闹铃的声音
 * AudioManager.STREAM_MUSIC 手机音乐的声音
 * AudioManager.STREAM_NOTIFICATION 系统提示的通知
 * AudioManager.STREAM_RING  电话铃声的声音
 * AudioManager.STREAM_SYSTEM  手机系统的声音
 * AudioManager.STREAM_VOICE_CALL 语音电话的声音
 * AudioManager.STREAM_DTMF   DTMF音调的声音
 * <p>
 * 第二个参数：direction声音的增大，减少
 * AudioManager.ADJUST_RAISE
 * AudioManager.ADJUST_LOWER
 * 第三个参数：flags 调整声音时标志，如指定AudioManager.FLAG_SHOW_UI，是调整声音时显示音量进度条
 * <p>
 * setMicrophoneMute(boolean on);设置麦克风静音
 * setSpeakerphoneOn(boolean on):设置是否打开扩音器
 * setMode(int mode);设置声音模式，NORMAL,RINGTONE,IN_CALL
 * setRingerMode(int ringermode);设置手机电话铃声的模式，
 * RINGER_MODE_NORMAL;正常的手机铃声
 * RINGER_MODE_SILENT;手机铃声静音
 * RINGER_MODE_VIBRATE:手机振动
 * <p>
 * setStreamMute(int streamType,boolean state);设置指定类型的声音的静音
 * setStreamVolume(int streamtype.int index,int flags)；设置指定类型的的音量值
 */
public class ServiceMusic extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void playMedia() {
        MediaPlayer media = MediaPlayer.create(this, R.raw.beep);
        media.setLooping(true);
        media.start();//开始播放
    }

    private AudioManager audioManager;

    private void requestAudioFocus() {
        if (audioManager == null)
            audioManager = (AudioManager) this
                    .getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            int ret = audioManager.requestAudioFocus(mAudioFocusChangeListener,
                    AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (ret != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                Log.i("service", "request audio focus fail. " + ret);
            } else {
//                聚焦了，就可以播放了
            }
        }

//        audioManager.setMode();

    }

    private void abandonAudioFocus() {
        //Build.VERSION.SDK_INT表示当前SDK的版本，Build.VERSION_CODES.ECLAIR_MR1为SDK 7版本 ，
        //因为AudioManager.OnAudioFocusChangeListener在SDK8版本开始才有。
        mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
//                    AUDIOFOCUS_LOSS:
//                    你已经丢失了音频焦点比较长的时间了．你必须停止所有的音频播放．
//                    因为预料到你可能很长时间也不能再获音频焦点，所以这里是清理你的资源的好地方．
//                    比如，你必须释放MediaPlayer．

                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
//                    AUDIOFOCUS_GAIN:
//                    你已获得了音频焦点．

                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
//                    AUDIOFOCUS_LOSS_TRANSIENT:
//                    你临时性的丢掉了音频焦点，很快就会重新获得．
//                    你必须停止所有的音频播放，但是可以保留你的资源，因为你可能很快就能重新获得焦点．

                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
//                    AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
//                    你临时性的丢掉了音频焦点，但是你被允许继续以低音量播放，而不是完全停止．

                }
            }
        };


        if (audioManager != null) {
            audioManager.abandonAudioFocus(mAudioFocusChangeListener);
            audioManager = null;
        }
    }

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = null;

    public void otherValue(){
        //获取AudioManager实例对象
        AudioManager audioManage = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        //获取最大音量和当前音量，参数：STREAM_VOICE_CALL（通话）、STREAM_SYSTEM（系统声音）、STREAM_RING（铃声）、STREAM_MUSIC（音乐）和STREAM_ALARM（闹铃）
//        int max = audioManager.getStreamMaxVolume( int streamType);
//        int current = audioManager.getStreamVolume( int streamType);
        //获取当前的铃声模式，返回值：RINGER_MODE_NORMAL（普通）、RINGER_MODE_SILENT（静音）或者RINGER_MODE_VIBRATE（震动）
        int rMode = audioManager.getRingerMode();
        //获取当前音频模式，返回值：MODE_NORMAL（普通）、MODE_RINGTONE（铃声）、MODE_IN_CALL（呼叫）或者MODE_IN_COMMUNICATION（通话）
        int mode = audioManager.getMode();

//设置音量大小，第一个参数：STREAM_VOICE_CALL（通话）、STREAM_SYSTEM（系统声音）、STREAM_RING（铃声）、STREAM_MUSIC（音乐）和STREAM_ALARM（闹铃）；第二个参数：音量值，取值范围为0-7；第三个参数：可选标志位，用于显示出音量调节UI（AudioManager.FLAG_SHOW_UI）。
//        audioManager.setStreamVolume( int streamType, int index, int flags);
//设置铃声模式，参数：RINGER_MODE_NORMAL（普通）、RINGER_MODE_SILENT（静音）或者RINGER_MODE_VIBRATE（震动）
//        audioManager.getRingerMode( int ringerMode);
//设置音频模式，参数：MODE_NORMAL（普通）、MODE_RINGTONE（铃声）、MODE_IN_CALL（呼叫）或者MODE_IN_COMMUNICATION（通话）
//        audioManager.setMode( int mode);
//设置静音/取消静音，第二个参数：请求静音状态，true（静音）false（取消静音）
//        audioManager.setStreamMute( int streamType, boolean state);
//调节手机音量大小，第二个参数：调整音量的方向，可取ADJUST_LOWER（降低）、ADJUST_RAISE（升高）、ADJUST_SAME（不变）。
//        audioManager.adjustStreamVolume(int streamType, int direction, int flags);
    }

}