package com.mykotlin

import android.app.Application
import com.mykotlin.utils.ToolKtNotiManager

class KtApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ToolKtNotiManager.instance.createNotificationChannel(this)

    }
}