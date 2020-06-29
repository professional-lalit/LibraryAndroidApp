package com.library.app.screens.onboarding.login

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoginValidationUsecaseTest {

    //System Under Test
    val SUT = LoginValidationUsecase()

    @Before
    fun prepareTest() {

    }

    @Test
    fun `valid credentials`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        //Act
        val code = SUT.validateCredentials(email, password)
        //Assert
        Assert.assertEquals(code, LoginValidationUsecase.LoginValidations.VALID)
    }

    @Test
    fun `empty email`() {
        //Arrange
        val email = ""
        val password = "123456"
        //Act
        val code = SUT.validateCredentials(email, password)
        //Assert
        Assert.assertEquals(code, LoginValidationUsecase.LoginValidations.EMPTY_EMAIL_ERROR)
    }

    @Test
    fun `invalid email`() {
        //Arrange
        val email = "lalitgmail.com"
        val password = "123@456"
        //Act
        val code = SUT.validateCredentials(email, password)
        //Assert
        Assert.assertEquals(code, LoginValidationUsecase.LoginValidations.INVALID_EMAIL_ERROR)
    }

    @Test
    fun `empty password`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = ""
        //Act
        val code = SUT.validateCredentials(email, password)
        //Assert
        Assert.assertEquals(code, LoginValidationUsecase.LoginValidations.EMPTY_PASSWORD_ERROR)
    }

    @Test
    fun `password length minimum 6`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "12345"
        //Act
        val code = SUT.validateCredentials(email, password)
        //Assert
        Assert.assertEquals(code, LoginValidationUsecase.LoginValidations.INVALID_PASSWORD_LENGTH_ERROR)
    }

    @Test
    fun `password atleast 1 special character`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123456"
        //Act
        val code = SUT.validateCredentials(email, password)
        //Assert
        Assert.assertEquals(code, LoginValidationUsecase.LoginValidations.SPCHR_PASSWORD_ERROR)
    }

}