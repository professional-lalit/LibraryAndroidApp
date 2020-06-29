package com.library.app.screens.onboarding.change_password

import com.library.app.common.Constants
import com.library.app.common.Constants.MINIMUM_PASSWORD_LENGTH
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * This class validates input on Change Password screen.
 * It returns the validation code on which the Activity/Screen acts upon.
 */
class ChangePasswordValidationUsecase @Inject constructor() {

    private val INVALID_PASSWORD_PATTERN: Pattern = Pattern.compile(Constants.invalidPwdPattern)

    private fun hasSpecialChar(password: String): Boolean {
        return password.isNotEmpty() && !INVALID_PASSWORD_PATTERN.matcher(password).matches()
    }

    enum class ChangePasswordValidations {
        NEW_PASSWORD_EMPTY,
        NEW_PASSWORD_LENGTH_ERROR,
        NEW_PASSWORD_SPCHR_ERROR,
        PASSWORD_MATCHING_ERROR,
        VALID
    }

    fun getValidationCode(
        newPassword: String,
        confirmedPassword: String
    ): ChangePasswordValidations {
        if (newPassword.isEmpty()) {
            return ChangePasswordValidations.NEW_PASSWORD_EMPTY
        } else if (newPassword.length < MINIMUM_PASSWORD_LENGTH) {
            return ChangePasswordValidations.NEW_PASSWORD_LENGTH_ERROR
        } else if (!hasSpecialChar(newPassword)) {
            return ChangePasswordValidations.NEW_PASSWORD_SPCHR_ERROR
        } else if (confirmedPassword != newPassword) {
            return ChangePasswordValidations.PASSWORD_MATCHING_ERROR
        }
        return ChangePasswordValidations.VALID
    }

}