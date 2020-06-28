package com.library.app.di.modules

import android.content.Context
import com.library.app.di.annotations.ChangePasswordScope
import com.library.app.di.annotations.ForgotPasswordScope
import com.library.app.di.annotations.LoginScope
import com.library.app.di.annotations.SignupScope
import com.library.app.screens.common.DialogManager
import com.library.app.screens.onboarding.change_password.ChangePasswordUIInteractor
import com.library.app.screens.onboarding.forgot_password.ForgotPasswordUIInteractor
import com.library.app.screens.onboarding.login.LoginActivity
import com.library.app.screens.onboarding.login.LoginUIInteractor
import com.library.app.screens.onboarding.signup.SignupUIInteractor
import dagger.Module
import dagger.Provides

/**
 * Created by Lalit Hajare, Software Engineer on 15/6/20
 * This class provides all the UIInteractor classes needed to manage the UI layer
 */
@Module
class UIInteractorModule {

    @Provides
    @LoginScope
    fun provideLoginUIInteractor(context: Context): LoginUIInteractor {
        return LoginUIInteractor(context)
    }

    @Provides
    @SignupScope
    fun provideSignupUIInteractor(context: Context): SignupUIInteractor {
        return SignupUIInteractor(context)
    }

    @Provides
    @ForgotPasswordScope
    fun provideForgotPasswordUIInteractor(
        context: Context,
        dialogManager: DialogManager
    ): ForgotPasswordUIInteractor {
        return ForgotPasswordUIInteractor(context, dialogManager)
    }

    @Provides
    @ChangePasswordScope
    fun provideChangePasswordUIInteractor(
        context: Context,
        dialogManager: DialogManager
    ): ChangePasswordUIInteractor {
        return ChangePasswordUIInteractor(context, dialogManager)
    }


}