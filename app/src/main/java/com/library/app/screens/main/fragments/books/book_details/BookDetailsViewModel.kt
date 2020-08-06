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

/**
 * Created by Lalit N. Hajare, Software Engineer on 30/07/2020
 */
class BookDetailsViewModel @Inject constructor(val mBookRepository: BookRepository) :
    BaseViewModel() {

    private var mBookDetails = MutableLiveData<BookDetails>()
    private var mBookPreview = MutableLiveData<BookPreview>()

    var bookDetails: LiveData<BookDetails> = mBookDetails
    var bookPreview: LiveData<BookPreview> = mBookPreview

    fun fetchBookDetails(isbn: String) {
        launch {
            withContext(Dispatchers.IO) {
                mBookDetails.postValue(mBookRepository.getBookDetails(isbn))
            }
        }
    }

    fun fetchBookPreview(isbn: String) {
        launch {
            withContext(Dispatchers.IO) {
                mBookPreview.postValue(mBookRepository.getBookPreview(isbn))
            }
        }
    }
}