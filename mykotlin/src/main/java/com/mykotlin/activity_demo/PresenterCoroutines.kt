package com.mykotlin.activity_demo

import com.toolmvplibrary.activity_root.RootPresenter

class PresenterCoroutines : RootPresenter<InterFaceCoroutines, ModelCoroutines>() {
    override fun createModel(): ModelCoroutines {
        return ModelCoroutines();
    }

    fun click(onclick:(Int)->Unit){
        onclick(1)
    }
}