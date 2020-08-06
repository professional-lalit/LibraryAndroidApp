package com.library.app.di.modules

import com.library.app.screens.main.fragments.books.book_details.BookDetailsFragment
import com.library.app.screens.main.fragments.books.book_details.BookPreviewFragment
import com.library.app.screens.main.fragments.books.book_list.BookListFragment
import com.library.app.screens.main.fragments.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Lalit N. Hajare, Software Engineer on 07/08/2020
 */

@Module
abstract class MainFragmentsModule {

    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun provideBookListFragment(): BookListFragment

    @ContributesAndroidInjector
    abstract fun provideBookDetailsFragment(): BookDetailsFragment

    @ContributesAndroidInjector
    abstract fun provideBookPreviewFragment(): BookPreviewFragment

}