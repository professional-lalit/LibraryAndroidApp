package com.library.app.screens.main.fragments.books.book_details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import com.library.app.BR
import com.library.app.R
import com.library.app.common.ImageLoader
import com.library.app.databinding.FragmentBookDetailsBinding
import com.library.app.models.BookDetails
import javax.inject.Inject

/**
 * Created by Lalit N. Hajare, Software Engineer on 30/07/2020
 */
class BookDetailsUIInteractor @Inject constructor(val mContext: Context) : BaseObservable() {

    var mBookDetailsController: BookDetailsController? = null
    var mBinding: FragmentBookDetailsBinding? = null

    /**
     * Provides the view to which this interactor operates
     */
    fun getRootView(): View {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.fragment_book_details,
            null,
            false
        )
        mBinding?.bookDetailsUIInteractor = this
        return mBinding?.root!!
    }

    @Bindable
    var loading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    fun showBookDetails(bookDetails: BookDetails) {
        ImageLoader.getImage(mBinding!!.imgBook, bookDetails.thumbnailUrl)
        if (bookDetails.title!!.isNotEmpty()) {
            mBinding!!.txtBookTitle.text = bookDetails.title!!
        }
        mBinding!!.txtBookPagecount.text = bookDetails.pageCount.toString()
        if (bookDetails.isbn!!.isNotEmpty()) {
            mBinding!!.txtBookIsbn.text = bookDetails.isbn
        }
        if (bookDetails.categories!!.isNotEmpty()) {
            mBinding!!.txtBookCategory.text = bookDetails.categories.toString()
        }
        if (bookDetails.publishedDate!!.isNotEmpty()) {
            mBinding!!.txtBookPublishDate.text = bookDetails.publishedDate
        }
        if (bookDetails.authors!!.isNotEmpty()) {
            mBinding!!.txtBookAuthors.text = bookDetails.authors.toString()
        }
        if (bookDetails.shortDescription != null && bookDetails.shortDescription!!.isNotEmpty()) {
            mBinding!!.txtBookShortDesc.text = bookDetails.shortDescription
        } else {
            mBinding!!.txtBookShortDesc.text = mContext.getString(R.string.not_found)
        }

        if (bookDetails.longDescription != null && bookDetails.longDescription!!.isNotEmpty()) {
            mBinding!!.txtBookLongDesc.text = bookDetails.longDescription
        } else {
            mBinding!!.txtBookLongDesc.text = mContext.getString(R.string.not_found)
        }
    }

    interface BookDetailsController {
        fun openBookPreview()
        fun addToCart()
    }

}