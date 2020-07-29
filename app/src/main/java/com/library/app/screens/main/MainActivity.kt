package com.library.app.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.library.app.R
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.main.fragments.home.HomeFragment
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var fragmentContainer: FrameLayout

    @Inject
    lateinit var mHomeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        fragmentContainer = findViewById(R.id.fragment_container)
        supportFragmentManager.beginTransaction().add(fragmentContainer.id, mHomeFragment).commit()
    }

}