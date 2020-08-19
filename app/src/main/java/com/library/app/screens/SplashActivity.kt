package com.library.app.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.library.app.R
import com.library.app.common.Prefs
import com.library.app.screens.common.BaseActivity
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var mPrefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if (mPrefs.accessToken!!.isEmpty()) {
                mScreenNavigator.OnBoarding().openLoginActivityAfterSplash(this)
            } else {
                mScreenNavigator.OnBoarding().openHomePageAfterSplash(this)
            }
        }, 2000)

    }
}