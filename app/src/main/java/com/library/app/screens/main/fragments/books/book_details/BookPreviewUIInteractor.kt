package com.library.app.screens.main.fragments.books.book_details

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import com.library.app.BR
import com.library.app.R
import com.library.app.databinding.FragmentBookDetailsBinding
import com.library.app.databinding.FragmentBookPreviewBinding
import com.library.app.models.BookPreview
import com.library.app.screens.common.UIInteractor
import javax.inject.Inject

/**
 * Created by Lalit N. Hajare, Software Engineer on 06/08/2020
 */
class BookPreviewUIInteractor @Inject constructor(val mContext: Context) : BaseObservable(),
    UIInteractor {

    var mBinding: FragmentBookPreviewBinding? = null

    override fun getRootView(): View {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.fragment_book_preview,
            null,
            false
        )
        mBinding?.bookPreviewUIInteractor = this
        return mBinding?.root!!
    }

    @Bindable
    var loading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    @SuppressLint("SetJavaScriptEnabled")
    fun showPreview(bookPreview: BookPreview) {
        mBinding!!.webView.settings.javaScriptEnabled = true
        mBinding!!.webView.loadData(
            bookPreview.previewText,
            "text/html",
            "UTF-8"
        )
    }

    interface BookPreviewController

}