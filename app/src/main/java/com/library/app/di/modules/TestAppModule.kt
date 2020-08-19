package com.library.app.di.modules

import android.content.Context
import com.inputvalidationmanager.module.ValidationManager
import com.library.app.common.Prefs
import com.library.app.common.UITestApplication
import com.dialogsmanager.module.DialogManager
import com.library.app.screens.common.ScreenNavigator
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
    fun getDialogManager(context: Context): com.dialogsmanager.module.DialogManager {
        return com.dialogsmanager.module.DialogManager(context)
    }

    @Singleton
    @Provides
    fun getValidationManager(): ValidationManager {
        return ValidationManager()
    }


}