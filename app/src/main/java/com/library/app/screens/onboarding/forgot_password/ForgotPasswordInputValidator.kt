package com.library.app.screens.onboarding.forgot_password

import com.library.app.common.Constants
import com.library.app.screens.common.InputValidator
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 29/6/20
 * This class is used to validate the email entered in ForgotPassword Screen
 * * It returns the validation code on which the Activity/Screen acts upon.
 */
class ForgotPasswordInputValidator @Inject constructor() : InputValidator() {

    override fun isValid(): ValidationCode {
        if (email.isEmpty()) {
            return ValidationCode.EMPTY_EMAIL_ERROR
        } else if (!isValidEmail(email)) {
            return ValidationCode.INVALID_EMAIL_ERROR
        }
        return ValidationCode.VALID
    }

}