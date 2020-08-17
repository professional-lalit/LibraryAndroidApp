package com.inputvalidationmanager.module.validators


/**
 * Created by Lalit Hajare, Software Engineer on 29/6/20
 * This class is used to validate the email entered in ForgotPassword Screen
 * * It returns the validation code on which the Activity/Screen acts upon.
 */
class ForgotPasswordInputValidator : InputValidator() {

    override fun isValid(): ValidationCode {
        if (email.isEmpty()) {
            return ValidationCode.EMPTY_EMAIL_ERROR
        } else if (!isValidEmail(email)) {
            return ValidationCode.INVALID_EMAIL_ERROR
        }
        return ValidationCode.VALID
    }

}