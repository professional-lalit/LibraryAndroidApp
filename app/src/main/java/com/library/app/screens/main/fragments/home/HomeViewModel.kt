package com.library.app.screens.main.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.library.app.common.BaseViewModel
import com.library.app.repositories.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.library.app.networking.Result
import javax.inject.Inject

class HomeViewModel @Inject constructor(val mBookRepository: BookRepository) : BaseViewModel() {

    private val mTrendingBookList = MutableLiveData<Result<Any>>()
    private val mRecentBooksVisitedList = MutableLiveData<Result<Any>>()
    private val mCategoryList = MutableLiveData<Result<Any>>()

    val trendingBookList: LiveData<Result<Any>> = mTrendingBookList
    val recentBooksVisitedList: LiveData<Result<Any>> = mRecentBooksVisitedList
    val categoryList: LiveData<Result<Any>> = mCategoryList

    /**
     * Gets all the data needed to show on Home Screen
     */
    fun fetchHomeData() {
        launch {
            withContext(Dispatchers.IO) {
                mTrendingBookList.postValue(mBookRepository.getTrendingBooks())
            }
            withContext(Dispatchers.IO) {
                val list = mBookRepository.getRecentVisitedBooks()
                mRecentBooksVisitedList.postValue(list)
            }
            withContext(Dispatchers.IO) {
                mCategoryList.postValue(mBookRepository.getBookCategories())
            }
        }
    }

}