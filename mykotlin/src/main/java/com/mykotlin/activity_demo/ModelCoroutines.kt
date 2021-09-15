package com.mykotlin.activity_demo

import com.toolmvplibrary.activity_root.RootModel

class ModelCoroutines :RootModel{
    //匿名函数
    val sumLambda: (Int, Int) -> Int = {x,y -> x+y}
//    可变参数
    fun vars(vararg v:Int){
        for(vt in v){
            print(vt)
        }
    }
    fun mian(){
        vars(1,2,3,4,5)
    }
}