package com.library.app.common

import android.content.Context
import android.content.Intent
import androidx.multidex.MultiDex
import com.library.app.di.components.AppComponent
import com.library.app.di.components.DaggerAppComponent
import com.library.app.screens.onboarding.login.LoginActivity
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


open class CustomApplication : DaggerApplication() {

    private lateinit var mAppComponent: AppComponent

    companion object {
        var appInstance: CustomApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        mAppComponent = DaggerAppComponent.builder().application(this).build()
        return mAppComponent
    }

    fun getAppComponent(): AppComponent {
        return mAppComponent
    }

    fun logout() {
        val prefs = mAppComponent.getPrefs()
        prefs.clearData()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        startActivity(intent)
    }

}