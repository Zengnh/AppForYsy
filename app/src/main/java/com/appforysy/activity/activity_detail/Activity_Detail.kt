package com.appforysy.activity.activity_detail


import com.appforysy.R
import com.toolmvplibrary.activity_root.ActivityRootInit

class Activity_Detail : ActivityRootInit<PresenterDetail>() {
    override fun setCutLayout(): Int {
        return R.layout.activity_detail;
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun setPresenter(): PresenterDetail {
        return PresenterDetail()
    }


}