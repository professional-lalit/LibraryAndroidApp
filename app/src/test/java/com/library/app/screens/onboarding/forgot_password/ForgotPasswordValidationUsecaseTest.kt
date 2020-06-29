package com.library.app.screens.onboarding.forgot_password

import com.library.app.screens.onboarding.change_password.ChangePasswordValidationUsecase
import org.junit.Assert
import org.junit.Test

/**
 * Created by Lalit Hajare, Software Engineer on 29/6/20
 * This class ensures the working functionality of `ForgotPasswordValidationUsecase` class.
 * It tests the functionality for various inputs like;
 * 1. Valid email
 * 2. Invalid email
 * 3. Non-empty email
 * @see ForgotPasswordValidationUsecase
 */
class ForgotPasswordValidationUsecaseTest {

    val SUT = ForgotPasswordValidationUsecase()

    @Test
    fun `valid email`() {
        //Arrange
        val email = "abc@bcd.com"
        //Act
        val code = SUT.getValidationCode(email)
        //Assert
        Assert.assertEquals(ForgotPasswordValidationUsecase.ForgotPasswordValidations.VALID, code)
    }

    @Test
    fun `invalid email`() {
        //Arrange
        val email = "abcbcd.com"
        //Act
        val code = SUT.getValidationCode(email)
        //Assert
        Assert.assertEquals(
            ForgotPasswordValidationUsecase.ForgotPasswordValidations.INVALID_EMAIL_ERROR,
            code
        )
    }

    @Test
    fun `empty email`() {
        //Arrange
        val email = ""
        //Act
        val code = SUT.getValidationCode(email)
        //Assert
        Assert.assertEquals(
            ForgotPasswordValidationUsecase.ForgotPasswordValidations.EMPTY_EMAIL_ERROR,
            code
        )
    }

}