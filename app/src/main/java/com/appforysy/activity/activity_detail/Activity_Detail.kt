package com.appforysy.activity.activity_detail


import android.widget.EditText
import com.appforysy.R
import com.toolmvplibrary.activity_root.ActivityRootInit
import kotlinx.android.synthetic.main.activity_detail.*

class Activity_Detail : ActivityRootInit<PresenterDetail>() {
    override fun setCutLayout(): Int {
        return R.layout.activity_detail;
    }

    //    var edit:EditText=findViewById(R.id.edit)
//    var buttonConnect
//    var sendText
//    var textResult
    override fun initView() {
        buttonConnect.setOnClickListener {
            presenter.initConnect()
        }
        sendText.setOnClickListener { presenter.sendMsg(edit.text.toString().trim()) }
    }

    override fun initData() {
    }

    override fun setPresenter(): PresenterDetail {
        return PresenterDetail()
    }


}