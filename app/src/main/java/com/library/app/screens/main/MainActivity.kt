package com.library.app.screens.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.library.app.R
import com.library.app.common.CustomApplication
import com.library.app.common.Prefs
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.main.fragments.home.HomeFragment
import javax.inject.Inject


class MainActivity : BaseActivity() {

    val mHomeFragment = HomeFragment()

    private lateinit var fragmentContainer: FrameLayout

    private lateinit var mDrawerLayout: DrawerLayout

    private lateinit var mToolbar: Toolbar

    private lateinit var txtName: TextView

    @Inject
    lateinit var mPrefs: Prefs

    enum class MainScreenFragments(tag: String) {
        HOME("home"), BOOK_LIST("book_list"), BOOK_DETAILS("book_details"),
        BOOK_PREVIEW("book_preview")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolbar()

        initViews()

        setViews()
    }

    private fun setUpToolbar() {
        mToolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(mToolbar)
    }

    private fun setNavActionBar() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setHomeActionBar() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_drawer)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews() {
        mDrawerLayout = findViewById(R.id.drawer)
        fragmentContainer = findViewById(R.id.fragment_container)
        val headerView = findViewById<NavigationView>(R.id.home_navview).getHeaderView(0)
        txtName = headerView.findViewById(R.id.txt_name)
    }

    private fun setViews() {
        supportFragmentManager.beginTransaction()
            .add(fragmentContainer.id, mHomeFragment)
            .commit()
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                setNavActionBar()
            } else {
                setHomeActionBar()
            }
        }
        setHomeActionBar()
        txtName.text = mPrefs.userId
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount == 0) {
                    if (!mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                        mDrawerLayout.openDrawer(GravityCompat.START)
                    } else {
                        mDrawerLayout.closeDrawer(GravityCompat.START)
                    }
                } else {
                    onBackPressed()
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}