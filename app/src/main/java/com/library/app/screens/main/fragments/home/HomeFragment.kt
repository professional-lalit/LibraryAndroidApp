package com.library.app.screens.main.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.common.ViewModelProviderFactory
import com.library.app.models.Book
import com.library.app.models.Category
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.common.BaseFragment
import com.library.app.screens.main.MainActivity
import com.library.app.screens.main.fragments.books.book_details.BookDetailsActivity
import com.library.app.screens.main.fragments.books.book_details.BookDetailsFragment
import com.library.app.screens.main.fragments.books.book_list.BookListFragment
import com.library.app.utils.Utils
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment(),
    HomeUIInteractor.HomeUIController {

    private lateinit var mHomeViewModel: HomeViewModel

    @Inject
    lateinit var mHomeUIInteractor: HomeUIInteractor

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHomeViewModel = ViewModelProvider(
            this, providerFactory
        ).get(HomeViewModel::class.java)
        mHomeUIInteractor.homeUIController = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return mHomeUIInteractor.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHomeData()
    }

    private fun getHomeData() {
        mHomeViewModel.trendingBookList.observe(this, Observer { list ->
            mHomeUIInteractor.trendingBooksLoading = false
            mHomeUIInteractor.showTrendingBooks(list)
        })
        mHomeViewModel.recentBooksVisitedList.observe(this, Observer { list ->
            mHomeUIInteractor.recentVisitedBooksLoading = false
            mHomeUIInteractor.showRecentVisitedBooks(list)
        })
        mHomeViewModel.categoryList.observe(this, Observer { list ->
            mHomeUIInteractor.categoriesLoading = false
            mHomeUIInteractor.showCategories(list)
        })
        mHomeUIInteractor.trendingBooksLoading = true
        mHomeUIInteractor.recentVisitedBooksLoading = true
        mHomeUIInteractor.categoriesLoading = true
        mHomeViewModel.fetchHomeData()
    }

    override fun openBooksOfCategory(category: Category) {
        val bundle = Bundle()
        bundle.putString(BookListFragment.CATEGORY_NAME, category.name)
        val bookListFragment = BookListFragment()
        bookListFragment.arguments = bundle
        fragmentManager!!.beginTransaction()
            .addToBackStack(MainActivity.MainScreenFragments.BOOK_LIST.name)
            .add(this.id, bookListFragment).commit()
    }

    override fun openBookDetails(book: Book) {
        val bundle = Bundle()
        bundle.putString(BookDetailsFragment.ISBN, book.isbn)
        val bookDetailsFragment = BookDetailsFragment()
        bookDetailsFragment.arguments = bundle
        fragmentManager!!.beginTransaction()
            .addToBackStack(MainActivity.MainScreenFragments.BOOK_DETAILS.name)
            .add(this.id, bookDetailsFragment).commit()
    }

}