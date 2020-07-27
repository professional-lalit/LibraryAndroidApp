package com.library.app.screens.onboarding.login

import android.app.PendingIntent.getActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.library.app.R
import com.library.app.utils.FileReader
import com.library.app.utils.ToastMatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        webServer.shutdown()
    }

    @Test
    fun login_success() {

        webServer.enqueue(MockResponse().setBody(FileReader.readStringFromFile("login_success.json")))

        onView(withId(R.id.edt_email)).perform(typeText(emailToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.edt_password)).perform(typeText(passwordToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withText("login successful")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    
    @Test
    fun login_fail(){
        webServer.enqueue(MockResponse().setBody(FileReader.readStringFromFile("login_error.json")))

        onView(withId(R.id.edt_email)).perform(typeText(emailToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.edt_password)).perform(typeText(passwordToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withText("Wrong password!")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

}