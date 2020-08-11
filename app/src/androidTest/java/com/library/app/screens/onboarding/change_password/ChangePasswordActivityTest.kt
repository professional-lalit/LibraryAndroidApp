package com.library.app.screens.onboarding.change_password

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.library.app.R
import com.library.app.screens.common.BaseUITest
import com.library.app.screens.common.SCREEN_DELAY
import com.library.app.screens.main.MainActivity
import com.library.app.screens.onboarding.login.LoginActivity
import com.library.app.utils.FileReader
import com.library.app.utils.ToastMatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.MockResponse
import org.awaitility.Awaitility
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

const val OLD_PASSWORD_NOT_MATCHING_MSG = "Old password does not match"
const val PASSWORD_CHANGE_SUCCESSFUL_MSG = "Password changed successfully!"

/**
 * Created by Lalit N. Hajare, Software Engineer on 11/08/2020
 */
/**
 * The purpose of this test is to verify;
 * 1. When old password is not matching
 * 2. When password is recovered
 * the expected dialog is displayed.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class ChangePasswordActivityTest : BaseUITest() {

    private val oldPasswordToBeTyped = "1234@Abc"
    private val newPasswordToBeTyped = "123@Abc"
    private val confirmPasswordToBeTyped = "123@Abc"

    @get:Rule
    var activityRule = ActivityTestRule(ChangePasswordActivity::class.java)

    @Before
    fun setUp() {
        initTest()
        onView(withId(R.id.edt_old_password)).perform(ViewActions.clearText())
        onView(withId(R.id.edt_new_password)).perform(ViewActions.clearText())
        onView(withId(R.id.edt_confirm_password))
            .perform(ViewActions.clearText())
    }

    @After
    fun tearDown() {
        clearTest()
    }

    @Test
    fun test_old_password_not_matching() {
        prepareFailMockAPIResponse()

        enterData()

        onView(withId(R.id.btn_submit)).perform(ViewActions.click())

        Awaitility.await().until {
            !activityRule.activity.mChangePasswordUIInteractor.loading
        }

        checkFailingConditions()
    }

    @Test
    fun test_change_password_success() {

        prepareSuccessMockAPIResponse()

        enterData()

        onView(withId(R.id.btn_submit)).perform(ViewActions.click())

        Awaitility.await().until {
            !activityRule.activity.mChangePasswordUIInteractor.loading
        }

        checkSuccessConditions()
    }

    private fun prepareSuccessMockAPIResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("onboarding_apis/change_password_apis/password_change_successful.json"))
        mockResponse.setResponseCode(200)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        webServer.enqueue(mockResponse)
    }

    private fun prepareFailMockAPIResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("onboarding_apis/change_password_apis/old_password_match_failed.json"))
        mockResponse.setResponseCode(400)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        webServer.enqueue(mockResponse)
    }

    private fun enterData() {
        onView(withId(R.id.edt_old_password))
            .perform(ViewActions.typeText(oldPasswordToBeTyped), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.edt_new_password))
            .perform(
                ViewActions.typeText(newPasswordToBeTyped), ViewActions.closeSoftKeyboard()
            )
        onView(withId(R.id.edt_confirm_password))
            .perform(
                ViewActions.typeText(confirmPasswordToBeTyped), ViewActions.closeSoftKeyboard()
            )
    }

    private fun checkSuccessConditions() {
        onView(withText(PASSWORD_CHANGE_SUCCESSFUL_MSG))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    private fun checkFailingConditions() {
        onView(withText(OLD_PASSWORD_NOT_MATCHING_MSG))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }


}