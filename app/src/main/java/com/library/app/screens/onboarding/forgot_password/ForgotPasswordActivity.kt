package com.library.app.screens.onboarding.forgot_password

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.common.Constants
import com.library.app.networking.Result
import com.library.app.networking.models.ForgotPasswordResposeSchema
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.common.DialogManager
import com.library.app.screens.common.Navigation
import javax.inject.Inject

/**
 * This class consists controlling logic for Forgot Password block on Flowchart
 * The UI operations are managed `ForgotPasswordUIInteractor`.
 * @see ForgotPasswordUIInteractor
 * The `ForgotPasswordViewModel` manages the business logic for this class.
 * @see ForgotPasswordViewModel
 */
class ForgotPasswordActivity : BaseActivity(),
    ForgotPasswordUIInteractor.ForgotPasswordController {

    @Inject
    lateinit var mForgotPasswordViewModel: ForgotPasswordViewModel

    @Inject
    lateinit var mForgotPasswordUIInteractor: ForgotPasswordUIInteractor

    @Inject
    lateinit var mNavigation: Navigation

    override fun onStart() {
        super.onStart()

        mForgotPasswordViewModel =
            ViewModelProvider(this, providerFactory).get(ForgotPasswordViewModel::class.java)

        observeResult()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mForgotPasswordUIInteractor.mForgotPasswordController = this

        setContentView(mForgotPasswordUIInteractor.getRootView())

    }

    private fun observeResult() {
        mForgotPasswordViewModel.result.observe(this, Observer { result ->
            mForgotPasswordUIInteractor.loading = false
            if (result is Result.Success) {
                val forgotPasswordResposeSchema = result.data as ForgotPasswordResposeSchema
                mForgotPasswordUIInteractor.showPasswordRecoverySuccessDialog(
                    forgotPasswordResposeSchema
                )
            } else if (result is Result.Error) {
                val errorResponseSchema = result.errorResponseSchema
                mForgotPasswordUIInteractor.showPasswordRecoveryErrorDialog(errorResponseSchema)
            }
        })
    }

    override fun onSubmitEmail(email: String) {
        mForgotPasswordViewModel.submitEmail(email)
        mForgotPasswordUIInteractor.loading = true
    }

    override fun getDialogFragmentManager(): FragmentManager {
        return supportFragmentManager
    }

    override fun openChangePasswordScreen() {
        mNavigation.OnBoarding().openChangePasswordScreen(this)
    }
}