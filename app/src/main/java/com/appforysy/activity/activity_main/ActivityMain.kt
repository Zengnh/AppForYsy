package com.appforysy.activity.activity_main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import com.appforysy.R
import com.appforysy.activity.activity_main.dashboard.DashboardFragment
import com.appforysy.activity.activity_main.home.HomeFragment
import com.appforysy.activity.activity_main.imgcard.FragmentImgCard
import com.appforysy.activity.activity_main.notifications.NotificationsFragment
import com.appforysy.service.ServiceMain
import com.toolmvplibrary.activity_root.ActivityRootInit
import com.toolmvplibrary.tool_app.LogUtil
import com.toolmvplibrary.tool_app.ToolLanguage
import com.toolmvplibrary.tool_app.ToolPreferences
import com.workysy.activity.activity_main.notifications.WorkFragment
import kotlinx.android.synthetic.main.activity_main.*


class ActivityMain : ActivityRootInit<PresenterMain>(),
    InterUiMain {


    override fun setPresenter(): PresenterMain {
        return PresenterMain()
    }

    override fun setCutLayout(): Int {
        return R.layout.activity_main
    }

    lateinit var adapter: AdapterBtnItem
    override fun initView() {
        setBlackFull()
        var lan = ToolPreferences.getString(this, "lan");
        if (!TextUtils.isEmpty(lan)) {
            ToolLanguage.changeAppLanguage(context, lan)
        }
//        YsyApplication.changeAppLanguage(getResources(),"zh");
        presenter.initData();
        adapter =AdapterBtnItem(presenter.getBtnData())
        nav_view.adapter = adapter
        nav_view.numColumns = adapter.count

        nav_view.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    chagneFragmen(FragmentImgCard());
                } else if (position == 1) {
                    chagneFragmen(HomeFragment());
                } else if (position == 2) {
                    chagneFragmen(WorkFragment());
                } else if (position == 3) {
                    chagneFragmen(DashboardFragment());
                } else if (position == 4) {
                    chagneFragmen(NotificationsFragment());
                } else if (position == 5) {

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
        chagneFragmen(FragmentImgCard());
    }


    override fun reflushBtn() {
        nav_view.numColumns = adapter.count
        adapter.notifyDataSetChanged()
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