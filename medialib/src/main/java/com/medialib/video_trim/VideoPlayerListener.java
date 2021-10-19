package com.medialib.video_trim;

import android.widget.SeekBar;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoPlayerListener implements IMediaPlayer.OnCompletionListener,
        IMediaPlayer.OnErrorListener, IMediaPlayer.OnPreparedListener, IMediaPlayer.OnSeekCompleteListener ,
        SeekBar.OnSeekBarChangeListener, IMediaPlayer.OnInfoListener, IMediaPlayer.OnBufferingUpdateListener
{
    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {

    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {

    }

    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
        if (what == IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED) {
            //这里返回了视频旋转的角度，根据角度旋转视频到正确的画面
            //ToastUtils.showToast("旋转了:"+extra);
        }
        return false;
    }
}
