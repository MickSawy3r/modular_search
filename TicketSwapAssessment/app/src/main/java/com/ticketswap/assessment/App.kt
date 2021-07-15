package com.ticketswap.assessment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.ticketswap.cache.CacheLibrary

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        CacheLibrary.init(this)
    }
}
