package com.library.app.di.modules

import com.library.app.di.annotations.books.BookDetailScope
import com.library.app.di.annotations.MainScope
import com.library.app.di.annotations.books.BookListScope
import com.library.app.screens.main.fragments.books.book_details.BookDetailsFragment
import com.library.app.screens.main.fragments.books.book_details.BookDetailsUIInteractor
import com.library.app.screens.main.fragments.books.book_list.BookListFragment
import com.library.app.screens.main.fragments.books.book_list.BookListUIInteractor
import com.library.app.screens.main.fragments.home.HomeFragment
import com.library.app.screens.main.fragments.home.HomeUIInteractor
import dagger.Module
import dagger.Provides

/**
 * Created by Lalit N. Hajare, Software Engineer on 29/07/2020
 */
@Module
class MainFragmentBuildersModule {

    @MainScope
    @Provides
    fun contributeHomeFragment(
        homeUIInteractor: HomeUIInteractor,
        bookListFragment: BookListFragment
    ): HomeFragment {
        return HomeFragment(homeUIInteractor, bookListFragment)
    }

    @MainScope
    @Provides
    fun contributeBookDetailsFragment(bookDetailsUIInteractor: BookDetailsUIInteractor): BookDetailsFragment {
        return BookDetailsFragment(bookDetailsUIInteractor)
    }

    @MainScope
    @Provides
    fun contributeBookListFragment(
        bookListUIInteractor: BookListUIInteractor,
        bookDetailsFragment: BookDetailsFragment
    ): BookListFragment {
        return BookListFragment(bookListUIInteractor, bookDetailsFragment)
    }

}