package com.library.app.screens.onboarding.forgot_password

import com.library.app.common.Constants
import com.library.app.screens.onboarding.login.LoginValidationUsecase
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 29/6/20
 * This class is used to validate the email entered in ForgotPassword Screen
 * * It returns the validation code on which the Activity/Screen acts upon.
 */
class ForgotPasswordValidationUsecase @Inject constructor() {
    private val EMAIL_PATTERN: Pattern = Pattern.compile(Constants.emailPattern)

    enum class ForgotPasswordValidations { EMPTY_EMAIL_ERROR, INVALID_EMAIL_ERROR, VALID }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && EMAIL_PATTERN.matcher(email).matches()
    }

    fun getValidationCode(email: String): ForgotPasswordValidations {
        if (email.isEmpty()) {
            return ForgotPasswordValidations.EMPTY_EMAIL_ERROR
        } else if (!isValidEmail(email)) {
            return ForgotPasswordValidations.INVALID_EMAIL_ERROR
        }
        return ForgotPasswordValidations.VALID
    }

}