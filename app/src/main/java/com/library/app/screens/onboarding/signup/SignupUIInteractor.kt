package com.library.app.screens.onboarding.signup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import com.library.app.BR
import com.library.app.R
import com.library.app.databinding.ActivitySignupBinding
import makeToast
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 15/6/20
 */
class SignupUIInteractor @Inject constructor(val mContext: Context) : BaseObservable() {

    var signupController: SignupController? = null

    /**
     * Provides the view to which this interactor operates.
     * Abstracts the UI implementation file details, hence, the activity is easily replaceable
     */
    fun getRootView(
        signupViewModel: SignupViewModel
    ): View {
        val binding: ActivitySignupBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.activity_signup,
            null,
            false
        )
        binding.signupViewModel = signupViewModel
        binding.signupUIInteractor = this
        return binding.root
    }

    // ************************************* UI ELEMENTS ***************************************

    /**
     * Form fields to submit
     */
    var name: String = ""
    var email: String = ""
    var password: String = ""
    var repassword: String = ""

    /**
     * Determines when to show progressbar on signup screen & hide signup button
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

    fun showSignupSuccessDialog(message: String) {
        makeToast(mContext, message)
    }

    fun showSignupFailDialog(message: String) {
        makeToast(mContext, message)
    }

    // ************************************* MESSAGES ***************************************

    // ************************************ ACTIONS *****************************************

    fun signup() {
        signupController?.signUpUser(name, email, password, repassword)
    }

    fun openLoginScreen() {
        signupController?.openLoginScreen()
    }

    // ************************************ ACTIONS *****************************************

    /**
     * A standard to communicate with the controller.
     */
    interface SignupController {
        fun signUpUser(name: String, email: String, password: String, repassword: String)
        fun openLoginScreen()
    }
}