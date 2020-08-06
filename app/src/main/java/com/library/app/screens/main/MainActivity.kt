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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.library.app.R
import com.library.app.common.CustomApplication
import com.library.app.common.Prefs
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.main.fragments.home.HomeFragment


class MainActivity : BaseActivity() {

    private lateinit var fragmentContainer: FrameLayout

    private lateinit var mDrawerLayout: DrawerLayout

    private lateinit var mToolbar: Toolbar

    enum class MainScreenFragments(tag: String) {
        HOME("home"), BOOK_LIST("book_list"), BOOK_DETAILS("book_details"),
        BOOK_PREVIEW("book_preview")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpDrawerAndToolbar()

        initViews()
    }

    private fun setUpDrawerAndToolbar() {

        mToolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(mToolbar)

        mDrawerLayout = findViewById(R.id.drawer)
        val toggle = ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setNavToolbarBackButton() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setHomeToolbarBackButton() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_drawer)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews() {
        fragmentContainer = findViewById(R.id.fragment_container)
        supportFragmentManager.beginTransaction()
            .add(fragmentContainer.id, HomeFragment()).commit()
//        findViewById<TextView>(R.id.txt_name).text =
//            CustomApplication.appInstance!!.getAppComponent().getPrefs().user!!.name
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    //Manage the back button on action bar
    override fun onBackPressed() {
        if (getCurrentFragment() is HomeFragment?) {
            setHomeToolbarBackButton()
        } else {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun getCurrentFragment(): Fragment? {
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            val fragmentTag: String? =
                fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name
            return fragmentManager.findFragmentByTag(fragmentTag)
        }
        return null
    }

}