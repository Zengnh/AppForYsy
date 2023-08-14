package com.appforysy.activity.activity_main.information

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appforysy.R
import com.tencent.bugly.proguard.v

/**
 * author:znh
 * time:2023/7/7
 * desc:
 */
class AdapterInfoDay(data: ArrayList<ItemInfo>) :
    RecyclerView.Adapter<AdapterInfoDay.HolderInfo>() {
    private var datalist = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderInfo {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_information_second, parent, false)
        return HolderInfo(view)
    }
    override fun onBindViewHolder(holder: HolderInfo, position: Int) {
        var item=datalist.get(position)
        holder.itemTextView.setText(item.itemInfo)
        holder.contentRemark.setText(item.contentRemark)
        holder.contentInfo.setText(item.contentInfo)
    }
    override fun getItemCount(): Int {
        return datalist.size
    }

    class HolderInfo(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var contentRemark: TextView = itemView.findViewById(R.id.contentRemark)
        var contentInfo: TextView = itemView.findViewById(R.id.contentInfo)
        var itemTextView: TextView = itemView.findViewById(R.id.itemTextView)
    }
}