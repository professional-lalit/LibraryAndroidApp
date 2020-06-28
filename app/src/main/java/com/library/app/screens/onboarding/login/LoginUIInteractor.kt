package com.library.app.screens.onboarding.login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.library.app.BR
import com.library.app.R
import com.library.app.common.CustomApplication
import com.library.app.databinding.ActivityLoginBinding
import makeToast
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 15/6/20
 * This class is used to operate the UI changes as directed by it's controller
 * This class is only used to manage the UI for `activity_login`
 */
class LoginUIInteractor @Inject constructor(val mContext: Context) : BaseObservable() {

    var loginController: LoginController? = null

    /**
     * Provides the view to which this interactor operates
     */
    fun getRootView(): View {
        val binding: ActivityLoginBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.activity_login,
            null,
            false
        )
        binding.loginUIInteractor = this
        return binding.root
    }

    // ************************************* UI ELEMENTS ***************************************

    /**
     * Form fields to submit
     */
    var email: String = ""
    var password: String = ""

    /**
     * Determines when to show progressbar on login screen & hide login button
     */
    @Bindable
    var loading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    // ************************************* UI ELEMENTS ***************************************

    // ************************************* MESSAGES ***************************************

    fun showValidationDialog(message: String) {
        makeToast(mContext, message)
    }

    fun showLoginSuccessDialog(message: String) {
        makeToast(mContext, message)
    }

    fun showLoginFailDialog(message: String) {
        makeToast(mContext, message)
    }

    // ************************************* MESSAGES ***************************************

    // ************************************ EVENTS *****************************************

    fun login() {
        loginController?.loginUser(email, password)
    }

    fun openSignupScreen() {
        loginController?.openSignupScreen()
    }

    fun openForgotPasswordScreen() {
        loginController?.openForgotPasswordScreen()
    }

    // ************************************ EVENTS *****************************************

    /**
     * A standard to communicate with the controller.
     */
    interface LoginController {
        fun loginUser(email: String, password: String)
        fun openSignupScreen()
        fun openForgotPasswordScreen()
    }


}