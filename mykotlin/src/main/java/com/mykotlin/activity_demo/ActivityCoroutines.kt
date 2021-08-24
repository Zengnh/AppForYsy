package com.mykotlin.activity_demo

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.mykotlin.R
import com.toolmvplibrary.activity_root.ActivityRoot
import com.toolmvplibrary.tool_app.LogUtil
import kotlinx.coroutines.*
import java.nio.file.Files.find

//implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1"

class ActivityCoroutines : ActivityRoot<PresenterCoroutines>() {
    override fun setPresenter(): PresenterCoroutines {
        return PresenterCoroutines()
    }
    fun TextView.isBold()=this.apply {
        this.paint.isFakeBoldText = true
    }
    var TAG = "znh_coroutines"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actiivty_coroutines)

        findViewById<TextView>(R.id.textcenter).isBold()

//        Log.e(TAG, "主线程id：${mainLooper.thread.id}")
//        test()
//        Log.e(TAG, "协程执行结束")
//
//        Log.e(TAG, "主线程id：${mainLooper.thread.id}")
//        val job = GlobalScope.launch {
//            delay(6000)
//            Log.e(TAG, "协程执行结束 -- 线程id：${Thread.currentThread().id}")
//        }
//        Log.e(TAG, "主线程执行结束")
////Job中的方法
//        job.isActive
//        job.isCancelled
//        job.isCompleted
//        job.cancel()
//        job.join()
        GlobalScope.launch {
            val token = getToken()
            val userInfo = getUserInfo(token)
            setUserInfo(userInfo)
        }
        repeat(8){
            Log.i(TAG,"主线程执行$it")
        }

        presenter.click(clickIn)
        GlobalScope.launch {
            val result1 = GlobalScope.async {
                getResult1()
            }
            val result2 = GlobalScope.async {
                getResult2()
            }
            val result = result1.await() + result2.await()
            Log.e(TAG,"result = $result")
        }

    }

    private fun setUserInfo(userInfo: String) {
        Log.i(TAG, "setUserInfo"+userInfo)
    }

    private suspend fun getToken(): String {
        delay(2000)
        Log.i(TAG, "exe getToken")
        return "token"
    }

    private suspend fun getUserInfo(token: String): String {
        delay(2000)
        Log.i(TAG, "exe getUserInfo")
        return "$token - userInfo"
    }


    private fun test() = runBlocking {
        repeat(8) {
            Log.i(TAG, "协程执行$it 线程id：${Thread.currentThread().id}")
            delay(1000)
        }
    }
    private suspend fun getResult1(): Int {
        delay(3000)
        return 1
    }

    private suspend fun getResult2(): Int {
        delay(4000)
        return 2
    }

    private val clickIn=fun(pos:Int){
       val a="13412334";
        val b=a?.length
        val c=a?.length?:txtLeng()
        Log.i(TAG, "setUserInfo-----$pos")

        when(pos){
            1->{}
            2->{}
            else->{}
        }
    }
    private fun txtLeng():Int{
        return 13
    }
}
//上下文可以有很多作用，包括携带参数，拦截协程执行等等，多数情况下我们不需要自己去实现上下文，只需要使用现成的就好。上下文有一个重要的作用就是线程切换，Kotlin协程使用调度器来确定哪些线程用于协程执行，Kotlin提供了调度器给我们使用：
//
//Dispatchers.Main：使用这个调度器在 Android 主线程上运行一个协程。可以用来更新UI 。在UI线程中执行
//Dispatchers.IO：这个调度器被优化在主线程之外执行磁盘或网络 I/O。在线程池中执行
//Dispatchers.Default：这个调度器经过优化，可以在主线程之外执行 cpu 密集型的工作。例如对列表进行排序和解析 JSON。在线程池中执行。
//Dispatchers.Unconfined：在调用的线程直接执行。
