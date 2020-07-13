package com.library.app.screens.onboarding.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.library.app.R
import com.library.app.utils.FileReader
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
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

    private var webServer: MockWebServer? = null


    @Before
    @Throws(Exception::class)
    fun setup() {
        webServer = MockWebServer()
        webServer!!.start(8080)
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                OkHttpClient()
            )
        )
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        webServer?.shutdown()
    }

    @Test
    fun enter_email_password_check_login_success() {

        onView(withId(R.id.edt_email)).perform(typeText(emailToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.edt_password)).perform(typeText(passwordToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        webServer!!.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(FileReader.readStringFromFile("success_response.json"))
            }
        }

        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.GONE)))

    }

}