package com.library.app.screens.onboarding.change_password

import com.library.app.R
import com.library.app.common.Constants
import java.util.regex.Pattern
import javax.inject.Inject

class ChangePasswordValidationUsecase @Inject constructor() {

    private val INVALID_PASSWORD_PATTERN: Pattern = Pattern.compile(Constants.invalidPwdPattern)

    private fun hasSpecialChar(password: String): Boolean {
        return password.isNotEmpty() && !INVALID_PASSWORD_PATTERN.matcher(password).matches()
    }

    enum class ChangePasswordValidations {
        NEW_PASSWORD_EMPTY,
        NEW_PASSWORD_LENGTH_ERROR,
        NEW_PASSWORD_SPCHR_ERROR,
        MATCHING_ERROR,
        VALID
    }

    fun isValid(
        newPassword: String,
        confirmPassword: String,
        cb: (ChangePasswordValidations) -> Unit
    ): Boolean {
        if (newPassword.isEmpty()) {
            cb.invoke(ChangePasswordValidations.NEW_PASSWORD_EMPTY)
            return false
        } else if (newPassword.length < 6) {
            cb.invoke(ChangePasswordValidations.NEW_PASSWORD_LENGTH_ERROR)
            return false
        } else if (!hasSpecialChar(newPassword)) {
            cb.invoke(ChangePasswordValidations.NEW_PASSWORD_SPCHR_ERROR)
            return false
        } else if (confirmPassword != newPassword) {
            cb.invoke(ChangePasswordValidations.MATCHING_ERROR)
            return false
        }
        return true
    }

}