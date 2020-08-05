package com.library.app.di.modules

import android.content.Context
import com.library.app.di.annotations.*
import com.library.app.di.annotations.books.BookDetailScope
import com.library.app.di.annotations.books.BookListScope
import com.library.app.screens.common.DialogManager
import com.library.app.screens.common.ValidationManager
import com.library.app.screens.main.fragments.books.book_details.BookDetailsUIInteractor
import com.library.app.screens.main.fragments.books.book_list.BookListUIInteractor
import com.library.app.screens.main.fragments.home.HomeUIInteractor
import com.library.app.screens.onboarding.change_password.ChangePasswordUIInteractor
import com.library.app.screens.onboarding.forgot_password.ForgotPasswordUIInteractor
import com.library.app.screens.onboarding.login.LoginUIInteractor
import com.library.app.screens.onboarding.signup.SignupUIInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Lalit Hajare, Software Engineer on 15/6/20
 * This class provides all the UIInteractor classes needed to manage the UI layer
 */
@Module
class UIInteractorModule {

    @Singleton
    @Provides
    fun provideLoginUIInteractor(
        context: Context,
        validationManager: ValidationManager
    ): LoginUIInteractor {
        return LoginUIInteractor(context, validationManager)
    }

    @Singleton
    @Provides
    fun provideSignupUIInteractor(
        context: Context,
        validationManager: ValidationManager
    ): SignupUIInteractor {
        return SignupUIInteractor(context, validationManager)
    }

    @Singleton
    @Provides
    fun provideForgotPasswordUIInteractor(
        context: Context,
        dialogManager: DialogManager,
        validationManager: ValidationManager
    ): ForgotPasswordUIInteractor {
        return ForgotPasswordUIInteractor(context, dialogManager, validationManager)
    }

    @Singleton
    @Provides
    fun provideChangePasswordUIInteractor(
        context: Context,
        dialogManager: DialogManager,
        validationManager: ValidationManager
    ): ChangePasswordUIInteractor {
        return ChangePasswordUIInteractor(context, dialogManager, validationManager)
    }

    @Singleton
    @Provides
    fun provideHomeUIInteractor(context: Context): HomeUIInteractor {
        return HomeUIInteractor(context)
    }

    @Singleton
    @Provides
    fun provideBookDetailsUIInteractor(context: Context): BookDetailsUIInteractor {
        return BookDetailsUIInteractor(context)
    }


    @Singleton
    @Provides
    fun provideBookListUIInteractor(context: Context): BookListUIInteractor {
        return BookListUIInteractor(context)
    }

}