package com.library.app.screens.onboarding.login

import androidx.lifecycle.Observer
import com.library.app.networking.Result
import com.library.app.networking.models.LoginResponseSchema
import com.library.app.repositories.AuthRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.library.app.networking.models.ErrorObj
import com.library.app.networking.models.ErrorResponseSchema
import com.nhaarman.mockitokotlin2.anyOrNull
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by Lalit Hajare, Software Engineer on 9/6/20
 * The purpose of this class is to ensure the desired behaviour of `LoginViewModel`
 * @see com.library.app.screens.onboarding.login.LoginViewModel for more information
 */
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    /**
     * Dependencies for SUT
     */
    private val mAuthRepository = mock(AuthRepository::class.java)
    private val mLoginInputValidator: LoginInputValidator =
        mock(LoginInputValidator::class.java)

    private var SUT = LoginViewModel(mAuthRepository, mLoginInputValidator)

    private val email = "test@gmail.com"
    private val password = "123@Abc"


    @Before
    fun prepareTest() {

    }

    /**
     * When access token & userId is not acquired,
     * Confirm that ViewModel delivers error response to livedata
     */
    @Test
    fun `login failed`() = runBlockingTest {
        //Arrange
        val response = Result.Error(
            ErrorResponseSchema(
                "xxxxxx",
                arrayListOf(ErrorObj("xxxx", "xxxx", "xxxx", "xxxx"))
            )
        )
        `when`(SUT.authRepository.login(anyOrNull())).thenReturn(
            response
        )
        val observer = mock(Observer::class.java) as Observer<Result<Any>>
        SUT.result.observeForever(observer)
        //Act
        SUT.login(email, password)
        //Assert
        val captor = ArgumentCaptor.forClass(Result::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(response, value)
        }
    }


    /**
     * When access token & userId is acquired,
     * Confirm that ViewModel delivers success response to livedata
     */
    @Test
    fun `login successful`() = runBlockingTest {
        //Arrange
        val successResponse = Result.Success(LoginResponseSchema("XXXXXXXX", "XXXX"))
        `when`(SUT.authRepository.login(anyOrNull())).thenReturn(
            successResponse
        )
        val observer = mock(Observer::class.java) as Observer<Result<Any>>
        SUT.result.observeForever(observer)
        //Act
        SUT.login(email, password)
        //Assert
        val captor = ArgumentCaptor.forClass(Result::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(successResponse, value)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}