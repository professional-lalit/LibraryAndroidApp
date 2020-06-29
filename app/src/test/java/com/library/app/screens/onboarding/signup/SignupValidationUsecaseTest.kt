package com.library.app.screens.onboarding.signup

import android.content.Context
import com.library.app.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class SignupValidationUsecaseTest {

    //System Under Test
    val SUT = SignupValidationUsecase()


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
        val value = SUT.validateCredentials(name, email, password, confPassword)
        //Assert
        Assert.assertEquals(value, SignupValidationUsecase.SignupValidations.VALID)
    }

    @Test
    fun `empty email`() {
        //Arrange
        val email = ""
        val password = "123@456"
        val name = "Lalit Hajare"
        val confPassword = "123@456"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword)
        //Assert
        Assert.assertEquals(value, SignupValidationUsecase.SignupValidations.EMPTY_EMAIL_ERROR)
    }

    @Test
    fun `invalid email`() {
        //Arrange
        val email = "lalitgmail.com"
        val password = "123@456"
        val name = "Lalit Hajare"
        val confPassword = "123@456"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword)
        //Assert
        Assert.assertEquals(value, SignupValidationUsecase.SignupValidations.INVALID_EMAIL_ERROR)
    }

    @Test
    fun `empty password`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = ""
        val name = "Lalit Hajare"
        val confPassword = ""
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword)
        //Assert
        Assert.assertEquals(value, SignupValidationUsecase.SignupValidations.EMPTY_PASSWORD_ERROR)
    }

    @Test
    fun `password length minimum 6`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "12345"
        val name = "Lalit Hajare"
        val confPassword = "12345"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword)
        //Assert
        Assert.assertEquals(value, SignupValidationUsecase.SignupValidations.INVALID_PASSWORD_LENGTH_ERROR)
    }

    @Test
    fun `password atleast 1 special character`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123456"
        val name = "Lalit Hajare"
        val confPassword = "12345"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword)
        //Assert
        Assert.assertEquals(value, SignupValidationUsecase.SignupValidations.SPCHR_PASSWORD_ERROR)
    }

    fun `non matching passwords`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        val name = "Lalit Hajare"
        val confPassword = "123@45"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword)
        //Assert
        Assert.assertEquals(value, SignupValidationUsecase.SignupValidations.UNMATCHING_PASSWORDS_ERROR)
    }

    fun `empty name`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        val name = ""
        val confPassword = "123@456"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword)
        //Assert
        Assert.assertEquals(value, SignupValidationUsecase.SignupValidations.EMPTY_NAME_ERROR)
    }

    fun `last name not entered`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        val name = "Lalit"
        val confPassword = "123@45"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword)
        //Assert
        Assert.assertEquals(value, SignupValidationUsecase.SignupValidations.FULL_NAME_ERROR)
    }


}