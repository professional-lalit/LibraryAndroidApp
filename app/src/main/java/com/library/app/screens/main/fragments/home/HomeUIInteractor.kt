package com.library.app.screens.main.fragments.home

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.library.app.BR
import com.library.app.R
import com.library.app.databinding.ActivityLoginBinding
import com.library.app.databinding.FragmentHomeBinding
import com.library.app.models.Book
import com.library.app.models.Category
import com.library.app.screens.main.fragments.books.BookAdapter
import com.library.app.screens.main.fragments.books.CategoryAdapter
import javax.inject.Inject

/**
 * Created by Lalit N. Hajare, Software Engineer on 29/07/2020
 */
class HomeUIInteractor @Inject constructor(private val context: Context) : BaseObservable(),
        (Any) -> Unit {

    var homeUIController: HomeUIController? = null
    private var mHomeBinding: FragmentHomeBinding? = null

    /**
     * Provides the view to which this interactor operates
     */
    fun getRootView(): View {
        mHomeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_home,
            null,
            false
        )
        mHomeBinding?.homeUIInteractor = this
        return mHomeBinding?.root!!
    }

    fun showTrendingBooks(list: ArrayList<Book>) {
        mHomeBinding!!.trendingBooksRecycler.adapter = BookAdapter(list, this)
        mHomeBinding!!.trendingBooksRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        autoScrollListLeft(mHomeBinding!!.trendingBooksRecycler)
    }

    fun showRecentVisitedBooks(list: ArrayList<Book>) {
        mHomeBinding!!.recentVisitedBooksRecycler.adapter = BookAdapter(list, this)
        mHomeBinding!!.recentVisitedBooksRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        autoScrollListRight(mHomeBinding!!.recentVisitedBooksRecycler)
    }

    fun showCategories(list: ArrayList<Category>) {
        mHomeBinding!!.categoriesRecycler.adapter = CategoryAdapter(list, this)
        mHomeBinding!!.categoriesRecycler.layoutManager = GridLayoutManager(context, 3)
    }

    @Bindable
    var trendingBooksLoading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.trendingBooksLoading)
        }

    @Bindable
    var recentVisitedBooksLoading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.recentVisitedBooksLoading)
        }

    @Bindable
    var categoriesLoading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.categoriesLoading)
        }

    private fun autoScrollListRight(recyclerView: RecyclerView) {
        val speedScroll = 3000L
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            var count = 0
            var flag = true
            override fun run() {
                if (count < recyclerView.adapter!!.itemCount) {
                    if (count == recyclerView.adapter!!.itemCount - 1) {
                        flag = false
                    } else if (count == 0) {
                        flag = true
                    }
                    if (flag) count++ else count--
                    recyclerView.smoothScrollToPosition(count)
                    handler.postDelayed(this, speedScroll)
                }
            }
        }
        handler.postDelayed(runnable, speedScroll)
    }

    private fun autoScrollListLeft(recyclerView: RecyclerView) {
        val speedScroll = 4000L
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            var count = recyclerView.adapter!!.itemCount - 1
            var flag = true
            override fun run() {
                if (count < recyclerView.adapter!!.itemCount) {
                    if (count == recyclerView.adapter!!.itemCount - 1) {
                        flag = true
                    } else if (count == 0) {
                        flag = false
                    }
                    if (flag) {
                        count--
                    } else {
                        count = recyclerView.adapter!!.itemCount - 1
                    }
                    recyclerView.smoothScrollToPosition(count)
                    handler.postDelayed(this, speedScroll)
                }
            }
        }
        handler.postDelayed(runnable, speedScroll)
    }


    /**
     * Callback from list item click along with clicked item data
     */
    override fun invoke(model: Any) {
        if (model is Book)
            homeUIController!!.openBookDetails(model)
        else if (model is Category)
            homeUIController!!.openBooksOfCategory(model)
    }


    interface HomeUIController {
        fun openBooksOfCategory(category: Category)
        fun openBookDetails(book: Book)
    }

}