package com.library.app.screens.onboarding.login

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.R
import com.library.app.networking.Result
import com.library.app.networking.models.LoginResponseSchema
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.common.Navigation
import com.library.app.screens.onboarding.signup.SignupValidationUsecase
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class encapsulates the `Login` process in Flow chart
 */
class LoginActivity : BaseActivity(), LoginUIInteractor.LoginController {

    /**
     * Consists the controller logic for this class
     */
    lateinit var loginViewModel: LoginViewModel

    /**
     * Used to navigate between screens
     */
    @Inject
    lateinit var mNavigation: Navigation

    /**
     * Used to delegate the responsiblity of UI operations
     */
    @Inject
    lateinit var mLoginUIInteractor: LoginUIInteractor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this, providerFactory).get(LoginViewModel::class.java)

        mLoginUIInteractor.loginController = this

        setContentView(mLoginUIInteractor.getRootView())

        observeLoginValidation()

        observeLoginResult()
    }

    /**
     * Shows the validation messages if invalid input is given to login form
     */
    private fun observeLoginValidation() {
        loginViewModel.validationMsg.observe(this, Observer { msg ->
            mLoginUIInteractor.showValidationDialog(msg)
        })
    }

    /**
     * Observes the result of login API call.
     * Uses LoginUIInteractor to show the response from Server
     */
    private fun observeLoginResult() {
        loginViewModel.result.observe(this, Observer { result ->
            mLoginUIInteractor.loading = false
            if (result is Result.Success) {
                val response = result.data as LoginResponseSchema
                mLoginUIInteractor.showLoginSuccessDialog(response.message!!)
            } else if (result is Result.Error) {
                val err = result.errorResponseSchema
                mLoginUIInteractor.showLoginFailDialog(err.data[0].msg)
            }
        })
    }

    /**
     * Initiates the Login API call
     */
    override fun loginUser(email: String, password: String) {
        val validationCode = loginViewModel.login(email, password)
        handleValidation(validationCode)
    }

    private fun handleValidation(validationCode: LoginValidationUsecase.LoginValidations) {
        when (validationCode) {
            LoginValidationUsecase.LoginValidations.EMPTY_EMAIL_ERROR -> getString(R.string.plz_enter_email)
            LoginValidationUsecase.LoginValidations.INVALID_EMAIL_ERROR -> getString(R.string.plz_enter_valid_email)
            LoginValidationUsecase.LoginValidations.EMPTY_PASSWORD_ERROR -> getString(R.string.plz_enter_pwd)
            LoginValidationUsecase.LoginValidations.INVALID_PASSWORD_LENGTH_ERROR -> getString(R.string.pwd_shd_contain_6_char)
            LoginValidationUsecase.LoginValidations.SPCHR_PASSWORD_ERROR -> getString(R.string.pwd_shd_have_1_sp_char)
            LoginValidationUsecase.LoginValidations.VALID -> mLoginUIInteractor.loading = true
        }
    }

    /**
     * Intent to open the Signup Screen
     */
    override fun openSignupScreen() {
        mNavigation.OnBoarding().openSignupScreen(this)
    }

    override fun openForgotPasswordScreen() {
        mNavigation.OnBoarding().openForgotPasswordScreen(this)
    }
}