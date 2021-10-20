package com.mykotlin.activity.activity_music

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.mykotlin.R
import com.mykotlin.activity.activity_music.fragment.Fragment1234
import com.mykotlin.activity.activity_music.fragment.FragmentABCD
import com.mykotlin.activity.activity_music.fragment.FragmentDoReMi
import com.mykotlin.databinding.ActivityMusicBinding
import com.toolmvplibrary.activity_root.ActivityRoot
class ActivityMusic : ActivityRoot<PresenterMusic>() {
    override fun setPresenter(): PresenterMusic {
        return PresenterMusic()
    }
    lateinit var binding: ActivityMusicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMusicBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_music)
        setContentView(binding.root)
        initView()
        initEvent()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlay.release()
    }

    fun userFragment(type: Int) {
        val fragment = supportFragmentManager.beginTransaction()
        if (type == 0) {
            fragment.replace(R.id.fragmentContent, Fragment1234())
        } else if (type == 1) {
            fragment.replace(R.id.fragmentContent, FragmentABCD())
        } else {
            fragment.replace(R.id.fragmentContent, FragmentDoReMi())
        }
        fragment.commit()
    }


    private fun initEvent() {

    }


//    fun vars(varage v:Int){
//
//    }

    val musicPlay = MediaJsHandler(this)
    private fun initData() {
        userFragment(2)
    }

    private val title = arrayOf("26字母", "9数字", "音乐")
    private fun initView() {
        val xiaoxinxin =
            "1 1 5 5 6 6 5 -- 4 4 3 3 2 2 1 -- 5 5 4 4 3 3 2 -- 5 5 4 4 3 3 2 -- 1 1 5 5 6 6 4 -- 4 4 3 3 2 2 1 --"
        binding.musicContent.setText(xiaoxinxin)
//        tabayView.title
        for (i in title.indices) {
            binding.tabayView.addTab(binding.tabayView.newTab())
            binding.tabayView.getTabAt(i)?.setText(title[i])
            if (i == 2) {
                binding.tabayView.getTabAt(i)?.select()
            }
        }

        binding.tabayView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val str = tab?.text
                if (str != null) {
                    if (str.equals("26字母")) {
                        userFragment(1)
                    } else if (str.equals("9数字")) {
                        userFragment(0)
                    } else {
                        userFragment(2)
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

}