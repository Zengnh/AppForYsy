package com.mykotlin.activity

//kotlin实现双重校验单例
class SingletonDemo private constructor() {
    companion object {
        val instance: SingletonDemo by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        SingletonDemo() }
    }
}

object User{

}
