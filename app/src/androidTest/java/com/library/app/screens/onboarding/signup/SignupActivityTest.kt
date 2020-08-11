package com.library.app.screens.onboarding.signup

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.library.app.R
import com.library.app.screens.common.BaseUITest
import com.library.app.utils.FileReader
import com.library.app.utils.ToastMatcher
import okhttp3.mockwebserver.MockResponse
import org.awaitility.Awaitility
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

/**
 * Created by Lalit N. Hajare, Software Engineer on 10/08/2020
 */

private const val SIGN_SUCCESS_MSG = "User created!"
private const val SIGN_FAIL_EMAIL_EXIST_MSG = "E-Mail address already exists!"

/**
 * The purpose of this test is to verify;
 * 1. When submitted email already exists
 * 2. When submitted email & other info are legitimate
 * the expected toast message is displayed.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class SignupActivityTest : BaseUITest() {

    private val emailToBeTyped = "hajare.lalit@gmail.com"
    private val passwordToBeTyped = "123@Abc"
    private val nameToBeTyped: String = "Lalit Hajare"
    private val confirmPasswordToBeTyped: String = "123@Abc"

    @get:Rule
    var activityRule = ActivityTestRule(SignupActivity::class.java)

    @Before
    fun setUp() {
        initTest()
        Espresso.onView(ViewMatchers.withId(R.id.edt_email)).perform(ViewActions.clearText())
        Espresso.onView(ViewMatchers.withId(R.id.edt_password)).perform(ViewActions.clearText())
    }

    @After
    fun tearDown() {
        clearTest()
    }

    @Test
    fun signup_sucess() {
        prepareSuccessResponse()
        enterData()

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit)).perform(ViewActions.click())

        Awaitility.await().until {
            !activityRule.activity.mSignupUIInteractor.loading
        }
        checkSuccessConditions()
    }

    @Test
    fun signup_fail() {
        prepareEmailAlreadyExistsResponse()
        enterData()

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit)).perform(ViewActions.click())

        Awaitility.await().until { !activityRule.activity.mSignupUIInteractor.loading }
        checkFailureEmailAlreadyExistConditions()
    }

    private fun checkSuccessConditions() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_submit))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.pgbar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(ViewMatchers.withText(SIGN_SUCCESS_MSG)).inRoot(ToastMatcher())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun checkFailureEmailAlreadyExistConditions() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_submit))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.pgbar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(ViewMatchers.withText(SIGN_FAIL_EMAIL_EXIST_MSG)).inRoot(ToastMatcher())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun enterData() {
        Espresso.onView(ViewMatchers.withId(R.id.edt_name))
            .perform(ViewActions.typeText(nameToBeTyped), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.edt_email))
            .perform(ViewActions.typeText(emailToBeTyped), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.edt_password)).perform(
            ViewActions.typeText(passwordToBeTyped),
            ViewActions.closeSoftKeyboard()
        )
        Espresso.onView(ViewMatchers.withId(R.id.edt_confirm_password)).perform(
            ViewActions.typeText(confirmPasswordToBeTyped),
            ViewActions.closeSoftKeyboard()
        )
    }

    private fun prepareSuccessResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("onboarding_apis/signup_apis/signup_success.json"))
        mockResponse.setResponseCode(200)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        webServer.enqueue(mockResponse)
    }

    private fun prepareEmailAlreadyExistsResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("onboarding_apis/signup_apis/signup_fail_email_exists.json"))
        mockResponse.setResponseCode(400)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        webServer.enqueue(mockResponse)
    }

}