package com.library.app.screens.main.fragments.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.library.app.R
import com.library.app.models.Book
import com.library.app.screens.common.BaseViewHolder
import com.library.app.screens.main.fragments.books.BookViewHolder

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 */
class BookAdapter(private val mBookList: ArrayList<Book>) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BookViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mBookList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBindView(mBookList[position])
    }
}