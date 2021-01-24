package com.gsoor

import android.app.Application
import com.gsoor.base.BaseActivity
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import java.util.*


@HiltAndroidApp
class App :Application(){

//    companion object {
//        var  mPrefs: SharedPreferences?=null
//        var  sInstance : App ?=null
//
//    }
    @Suppress("UNUSED_VARIABLE")
    override fun onCreate() {
        super.onCreate()

        // you can use this instance for DI or get it via Lingver.getInstance() later on
    Lingver.init(this, ChangeLanguage.getLanguage(this))
    }
}