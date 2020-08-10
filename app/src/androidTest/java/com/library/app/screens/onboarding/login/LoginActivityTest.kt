package com.library.app.screens.onboarding.login

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.library.app.R
import com.library.app.screens.common.SCREEN_DELAY
import com.library.app.screens.main.MainActivity
import com.library.app.utils.FileReader
import com.library.app.utils.ToastMatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.awaitility.Awaitility.await
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep
import java.time.Duration
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {

    private val emailToBeTyped = "hajare.lalit@gmail.com"
    private val passwordToBeTyped = "123@Abc"

    @get:Rule
    var activityRule = ActivityTestRule(LoginActivity::class.java)


    private var webServer = MockWebServer()

    @Before
    @Throws(Exception::class)
    fun setup() {
        webServer.start(8080)
        Intents.init()
        onView(withId(R.id.edt_email)).perform(clearText())
        onView(withId(R.id.edt_password)).perform(clearText())
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        webServer.shutdown()
        Intents.release()
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
        mockResponse.setBody(FileReader.readStringFromFile("login_success.json"))
        mockResponse.setResponseCode(200)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        webServer.enqueue(mockResponse)
    }

    private fun prepareFailMockAPIResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("login_error.json"))
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
        onView(withText("Login successful!")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        CoroutineScope(Dispatchers.IO).launch {
            delay(SCREEN_DELAY)
            intended(allOf(hasComponent(MainActivity::class.java.name)))
        }
    }

    private fun checkFailingConditions() {
        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withText("Wrong password!")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        CoroutineScope(Dispatchers.IO).launch {
            delay(SCREEN_DELAY)
            intended(allOf(not(hasComponent(MainActivity::class.java.name))))
        }
    }


}