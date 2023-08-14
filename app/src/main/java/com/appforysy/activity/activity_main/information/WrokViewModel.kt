package com.appforysy.activity.activity_main.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WrokViewModel : ViewModel() {
    fun initData() {
        var item=ItemInfo()
        item.itemInfo="第1步"
        item.contentRemark="xielslijgielse"
        item.contentInfo="内容1"
        datalist.add(item)

        var item2=ItemInfo()
        item2.itemInfo="第2步"
        item2.contentRemark="xielslijgielse"
        item2.contentInfo="内容2"
        datalist.add(item2)

        var item3=ItemInfo()
        item3.itemInfo="第3步"
        item3.contentRemark="测试跑马灯内容。请查收"
        item3.contentInfo="内容3"
        datalist.add(item3)

        var item4=ItemInfo()
        item4.itemInfo="第4步"
        item4.contentRemark="xielslijgielse"
        item4.contentInfo="内容3"
        datalist.add(item4)
        datalist.add(item4)
        datalist.add(item4)
        datalist.add(item4)

//        ----------------------------------------------------------------------
        var aitem=ItemInfo()
        aitem.itemInfo="第1步"
        aitem.contentRemark="xielslijgielse"
        aitem.contentInfo="内容1"
        datalistDayy.add(aitem)

        var aitem2=ItemInfo()
        aitem2.itemInfo="第2步"
        aitem2.contentRemark="xielslijgielse"
        aitem2.contentInfo="内容2"
        datalistDayy.add(aitem2)

        var aitem3=ItemInfo()
        aitem3.itemInfo="第3步"
        aitem3.contentRemark="测试跑马灯内容。请查收"
        aitem3.contentInfo="内容3"
        datalistDayy.add(aitem3)

        var aitem4=ItemInfo()
        aitem4.itemInfo="第4步"
        aitem4.contentRemark="xielslijgielse"
        aitem4.contentInfo="内容3"
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
        datalistDayy.add(aitem4)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

     var datalist= arrayListOf<ItemInfo>()
     var datalistDayy= arrayListOf<ItemInfo>()
}