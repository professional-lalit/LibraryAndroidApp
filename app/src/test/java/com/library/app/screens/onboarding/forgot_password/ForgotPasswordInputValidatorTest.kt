package com.library.app.screens.onboarding.forgot_password

import com.library.app.screens.common.InputValidator
import com.library.app.screens.common.ValidationManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Lalit Hajare, Software Engineer on 29/6/20
 * This class ensures the working functionality of `ForgotPasswordValidationUsecase` class.
 * It tests the functionality for various inputs like;
 * 1. Valid email
 * 2. Invalid email
 * 3. Non-empty email
 * @see ForgotPasswordInputValidator
 */
class ForgotPasswordInputValidatorTest {

    val mValidationManager = ValidationManager()
    private lateinit var SUT: InputValidator

    @Before
    fun prepareTest() {

    }

    @Test
    fun `valid email`() {
        //Arrange
        val email = "abc@bcd.com"
        //Act
        SUT = mValidationManager.getForgotPasswordValidator(email)
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.VALID, code)
    }

    @Test
    fun `invalid email`() {
        //Arrange
        val email = "abcbcd.com"
        //Act
        SUT = mValidationManager.getForgotPasswordValidator(email)
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.INVALID_EMAIL_ERROR, code)
    }

    @Test
    fun `empty email`() {
        //Arrange
        val email = ""
        //Act
        SUT = mValidationManager.getForgotPasswordValidator(email)
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.EMPTY_EMAIL_ERROR, code)
    }

}