package com.library.app.screens.onboarding.signup

import android.content.Context
import com.library.app.R
import com.library.app.common.Constants.emailPattern
import com.library.app.common.Constants.invalidPwdPattern
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class validation the fields on Signup screen
 * The verification class for this class is com.library.app.screens.onboarding.signup.SignupValidationUsecaseTest
 */
class SignupValidationUsecase @Inject constructor() {

    private val EMAIL_PATTERN: Pattern = Pattern.compile(emailPattern)
    private val INVALID_PASSWORD_PATTERN: Pattern = Pattern.compile(invalidPwdPattern)

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && EMAIL_PATTERN.matcher(email).matches()
    }

    private fun hasSpecialChar(password: String): Boolean {
        return password.isNotEmpty() && !INVALID_PASSWORD_PATTERN.matcher(password).matches()
    }

    enum class SignupValidations {
        EMPTY_NAME_ERROR, FULL_NAME_ERROR, EMPTY_EMAIL_ERROR, INVALID_EMAIL_ERROR, EMPTY_PASSWORD_ERROR,
        INVALID_PASSWORD_LENGTH_ERROR, SPCHR_PASSWORD_ERROR, UNMATCHING_PASSWORDS_ERROR, VALID
    }

    /**
     * Returns Boolean value whether the credentials are in proper format before sending to server
     * Also, invokes the callback function passed in this functions, returning appropriate message
     */
    fun validateCredentials(
        name: String,
        email: String,
        password: String,
        confPassword: String
    ): SignupValidations {
        if (name.isEmpty()) {
            return SignupValidations.EMPTY_NAME_ERROR
        } else if (!name.contains(" ")) {
            return SignupValidations.FULL_NAME_ERROR
        } else if (email.isEmpty()) {
            return SignupValidations.EMPTY_EMAIL_ERROR
        } else if (!isValidEmail(email)) {
            return SignupValidations.INVALID_EMAIL_ERROR
        } else if (password.isEmpty()) {
            return SignupValidations.EMPTY_PASSWORD_ERROR
        } else if (password.length < 6) {
            return SignupValidations.INVALID_PASSWORD_LENGTH_ERROR
        } else if (!hasSpecialChar(password)) {
            return SignupValidations.SPCHR_PASSWORD_ERROR
        } else if (confPassword != password) {
            return SignupValidations.UNMATCHING_PASSWORDS_ERROR
        }
        return SignupValidations.VALID
    }

}