package com.library.app.screens.common

import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 2/7/20
 * This class manages the initializations of Validators
 * Each function in this class initializes the Validator using Builder Pattern
 * designed in `InputValidator` class.
 * @see InputValidator
 */
class ValidationManager {

    /**
     * Builds the `SignupValidator` and returns
     * @see com.library.app.screens.onboarding.signup.SignupInputValidator
     * @param name: 'name' entered in Signup Screen to validate
     * @param email: 'email' entered in Signup Screen to validate
     * @param password: 'password' entered in Signup Screen to validate
     * @param confirmPassword: 'confirmPassword' entered in Signup Screen to validate
     */
    fun getSignupValidator(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): InputValidator {
        return InputValidator.Builder()
            .scheme(ValidatorFactory.ValidationScheme.SIGNUP)
            .name(name)
            .email(email)
            .password(password)
            .confirmedPassword(confirmPassword)
            .build()
    }

    /**
     * Builds the `LoginValidator` and returns
     * @see com.library.app.screens.onboarding.login.LoginInputValidator
     * @param email: 'email' entered in Login Screen to validate
     * @param password: 'password' entered in Login Screen to validate
     */
    fun getLoginValidator(
        email: String,
        password: String
    ): InputValidator {
        return InputValidator.Builder()
            .scheme(ValidatorFactory.ValidationScheme.LOGIN)
            .email(email)
            .password(password)
            .build()
    }

    /**
     * Builds the `ChangePasswordValidator` and returns
     * @see com.library.app.screens.onboarding.change_password.ChangePasswordInputValidator
     * @param oldPassword: 'oldPassword' entered in ChangePassword Screen to validate
     * @param password: 'password' entered in ChangePassword Screen to validate
     * @param confirmedPassword: 'confirmedPassword' entered in ChangePassword Screen to validate
     */
    fun getChangePasswordValidator(
        oldPassword: String,
        newPassword: String,
        confirmedPassword: String
    ): InputValidator {
        return InputValidator.Builder()
            .scheme(ValidatorFactory.ValidationScheme.CHANGE_PASSWORD)
            .oldPassword(oldPassword)
            .newPassword(newPassword)
            .confirmedPassword(confirmedPassword)
            .build()
    }

    /**
     * Builds the `ForgotPasswordValidator` and returns
     * @see com.library.app.screens.onboarding.forgot_password.ForgotPasswordInputValidator
     * @param email: 'email' entered in ForgotPassword Screen to validate
     */
    fun getForgotPasswordValidator(
        email: String
    ): InputValidator {
        return InputValidator.Builder()
            .scheme(ValidatorFactory.ValidationScheme.FORGOT_PASSWORD)
            .email(email)
            .build()
    }

}