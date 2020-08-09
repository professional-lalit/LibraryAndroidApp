package com.library.app.screens.onboarding.login

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.library.app.R
import com.library.app.screens.main.MainActivity
import com.library.app.utils.FileReader
import com.library.app.utils.ToastMatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.awaitility.Awaitility.await
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
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
        webServer.enqueue(MockResponse().setBody(FileReader.readStringFromFile("login_error.json")))

        onView(withId(R.id.edt_email)).perform(typeText(emailToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.edt_password)).perform(typeText(passwordToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        sleep(1500)

        onView(withText("Wrong password!")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun login_success() {

        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("login_success.json"))
        mockResponse.setResponseCode(200)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)

        webServer.enqueue(mockResponse)

        val intent = Intent()
        val intentResult = ActivityResult(Activity.RESULT_OK, intent)
        intending(anyIntent()).respondWith(intentResult)

        onView(withId(R.id.btn_login)).perform(click())

        await().until {
            !activityRule.activity.loginViewModel.loading
        }

        onView(withId(R.id.edt_email)).perform(typeText(emailToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.edt_password)).perform(typeText(passwordToBeTyped), closeSoftKeyboard())
        intended(allOf(hasComponent(MainActivity::class.java.name)))


    }


}