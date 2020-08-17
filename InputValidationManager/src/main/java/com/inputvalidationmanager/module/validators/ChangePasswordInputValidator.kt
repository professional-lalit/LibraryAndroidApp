package com.inputvalidationmanager.module.validators

import com.inputvalidationmanager.module.Constants.MINIMUM_PASSWORD_LENGTH


/**
 * This class validates input on Change Password screen.
 * It returns the validation code on which the Activity/Screen acts upon.
 */
class ChangePasswordInputValidator : InputValidator() {

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
        return ValidationCode.VALID
    }

}