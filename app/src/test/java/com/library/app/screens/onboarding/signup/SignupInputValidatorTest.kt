package com.library.app.screens.onboarding.signup

import com.library.app.screens.common.InputValidator
import com.library.app.screens.common.ValidationManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SignupInputValidatorTest {

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
        val name = "Lalit Hajare"
        val confPassword = "123@456"
        //Act
        SUT = mValidationManager.getSignupValidator(name, email, password, confPassword)
        val value = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.VALID, value)
    }

    @Test
    fun `empty email`() {
        //Arrange
        val email = ""
        val password = "123@456"
        val name = "Lalit Hajare"
        val confPassword = "123@456"
        //Act
        SUT = mValidationManager.getSignupValidator(name, email, password, confPassword)
        val value = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.EMPTY_EMAIL_ERROR, value)
    }

    @Test
    fun `invalid email`() {
        //Arrange
        val email = "lalitgmail.com"
        val password = "123@456"
        val name = "Lalit Hajare"
        val confPassword = "123@456"
        //Act
        SUT = mValidationManager.getSignupValidator(name, email, password, confPassword)
        val value = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.INVALID_EMAIL_ERROR, value)
    }

    @Test
    fun `empty password`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = ""
        val name = "Lalit Hajare"
        val confPassword = ""
        //Act
        SUT = mValidationManager.getSignupValidator(name, email, password, confPassword)
        val value = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.EMPTY_PASSWORD_ERROR, value)
    }

    @Test
    fun `password length minimum 6`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "12345"
        val name = "Lalit Hajare"
        val confPassword = "12345"
        //Act
        SUT = mValidationManager.getSignupValidator(name, email, password, confPassword)
        val value = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.INVALID_PASSWORD_LENGTH_ERROR, value)
    }

    @Test
    fun `password atleast 1 special character`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123456"
        val name = "Lalit Hajare"
        val confPassword = "12345"
        //Act
        SUT = mValidationManager.getSignupValidator(name, email, password, confPassword)
        val value = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.SPCHR_PASSWORD_ERROR, value)
    }

    fun `non matching passwords`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        val name = "Lalit Hajare"
        val confPassword = "123@45"
        //Act
        SUT = mValidationManager.getSignupValidator(name, email, password, confPassword)
        val value = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.UNMATCHING_PASSWORDS_ERROR, value)
    }

    fun `empty name`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        val name = ""
        val confPassword = "123@456"
        //Act
        SUT = mValidationManager.getSignupValidator(name, email, password, confPassword)
        val value = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.EMPTY_NAME_ERROR, value)
    }

    fun `last name not entered`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        val name = "Lalit"
        val confPassword = "123@45"
        //Act
        SUT = mValidationManager.getSignupValidator(name, email, password, confPassword)
        val value = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.FULL_NAME_ERROR, value)
    }


}