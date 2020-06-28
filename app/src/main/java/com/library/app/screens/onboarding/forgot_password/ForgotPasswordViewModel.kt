package com.library.app.screens.onboarding.forgot_password

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.library.app.common.BaseViewModel
import com.library.app.networking.Result
import com.library.app.networking.models.BaseResponseSchema
import com.library.app.networking.models.ForgotPasswordRequestSchema
import com.library.app.networking.models.ForgotPasswordResposeSchema
import com.library.app.repositories.AuthRepository
import kotlinx.coroutines.launch
import makeToast
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 22/6/20
 */
class ForgotPasswordViewModel @Inject constructor(
    val context: Context,
    val mAuthRepository: AuthRepository
) : BaseViewModel() {

    private val mResult = MediatorLiveData<Result<Any>>()

    val result: LiveData<Result<Any>> = mResult

    fun submitEmail(email: String) {
        val request = ForgotPasswordRequestSchema(email)
        launch {
            mResult.postValue(mAuthRepository.forgotPassword(request))
        }
    }

}