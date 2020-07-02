package com.library.app.screens.common

import com.library.app.common.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 8/6/20
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var mScreenNavigator: ScreenNavigator
}