package com.library.app.di.modules

import androidx.lifecycle.ViewModel
import com.library.app.di.annotations.BookDetailScope
import com.library.app.di.annotations.MainScope
import com.library.app.di.annotations.ViewModelKey
import com.library.app.screens.main.fragments.books.book_details.BookDetailsViewModel
import com.library.app.screens.main.fragments.home.HomeViewModel
import com.library.app.screens.onboarding.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 */
@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @MainScope
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @MainScope
    @BookDetailScope
    @ViewModelKey(BookDetailsViewModel::class)
    abstract fun bindBookDetailsViewModel(bookDetailsViewModel: BookDetailsViewModel): ViewModel


}