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

    fun init7Data() {
        datalist.clear()
        var bean = ItemMusic()
        bean.itemName = "do"
        datalist.add(bean)
        var bean2 = ItemMusic()
        bean2.itemName = "re"
        datalist.add(bean2)
        var bean3 = ItemMusic()
        bean3.itemName = "mi"
        datalist.add(bean3)
        var beanfa = ItemMusic()
        beanfa.itemName = "fa"
        datalist.add(beanfa)
        var beanso = ItemMusic()
        beanso.itemName = "so"
        datalist.add(beanso)
        var beanla = ItemMusic()
        beanla.itemName = "la"
        datalist.add(beanla)
        var beanxi = ItemMusic()
        beanxi.itemName = "xi"
        datalist.add(beanxi)
    }
}