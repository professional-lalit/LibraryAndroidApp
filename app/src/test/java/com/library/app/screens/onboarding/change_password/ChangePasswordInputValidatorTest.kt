package com.library.app.screens.onboarding.change_password

import com.inputvalidationmanager.module.ValidationManager
import com.inputvalidationmanager.module.validators.InputValidator
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Lalit Hajare, Software Engineer on 29/6/20
 * This class ensures the working functionality of `ChangePasswordValidationUsecase` class.
 * It tests the functionality for various inputs like;
 * 1. invalid new password convention
 * 2. invalid new password length
 * 3. whether new password matches with re-rentered password
 * 4. special characters in new password
 * 5. Valid credentials
 * @see com.library.app.screens.onboarding.change_password.ChangePasswordInputValidator
 */
@RunWith(JUnit4::class)
class ChangePasswordInputValidatorTest {

    val mValidationManager = ValidationManager()
    private lateinit var SUT: InputValidator

    @Before
    fun prepareTest() {

    }

    @Test
    fun `test valid credentials`() {
        //Arrange
        val oldPassword = "123@Abcd"
        val newPassword = "123@Abc"
        val confirmedPassword = "123@Abc"
        SUT = mValidationManager.getChangePasswordValidator(
            oldPassword,
            newPassword,
            confirmedPassword
        )
        //Act
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.VALID, code)
    }

    @Test
    fun `test non matching passwords`() {
        //Arrange
        val oldPassword = "123@Abcd"
        val newPassword = "123@Acc"
        val confirmedPassword = "123@Abc"
        SUT = mValidationManager.getChangePasswordValidator(
            oldPassword,
            newPassword,
            confirmedPassword
        )
        //Act
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.UNMATCHING_PASSWORDS_ERROR, code)
    }

    @Test
    fun `test empty new password`() {
        //Arrange
        val oldPassword = "123@Abcd"
        val newPassword = ""
        val confirmedPassword = "123@Abc"
        SUT = mValidationManager.getChangePasswordValidator(
            oldPassword,
            newPassword,
            confirmedPassword
        )
        //Act
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.NEW_PASSWORD_EMPTY, code)
    }

    @Test
    fun `test invalid length of new password`() {
        //Arrange
        val oldPassword = "123@Abcd"
        val newPassword = "123Ac"
        val confirmedPassword = "123@Abc"
        SUT = mValidationManager.getChangePasswordValidator(
            oldPassword,
            newPassword,
            confirmedPassword
        )
        //Act
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.NEW_PASSWORD_LENGTH_ERROR, code)
    }


    @Test
    fun `test no special character in new password`() {
        //Arrange
        val oldPassword = "123@Abcd"
        val newPassword = "123Abc"
        val confirmedPassword = "123@Abc"
        SUT = mValidationManager.getChangePasswordValidator(
            oldPassword,
            newPassword,
            confirmedPassword
        )
        //Act
        val code = SUT.isValid()
        //Assert
        Assert.assertEquals(InputValidator.ValidationCode.NEW_PASSWORD_SPCHR_ERROR, code)
    }

}