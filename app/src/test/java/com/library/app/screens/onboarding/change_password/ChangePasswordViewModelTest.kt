package com.library.app.screens.onboarding.change_password

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.library.app.networking.Result
import com.library.app.networking.models.ChangePasswordResponseSchema
import com.library.app.networking.models.ErrorObj
import com.library.app.networking.models.ErrorResponseSchema
import com.library.app.repositories.AuthRepository
import com.library.app.screens.onboarding.login.LoginInputValidator
import com.library.app.screens.onboarding.login.LoginViewModel
import com.nhaarman.mockitokotlin2.anyOrNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import observeOnce
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

/**
 * Created by Lalit Hajare, Software Engineer on 3/7/20
 * This class ensures functioning of `ChangePasswordViewModelTest`
 * @see ChangePasswordViewModel
 * 1. Test whether the change password API result was 'Result.Error', i.e, API Failure
 * 2. Test whether the change password API result was 'Result.Success', i.e, API Success
 */
@RunWith(JUnit4::class)
class ChangePasswordViewModelTest {


    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * Dependencies for SUT
     */
    private val mAuthRepository = Mockito.mock(AuthRepository::class.java)

    private var SUT = ChangePasswordViewModel(mAuthRepository)

    private val oldPassword = "123@Abc"
    private val newPassword = "123@Abc"

    @Before
    fun prepareTest() {
    }

    @Test
    fun `change password failed old password wrong`() = runBlockingTest {
        //Arrange
        val response = Result.Error(
            ErrorResponseSchema(
                "xxxxxx",
                arrayListOf(ErrorObj("xxxx", "xxxx", "xxxx", "xxxx"))
            )
        )
        Mockito.`when`(SUT.mAuthRepository.changePassword(anyOrNull(), anyOrNull()))
            .thenReturn(response)
        //Act
        SUT.submitNewPassword(oldPassword, newPassword)
        //Assert
        SUT.result.observeOnce { value ->
            org.junit.Assert.assertEquals(response, value)
        }
    }

    @Test
    fun `change password success`() = runBlockingTest {
        //Arrange
        val response = Result.Success(
            ChangePasswordResponseSchema("XXXXX")
        )
        Mockito.`when`(SUT.mAuthRepository.changePassword(anyOrNull(), anyOrNull()))
            .thenReturn(response)
        //Act
        SUT.submitNewPassword(oldPassword, newPassword)
        //Assert
        SUT.result.observeOnce { value ->
            org.junit.Assert.assertEquals(response, value)
        }
    }

}