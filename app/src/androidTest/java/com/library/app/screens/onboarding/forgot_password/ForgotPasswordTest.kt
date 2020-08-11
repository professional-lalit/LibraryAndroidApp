package com.library.app.screens.onboarding.forgot_password

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.library.app.R
import com.library.app.screens.common.BaseUITest
import com.library.app.screens.common.SCREEN_DELAY
import com.library.app.screens.main.MainActivity
import com.library.app.screens.onboarding.change_password.PASSWORD_CHANGE_SUCCESSFUL_MSG
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

const val FORGOT_PASSWORD_SUCCESS_MSG = "An email has been sent regarding new password: Um2inlypYc"
const val FORGOT_PASSWORD_EMAIL_NOT_FOUND_MSG = "A user with this email could not be found."

/**
 * Created by Lalit N. Hajare, Software Engineer on 11/08/2020
 */

/**
 * The purpose of this test is to verify;
 * 1. When submitted email is not present in database
 * 2. When submitted email is present in database
 * the expected dialog message is displayed.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class ForgotPasswordTest : BaseUITest() {


    private val emailToBeTyped = "hajare.lalit@gmail.com"

    @get:Rule
    var activityRule = ActivityTestRule(ForgotPasswordActivity::class.java)

    @Before
    fun setUp() {
        initTest()
        Espresso.onView(ViewMatchers.withId(R.id.edt_email)).perform(ViewActions.clearText())
    }

    @After
    fun tearDown() {
        clearTest()
    }

    @Test
    fun test_fail_email_not_found() {
        prepareFailMockAPIResponse()

        enterData()

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit)).perform(ViewActions.click())

        Awaitility.await().until {
            !activityRule.activity.mForgotPasswordUIInteractor.loading
        }

        checkFailingConditions()
    }

    @Test
    fun test_success() {

        prepareSuccessMockAPIResponse()

        enterData()

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit)).perform(ViewActions.click())

        Awaitility.await().until {
            !activityRule.activity.mForgotPasswordUIInteractor.loading
        }

        checkSuccessConditions()

    }

    private fun prepareSuccessMockAPIResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("onboarding_apis/forgot_pwd_apis/forgot_pwd_success.json"))
        mockResponse.setResponseCode(200)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        webServer.enqueue(mockResponse)
    }

    private fun prepareFailMockAPIResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("onboarding_apis/forgot_pwd_apis/forgot_pwd_fail_email_not_found.json"))
        mockResponse.setResponseCode(400)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        webServer.enqueue(mockResponse)
    }

    private fun enterData() {
        Espresso.onView(ViewMatchers.withId(R.id.edt_email))
            .perform(ViewActions.typeText(emailToBeTyped), ViewActions.closeSoftKeyboard())
    }

    private fun checkSuccessConditions() {
        Espresso.onView(ViewMatchers.withText(FORGOT_PASSWORD_SUCCESS_MSG))
            .inRoot(RootMatchers.isDialog())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun checkFailingConditions() {
        Espresso.onView(ViewMatchers.withText(FORGOT_PASSWORD_EMAIL_NOT_FOUND_MSG))
            .inRoot(RootMatchers.isDialog())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}