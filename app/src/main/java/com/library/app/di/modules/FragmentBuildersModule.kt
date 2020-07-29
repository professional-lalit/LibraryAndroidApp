package com.library.app.di.modules

import com.library.app.di.annotations.AuthScope
import com.library.app.di.annotations.MainScope
import com.library.app.screens.SplashActivity
import com.library.app.screens.main.fragments.home.HomeFragment
import com.library.app.screens.main.fragments.home.HomeUIInteractor
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * Created by Lalit N. Hajare, Software Engineer on 29/07/2020
 */
@Module
class FragmentBuildersModule {

    @MainScope
    @Provides
    fun contributeHomeFragment(homeUIInteractor: HomeUIInteractor): HomeFragment {
        return HomeFragment(homeUIInteractor)
    }

}