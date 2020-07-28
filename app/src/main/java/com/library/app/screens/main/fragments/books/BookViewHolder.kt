package com.library.app.screens.main.fragments.books

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.library.app.R
import com.library.app.common.ImageLoadManager
import com.library.app.models.Book
import com.library.app.screens.common.BaseViewHolder
import javax.inject.Inject

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 */
class BookViewHolder(private val mItemView: View) : BaseViewHolder(mItemView) {

    private val imgBook: ImageView = mItemView.findViewById(R.id.img_book)
    private val txtBookTitle: TextView = mItemView.findViewById(R.id.txt_book_title)
    private val txtBookCategories: TextView = mItemView.findViewById(R.id.txt_book_categories)
    private val txtBookDescription: TextView = mItemView.findViewById(R.id.txt_book_description)

    @Inject
    lateinit var mImageLoadManager: ImageLoadManager

    override fun onBindView(localData: Any) {
        val book = localData as Book
        mImageLoadManager.getImage(imgBook, book.thumbnailUrl!!)
        txtBookTitle.text = book.title
        txtBookCategories.text =
            mItemView.context.getString(R.string.category) + ": " + book.categories
        txtBookDescription.text = book.shortDescription
    }


}