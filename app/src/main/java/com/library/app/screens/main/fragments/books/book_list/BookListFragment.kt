package com.library.app.screens.main.fragments.books.book_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.common.CustomApplication
import com.library.app.common.ViewModelProviderFactory
import com.library.app.di.annotations.books.BookListScope
import com.library.app.models.Book
import com.library.app.networking.Result
import com.library.app.networking.models.BookListResponseSchema
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.common.BaseFragment
import com.library.app.screens.main.MainActivity
import com.library.app.screens.main.fragments.books.book_details.BookDetailsFragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BookListFragment : BaseFragment(), BookListUIInteractor.BookListController {

    @Inject
    lateinit var bookListUIInteractor: BookListUIInteractor


    companion object {
        const val CATEGORY_NAME = "category_name"
    }

    private var mCategory: String? = null

    private var mPageIndex = 1

    private lateinit var mBookListViewModel: BookListViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        mCategory = args?.getString(CATEGORY_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBookListViewModel = ViewModelProvider(
            this, providerFactory
        ).get(BookListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bookListUIInteractor.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookListUIInteractor.mBookListController = this
        loadBookList()
        Log.i("Book List", "book list screen memory: $this")
    }

    private fun loadBookList() {
        mBookListViewModel.bookList.observe(this, Observer { result ->
            if (result is Result.Success) {
                bookListUIInteractor.addBooksInList(result.data as BookListResponseSchema)
            }
        })
        mBookListViewModel.fetchBooks(mCategory, mPageIndex)
    }

    override fun openBookDetails(book: Book) {
        val bundle = Bundle()
        bundle.putString(BookDetailsFragment.ISBN, book.isbn)
        val mBookDetailsFragment = BookDetailsFragment()
        mBookDetailsFragment.arguments = bundle
        fragmentManager!!.beginTransaction()
            .addToBackStack(MainActivity.MainScreenFragments.BOOK_DETAILS.name)
            .add(this.id, mBookDetailsFragment).commit()
    }

    override fun loadMore() {
        mPageIndex += 1
        loadBookList()
    }


}