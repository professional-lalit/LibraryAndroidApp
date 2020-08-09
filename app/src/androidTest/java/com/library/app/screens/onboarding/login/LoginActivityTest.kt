package com.library.app.screens.onboarding.login

import android.view.View
import android.widget.Button
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.library.app.R
import com.library.app.screens.main.MainActivity
import com.library.app.utils.FileReader
import com.library.app.utils.ToastMatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.awaitility.Awaitility.await
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep
import java.time.Duration

@RunWith(AndroidJUnit4::class)
@LargeTest
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

        webServer.enqueue(MockResponse().setBody(FileReader.readStringFromFile("login_success.json")))

        onView(withId(R.id.edt_email)).perform(typeText(emailToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.edt_password)).perform(typeText(passwordToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        onView(withText("Login successful!")).inRoot(
            ToastMatcher()
        ).check(matches(isDisplayed()))

        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        intended(hasComponent(MainActivity::class.java.name))

    }


}