package com.library.app.screens.main.fragments.books.book_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.R
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.main.fragments.home.HomeViewModel
import com.library.app.utils.Utils
import javax.inject.Inject

class BookDetailsFragment @Inject constructor(val mBookDetailsUIInteractor: BookDetailsUIInteractor) :
    Fragment(),
    BookDetailsUIInteractor.BookDetailsController {

    private lateinit var mBookDetailsViewModel: BookDetailsViewModel
    private var mISBN: String? = ""

    companion object {
        const val ISBN = "isbn"
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        mISBN = args!!.getString(ISBN, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBookDetailsViewModel = ViewModelProvider(
            this,
            (activity as BaseActivity).providerFactory
        ).get(BookDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return mBookDetailsUIInteractor.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBookDetailsUIInteractor.mBookDetailsController = this
        loadBookDetails()
    }

    private fun loadBookDetails() {
        mBookDetailsViewModel.bookDetails.observe(this, Observer { bookDetails ->
            mBookDetailsUIInteractor.loading = false
            if (bookDetails != null) {
                mBookDetailsUIInteractor.showBookDetails(bookDetails)
            } else {
                Utils.showToast(getString(R.string.book_not_found))
                requireActivity().finish()
            }
        })
        mBookDetailsUIInteractor.loading = true
        mBookDetailsViewModel.fetchBookDetails(mISBN!!)
    }

    override fun openBookPreview() {

    }

    override fun addToCart() {

    }

}