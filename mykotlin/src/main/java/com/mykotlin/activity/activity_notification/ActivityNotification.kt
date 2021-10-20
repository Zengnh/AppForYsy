package com.mykotlin.activity.activity_notification
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mykotlin.R

import com.mykotlin.activity.BaseActivity
import com.mykotlin.databinding.ActivityKtNotificationBinding
//import com.mykotlin.databinding.ActivityKtNotificationBinding
import com.mykotlin.utils.ToolKtNotiManager
class ActivityNotification :BaseActivity() {
    private lateinit var binding: ActivityKtNotificationBinding

    private lateinit var viewModel: NotificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityKtNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this).get(NotificationViewModel::class.java)

//        setContentView(R.layout.activity_kt_notification)
        initEvent()
        initData()
    }

    private fun initData() {
        viewModel.text.observe(this,object: Observer<String> {
            override fun onChanged(t: String?) {
//              text数据变更了
                Log.i("znh","22222"+viewModel.text.value+"      "+t);
                binding.textValue.setText(viewModel.text.value)
            }
        })
    }


    private fun initEvent() {
        binding.btn1.setOnClickListener {
            ToolKtNotiManager.instance.createSimple(this)
        }
        binding.btn2.setOnClickListener {

            viewModel.text.value="2222"
        }
        binding.btn3.setOnClickListener {  }
        binding.btn4.setOnClickListener {  }
        binding.btn5.setOnClickListener {ToolKtNotiManager.instance.toSetNoti(this) }
    }
}