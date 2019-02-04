package com.panatchai.abcnews

import android.app.Application
import com.panatchai.abcnews.data.util.RequestUtil
import com.panatchai.abcnews.util.DependencyModule

class FeedApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // initialize the singleton once
        DependencyModule.getInstance(this)
        RequestUtil.getInstance(this)
    }
}