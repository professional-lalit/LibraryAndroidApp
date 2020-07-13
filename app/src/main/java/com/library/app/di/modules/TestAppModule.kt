package com.library.app.di.modules

import android.content.Context
import com.library.app.common.CustomApplication
import com.library.app.common.Prefs
import com.library.app.common.UITestApplication
import com.library.app.screens.common.DialogManager
import com.library.app.screens.common.ScreenNavigator
import com.library.app.screens.common.ValidationManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestAppModule {

    @Provides
    fun getContext(application: UITestApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun getPreferences(application: UITestApplication): Prefs {
        return Prefs(application)
    }

    @Singleton
    @Provides
    fun getNavigator(): ScreenNavigator {
        return ScreenNavigator()
    }

    @Singleton
    @Provides
    fun getDialogManager(context: Context): DialogManager {
        return DialogManager(context)
    }

    @Singleton
    @Provides
    fun getValidationManager(): ValidationManager {
        return ValidationManager()
    }


}