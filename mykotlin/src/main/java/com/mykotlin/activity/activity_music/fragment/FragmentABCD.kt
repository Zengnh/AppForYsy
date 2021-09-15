package com.mykotlin.activity.activity_music.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mykotlin.R
import com.mykotlin.activity.activity_music.AdapterRecyclerMusic
import com.mykotlin.activity.activity_music.InterFaceMusic
import com.mykotlin.activity.activity_music.ItemClick
import com.mykotlin.activity.activity_music.ItemMusic
import com.toolmvplibrary.activity_root.FragmenRoot

class FragmentABCD : FragmenRoot<PrementerFragmentMusic>(), InterFaceMusic {

    override fun createPresenter(): PrementerFragmentMusic {
        return PrementerFragmentMusic()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view123 = inflater.inflate(R.layout.fragment_music1234, container, false)
        recyclerView = view123.findViewById(R.id.recyclerView);
        return view123
    }

    var recyclerView: RecyclerView? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    var gridLayoutManager: GridLayoutManager? = null
    var numGrid = 9;
    var adapter: AdapterRecyclerMusic? = null
    private fun initView() {
        gridLayoutManager = GridLayoutManager(context, numGrid)
//        gridLayoutManager?.spanCount=numGrid
        recyclerView?.layoutManager = gridLayoutManager
        presenter.init26Data()
        adapter = AdapterRecyclerMusic(presenter.datalist, object : ItemClick {
            override fun itemClick(item: ItemMusic?) {
                item?.let { presenter.play(it.itemName) }
            }
        })
        var hight: Int? = recyclerView?.height;
        if (hight == null || hight <= 0) {
            hight = 200;
        }
        adapter!!.setItemHight(hight!!)
        recyclerView?.adapter = adapter
    }
}