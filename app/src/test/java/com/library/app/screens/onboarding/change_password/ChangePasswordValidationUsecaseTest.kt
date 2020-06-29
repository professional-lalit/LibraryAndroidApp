package com.library.app.screens.onboarding.change_password

import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Lalit Hajare, Software Engineer on 29/6/20
 * This class ensures the working functionality of `ChangePasswordValidationUsecase` class.
 * It tests the functionality for various inputs like;
 * 1. invalid new password convention
 * 2. invalid new password length
 * 3. whether new password matches with re-rentered password
 * 4. special characters in new password
 * 5. Valid credentials
 * @see com.library.app.screens.onboarding.change_password.ChangePasswordValidationUsecase
 */
class ChangePasswordValidationUsecaseTest {

    val SUT = ChangePasswordValidationUsecase()

    @Before
    fun prepareTest() {

    }

    @Test
    fun `test valid credentials`() {
        //Arrange
        val newPassword = "123@Abc"
        val confirmedPassword = "123@Abc"
        //Act
        val code = SUT.getValidationCode(newPassword, confirmedPassword)
        //Assert
        Assert.assertEquals(ChangePasswordValidationUsecase.ChangePasswordValidations.VALID, code)
    }

    @Test
    fun `test non matching passwords`() {
        //Arrange
        val newPassword = "123@Ac"
        val confirmedPassword = "123@Abc"
        //Act
        val code = SUT.getValidationCode(newPassword, confirmedPassword)
        //Assert
        Assert.assertEquals(ChangePasswordValidationUsecase.ChangePasswordValidations.PASSWORD_MATCHING_ERROR, code)
    }

    @Test
    fun `test empty new password`() {
        //Arrange
        val newPassword = ""
        val confirmedPassword = "123@Abc"
        //Act
        val code = SUT.getValidationCode(newPassword, confirmedPassword)
        //Assert
        Assert.assertEquals(ChangePasswordValidationUsecase.ChangePasswordValidations.NEW_PASSWORD_EMPTY, code)
    }

    @Test
    fun `test invalid length of new password`() {
        //Arrange
        val newPassword = "123Ac"
        val confirmedPassword = "123@Abc"
        //Act
        val code = SUT.getValidationCode(newPassword, confirmedPassword)
        //Assert
        Assert.assertEquals(ChangePasswordValidationUsecase.ChangePasswordValidations.NEW_PASSWORD_LENGTH_ERROR, code)
    }


    @Test
    fun `test no special character in new password`() {
        //Arrange
        val newPassword = "123Abc"
        val confirmedPassword = "123@Abc"
        //Act
        val code = SUT.getValidationCode(newPassword, confirmedPassword)
        //Assert
        Assert.assertEquals(ChangePasswordValidationUsecase.ChangePasswordValidations.NEW_PASSWORD_SPCHR_ERROR, code)
    }

}