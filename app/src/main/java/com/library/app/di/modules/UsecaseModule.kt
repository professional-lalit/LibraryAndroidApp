package com.library.app.di.modules

import android.content.Context
import com.library.app.screens.onboarding.change_password.ChangePasswordValidationUsecase
import com.library.app.screens.onboarding.login.LoginValidationUsecase
import com.library.app.screens.onboarding.signup.SignupValidationUsecase
import dagger.Module
import dagger.Provides

@Module
class UsecaseModule {

    @Provides
    fun getSignupValidationUsecase(): SignupValidationUsecase {
        return SignupValidationUsecase()
    }

    @Provides
    fun getLoginValidationUsecase(): LoginValidationUsecase {
        return LoginValidationUsecase()
    }

    @Provides
    fun getChangePasswordValidationUsecase(): ChangePasswordValidationUsecase {
        return ChangePasswordValidationUsecase()
    }

}