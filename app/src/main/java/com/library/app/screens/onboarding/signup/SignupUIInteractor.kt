package com.library.app.screens.onboarding.signup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import com.inputvalidationmanager.module.ValidationManager
import com.inputvalidationmanager.module.validators.InputValidator
import com.library.app.BR
import com.library.app.R
import com.library.app.databinding.ActivitySignupBinding
import makeToast
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 15/6/20
 * This class acts as UI manager for `SignupActivity`
 * It abstracts the UI logic from core business logic and helps `SignupActivity`
 * work independently as `controller` through `SignupController`
 * @see SignupController
 * @see SignupActivity
 */
class SignupUIInteractor @Inject constructor(
    val mContext: Context,
    val mValidationManager: ValidationManager
) :
    BaseObservable() {

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

    fun showValidationMsg(message: String) {
        makeToast(mContext, message)
    }

    fun showSignupSuccessMsg(message: String) {
        makeToast(mContext, message)
    }

    fun showSignupFailureMsg(message: String) {
        makeToast(mContext, message)
    }

    // ************************************* MESSAGES ***************************************

    // ************************************ ACTIONS *****************************************

    fun signup() {
        if (checkValidation()) {
            signupController?.signUpUser(name, email, password)
        }
    }

    private fun checkValidation(): Boolean {
        val validator = mValidationManager.getSignupValidator(name, email, password, repassword)
        when (validator.isValid()) {
            InputValidator.ValidationCode.EMPTY_NAME_ERROR -> {
                showValidationMsg(mContext.getString(R.string.plz_enter_name))
                return false
            }
            InputValidator.ValidationCode.FULL_NAME_ERROR -> {
                showValidationMsg(mContext.getString(R.string.plz_first_last_name))
                return false
            }
            InputValidator.ValidationCode.EMPTY_EMAIL_ERROR -> {
                showValidationMsg(mContext.getString(R.string.plz_enter_email))
                return false
            }
            InputValidator.ValidationCode.INVALID_EMAIL_ERROR -> {
                showValidationMsg(mContext.getString(R.string.plz_enter_valid_email))
                return false
            }
            InputValidator.ValidationCode.EMPTY_PASSWORD_ERROR -> {
                showValidationMsg(mContext.getString(R.string.plz_enter_pwd))
                return false
            }
            InputValidator.ValidationCode.INVALID_PASSWORD_LENGTH_ERROR -> {
                showValidationMsg(mContext.getString(R.string.pwd_shd_contain_6_char))
                return false
            }
            InputValidator.ValidationCode.NEW_PASSWORD_SPCHR_ERROR -> {
                showValidationMsg(mContext.getString(R.string.pwd_shd_have_1_sp_char))
                return false
            }
            InputValidator.ValidationCode.UNMATCHING_PASSWORDS_ERROR -> {
                showValidationMsg(mContext.getString(R.string.plz_enter_name))
                return false
            }
            InputValidator.ValidationCode.VALID -> return true
            else -> {
                return false
            }
        }
    }

    fun openLoginScreen() {
        signupController?.openLoginScreen()
    }

    // ************************************ ACTIONS *****************************************

    /**
     * A standard to communicate with the controller.
     */
    interface SignupController {
        fun signUpUser(name: String, email: String, password: String)
        fun openLoginScreen()
    }
}