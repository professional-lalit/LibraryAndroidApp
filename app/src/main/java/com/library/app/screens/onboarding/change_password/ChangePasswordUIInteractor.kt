package com.library.app.screens.onboarding.change_password

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import com.library.app.BR
import com.library.app.R
import com.library.app.databinding.ActivityChangePasswordBinding
import com.library.app.databinding.ActivityLoginBinding
import com.library.app.networking.models.ChangePasswordResponseSchema
import com.library.app.networking.models.ErrorResponseSchema
import com.library.app.screens.common.BaseController
import com.library.app.screens.common.DialogManager
import com.library.app.screens.common.InputValidator
import com.library.app.screens.common.ValidationManager
import com.library.app.screens.onboarding.login.LoginViewModel
import makeToast
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 16/6/20
 * This class manages the UI operations on 'activity_change_password'
 * This class is controlled through `ChangePasswordController`
 * @see ChangePasswordController
 * @param mContext: used to get strings from resources to show messages
 * @param mDialogManager: used to show dialogs, `DialogManager` manages the initialization and build
 * @param mValidationManager: used to validate the input on screen.
 */
class ChangePasswordUIInteractor @Inject constructor(
    val mContext: Context,
    val mDialogManager: DialogManager,
    val mValidationManager: ValidationManager
) : BaseObservable() {

    var changePasswordController: ChangePasswordController? = null

    /**
     * Provides the view to which this interactor operates
     */
    fun getRootView(): View {
        val binding: ActivityChangePasswordBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.activity_change_password,
            null,
            false
        )
        binding.changePasswordUIInteractor = this
        return binding.root
    }

    /**
     * Fields in form to submit
     */
    var oldPasswpord: String = ""
    var newPasswpord: String = ""
    var rePasswpord: String = ""

    /**
     * Determines when to show progressbar on login screen & hide login button
     */
    @Bindable
    var loading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }


    // ************************************* EVENTS ****************************************

    fun submit() {
        if (checkValidation())
            changePasswordController?.submit(oldPasswpord, newPasswpord, rePasswpord)
    }

    private fun checkValidation(): Boolean {
        val validator = mValidationManager
            .getChangePasswordValidator(oldPasswpord, newPasswpord, rePasswpord)
        when (validator.isValid()) {
            InputValidator.ValidationCode.EMPTY_PASSWORD_ERROR -> {
                showValidationDialog(mContext.getString(R.string.plz_enter_pwd))
                return false
            }
            InputValidator.ValidationCode.INVALID_PASSWORD_LENGTH_ERROR -> {
                showValidationDialog(mContext.getString(R.string.pwd_shd_contain_6_char))
                return false
            }
            InputValidator.ValidationCode.NEW_PASSWORD_SPCHR_ERROR -> {
                showValidationDialog(mContext.getString(R.string.pwd_shd_have_1_sp_char))
                return false
            }
            InputValidator.ValidationCode.UNMATCHING_PASSWORDS_ERROR -> {
                showValidationDialog(mContext.getString(R.string.plz_enter_name))
                return false
            }
            InputValidator.ValidationCode.VALID -> return true
            else -> {
                return false
            }
        }
    }

    // ************************************* EVENTS ****************************************

    // ************************************* ACTIONS ****************************************

    fun showValidationDialog(message: String) {
        makeToast(mContext, message)
    }

    fun showChangePasswordSuccessDialog(changePasswordResponseSchema: ChangePasswordResponseSchema) {
        mDialogManager.showChangePasswordSuccessDialog(
            changePasswordController!!.getDialogFragmentManager(),
            changePasswordResponseSchema.message!!
        ) {
            changePasswordController!!.openLoginScreen()
        }
    }

    fun showChangePasswordFailureDialog(errorResponseSchema: ErrorResponseSchema) {
        mDialogManager.showChangePasswordSuccessDialog(
            changePasswordController!!.getDialogFragmentManager(),
            errorResponseSchema.message
        ) {

        }
    }

    fun showChangePasswordValidationDialog(msg: String) {
        makeToast(mContext, msg)
    }

    // ************************************* ACTIONS ****************************************

    /**
     * A standard to communicate with the controller.
     */
    interface ChangePasswordController : BaseController {
        fun submit(oldPassword: String, password: String, repassword: String)
        fun openLoginScreen()
    }

}