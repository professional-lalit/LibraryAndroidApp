package com.library.app.screens.onboarding.change_password

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.R
import com.library.app.networking.Result
import com.library.app.networking.models.ChangePasswordResponseSchema
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.onboarding.forgot_password.ForgotPasswordUIInteractor
import com.library.app.screens.onboarding.forgot_password.ForgotPasswordViewModel
import javax.inject.Inject

/**
 * This class consists controlling logic for Change Password block on Flowchart
 * The UI operations are managed `ChangePasswordUIInteractor`.
 * @see ChangePasswordUIInteractor
 * The `ChangePasswordViewModel` manages the business logic for this class.
 * @see ChangePasswordViewModel
 */
class ChangePasswordActivity : BaseActivity(), ChangePasswordUIInteractor.ChangePasswordController {

    lateinit var mChangePasswordViewModel: ChangePasswordViewModel

    @Inject
    lateinit var mChangePasswordUIInteractor: ChangePasswordUIInteractor

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
        mChangePasswordViewModel.submitNewPassword(
            oldPassword,
            password
        )
    }

    override fun openLoginScreen() {
        mScreenNavigator.OnBoarding().openLoginScreenAfterForgotPasswordFlow(this)
    }

    override fun getDialogFragmentManager(): FragmentManager {
        return supportFragmentManager
    }
}