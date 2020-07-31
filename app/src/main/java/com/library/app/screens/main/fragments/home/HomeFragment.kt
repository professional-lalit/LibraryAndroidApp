package com.library.app.screens.main.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.models.Book
import com.library.app.models.Category
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.common.BaseFragment
import com.library.app.screens.main.fragments.books.book_details.BookDetailsActivity
import com.library.app.utils.Utils
import javax.inject.Inject


class HomeFragment @Inject constructor(val mHomeUIInteractor: HomeUIInteractor) : BaseFragment(),
    HomeUIInteractor.HomeUIController {

    private lateinit var mHomeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHomeViewModel = ViewModelProvider(
            this,
            (activity as BaseActivity).providerFactory
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
        Utils.showToast("open book category clicked: " + category.name)
    }

    override fun openBookDetails(book: Book) {
        val intent = Intent(activity, BookDetailsActivity::class.java)
        intent.putExtra("isbn", book.isbn)
        startActivity(intent)
    }

}