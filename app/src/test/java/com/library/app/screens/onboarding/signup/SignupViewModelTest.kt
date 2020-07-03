package com.library.app.screens.onboarding.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.library.app.networking.Result
import com.library.app.networking.models.ErrorObj
import com.library.app.networking.models.ErrorResponseSchema
import com.library.app.networking.models.SignupResponseSchema
import com.library.app.repositories.AuthRepository
import com.nhaarman.mockitokotlin2.anyOrNull
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import observeOnce
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Lalit Hajare, Software Engineer on 11/6/20
 * The purpose of this class is to ensure the desired behaviour of `SignupViewModel`
 * @see com.library.app.screens.onboarding.signup.SignupViewModel for more information
 * 1. Test whether the sign up API result was 'Result.Error', i.e, API Failure
 * 2. Test whether the sign up API result was 'Result.Success', i.e, API Success
 */
@RunWith(JUnit4::class)
class SignupViewModelTest {

    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * Dependencies for SUT
     */
    private val mAuthRepository: AuthRepository = Mockito.mock(AuthRepository::class.java)
    private val mSignupInputValidator: SignupInputValidator =
        Mockito.mock(SignupInputValidator::class.java)

    private var SUT = SignupViewModel(mAuthRepository, mSignupInputValidator)

    private val name = "XXXXX"
    private val email = "XXXXX"
    private val password = "XXXXX"

    @Before
    fun prepareTest() {

    }

    /**
     * When userId is not acquired,
     * Confirm that ViewModel delivers error response to livedata
     */
    @Test
    fun `signup failed`() = runBlockingTest {
        //Arrange
        val errorResponse = Result.Error(
            ErrorResponseSchema(
                "xxxxxx",
                arrayListOf(ErrorObj("xxxx", "xxxx", "xxxx", "xxxx"))
            )
        )
        Mockito.`when`(SUT.authRepository.signup(anyOrNull())).thenReturn(
            errorResponse
        )
        //Act
        SUT.signup(
            name,
            email,
            password
        )
        //Assert
        SUT.result.observeOnce { value ->
            org.junit.Assert.assertEquals(errorResponse, value)
        }
    }

    /**
     * When userId is acquired,
     * Confirm that ViewModel delivers error response to livedata
     */
    @Test
    fun `signup success`() = runBlockingTest {
        //Arrange
        val successResponse = Result.Success(SignupResponseSchema("XXXXX"))
        Mockito.`when`(SUT.authRepository.signup(anyOrNull())).thenReturn(
            successResponse
        )
        //Act
        SUT.signup(
            name,
            email,
            password
        )
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