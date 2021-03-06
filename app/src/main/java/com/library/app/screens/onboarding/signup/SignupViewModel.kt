package com.library.app.screens.onboarding.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.library.app.common.BaseViewModel
import com.library.app.networking.Result
import com.library.app.networking.models.SignupRequestSchema
import com.library.app.repositories.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 11/6/20
 * This class is used as a controller for signup screen (LoginActivity)
 * & encapsulates the business logic `Signup` process on Flowchart
 * The main purpose of this class is to deliver the result from `AuthRepository`
 * @see com.library.app.repositories.AuthRepository
 * It validates the signup screen data using `SignupValidationUsecase`
 * @see com.library.app.screens.onboarding.signup.SignupInputValidator
 */
class SignupViewModel @Inject constructor(
    /**
     * Calls underlying API endpoint & returns API result
     */
    val authRepository: AuthRepository
) : BaseViewModel() {

    /**
     * Private Mutable Data, client program cannot access this data
     */
    private val mSignupResult = MediatorLiveData<Result<Any>>()
    private val mValidationResult = MediatorLiveData<String>()

    /**
     * Public Immutable Data, client program can only observe changes
     */
    val result: LiveData<Result<Any>> = mSignupResult
    val validationMsg: LiveData<String> = mValidationResult

    /**
     * Get signup result from `AuthRepository` & update the LiveData
     */
    fun signup(
        name: String,
        email: String,
        password: String
    ) {
        val signupRequestSchema = SignupRequestSchema(name, email, password)
        launch {
            val response = authRepository.signup(signupRequestSchema)
            mSignupResult.postValue(response)
        }
    }
}
