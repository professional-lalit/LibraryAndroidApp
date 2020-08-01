package com.library.app.screens.main.fragments.books.book_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.library.app.common.BaseViewModel
import com.library.app.models.Book
import com.library.app.repositories.BookRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Lalit N. Hajare, Software Engineer on 01/08/2020
 */
class BookListViewModel @Inject constructor(val mBookRepository: BookRepository) : BaseViewModel() {

    private var mBookList = MediatorLiveData<ArrayList<Book>>()

    var bookList: LiveData<ArrayList<Book>> = mBookList

    fun fetchBooks(category: String?, pageIndex: Int) {
        launch {
            withContext(coroutineContext) {
                mBookList.postValue(mBookRepository.getBooksOfCategory(category, pageIndex))
            }
        }
    }

}