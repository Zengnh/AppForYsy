package com.mykotlin.activity.activity_music;

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import com.mykotlin.R
import org.json.JSONException
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MediaJsHandler(
    val context: Context
) {

    private var sounds: Map<String, Int>

    init {
        sounds = initSound()
    }


    //================================== 播放单个本地音频 ================================================
    val allPlayer = ArrayList<MediaPlayer>()

    fun getStopMusic(): MediaPlayer {
        for (play in allPlayer) {
            if (!play.isPlaying) {
                return play
            }
        }
        val newPlayer = MediaPlayer()
        allPlayer.add(newPlayer)
        return newPlayer
    }


    fun playLocalSound(data: String) {
        val t = data.toUpperCase()
        val sound = sounds[t]
        if (null == sound) {
            return
        }
        val player = getStopMusic()

        val afd: AssetFileDescriptor = context.resources.openRawResourceFd(sound)
        try {
            player?.reset()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        player?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        player?.setOnCompletionListener {
        }
        player?.setOnErrorListener(MediaPlayer.OnErrorListener { mp, what, extra ->
            false
        })
        player?.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer?) {
                mp?.start()
            }
        })
        afd.close()
        player?.prepare()

    }

    /**
     * 销毁
     */
    fun release() {
        for (play in allPlayer) {
            if (play.isPlaying) {
                play.stop()
            }
            play.release()
        }
        allPlayer.clear()
    }

    /**
     * 初始化本地音频
     */
    private fun initSound(): Map<String, Int> {
        val map: MutableMap<String, Int> =
            HashMap()
        //数字
        map["0"] = R.raw.sound_0
        map["1"] = R.raw.sound_1
        map["2"] = R.raw.sound_2
        map["3"] = R.raw.sound_3
        map["4"] = R.raw.sound_4
        map["5"] = R.raw.sound_5
        map["6"] = R.raw.sound_6
        map["7"] = R.raw.sound_7
        map["8"] = R.raw.sound_8
        map["9"] = R.raw.sound_9
        //字母
        map["A"] = R.raw.sound_a
        map["B"] = R.raw.sound_b
        map["C"] = R.raw.sound_c
        map["D"] = R.raw.sound_d
        map["E"] = R.raw.sound_e
        map["F"] = R.raw.sound_f
        map["G"] = R.raw.sound_g
        map["H"] = R.raw.sound_h
        map["I"] = R.raw.sound_i
        map["J"] = R.raw.sound_j
        map["K"] = R.raw.sound_k
        map["L"] = R.raw.sound_l
        map["M"] = R.raw.sound_m
        map["N"] = R.raw.sound_n
        map["O"] = R.raw.sound_o
        map["P"] = R.raw.sound_p
        map["Q"] = R.raw.sound_q
        map["R"] = R.raw.sound_r
        map["S"] = R.raw.sound_s
        map["T"] = R.raw.sound_t
        map["U"] = R.raw.sound_u
        map["V"] = R.raw.sound_v
        map["W"] = R.raw.sound_w
        map["X"] = R.raw.sound_x
        map["Y"] = R.raw.sound_y
        map["Z"] = R.raw.sound_z

//        //笔画
//        map["点"] = R.raw.dian
//        map["横"] = R.raw.heng
//        map["横钩"] = R.raw.heng_gou
//        map["横撇"] = R.raw.heng_pie
//        map["横撇弯钩"] = R.raw.heng_pie_wan_gou
//        map["横斜钩"] = R.raw.heng_xue_gou
//        map["横折"] = R.raw.heng_zhe
//        map["横折钩"] = R.raw.heng_zhe_gou
//        map["横折提"] = R.raw.heng_zhe_ti
//        map["横折弯"] = R.raw.heng_zhe_wan
//        map["横折弯钩"] = R.raw.heng_zhe_wan_gou
//        map["横折折"] = R.raw.heng_zhe_zhe
//        map["横折折撇"] = R.raw.heng_zhe_zhe_pie
//        map["横折折折"] = R.raw.heng_zhe_zhe_zhe
//        map["横折折折钩"] = R.raw.heng_zhe_zhe_zhe_gou
//        map["捺"] = R.raw.na
//        map["撇"] = R.raw.pie
//        map["撇点"] = R.raw.pie_dian
//        map["撇折"] = R.raw.pie_zhe
//        map["竖"] = R.raw.shu
//        map["竖钩"] = R.raw.shu_gou
//        map["竖提"] = R.raw.shu_ti
//        map["竖弯"] = R.raw.shu_wan
//        map["竖弯钩"] = R.raw.shu_wan_gou
//        map["竖折"] = R.raw.shu_zhe
//        map["竖折撇"] = R.raw.shu_zhe_pie
//        map["竖折折"] = R.raw.shu_zhe_zhe
//        map["竖折折钩"] = R.raw.shu_zhe_zhe_gou
//        map["提"] = R.raw.ti
//        map["弯钩"] = R.raw.wan_gou
//        map["卧钩"] = R.raw.wo_gou
//        map["斜钩"] = R.raw.xue_gou
        return map
    }
}