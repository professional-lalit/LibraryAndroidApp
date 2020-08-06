package com.library.app.screens.main.fragments.books.book_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.R
import com.library.app.common.ViewModelProviderFactory
import com.library.app.screens.main.MainActivity
import com.library.app.utils.Utils
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BookDetailsFragment : DaggerFragment(),
    BookDetailsUIInteractor.BookDetailsController {


    @Inject
    lateinit var mBookDetailsUIInteractor: BookDetailsUIInteractor

    @Inject
    lateinit var mBookPreviewFragment: BookPreviewFragment


    lateinit var mBookDetailsViewModel: BookDetailsViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

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
            this, providerFactory
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
        val bundle = Bundle()
        bundle.putString(BookPreviewFragment.ISBN, mISBN)
        mBookPreviewFragment.arguments = bundle
        fragmentManager!!.beginTransaction()
            .addToBackStack(MainActivity.MainScreenFragments.BOOK_PREVIEW.name)
            .add(this.id, mBookPreviewFragment).commit()
    }

    override fun addToCart() {

    }

}