package com.library.app.screens.onboarding.login

import com.library.app.screens.common.InputValidator
import com.library.app.screens.common.ValidationManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
    fun `password atleast 1 special character`() {
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