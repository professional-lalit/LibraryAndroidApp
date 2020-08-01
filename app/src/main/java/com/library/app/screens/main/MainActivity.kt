package com.library.app.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toolbar
import com.library.app.R
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.main.fragments.home.HomeFragment
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var fragmentContainer: FrameLayout

    enum class MainScreenFragments(tag: String) {
        HOME("home"), BOOK_LIST("book_list"), BOOK_DETAILS("book_details")
    }

    @Inject
    lateinit var mHomeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolbar()

        initViews()

    }

    private fun setUpToolbar() {
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeAsUpIndicator(android.R.drawable.ic_media_previous)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews() {
        fragmentContainer = findViewById(R.id.fragment_container)
        supportFragmentManager.beginTransaction()
            .add(fragmentContainer.id, mHomeFragment).commit()
    }


}