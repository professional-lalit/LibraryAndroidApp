package com.library.app.screens.main.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.models.Book
import com.library.app.models.Category
import com.library.app.networking.Result
import com.library.app.networking.models.BookListResponseSchema
import com.library.app.networking.models.CategoryListResponseSchema
import com.library.app.screens.common.BaseFragment
import com.library.app.screens.main.MainActivity
import com.library.app.screens.main.fragments.books.book_details.BookDetailsFragment
import com.library.app.screens.main.fragments.books.book_list.BookListFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : BaseFragment(),
    HomeUIInteractor.HomeUIController {

    private lateinit var mHomeViewModel: HomeViewModel

    @Inject
    lateinit var mHomeUIInteractor: HomeUIInteractor

    val mBookListFragment = BookListFragment()
    val mBookDetailsFragment = BookDetailsFragment()

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
        mHomeUIInteractor.setAdapters()
        getHomeData()
    }

    private fun getHomeData() {
        mHomeViewModel.trendingBookList.observe(this, Observer { result ->
            mHomeUIInteractor.trendingBooksLoading = false
            if (result is Result.Success) {
                mHomeUIInteractor.showTrendingBooks(result.data as BookListResponseSchema)
            }
        })
        mHomeViewModel.recentBooksVisitedList.observe(this, Observer { result ->
            mHomeUIInteractor.recentVisitedBooksLoading = false
            if (result is Result.Success) {
                mHomeUIInteractor.showRecentVisitedBooks(result.data as BookListResponseSchema)
            }
        })
        mHomeViewModel.categoryList.observe(this, Observer { result ->
            mHomeUIInteractor.categoriesLoading = false
            if (result is Result.Success) {
                mHomeUIInteractor.showCategories(result.data as CategoryListResponseSchema)
            }
        })
        mHomeUIInteractor.trendingBooksLoading = true
        mHomeUIInteractor.recentVisitedBooksLoading = true
        mHomeUIInteractor.categoriesLoading = true
        mHomeViewModel.fetchHomeData()
    }

    override fun openBooksOfCategory(category: Category) {
        val bundle = Bundle()
        bundle.putString(BookListFragment.CATEGORY_NAME, category.name)
        mBookListFragment.arguments = bundle

        GlobalScope.launch {
            delay(500)
            fragmentManager!!.beginTransaction()
                .addToBackStack(MainActivity.MainScreenFragments.BOOK_LIST.name)
                .add(this@HomeFragment.id, mBookListFragment).commit()
        }
    }

    override fun openBookDetails(book: Book) {
        val bundle = Bundle()
        bundle.putString(BookDetailsFragment.ISBN, book.isbn)
        mBookDetailsFragment.arguments = bundle

        GlobalScope.launch {
            delay(500)
            fragmentManager!!.beginTransaction()
                .addToBackStack(MainActivity.MainScreenFragments.BOOK_DETAILS.name)
                .add(this@HomeFragment.id, mBookDetailsFragment).commit()
        }
    }

}