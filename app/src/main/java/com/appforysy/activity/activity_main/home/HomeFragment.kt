package com.appforysy.activity.activity_main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.appforysy.R
import com.appforysy.activity.activity_detail.Activity_Detail
import com.appforysy.activity.activity_img_main.AdapterHomeFra
import com.appforysy.activity.activity_img_main.ItemMain
import com.appforysy.activity.activity_main.ActivityMain
import com.toolmvplibrary.activity_root.ItemClick
import com.toolmvplibrary.tool_app.ToolLanguage
import com.toolmvplibrary.tool_app.ToolPreferences
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        initEvent();
        initData();
    }

    var dataList = ArrayList<ItemMain>()
    lateinit var adapter: AdapterHomeFra
    fun initData() {
        dataList.add(ItemMain(1, R.mipmap.img_guide_1))
        dataList.add(ItemMain(1, R.mipmap.img_guide_2))
        dataList.add(ItemMain(1, R.mipmap.img_guide_3))
        dataList.add(ItemMain(1, R.mipmap.img_guide_4))

        adapter = AdapterHomeFra(dataList, object : ItemClick<Int> {
            override fun itemClick(rst: Int?) {
            }
        })

        recyclerViewMainFra.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewMainFra.adapter = adapter

        //设置分隔线
//        recyclerView.addItemDecoration( new DividerGridItemDecoration(this ));
//设置增加或删除条目的动画
//        recyclerView.setItemAnimator( new DefaultItemAnimator());
//        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
//        //将SnapHelper attach 到RecyclrView
//        linearSnapHelper.attachToRecyclerView(recyclerView);
        val pagerSnapHelper = PagerSnapHelper()
        //将SnapHelper attach 到RecyclrView
        //将SnapHelper attach 到RecyclrView
        pagerSnapHelper.attachToRecyclerView(recyclerViewMainFra)
    }

    fun initEvent() {
        getPick.setOnClickListener {
//            ToolLan.changeAppLanguage(context, "en")
            ToolPreferences.setString(context, "lan", "en");
            ToolLanguage.changeLanFinish(
                context,
                ActivityMain::class.java
            );
        }

        getToDetail.setOnClickListener {
            var intent = Intent(context, Activity_Detail::class.java)
            startActivity(intent)

        }
        buttonLoad.setOnClickListener {

//            ToolLan.changeAppLanguage(context, "zh")

            ToolPreferences.setString(context, "lan", "zn");
            ToolLanguage.changeLanFinish(
                context,
                ActivityMain::class.java
            );

//            var netPath="https://app.yousucloud.com/uploadfiles/im/apk/c5cf0f7f828a4761bcbfc029f39949e1.apk";
////            var location= Environment.getExternalStorageDirectory().parent+"/ysy_pack";
//            var location2= Environment.getExternalStorageDirectory().path + "/" + context?.packageName;
//            var location=context?.getApplicationContext()?.getFilesDir()?.getAbsolutePath()+"/ysy_pack";
//            var file= File(location);
//            Log.i("znh_download","path="+location)
//            if(!file.exists()){
//                file.mkdirs();
//            }
////            var file2=File(location2+"/"+System.currentTimeMillis()+"logo.txt");
//
//            var locationFile=location+"/"+System.currentTimeMillis()+"ysyapp.apk";
////            var fileApk=File(locationFile);
////            if(!fileApk.exists()){
////                fileApk.createNewFile();
////            }
//            ToolFileDownLoader.with().downLoadFile(netPath,locationFile,object:
//                InterListener {
//                override fun resultFinish(succ: ResultDownLoader?) {
//                    var fileApkLoad=File(succ!!.locatiFile);
//                    Log.i("znh_download","code "+" \n "+succ!!.code+"    "+fileApkLoad.length()+"  "+succ.netPath+" \n "+succ.locatiFile)
//                   if(succ.code==2){
//                       text_home.setText("已下载"+(succ.cutSize/1024).toString()+"kb")
//                   }else  if(succ.code==0){
////                        ToolInstallApk.insertApk(activity,succ.locatiFile, BuildConfig.APPLICATION_ID)
//                        ToolInstallApk.insertApk(activity,succ.locatiFile, context?.packageName)
//                   }
//                }
//            })
        }
    }


}
