package com.library.app.repositories

import com.google.gson.Gson
import com.library.app.common.Prefs
import com.library.app.networking.ApiCallInterface
import com.library.app.networking.Result
import com.library.app.networking.models.*
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by Lalit Hajare, Software Engineer on 10/6/20
 * The purpose of this class is ensure the behaviour of `AuthRepository`
 * @see com.library.app.repositories.AuthRepository for more information
 *
 */
@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    /**
     * Dependencies for SUT
     */
    private val mRetrofit: Retrofit = mock(Retrofit::class.java)
    private val mApiCallInterface: ApiCallInterface = mock(ApiCallInterface::class.java)
    private val mPrefs: Prefs = mock(Prefs::class.java)

    private lateinit var SUT: AuthRepository

    @Before
    fun setUp() {
        SUT = AuthRepository(mRetrofit, mApiCallInterface, mPrefs)
    }

    /**
     * Ensure if access token is acquired necessary preferences are set
     * & Success is returned
     */
    @Test
    fun `api access token acquired`() = runBlockingTest {
        //Arrange
        val userId = "XXXXX"
        val token = "XXXXXXXXXXXXXXX"
        val loginRequestSchema = LoginRequestSchema("123@abc.com", "123@abc")
        val loginResponseSchema = LoginResponseSchema(token, userId)
        val successApiResponse = Response.success(loginResponseSchema)
        `when`(mApiCallInterface.getAccessTokenAsync(any())).thenReturn(
            CompletableDeferred(
                successApiResponse
            )
        )

        //Act
        val result: Result<Any> = SUT.login(loginRequestSchema)
        delay(100)

        //Assert
        val captor = ArgumentCaptor.forClass(String::class.java)
        assertEquals(Result.Success::class.java, result.javaClass)
        verify(SUT.mPreferences).userId = capture(captor)
        assertEquals(userId, captor.value)
        verify(SUT.mPreferences).accessToken = capture(captor)
        assertEquals(token, captor.value)
    }


    /**
     * Ensure if access token is not acquired necessary preferences are not set
     * & Error is returned
     */
    @Test
    fun `api access token not acquired`() = runBlockingTest {
        //Arrange
        val userId = null
        val token = null
        val loginRequestSchema = LoginRequestSchema("123@abc.com", "123@abc")
        val loginResponseSchema = LoginResponseSchema(token, userId)
        val failedApiResponse = Response.success(loginResponseSchema)
        `when`(mApiCallInterface.getAccessTokenAsync(any())).thenReturn(
            CompletableDeferred(
                failedApiResponse
            )
        )
        //Act
        val result: Result<Any> = SUT.login(loginRequestSchema)
        delay(100)
        //Assert
        val captor = ArgumentCaptor.forClass(String::class.java)
        assertEquals(Result.Error::class.java, result.javaClass)
        verify(SUT.mPreferences).userId = capture(captor)
        assertEquals(userId, captor.value)
        verify(SUT.mPreferences).accessToken = capture(captor)
        assertEquals(token, captor.value)
    }

    /**
     * Ensure that success result is returned when valid request is sent
     */
    @Test
    fun `api signup success`() = runBlockingTest {
        //Arrange
        val signupRequestSchema = SignupRequestSchema("XXXX", "asd@add.com", "123@abc")
        val signupResponseSchema = SignupResponseSchema("XXXXXXXXX")
        val successApiResponse = Response.success(signupResponseSchema)
        `when`(mApiCallInterface.signupAsync(any())).thenReturn(
            CompletableDeferred(
                successApiResponse
            )
        )
        //Act
        val result: Result<Any> = SUT.signup(signupRequestSchema)
        delay(100)
        //Assert
        assertEquals(Result.Success::class.java, result.javaClass)
    }

    /**
     * Ensure that error result is returned when invalid request is sent
     */
    @Test
    fun `api signup failed`() = runBlockingTest {
        //Arrange
        val signupRequestSchema = SignupRequestSchema("XXXX", "asd@add.com", "123@abc")
        val signupResponseSchema = SignupResponseSchema(null)
        val failedApiResponse = Response.success(signupResponseSchema)
        `when`(mApiCallInterface.signupAsync(any())).thenReturn(
            CompletableDeferred(
                failedApiResponse
            )
        )
        //Act
        val result: Result<Any> = SUT.signup(signupRequestSchema)
        delay(100)
        //Assert
        assertEquals(Result.Error::class.java, result.javaClass)
    }

    /**
     * Ensure that success result is returned when invalid request is sent
     */
    @Test
    fun `api forgot password success`() = runBlockingTest {
        //Arrange
        val forgotPasswordRequestSchema = ForgotPasswordRequestSchema("XXXX")
        val forgotPasswordResposeSchema = ForgotPasswordResposeSchema("XXXX")
        val failedApiResponse = Response.success(forgotPasswordResposeSchema)
        `when`(mApiCallInterface.forgotPasswordAsync(any())).thenReturn(
            CompletableDeferred(
                failedApiResponse
            )
        )
        //Act
        val result: Result<Any> = SUT.forgotPassword(forgotPasswordRequestSchema)
        delay(100)
        //Assert
        assertEquals(Result.Success::class.java, result.javaClass)
    }

    /**
     * Ensure that error result is returned when invalid request is sent
     */
    @Test
    fun `api forgot password failure`() = runBlockingTest {
        //Arrange
        val forgotPasswordRequestSchema = ForgotPasswordRequestSchema("XXXX")
        val forgotPasswordResposeSchema = ForgotPasswordResposeSchema(null)
        val failedApiResponse = Response.success(forgotPasswordResposeSchema)
        `when`(mApiCallInterface.forgotPasswordAsync(any())).thenReturn(
            CompletableDeferred(
                failedApiResponse
            )
        )
        //Act
        val result: Result<Any> = SUT.forgotPassword(forgotPasswordRequestSchema)
        delay(100)
        //Assert
        assertEquals(Result.Error::class.java, result.javaClass)
    }

}