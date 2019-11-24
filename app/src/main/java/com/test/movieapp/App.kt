package com.test.movieapp

import android.app.Application
import com.test.movieapp.di.AppComponent
import com.test.movieapp.di.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var mAppComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    private fun initComponent() {
        mAppComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}
