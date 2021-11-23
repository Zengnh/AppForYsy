package com.mykotlin.utils

import android.util.Log

class SingleManager private constructor() {

    companion object{
        val instance:SingleManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            SingleManager()
        }
    }

    fun hhh(){
        textInfo(1,2,3,4,5,6,7,8,9,10)
    }

    fun textInfo(vararg k:Int){
        for (ab in k){
         Log.i("znh_test",ab.toString())
        }
    }



}