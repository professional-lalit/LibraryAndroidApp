package com.library.app.screens.onboarding.change_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.R
import com.library.app.networking.Result
import com.library.app.networking.models.ChangePasswordResponseSchema
import com.library.app.networking.models.ForgotPasswordResposeSchema
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.common.Navigation
import com.library.app.screens.onboarding.login.LoginViewModel
import javax.inject.Inject

class ChangePasswordActivity : BaseActivity(), ChangePasswordUIInteractor.ChangePasswordController {

    lateinit var mChangePasswordViewModel: ChangePasswordViewModel

    @Inject
    lateinit var mChangePasswordUIInteractor: ChangePasswordUIInteractor

    @Inject
    lateinit var mNavigation: Navigation

    override fun onStart() {
        super.onStart()
        mChangePasswordViewModel = ViewModelProvider(this, providerFactory)
            .get(ChangePasswordViewModel::class.java)

        observeResult()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mChangePasswordUIInteractor.changePasswordController = this

        setContentView(mChangePasswordUIInteractor.getRootView())
    }

    private fun observeResult() {
        mChangePasswordViewModel.result.observe(this, Observer { result ->
            mChangePasswordUIInteractor.loading = false
            if (result is Result.Success) {
                val changePasswordResposeSchema = result.data as ChangePasswordResponseSchema
                mChangePasswordUIInteractor.showChangePasswordSuccessDialog(
                    changePasswordResposeSchema
                )
            } else if (result is Result.Error) {
                val errorResponseSchema = result.errorResponseSchema
                mChangePasswordUIInteractor.showChangePasswordFailureDialog(errorResponseSchema)
            }
        })
    }

    override fun submit(oldPassword: String, password: String, repassword: String) {
        if (mChangePasswordViewModel.submitNewPassword(
                oldPassword,
                password,
                repassword
            ) { validationCode ->
                handleValidation(validationCode)
            }
        ) {
            mChangePasswordUIInteractor.loading = true
        }
    }

    private fun handleValidation(validationCode: ChangePasswordValidationUsecase.ChangePasswordValidations) {
        when (validationCode) {
            ChangePasswordValidationUsecase.ChangePasswordValidations.NEW_PASSWORD_EMPTY ->
                mChangePasswordUIInteractor.showChangePasswordValidationDialog(getString(R.string.plz_enter_pwd))
            ChangePasswordValidationUsecase.ChangePasswordValidations.NEW_PASSWORD_LENGTH_ERROR ->
                mChangePasswordUIInteractor.showChangePasswordValidationDialog(getString(R.string.pwd_shd_contain_6_char))
            ChangePasswordValidationUsecase.ChangePasswordValidations.NEW_PASSWORD_SPCHR_ERROR ->
                mChangePasswordUIInteractor.showChangePasswordValidationDialog(getString(R.string.pwd_shd_have_1_sp_char))
            ChangePasswordValidationUsecase.ChangePasswordValidations.MATCHING_ERROR ->
                mChangePasswordUIInteractor.showChangePasswordValidationDialog(getString(R.string.pwd_dont_match))
            else -> {
            }
        }
    }

    override fun openLoginScreen() {
        mNavigation.OnBoarding().openLoginScreenAfterForgotPasswordFlow(this)
    }

    override fun getDialogFragmentManager(): FragmentManager {
        return supportFragmentManager
    }
}