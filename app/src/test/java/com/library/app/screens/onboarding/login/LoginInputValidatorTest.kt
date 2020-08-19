package com.library.app.screens.onboarding.login

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * This class ensures the functioning of `LoginInputValidator`
 * It checks for:
 * 1. Valid credentials
 * 2. Empty email
 * 3. Invalid email
 * 4. Empty password
 * 5. password length minimum 6
 * 6. password at least 1 special character
 * @see LoginInputValidator
 * The instance of Validator is obtained through `ValidationManager`
 * @see ValidationManager
 */
@RunWith(JUnit4::class)
class LoginInputValidatorTest {

    //System Under Test
    val mValidationManager = ValidationManager()
    private lateinit var SUT: InputValidator

    @Before
    fun prepareTest() {

    }

    @Test
    fun `valid credentials`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        //Act
        SUT = mValidationManager.getLoginValidator(email, password)
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(code, InputValidator.ValidationCode.VALID)
    }

    @Test
    fun `empty email`() {
        //Arrange
        val email = ""
        val password = "123456"
        //Act
        SUT = mValidationManager.getLoginValidator(email, password)
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(code, InputValidator.ValidationCode.EMPTY_EMAIL_ERROR)
    }

    @Test
    fun `invalid email`() {
        //Arrange
        val email = "lalitgmail.com"
        val password = "123@456"
        //Act
        SUT = mValidationManager.getLoginValidator(email, password)
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(code, InputValidator.ValidationCode.INVALID_EMAIL_ERROR)
    }

    @Test
    fun `empty password`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = ""
        //Act
        SUT = mValidationManager.getLoginValidator(email, password)
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(code, InputValidator.ValidationCode.EMPTY_PASSWORD_ERROR)
    }

    @Test
    fun `password length minimum 6`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "12345"
        //Act
        SUT = mValidationManager.getLoginValidator(email, password)
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(code, InputValidator.ValidationCode.INVALID_PASSWORD_LENGTH_ERROR)
    }

    @Test
    fun `password at least 1 special character`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123456"
        //Act
        SUT = mValidationManager.getLoginValidator(email, password)
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(code, InputValidator.ValidationCode.SPCHR_PASSWORD_ERROR)
    }

}