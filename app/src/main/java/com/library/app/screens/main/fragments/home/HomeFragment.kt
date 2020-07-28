package com.library.app.screens.main.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.library.app.R
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.common.BaseFragment
import com.library.app.screens.main.fragments.books.BookAdapter
import com.library.app.screens.main.fragments.books.CategoryAdapter


class HomeFragment : BaseFragment() {

    private lateinit var mTrendingBooksRecyclerView: RecyclerView
    private lateinit var mRecentVisitedBooksRecyclerView: RecyclerView
    private lateinit var mCategoriesRecyclerView: RecyclerView
    lateinit var mHomeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHomeViewModel = ViewModelProvider(
            this,
            (activity as BaseActivity).providerFactory
        ).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTrendingBooksRecyclerView = view.findViewById(R.id.trending_books_recycler)
        mRecentVisitedBooksRecyclerView = view.findViewById(R.id.recent_visited_books_recycler)
        mCategoriesRecyclerView = view.findViewById(R.id.categories_recycler)

        showHomeData()
    }

    private fun showHomeData() {
        mHomeViewModel.trendingBookList.observe(this, Observer { list ->
            mTrendingBooksRecyclerView.adapter =
                BookAdapter(list)
            mTrendingBooksRecyclerView.layoutManager = LinearLayoutManager(context)
        })
        mHomeViewModel.recentBooksVisitedList.observe(this, Observer { list ->
            mRecentVisitedBooksRecyclerView.adapter =
                BookAdapter(list)
            mRecentVisitedBooksRecyclerView.layoutManager = LinearLayoutManager(context)
        })
        mHomeViewModel.categoryList.observe(this, Observer { list ->
            mCategoriesRecyclerView.adapter =
                CategoryAdapter(list)
            mRecentVisitedBooksRecyclerView.layoutManager = GridLayoutManager(context, 3)
        })
    }


}