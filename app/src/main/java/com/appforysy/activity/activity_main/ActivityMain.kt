package com.appforysy.activity.activity_main

import android.animation.ObjectAnimator
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Path
import android.os.IBinder
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.ImageView
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import com.appforysy.R
import com.appforysy.activity.activity_main.home.HomeFragmentJava
import com.appforysy.service.ServiceMain
import com.toolmvplibrary.activity_root.ActivityRootInit
import com.toolmvplibrary.tool_app.LogUtil

class ActivityMain : ActivityRootInit<PresenterMain>(), InterUiMain {
    override fun setPresenter(): PresenterMain {
        return PresenterMain()
    }

    override fun setCutLayout(): Int {
        return R.layout.activity_main
//        return R.layout.activity_main_screen_lib
    }

    lateinit var nav_view: GridView
    lateinit var adapter: AdapterBtnItem
    override fun initView() {
//        变更系统语言
//        var lan = ToolPreferences.getString(this, "lan");
//        if (!TextUtils.isEmpty(lan)) {
//            ToolLanguage.changeAppLanguage(context, lan)
//        }
        nav_view = findViewById(R.id.nav_view)
//        YsyApplication.changeAppLanguage(getResources(),"zh");
        presenter.initData();
        adapter = AdapterBtnItem(presenter.getBtnData())
        nav_view.adapter = adapter
        nav_view.numColumns = adapter.count

        nav_view.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (adapter.cutSelect != position) {
                    adapter.setItemClick(position);
                    chagneFragmen(adapter.getItem(position).fra);
                }
            }
        });
    }


    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i("unbindservice")
        unbindService(mConnection)
    }

    override fun initData() {
        bindServiceMain();
        chagneFragmen(HomeFragmentJava());
    }

    override fun reflushBtn() {
        nav_view.numColumns = adapter.count
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
    fun bindServiceMain() {
        LogUtil.i("bindservice")
        var intent = Intent(this, ServiceMain::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    var mConnection: ServiceConnection = object : ServiceConnection {
        // 当与service的连接意外断开时被调用
        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtil.i("sevice kill connection")
        }

        // 当与service的连接建立后被调用
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            LogUtil.i("sevice connection")
//            此方法有一个bind可以提供给本activity调用

            // 这个是用Binder进行内部进程,利用Binder调用Service里面的方法

            // 这个是用Binder进行内部进程,利用Binder调用Service里面的方法
            val binder: ServiceMain.MyBinder = service as ServiceMain.MyBinder
            services = binder.getService();
        }
    };
    lateinit var services: ServiceMain

    fun chagneFragmen(fra: Fragment) {
        val commit = this.supportFragmentManager.beginTransaction();
        commit.replace(R.id.nav_host_fragment, fra).commit();
    }


}