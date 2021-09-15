package com.mykotlin.activity.activity_music.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mykotlin.R
import com.mykotlin.activity.activity_music.*
import com.toolmvplibrary.activity_root.FragmenRoot

class FragmentDoReMi  : FragmenRoot<PrementerFragmentMusic>() , InterFaceMusic {

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
        sourcePool=ToolSourcePool.getInstance(context)
    }


    var sourcePool: ToolSourcePool?=null;

    var gridLayoutManager: GridLayoutManager? = null
    var numGrid = 7;
    var adapter: AdapterRecyclerMusic? = null
    private fun initView() {
        gridLayoutManager = GridLayoutManager(context, numGrid)
//        gridLayoutManager?.spanCount=numGrid
        recyclerView?.layoutManager = gridLayoutManager
        presenter.init7Data()
        adapter = AdapterRecyclerMusic(presenter.datalist, object : ItemClick {
            override fun itemClick(item: ItemMusic?) {
                item?.let {
                    sourcePool?.playSound(it.sourceMusic)
//                    presenter.play(it.itemName)
                }

            }
        })
        recyclerView?.adapter = adapter
    }

}