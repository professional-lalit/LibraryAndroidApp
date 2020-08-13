package com.library.app.screens.common

import android.content.Intent
import com.library.app.models.Book
import com.library.app.screens.SplashActivity
import com.library.app.screens.main.MainActivity
import com.library.app.screens.main.fragments.books.book_list.BookListFragment
import com.library.app.screens.onboarding.change_password.ChangePasswordActivity
import com.library.app.screens.onboarding.forgot_password.ForgotPasswordActivity
import com.library.app.screens.onboarding.login.LoginActivity
import com.library.app.screens.onboarding.signup.SignupActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 15/6/20
 * This class consists screen navigation routes, each function in this class is
 * an intent to open some screen.
 */

/**
 * This delay is added when new activity is started, this is done to address the issue faced while
 * instrumentation testing. The Views on older activity are not found when new activity is visible
 * instantly.
 */
const val SCREEN_DELAY = 500L

class ScreenNavigator @Inject constructor() {

    /**
     * This section refers to the navigation routes while user has not yet logged in
     */
    inner class OnBoarding {

        fun openSignupScreen(loginActivity: LoginActivity) {
            val intent = Intent(loginActivity, SignupActivity::class.java)
            loginActivity.startActivity(intent)
        }

        fun openForgotPasswordScreen(loginActivity: LoginActivity) {
            val intent = Intent(loginActivity, ForgotPasswordActivity::class.java)
            loginActivity.startActivity(intent)
        }

        fun signupBack(activity: SignupActivity) {
            activity.finish()
        }

        fun openChangePasswordScreen(forgotPasswordActivity: ForgotPasswordActivity) {
            val intent = Intent(forgotPasswordActivity, ChangePasswordActivity::class.java)
            forgotPasswordActivity.startActivity(intent)
        }

        fun openLoginScreenAfterForgotPasswordFlow(activity: ChangePasswordActivity) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }

        fun openHomePageAfterLogin(activity: LoginActivity) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(SCREEN_DELAY)
                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                activity.startActivity(intent)
                activity.finish()
            }
        }

        fun openHomePageAfterSplash(activity: SplashActivity) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }

        fun openLoginActivityAfterSplash(activity: SplashActivity) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }
    }


}