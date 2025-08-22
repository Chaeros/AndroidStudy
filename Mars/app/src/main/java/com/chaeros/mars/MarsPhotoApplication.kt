package com.chaeros.mars

import android.app.Application
import com.chaeros.mars.data.AppContainer
import com.chaeros.mars.data.DefaultAppContainer

class MarsPhotoApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}