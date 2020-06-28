package com.library.app.screens.onboarding.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.library.app.common.BaseViewModel
import com.library.app.networking.models.LoginRequestSchema
import com.library.app.networking.models.LoginResponseSchema
import kotlinx.coroutines.launch
import com.library.app.networking.Result
import com.library.app.repositories.AuthRepository
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class is used as a controller for login screen (LoginActivity)
 * & encapsulates the business logic `Login` process on Flowchart
 * The main purpose of this class is to deliver the result from `AuthRepository`
 * @see com.library.app.repositories.AuthRepository
 * It validates the login screen data using `LoginValidationUsecase`
 * @see com.library.app.screens.onboarding.login.LoginValidationUsecase
 */
class LoginViewModel @Inject constructor(
    /**
     * Calls underlying API endpoint & returns API result
     */
    val authRepository: AuthRepository,

    /**
     * Validates the data entered from screen
     */
    val loginValidationUsecase: LoginValidationUsecase
) :
    BaseViewModel() {
    /**
     * Private Mutable Data, client program cannot access this data
     */
    private val mLoginResult = MediatorLiveData<Result<Any>>()
    private val mValidationMessage = MediatorLiveData<String>()

    /**
     * Public Immutable Data, client program can only observe changes
     */
    val result: LiveData<Result<Any>> = mLoginResult
    val validationMsg: LiveData<String> = mValidationMessage

    /**
     * Get login result from `AuthRepository` & update the LiveData
     */
    fun login(email: String, password: String): Boolean {
        if (loginValidationUsecase.validateCredentials(
                email,
                password
            ) { errorMsg ->
                mValidationMessage.postValue(errorMsg)
            }
        ) {
            val loginRequestSchema = LoginRequestSchema(email, password)
            launch {
                val response = authRepository.login(loginRequestSchema)
                mLoginResult.postValue(response)
            }
            return true
        }
        return false
    }

}