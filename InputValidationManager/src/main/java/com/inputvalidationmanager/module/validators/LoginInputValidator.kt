package com.inputvalidationmanager.module.validators


/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class validation the fields on Login screen
 * It returns the validation code on which the Activity/Screen acts upon.
 */
class LoginInputValidator : InputValidator() {

    /**
     * Returns Boolean value whether the credentials are in proper format before sending to server
     * Also, invokes the callback function passed in this functions, returning validation code
     */
    override fun isValid(): ValidationCode {
        if (email.isEmpty()) {
            return ValidationCode.EMPTY_EMAIL_ERROR
        } else if (!isValidEmail(email)) {
            return ValidationCode.INVALID_EMAIL_ERROR
        } else if (password.isEmpty()) {
            return ValidationCode.EMPTY_PASSWORD_ERROR
        } else if (password.length < 6) {
            return ValidationCode.INVALID_PASSWORD_LENGTH_ERROR
        } else if (!hasSpecialChar(password)) {
            return ValidationCode.SPCHR_PASSWORD_ERROR
        }
        return ValidationCode.VALID
    }

}