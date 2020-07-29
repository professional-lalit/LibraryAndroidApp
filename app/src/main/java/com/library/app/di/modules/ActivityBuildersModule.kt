package com.library.app.di.modules

import com.library.app.di.annotations.*
import com.library.app.screens.SplashActivity
import com.library.app.screens.main.MainActivity
import com.library.app.screens.onboarding.change_password.ChangePasswordActivity
import com.library.app.screens.onboarding.forgot_password.ForgotPasswordActivity
import com.library.app.screens.onboarding.login.LoginActivity
import com.library.app.screens.onboarding.signup.SignupActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity


    @AuthScope
    @ChangePasswordScope
    @ContributesAndroidInjector
    abstract fun contributeChangePasswordActivity(): ChangePasswordActivity

    @AuthScope
    @ForgotPasswordScope
    @ContributesAndroidInjector
    abstract fun contributeForgotPasswordActivity(): ForgotPasswordActivity

    @AuthScope
    @LoginScope
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @AuthScope
    @SignupScope
    @ContributesAndroidInjector
    abstract fun contributeSignupActivity(): SignupActivity

    @MainScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}