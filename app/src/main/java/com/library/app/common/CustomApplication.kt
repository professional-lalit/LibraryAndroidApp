package com.library.app.common

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.library.app.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

open class CustomApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    open fun getApiUrl(): String {
        return Constants.BASE_URL
    }

}