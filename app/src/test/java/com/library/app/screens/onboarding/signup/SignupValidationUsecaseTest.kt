package com.library.app.screens.onboarding.signup

import android.content.Context
import com.library.app.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class SignupValidationUsecaseTest {

    //Dependency for SUT
    val mContextMock: Context = mock(Context::class.java)

    //System Under Test
    val SUT = SignupValidationUsecase(
        mContextMock
    )

    //Mocks for strings
    val EMPTY_EMAIL = "Please enter email"
    val INVALID_EMAIL = "Please enter valid email"
    val EMPTY_PASSWORD = "Please enter password"
    val PWD_MIN_6 = "Password should contain atleast 6 characters"
    val PWD_ATLEAST_1_SP_CHAR = "Password should have atleast 1 special character"
    val MSG_ENTER_NAME = "Please enter name"
    val MSG_FIRST_LAST_NAME = "Please enter first & last name"
    val MSG_PWD_MATCH = "Passwords don't match"

    @Before
    fun prepareTest() {
        Mockito.`when`(mContextMock.getString(R.string.plz_enter_email)).thenReturn(EMPTY_EMAIL)
        Mockito.`when`(mContextMock.getString(R.string.plz_enter_valid_email))
            .thenReturn(INVALID_EMAIL)
        Mockito.`when`(mContextMock.getString(R.string.plz_enter_pwd)).thenReturn(EMPTY_PASSWORD)
        Mockito.`when`(mContextMock.getString(R.string.pwd_shd_contain_6_char))
            .thenReturn(PWD_MIN_6)
        Mockito.`when`(mContextMock.getString(R.string.pwd_shd_have_1_sp_char))
            .thenReturn(PWD_ATLEAST_1_SP_CHAR)
        Mockito.`when`(mContextMock.getString(R.string.plz_enter_name)).thenReturn(MSG_ENTER_NAME)
        Mockito.`when`(mContextMock.getString(R.string.plz_first_last_name))
            .thenReturn(MSG_FIRST_LAST_NAME)
        Mockito.`when`(mContextMock.getString(R.string.pwd_dont_match)).thenReturn(MSG_PWD_MATCH)
    }

    @Test
    fun `valid credentials`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        val name = "Lalit Hajare"
        val confPassword = "123@456"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword) {}
        //Assert
        Assert.assertEquals(value, true)
    }

    @Test
    fun `empty email`() {
        //Arrange
        val email = ""
        val password = "123@456"
        val name = "Lalit Hajare"
        val confPassword = "123@456"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword) { msg ->
            //Assert
            Assert.assertEquals(msg, EMPTY_EMAIL)
        }
        //Assert
        Assert.assertEquals(value, false)
    }

    @Test
    fun `invalid email`() {
        //Arrange
        val email = "lalitgmail.com"
        val password = "123@456"
        val name = "Lalit Hajare"
        val confPassword = "123@456"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword) { msg ->
            //Assert
            Assert.assertEquals(msg, INVALID_EMAIL)
        }
        //Assert
        Assert.assertEquals(value, false)
    }

    @Test
    fun `empty password`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = ""
        val name = "Lalit Hajare"
        val confPassword = ""
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword) { msg ->
            //Assert
            Assert.assertEquals(msg, EMPTY_PASSWORD)
        }
        //Assert
        Assert.assertEquals(value, false)
    }

    @Test
    fun `password length minimum 6`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "12345"
        val name = "Lalit Hajare"
        val confPassword = "12345"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword) { msg ->
            //Assert
            Assert.assertEquals(msg, PWD_MIN_6)
        }
        //Assert
        Assert.assertEquals(value, false)
    }

    @Test
    fun `password atleast 1 special character`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123456"
        val name = "Lalit Hajare"
        val confPassword = "12345"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword) { msg ->
            //Assert
            Assert.assertEquals(msg, PWD_ATLEAST_1_SP_CHAR)
        }
        //Assert
        Assert.assertEquals(value, false)
    }

    fun `unmatching passwords`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        val name = "Lalit Hajare"
        val confPassword = "123@45"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword) { msg ->
            //Assert
            Assert.assertEquals(msg, MSG_PWD_MATCH)
        }
        //Assert
        Assert.assertEquals(value, false)
    }

    fun `empty name`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        val name = ""
        val confPassword = "123@456"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword) { msg ->
            //Assert
            Assert.assertEquals(msg, MSG_ENTER_NAME)
        }
        //Assert
        Assert.assertEquals(value, false)
    }

    fun `last name not entered`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        val name = "Lalit"
        val confPassword = "123@45"
        //Act
        val value = SUT.validateCredentials(name, email, password, confPassword) { msg ->
            //Assert
            Assert.assertEquals(msg, MSG_FIRST_LAST_NAME)
        }
        //Assert
        Assert.assertEquals(value, false)
    }


}