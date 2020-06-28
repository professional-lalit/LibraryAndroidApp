package com.library.app.screens.onboarding.login

import android.content.Context
import com.library.app.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class LoginValidationUsecaseTest {

    //Dependency for SUT
    val mContextMock: Context = mock(Context::class.java)

    //System Under Test
    val SUT = LoginValidationUsecase(
        mContextMock
    )

    //Mocks for strings
    val EMPTY_EMAIL = "Please enter email"
    val INVALID_EMAIL = "Please enter valid email"
    val EMPTY_PASSWORD = "Please enter password"
    val PWD_MIN_6 = "Password should contain atleast 6 characters"
    val PWD_ATLEAST_1_SP_CHAR = "Password should have atleast 1 special character"

    @Before
    fun prepareTest() {
        `when`(mContextMock.getString(R.string.plz_enter_email)).thenReturn(EMPTY_EMAIL)
        `when`(mContextMock.getString(R.string.plz_enter_valid_email)).thenReturn(INVALID_EMAIL)
        `when`(mContextMock.getString(R.string.plz_enter_pwd)).thenReturn(EMPTY_PASSWORD)
        `when`(mContextMock.getString(R.string.pwd_shd_contain_6_char)).thenReturn(PWD_MIN_6)
        `when`(mContextMock.getString(R.string.pwd_shd_have_1_sp_char)).thenReturn(
            PWD_ATLEAST_1_SP_CHAR
        )
    }

    @Test
    fun `valid credentials`() {
        //Arrange
        val email = "lalit@gmail.com"
        val password = "123@456"
        //Act
        val value = SUT.validateCredentials(email, password) { msg ->
            //Assert
            Assert.assertEquals(msg, EMPTY_EMAIL)
        }
        //Assert
        Assert.assertEquals(value, true)
    }

    @Test
    fun `empty email`() {
        //Arrange
        val email = ""
        val password = "123456"
        //Act
        val value = SUT.validateCredentials(email, password) { msg ->
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
        //Act
        val value = SUT.validateCredentials(email, password) { msg ->
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
        //Act
        val value = SUT.validateCredentials(email, password) { msg ->
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
        //Act
        val value = SUT.validateCredentials(email, password) { msg ->
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
        //Act
        val value = SUT.validateCredentials(email, password) { msg ->
            //Assert
            Assert.assertEquals(msg, PWD_ATLEAST_1_SP_CHAR)
        }
        //Assert
        Assert.assertEquals(value, false)
    }

}