package com.library.app.di.modules

import android.content.Context
import com.library.app.common.CustomApplication
import com.library.app.common.Prefs
import com.library.app.screens.common.DialogManager
import com.library.app.screens.common.ScreenNavigator
import com.library.app.screens.common.ValidationManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class provides app level modules
 */
@Module
class AppModule {


    @Provides
    fun getContext(application: CustomApplication): Context {
        return application.applicationContext
    }

    @Provides
    fun getPreferences(application: CustomApplication): Prefs {
        return Prefs(application)
    }

    @Provides
    fun getNavigator(): ScreenNavigator {
        return ScreenNavigator()
    }

    @Provides
    fun getDialogManager(context: Context): DialogManager {
        return DialogManager(context)
    }

    @Provides
    fun getValidationManager(): ValidationManager {
        return ValidationManager()
    }


}