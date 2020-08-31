package com.library.app.di.modules

import com.library.app.di.annotations.*
import com.library.app.screens.SplashActivity
import com.library.app.screens.main.MainActivity
import com.library.app.screens.main.BookDetailsActivity
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
    @ContributesAndroidInjector
    abstract fun contributeChangePasswordActivity(): ChangePasswordActivity

    @AuthScope
    @ContributesAndroidInjector
    abstract fun contributeForgotPasswordActivity(): ForgotPasswordActivity

    @AuthScope
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @AuthScope
    @ContributesAndroidInjector
    abstract fun contributeSignupActivity(): SignupActivity

    @MainScope
    @ContributesAndroidInjector(modules = [MainViewModelsModule::class, MainFragmentsModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @MainScope
    @ContributesAndroidInjector
    abstract fun contributeBookDetailsActivity(): BookDetailsActivity

}