package com.library.app.screens.onboarding.login

import androidx.lifecycle.Observer
import com.library.app.networking.Result
import com.library.app.networking.models.LoginRequestSchema
import com.library.app.networking.models.LoginResponseSchema
import com.library.app.repositories.AuthRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.library.app.networking.models.ErrorObj
import com.library.app.networking.models.ErrorResponseSchema
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.junit.MockitoJUnitRunner
import utils.ManagedCoroutineScope
import utils.TestScope


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
    private var response: Result<Any> = Result.Success(LoginResponseSchema("xxxx", "xxxx"))

    val mAuthRepository = mock(AuthRepository::class.java)
    private val mLoginValidationUsecase: LoginValidationUsecase =
        mock(LoginValidationUsecase::class.java)

    private var SUT = LoginViewModel(mAuthRepository, mLoginValidationUsecase)

    private val email = "test@gmail.com"
    private val password = "123@Abc"


    @Before
    fun setUp() {
        `when`(
            SUT.loginValidationUsecase.validateCredentials(
                anyOrNull(),
                anyOrNull(),
                anyOrNull()
            )
        ).thenReturn(true)
    }

    /**
     * When access token & userId is not acquired,
     * Confirm that ViewModel delivers error response to livedata
     */
    @Test
    fun `login failed`() = runBlockingTest {
        //Arrange
        response = Result.Error(
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
        verify(observer).onChanged(response)
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
        verify(observer).onChanged(successResponse)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}