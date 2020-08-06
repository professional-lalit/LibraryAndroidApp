package com.library.app.screens.main.fragments.books.book_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.R
import com.library.app.screens.common.BaseFragment
import com.library.app.screens.main.MainActivity
import javax.inject.Inject

class BookPreviewFragment @Inject constructor(val bookPreviewUIInteractor: BookPreviewUIInteractor) :
    BaseFragment(), BookPreviewUIInteractor.BookPreviewController {

    lateinit var mBookDetailsViewModel: BookDetailsViewModel

    private var mISBN: String? = ""

    companion object {
        const val ISBN = "isbn"
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        mISBN = args!!.getString(BookDetailsFragment.ISBN, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBookDetailsViewModel = ViewModelProvider(
            this,
            (activity as MainActivity).providerFactory
        ).get(BookDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bookPreviewUIInteractor.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBookDetailsViewModel.bookPreview.observe(this, Observer { bookPreview ->
            bookPreviewUIInteractor.loading = false
            bookPreviewUIInteractor.showPreview(bookPreview)
        })
        bookPreviewUIInteractor.loading = true
        mBookDetailsViewModel.fetchBookPreview(mISBN!!)
    }

}