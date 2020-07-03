package com.library.app.screens.onboarding.forgot_password

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.library.app.BR
import com.library.app.R
import com.library.app.common.Constants
import com.library.app.databinding.ActivityForgotPasswordBinding
import com.library.app.networking.models.ErrorResponseSchema
import com.library.app.networking.models.ForgotPasswordResposeSchema
import com.library.app.screens.common.BaseController
import com.library.app.screens.common.DialogManager
import com.library.app.screens.common.InputValidator
import com.library.app.screens.common.ValidationManager
import makeToast
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 21/6/20
 * This class manages the UI operations on 'activity_forgot_password'
 * and is controlled by ForgotPasswordController
 */
class ForgotPasswordUIInteractor @Inject constructor(
    val mContext: Context,
    val dialogManager: DialogManager,
    val mValidationManager: ValidationManager
) : BaseObservable() {

    var mForgotPasswordController: ForgotPasswordController? = null

    /**
     * Provides the view to which this interactor operates
     */
    fun getRootView(): View {
        val binding: ActivityForgotPasswordBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.activity_forgot_password,
            null,
            false
        )
        binding.forgotPasswordUIInteractor = this
        return binding.root
    }

    /**
     * Holds the email value entered
     */
    var email: String = "hajare.lalit@gmail.com"

    /**
     * Determines when to show progressbar on login screen & hide login button
     */
    @Bindable
    var loading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    // ***************************************** EVENTS ******************************************

    fun submit() {
        if (checkValidation())
            mForgotPasswordController?.onSubmitEmail(email)
    }

    private fun checkValidation(): Boolean {
        val validator = mValidationManager.getForgotPasswordValidator(email)
        when (validator.isValid()) {
            InputValidator.ValidationCode.EMPTY_EMAIL_ERROR -> {
                showValidationDialog(mContext.getString(R.string.plz_enter_email))
                return false
            }
            InputValidator.ValidationCode.INVALID_EMAIL_ERROR -> {
                showValidationDialog(mContext.getString(R.string.plz_enter_valid_email))
                return false
            }
            InputValidator.ValidationCode.VALID -> return true
            else -> {
                return false
            }
        }
    }

    // ***************************************** EVENTS ******************************************


    // ***************************************** ACTIONS ***************************************

    private fun showValidationDialog(message: String) {
        makeToast(mContext, message)
    }

    fun showPasswordRecoverySuccessDialog(forgotPasswordResposeSchema: ForgotPasswordResposeSchema) {
        dialogManager.showForgotPasswordSuccessDialog(
            mForgotPasswordController!!.getDialogFragmentManager(),
            forgotPasswordResposeSchema.message!!
        ) {
            mForgotPasswordController?.openChangePasswordScreen()
        }
    }

    fun showPasswordRecoveryErrorDialog(errorResponseSchema: ErrorResponseSchema) {
        dialogManager.showForgotPasswordFailureDialog(
            mForgotPasswordController!!.getDialogFragmentManager(),
            errorResponseSchema.message
        ) {
            email = ""
        }
    }

    // ***************************************** ACTIONS ***************************************

    interface ForgotPasswordController : BaseController {
        fun onSubmitEmail(email: String)
        fun openChangePasswordScreen()
    }

}