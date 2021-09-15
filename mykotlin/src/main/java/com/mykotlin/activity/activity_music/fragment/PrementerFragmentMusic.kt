package com.mykotlin.activity.activity_music.fragment

import com.mykotlin.activity.activity_music.InterFaceMusic
import com.mykotlin.activity.activity_music.ItemMusic
import com.mykotlin.activity.activity_music.MediaJsHandler
import com.mykotlin.activity.activity_music.ToolSourcePool
import com.toolmvplibrary.activity_root.RootPresenter

class PrementerFragmentMusic : RootPresenter<InterFaceMusic, ModelFragmentMusic>() {
    override fun createModel(): ModelFragmentMusic {
        musicPlay = MediaJsHandler(context)
        return ModelFragmentMusic()
    }

    var datalist = ArrayList<ItemMusic>()
    fun init9Data() {
        datalist.clear()
        for (i: Int in 1..9) {
            var bean = ItemMusic()
            bean.itemName = i.toString()
            datalist.add(bean)
        }
    }

    fun init26Data() {
        datalist.clear()
        for (i: Int in 1..26) {
            var bean = ItemMusic()
            bean.itemName = (i + 96).toChar().toString()
            datalist.add(bean)
        }
    }

    fun init7Data() {
        datalist.clear()

        var bean = ItemMusic()
        bean.sourceMusic = ToolSourcePool.KEY_A
        bean.itemName = "do"
        bean.itemRemark = "1"
        datalist.add(bean)


        var bean2 = ItemMusic()
        bean2.sourceMusic = ToolSourcePool.KEY_B
        bean2.itemName = "re"
        bean2.itemRemark = "2"
        datalist.add(bean2)


        var bean3 = ItemMusic()
        bean3.sourceMusic = ToolSourcePool.KEY_C
        bean3.itemName = "mi"
        bean3.itemRemark = "3"
        datalist.add(bean3)


        var beanfa = ItemMusic()
        beanfa.sourceMusic = ToolSourcePool.KEY_D
        beanfa.itemName = "fa"
        beanfa.itemRemark = "4"
        datalist.add(beanfa)


        var beanso = ItemMusic()
        beanso.sourceMusic = ToolSourcePool.KEY_E
        beanso.itemName = "so"
        beanso.itemRemark = "5"
        datalist.add(beanso)


        var beanla = ItemMusic()
        beanla.sourceMusic = ToolSourcePool.KEY_F
        beanla.itemName = "la"
        beanla.itemRemark = "6"
        datalist.add(beanla)


        var beanxi = ItemMusic()
        beanxi.sourceMusic = ToolSourcePool.KEY_G
        beanxi.itemName = "xi"
        beanxi.itemRemark = "7"
        datalist.add(beanxi)
    }

    var musicPlay: MediaJsHandler? = null
    override fun detachView() {
        super.detachView()
        musicPlay?.release()
    }

    fun play(name: String) {
        musicPlay?.playLocalSound(name)
    }
}