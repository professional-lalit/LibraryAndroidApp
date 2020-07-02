package com.library.app.screens.onboarding.change_password

import com.library.app.common.Constants
import com.library.app.common.Constants.MINIMUM_PASSWORD_LENGTH
import com.library.app.di.annotations.ChangePasswordScope
import com.library.app.screens.common.InputValidator
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * This class validates input on Change Password screen.
 * It returns the validation code on which the Activity/Screen acts upon.
 */
@ChangePasswordScope
class ChangePasswordInputValidator @Inject constructor() : InputValidator() {

    override fun isValid(): ValidationCode {
        if (newPassword.isEmpty()) {
            return ValidationCode.NEW_PASSWORD_EMPTY
        } else if (newPassword.length < MINIMUM_PASSWORD_LENGTH) {
            return ValidationCode.NEW_PASSWORD_LENGTH_ERROR
        } else if (!hasSpecialChar(newPassword)) {
            return ValidationCode.NEW_PASSWORD_SPCHR_ERROR
        } else if (confirmedPassword != newPassword) {
            return ValidationCode.UNMATCHING_PASSWORDS_ERROR
        }
        return ValidationCode.VALID    }

}