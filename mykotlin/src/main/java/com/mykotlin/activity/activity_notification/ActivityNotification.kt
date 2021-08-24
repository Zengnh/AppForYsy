package com.mykotlin.activity.activity_notification

import android.os.Bundle
import com.mykotlin.R
import com.mykotlin.activity.BaseActivity
import com.mykotlin.utils.ToolKtNotiManager
import kotlinx.android.synthetic.main.activity_kt_notification.*

class ActivityNotification :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt_notification)
        initEvent()
    }

    private fun initEvent() {
        btn1.setOnClickListener {
            ToolKtNotiManager.instance.createSimple(this)

        }
        btn2.setOnClickListener {  }
        btn3.setOnClickListener {  }
        btn4.setOnClickListener {  }
        btn5.setOnClickListener {ToolKtNotiManager.instance.toSetNoti(this) }
    }
}