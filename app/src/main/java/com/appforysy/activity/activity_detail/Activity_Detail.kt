package com.appforysy.activity.activity_detail
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.appforysy.R
import com.rootlibs.ok_http.OkHttpResultListener
import com.rootlibs.ok_http.PackHellowDown
import com.rootlibs.ok_http.PackHelloworld
import com.toolmvplibrary.activity_root.ActivityRootInit

class Activity_Detail : ActivityRootInit<PresenterDetail>() {
    override fun setCutLayout(): Int {
        return R.layout.activity_detail;
    }

    //    var edit:EditText=findViewById(R.id.edit)
//    var buttonConnect
//    var sendText
//    var textResult
    override fun initView() {
        var buttonConnect: Button =findViewById(R.id.buttonConnect)
        buttonConnect.setOnClickListener {
//            presenter.initConnect()

            var content = PackHelloworld<PackHellowDown>()
            content.upMap.put("aa", "bb")
            content.startService(object : OkHttpResultListener<PackHellowDown> {
                override fun result(content: PackHellowDown?) {
                    Log.i("znh", "@@@" + content?.cmdid);
                }
            })
        }
        var sendText:Button=findViewById(R.id.sendText)
        var edit:EditText=findViewById(R.id.edit)
        sendText.setOnClickListener { presenter.sendMsg(edit.text.toString().trim()) }
    }

    override fun initData() {
    }

    override fun setPresenter(): PresenterDetail {
        return PresenterDetail()
    }


}