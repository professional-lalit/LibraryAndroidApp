package com.library.app.screens.main.fragments.books.book_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.library.app.BR
import com.library.app.R
import com.library.app.databinding.FragmentBookDetailsBinding
import com.library.app.databinding.FragmentBookListBinding
import com.library.app.di.annotations.books.BookListScope
import com.library.app.models.Book
import com.library.app.screens.common.UIInteractor
import com.library.app.screens.main.fragments.books.BookAdapter
import com.library.app.screens.main.fragments.books.book_details.BookDetailsUIInteractor
import javax.inject.Inject

/**
 * Created by Lalit N. Hajare, Software Engineer on 01/08/2020
 */
@BookListScope
class BookListUIInteractor @Inject constructor(val mContext: Context) : BaseObservable(),
    UIInteractor,
        (Book) -> Unit {

    var mBookListController: BookListController? = null
    var mBinding: FragmentBookListBinding? = null

    private val mBookList = arrayListOf<Book>()
    private var mBookListAdapter: BookAdapter? = null

    override fun getRootView(): View {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.fragment_book_list,
            null,
            false
        )
        mBinding?.bookListUIInteractor = this
        return mBinding?.root!!
    }

    @Bindable
    var loading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    fun addBooksInList(list: ArrayList<Book>?) {
        if (mBinding!!.recyclerBooks.adapter == null) {
            mBookList.clear()
            setRecyclerAdapter()
        }
        if (list != null) {
            mBookList += list
            mBookListAdapter!!.notifyItemRangeInserted(mBookList.size - list.size, list.size)
            mBinding!!.txtNoRecords.visibility = View.GONE
        } else if (mBookList.isEmpty()) {
            mBinding!!.txtNoRecords.visibility = View.VISIBLE
        }
    }

    private fun setRecyclerAdapter() {
        mBookListAdapter = BookAdapter(mBookList, this)
        mBinding!!.recyclerBooks.adapter = mBookListAdapter
        mBinding!!.recyclerBooks.layoutManager = LinearLayoutManager(mContext)
        mBinding!!.recyclerBooks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!mBinding!!.recyclerBooks.canScrollVertically(1)) {
                    mBookListController!!.loadMore()
                }
            }
        })
    }

    override fun invoke(book: Book) {
        mBookListController!!.openBookDetails(book)
    }

    interface BookListController {
        fun openBookDetails(book: Book)
        fun loadMore()
    }

}