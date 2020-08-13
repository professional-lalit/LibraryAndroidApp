package com.library.app.screens.main.fragments.books.book_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.library.app.common.BaseViewModel
import com.library.app.models.BookDetails
import com.library.app.models.BookPreview
import com.library.app.repositories.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.library.app.networking.Result

/**
 * Created by Lalit N. Hajare, Software Engineer on 30/07/2020
 */
class BookDetailsViewModel @Inject constructor(val mBookRepository: BookRepository) :
    BaseViewModel() {

    private var mBookDetails = MutableLiveData<Result<Any>>()
    private var mBookPreview = MutableLiveData<Result<Any>>()

    var bookDetails: LiveData<Result<Any>> = mBookDetails
    var bookPreview: LiveData<Result<Any>> = mBookPreview

    fun fetchBookDetails(isbn: String) {
        launch {
            withContext(Dispatchers.IO) {
                mBookDetails.postValue(mBookRepository.getBookDetails(isbn))
            }
        }
    }

}