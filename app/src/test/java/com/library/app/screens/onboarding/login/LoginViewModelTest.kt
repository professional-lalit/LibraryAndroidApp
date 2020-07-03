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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import observeOnce
import org.junit.After
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by Lalit Hajare, Software Engineer on 9/6/20
 * The purpose of this class is to ensure the desired behaviour of `LoginViewModel`
 * @see com.library.app.screens.onboarding.login.LoginViewModel for more information
 * 1. Test whether the login up API result was 'Result.Error', i.e, API Failure
 * 2. Test whether the login up API result was 'Result.Success', i.e, API Success
 */
@RunWith(JUnit4::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * Dependencies for SUT
     */
    private val mAuthRepository = mock(AuthRepository::class.java)

    private var SUT = LoginViewModel(mAuthRepository)

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
        val errorResponse = Result.Error(
            ErrorResponseSchema(
                "xxxxxx",
                arrayListOf(ErrorObj("xxxx", "xxxx", "xxxx", "xxxx"))
            )
        )
        `when`(SUT.authRepository.login(anyOrNull())).thenReturn(
            errorResponse
        )
        //Act
        SUT.login(email, password)
        //Assert
        SUT.result.observeOnce { value ->
            org.junit.Assert.assertEquals(errorResponse, value)
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
        //Act
        SUT.login(email, password)
        //Assert
        SUT.result.observeOnce { value ->
            org.junit.Assert.assertEquals(successResponse, value)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}