package com.library.app.screens.onboarding.login

import com.library.app.common.Constants.emailPattern
import com.library.app.common.Constants.invalidPwdPattern
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class validation the fields on Login screen
 * The verification class for this class is com.library.app.screens.onboarding.login.LoginValidationUsecaseTest
 */
class LoginValidationUsecase @Inject constructor() {

    private val EMAIL_PATTERN: Pattern = Pattern.compile(emailPattern)
    private val INVALID_PASSWORD_PATTERN: Pattern = Pattern.compile(invalidPwdPattern)

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && EMAIL_PATTERN.matcher(email).matches()
    }

    private fun hasSpecialChar(password: String): Boolean {
        return password.isNotEmpty() && !INVALID_PASSWORD_PATTERN.matcher(password).matches()
    }

    enum class LoginValidations {
        EMPTY_EMAIL_ERROR, INVALID_EMAIL_ERROR, EMPTY_PASSWORD_ERROR, INVALID_PASSWORD_LENGTH_ERROR
        , SPCHR_PASSWORD_ERROR, VALID
    }

    /**
     * Returns Boolean value whether the credentials are in proper format before sending to server
     * Also, invokes the callback function passed in this functions, returning validation code
     */
    fun validateCredentials(email: String, password: String): LoginValidations {
        if (email.isEmpty()) {
            return LoginValidations.EMPTY_EMAIL_ERROR
        } else if (!isValidEmail(email)) {
            return LoginValidations.INVALID_EMAIL_ERROR
        } else if (password.isEmpty()) {
            return LoginValidations.EMPTY_PASSWORD_ERROR
        } else if (password.length < 6) {
            return LoginValidations.INVALID_PASSWORD_LENGTH_ERROR
        } else if (!hasSpecialChar(password)) {
            return LoginValidations.SPCHR_PASSWORD_ERROR
        }
        return LoginValidations.VALID
    }

}