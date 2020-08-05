package com.library.app.screens.onboarding.signup

import com.library.app.common.Constants.emailPattern
import com.library.app.common.Constants.invalidPwdPattern
import com.library.app.di.annotations.SignupScope
import com.library.app.screens.common.InputValidator
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class validation the fields on Signup screen
 * It returns the validation code on which the Activity/Screen acts upon.
 */
class SignupInputValidator @Inject constructor() : InputValidator() {

    /**
     * Returns Boolean value whether the credentials are in proper format before sending to server
     * Also, invokes the callback function passed in this functions, returning appropriate message
     */
    override fun isValid(): ValidationCode {
        if (name.isEmpty()) {
            return ValidationCode.EMPTY_NAME_ERROR
        } else if (!name.contains(" ")) {
            return ValidationCode.FULL_NAME_ERROR
        } else if (email.isEmpty()) {
            return ValidationCode.EMPTY_EMAIL_ERROR
        } else if (!isValidEmail(email)) {
            return ValidationCode.INVALID_EMAIL_ERROR
        } else if (password.isEmpty()) {
            return ValidationCode.EMPTY_PASSWORD_ERROR
        } else if (password.length < 6) {
            return ValidationCode.INVALID_PASSWORD_LENGTH_ERROR
        } else if (!hasSpecialChar(password)) {
            return ValidationCode.SPCHR_PASSWORD_ERROR
        } else if (confirmedPassword != password) {
            return ValidationCode.UNMATCHING_PASSWORDS_ERROR
        }
        return ValidationCode.VALID
    }

}