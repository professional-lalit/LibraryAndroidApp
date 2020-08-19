package com.library.app.di.modules

import android.content.Context
import com.inputvalidationmanager.module.ValidationManager
import com.dialogsmanager.module.DialogManager
import com.library.app.screens.main.fragments.books.book_details.BookDetailsUIInteractor
import com.library.app.screens.main.fragments.books.book_details.BookPreviewUIInteractor
import com.library.app.screens.main.fragments.books.book_list.BookListUIInteractor
import com.library.app.screens.main.fragments.home.HomeUIInteractor
import com.library.app.screens.onboarding.change_password.ChangePasswordUIInteractor
import com.library.app.screens.onboarding.forgot_password.ForgotPasswordUIInteractor
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
    fun provideLoginUIInteractor(
        context: Context,
        validationManager: ValidationManager
    ): LoginUIInteractor {
        return LoginUIInteractor(context, validationManager)
    }

    @Provides
    fun provideSignupUIInteractor(
        context: Context,
        validationManager: ValidationManager
    ): SignupUIInteractor {
        return SignupUIInteractor(context, validationManager)
    }

    @Provides
    fun provideForgotPasswordUIInteractor(
        context: Context,
        dialogManager: com.dialogsmanager.module.DialogManager,
        validationManager: ValidationManager
    ): ForgotPasswordUIInteractor {
        return ForgotPasswordUIInteractor(context, dialogManager, validationManager)
    }

    @Provides
    fun provideChangePasswordUIInteractor(
        context: Context,
        dialogManager: com.dialogsmanager.module.DialogManager,
        validationManager: ValidationManager
    ): ChangePasswordUIInteractor {
        return ChangePasswordUIInteractor(context, dialogManager, validationManager)
    }

    @Provides
    fun provideHomeUIInteractor(context: Context): HomeUIInteractor {
        return HomeUIInteractor(context)
    }

    @Provides
    fun provideBookListUIInteractor(context: Context): BookListUIInteractor {
        return BookListUIInteractor(context)
    }

    @Provides
    fun provideBookDetailsUIInteractor(context: Context): BookDetailsUIInteractor {
        return BookDetailsUIInteractor(context)
    }

    @Provides
    fun provideBookPreviewUIInteractor(context: Context): BookPreviewUIInteractor {
        return BookPreviewUIInteractor(context)
    }

}