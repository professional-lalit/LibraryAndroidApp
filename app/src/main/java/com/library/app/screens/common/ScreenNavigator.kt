package com.library.app.screens.common

import android.content.Intent
import com.library.app.screens.onboarding.change_password.ChangePasswordActivity
import com.library.app.screens.onboarding.forgot_password.ForgotPasswordActivity
import com.library.app.screens.onboarding.login.LoginActivity
import com.library.app.screens.onboarding.signup.SignupActivity

/**
 * Created by Lalit Hajare, Software Engineer on 15/6/20
 * This class consists screen navigation routes, each function in this class is
 * an intent to open some screen.
 */
class ScreenNavigator {

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

    }

}