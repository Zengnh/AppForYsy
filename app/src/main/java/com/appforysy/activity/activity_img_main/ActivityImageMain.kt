package com.appforysy.activity.activity_img_main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.appforysy.R
import com.appforysy.activity.activity_camera.ActivityCamrea
import com.appforysy.activity.activity_main.ActivityMain
import com.appforysy.activity.activity_edt_note.ActivityNotiEdit
import com.appforysy.activity.activity_rotation.ActivityRotation
import com.appforysy.activity.activity_time_note.ActivityTimeNote
import com.toolmvplibrary.activity_root.ActivityRoot
import com.toolmvplibrary.activity_root.ItemClick
import kotlinx.android.synthetic.main.activity_image_main.*
import java.util.*

class ActivityImageMain : ActivityRoot<PresenterImgOther>() {
    override fun setCutLayout(): Int {
        return R.layout.activity_image_main
    }

    override fun initView() {

    }


    var dataList = ArrayList<ItemMain>()
    lateinit var adapter: AdapterHomeFra
    override fun initData() {


        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            val permission = Manifest.permission.READ_EXTERNAL_STORAGE //这个是需要申请的权限信息
            val checkPermission =
                context?.let { ActivityCompat.checkSelfPermission(it, permission) }
        } else if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permission = Manifest.permission.CAMERA //这个是需要申请的权限信息
            val checkPermission =
                context?.let { ActivityCompat.checkSelfPermission(it, permission) }

        } else if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permission = Manifest.permission.RECORD_AUDIO //这个是需要申请的权限信息
            val checkPermission =
                context?.let { ActivityCompat.checkSelfPermission(it, permission) }

        } else if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE //这个是需要申请的权限信息
            val checkPermission =
                context?.let { ActivityCompat.checkSelfPermission(it, permission) }
        }


        dataList.add(ItemMain(R.mipmap.image_noti_head, ActivityNotiEdit::class.java))
        dataList.add(ItemMain(R.mipmap.image_time_info, ActivityTimeNote::class.java))
        dataList.add(ItemMain(3, R.mipmap.image_camer, ActivityCamrea::class.java))
        dataList.add(ItemMain(R.mipmap.image_main_img, ActivityMain::class.java))
        dataList.add(ItemMain(R.mipmap.image_rotation, ActivityRotation::class.java))
        dataList.add(ItemMain(5, R.mipmap.image_all_image))


        adapter = AdapterHomeFra(dataList, object : ItemClick<Int> {
            override fun itemClick(rst: Int?) {
                if (dataList.get(rst!!).toNext != null) {
                    var inTime = Intent(context, rst?.let { dataList.get(it).toNext })
                    startActivity(inTime)
                }
            }
        })

        recyclerViewMainFra.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewMainFra.adapter = adapter

        //设置分隔线
//        recyclerView.addItemDecoration( new DividerGridItemDecoration(this ));
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(recyclerViewMainFra)
    }

    override fun setPresenter(): PresenterImgOther {
       return PresenterImgOther()
    }
}