package com.library.app.screens.main.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.library.app.common.BaseViewModel
import com.library.app.models.Book
import com.library.app.models.Category
import com.library.app.networking.models.CategoryResponseSchema
import com.library.app.repositories.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(val mBookRepository: BookRepository) : BaseViewModel() {

    private val mTrendingBookList = MutableLiveData<ArrayList<Book>>()
    private val mRecentBooksVisitedList = MutableLiveData<ArrayList<Book>>()
    private val mCategoryList = MutableLiveData<ArrayList<Category>>()

    val trendingBookList: LiveData<ArrayList<Book>> = mTrendingBookList
    val recentBooksVisitedList: LiveData<ArrayList<Book>> = mRecentBooksVisitedList
    val categoryList: LiveData<ArrayList<Category>> = mCategoryList

    /**
     * Gets all the data needed to show on Home Screen
     */
    fun fetchHomeData() {
        launch {
            withContext(Dispatchers.IO) {
                mTrendingBookList.postValue(mBookRepository.getTrendingBooks())
            }
            withContext(Dispatchers.IO) {
                mRecentBooksVisitedList.postValue(mBookRepository.getRecentVisitedBooks())
            }
            withContext(Dispatchers.IO) {
                mCategoryList.postValue(mBookRepository.getBookCategories())
            }
        }
    }

}