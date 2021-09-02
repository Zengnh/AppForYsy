package com.mykotlin.activity.activity_music

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.mykotlin.R
import com.toolmvplibrary.activity_root.ActivityRoot
import kotlinx.android.synthetic.main.activity_music.*

class ActivityMusic : ActivityRoot<PresenterMusic>() {
    override fun setPresenter(): PresenterMusic {
        return PresenterMusic()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        initView()
        initEvent()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlay.release()
    }

    private fun initEvent() {
        text9Key.setOnClickListener {
            presenter.init9Data()
            adapter?.notifyDataSetChanged()
        }

        text26Key.setOnClickListener {
            presenter.init26Data()
            adapter?.notifyDataSetChanged()
        }
    }

    val musicPlay = MediaJsHandler(this)
    private fun initData() {
        presenter.init26Data()
        adapter?.notifyDataSetChanged()
    }

    var gridLayoutManager: GridLayoutManager? = null
    var numGrid = 9;
    var adapter: AdapterRecyclerMusic? = null
    private fun initView() {
        gridLayoutManager = GridLayoutManager(this, numGrid)
//        gridLayoutManager?.spanCount=numGrid
        recyclerMusic.layoutManager = gridLayoutManager

        adapter = AdapterRecyclerMusic(presenter.datalist, object : ItemClick {
            override fun itemClick(item: ItemMusic?) {
                item?.let { musicPlay.playLocalSound(it.itemName) }
            }
        })

        recyclerMusic.adapter = adapter
    }

}