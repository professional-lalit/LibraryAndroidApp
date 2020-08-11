package com.library.app.screens.onboarding.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.library.app.R
import com.library.app.screens.common.BaseUITest
import com.library.app.screens.common.SCREEN_DELAY
import com.library.app.screens.main.MainActivity
import com.library.app.utils.FileReader
import com.library.app.utils.ToastMatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.MockResponse
import org.awaitility.Awaitility.await
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

private const val LOGIN_SUCCESS_MSG = "Login successful!"
private const val LOGIN_FAILURE_MSG = "Wrong password!"

/**
 * The purpose of this test is to verify;
 * 1. When submitted email & password are legitimate
 * 2. When submitted email @ password are not legitimate
 * the expected toast message is displayed.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest : BaseUITest() {

    private val emailToBeTyped = "hajare.lalit@gmail.com"
    private val passwordToBeTyped = "123@Abc"

    @get:Rule
    var activityRule = ActivityTestRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        initTest()
        onView(withId(R.id.edt_email)).perform(clearText())
        onView(withId(R.id.edt_password)).perform(clearText())
    }

    @After
    fun tearDown() {
        clearTest()
    }

    @Test
    fun login_fail() {
        prepareFailMockAPIResponse()

        enterData()

        onView(withId(R.id.btn_login)).perform(click())

        await().until {
            !activityRule.activity.mLoginUIInteractor.loading
        }

        checkFailingConditions()
    }

    @Test
    fun login_success() {

        prepareSuccessMockAPIResponse()

        enterData()

        onView(withId(R.id.btn_login)).perform(click())

        await().until {
            !activityRule.activity.mLoginUIInteractor.loading
        }

        checkSuccessConditions()

    }

    private fun prepareSuccessMockAPIResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("onboarding_apis/login_apis/login_success.json"))
        mockResponse.setResponseCode(200)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        webServer.enqueue(mockResponse)
    }

    private fun prepareFailMockAPIResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("onboarding_apis/login_apis/login_error.json"))
        mockResponse.setResponseCode(400)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        webServer.enqueue(mockResponse)
    }

    private fun enterData() {
        onView(withId(R.id.edt_email)).perform(typeText(emailToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.edt_password)).perform(
            typeText(passwordToBeTyped),
            closeSoftKeyboard()
        )
    }

    private fun checkSuccessConditions() {
        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withText(LOGIN_SUCCESS_MSG)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        CoroutineScope(Dispatchers.IO).launch {
            delay(SCREEN_DELAY)
            intended(allOf(hasComponent(MainActivity::class.java.name)))
        }
    }

    private fun checkFailingConditions() {
        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withText(LOGIN_FAILURE_MSG)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        CoroutineScope(Dispatchers.IO).launch {
            delay(SCREEN_DELAY)
            intended(allOf(not(hasComponent(MainActivity::class.java.name))))
        }
    }


}