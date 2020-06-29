package com.library.app.screens.onboarding.signup

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.R
import com.library.app.networking.Result
import com.library.app.networking.models.SignupResponseSchema
import com.library.app.screens.common.BaseActivity
import com.library.app.screens.common.Navigation
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class encapsulates the `Signup` process in Flow chart
 */
class SignupActivity : BaseActivity(), SignupUIInteractor.SignupController {

    /**
     * Consists the controller logic for this class
     */
    lateinit var signupViewModel: SignupViewModel

    /**
     * Used to delegate the responsiblity of UI operations
     */
    @Inject
    lateinit var mSignupUIInteractor: SignupUIInteractor

    /**
     * Used to navigate between screens
     */
    @Inject
    lateinit var mNavigation: Navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signupViewModel = ViewModelProvider(this, providerFactory).get(SignupViewModel::class.java)

        mSignupUIInteractor.signupController = this

        setContentView(mSignupUIInteractor.getRootView(signupViewModel))

        observeSignupValidation()

        observeSignupResult()
    }

    /**
     * Shows the validation messages if invalid input is given to signup form
     */
    private fun observeSignupValidation() {
        signupViewModel.validationMsg.observe(this, Observer { msg ->
            mSignupUIInteractor.showValidationDialog(msg)
        })
    }

    /**
     * Shows the result of signup API call
     */
    private fun observeSignupResult() {
        signupViewModel.result.observe(this, Observer { result ->
            mSignupUIInteractor.loading = false
            if (result is Result.Success) {
                val response = result.data as SignupResponseSchema
                mSignupUIInteractor.showSignupSuccessDialog(response.message!!)
            } else if (result is Result.Error) {
                val err = result.errorResponseSchema
                mSignupUIInteractor.showSignupFailDialog(err.data[0].msg)
            }
        })
    }

    /**
     * Calls the Signup API via ViewModel
     */
    override fun signUpUser(name: String, email: String, password: String, repassword: String) {
        val validationCode = signupViewModel.signup(
            name,
            email,
            password,
            repassword
        )
        handleValidation(validationCode)
    }

    private fun handleValidation(validationCode: SignupValidationUsecase.SignupValidations) {
        when (validationCode) {
            SignupValidationUsecase.SignupValidations.EMPTY_NAME_ERROR -> getString(R.string.plz_enter_name)
            SignupValidationUsecase.SignupValidations.FULL_NAME_ERROR -> getString(R.string.plz_first_last_name)
            SignupValidationUsecase.SignupValidations.EMPTY_EMAIL_ERROR -> getString(R.string.plz_enter_email)
            SignupValidationUsecase.SignupValidations.INVALID_EMAIL_ERROR -> getString(R.string.plz_enter_valid_email)
            SignupValidationUsecase.SignupValidations.EMPTY_PASSWORD_ERROR -> getString(R.string.plz_enter_pwd)
            SignupValidationUsecase.SignupValidations.INVALID_PASSWORD_LENGTH_ERROR -> getString(R.string.pwd_shd_contain_6_char)
            SignupValidationUsecase.SignupValidations.SPCHR_PASSWORD_ERROR -> getString(R.string.pwd_shd_have_1_sp_char)
            SignupValidationUsecase.SignupValidations.UNMATCHING_PASSWORDS_ERROR -> getString(R.string.pwd_dont_match)
            SignupValidationUsecase.SignupValidations.VALID -> mSignupUIInteractor.loading = true
        }
    }

    /**
     * Pops this screen from stack
     */
    override fun openLoginScreen() {
        mNavigation.OnBoarding().signupBack(this)
    }
}