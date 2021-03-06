package com.library.app.screens.onboarding.signup

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.library.app.networking.Result
import com.library.app.networking.models.SignupResponseSchema
import com.library.app.screens.common.BaseActivity
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
            mSignupUIInteractor.showValidationMsg(msg)
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
                mSignupUIInteractor.showSignupSuccessMsg(response.message!!)
            } else if (result is Result.Error) {
                val err = result.errorResponseSchema
                val message = err.data[0].msg
                mSignupUIInteractor.showSignupFailureMsg(message)
            }
        })
    }

    /**
     * Calls the Signup API via ViewModel
     */
    override fun signUpUser(name: String, email: String, password: String) {
        signupViewModel.signup(
            name,
            email,
            password
        )
        mSignupUIInteractor.loading = true
    }

    /**
     * Pops this screen from stack
     */
    override fun openLoginScreen() {
        mScreenNavigator.OnBoarding().signupBack(this)
    }
}