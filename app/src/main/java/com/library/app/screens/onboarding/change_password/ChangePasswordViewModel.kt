package com.library.app.screens.onboarding.change_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.library.app.common.BaseViewModel
import com.library.app.networking.Result
import com.library.app.repositories.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 16/6/20
 * This class is used as a controller for change password screen (LoginActivity)
 * & encapsulates the business logic `change password` process on Flowchart
 * The main purpose of this class is to deliver the result from `AuthRepository`
 * @see com.library.app.repositories.AuthRepository
 */
class ChangePasswordViewModel @Inject constructor(
    val mAuthRepository: AuthRepository
) : BaseViewModel() {

    private val mResult = MediatorLiveData<Result<Any>>()

    val result: LiveData<Result<Any>> = mResult

    fun submitNewPassword(
        oldPassword: String,
        newPassword: String
    ) {
        launch {
            mResult.postValue(mAuthRepository.changePassword(oldPassword, newPassword))
        }
    }

}