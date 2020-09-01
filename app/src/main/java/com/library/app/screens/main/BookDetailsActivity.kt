package com.library.app.screens.main

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.library.app.R
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.main.fragments.books.book_details.BookDetailsFragment
import kotlinx.android.synthetic.main.activity_book_details.*
import javax.inject.Inject

class BookDetailsActivity : BaseActivity() {

    val bookDetailsFragment = BookDetailsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)
        findViewById<Toolbar>(R.id.toolbar_main).title = "Book Details"
        val bundle = Bundle()
        bundle.putString(BookDetailsFragment.ISBN, intent.getStringExtra("isbn"))

        bookDetailsFragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(container.id, bookDetailsFragment).commit()
    }

}