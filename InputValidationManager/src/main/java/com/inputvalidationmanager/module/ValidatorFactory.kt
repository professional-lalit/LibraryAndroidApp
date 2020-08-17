package com.inputvalidationmanager.module

import com.inputvalidationmanager.module.validators.*


/**
 * Created by Lalit Hajare, Software Engineer on 2/7/20
 */
class ValidatorFactory {

    enum class ValidationScheme {
        SIGNUP, LOGIN, FORGOT_PASSWORD, CHANGE_PASSWORD
    }

    fun getValidator(scheme: ValidationScheme): InputValidator {
        when (scheme) {

            ValidationScheme.SIGNUP -> {
                return SignupInputValidator()
            }

            ValidationScheme.LOGIN -> {
                return LoginInputValidator()
            }

            ValidationScheme.FORGOT_PASSWORD -> {
                return ForgotPasswordInputValidator()
            }

            ValidationScheme.CHANGE_PASSWORD -> {
                return ChangePasswordInputValidator()
            }
        }
    }

}