package com.mykotlin.activity.activity_music

import com.toolmvplibrary.activity_root.RootPresenter

class PresenterMusic : RootPresenter<InterFaceMusic, ModelMusic>() {
    override fun createModel(): ModelMusic {
        return ModelMusic();
    }

    fun click(onclick: (Int) -> Unit) {
        onclick(1)
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

}