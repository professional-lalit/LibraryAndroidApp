package com.library.app.screens.onboarding.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.library.app.R
import kotlinx.coroutines.delay
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {

    private val emailToBeTyped = "hajare.lalit@gmail.com"
    private val passwordToBeTyped = "123@Abc"

    @get:Rule
    var activityRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun enter_email_password_check_login_ui() {
        onView(withId(R.id.edt_email)).perform(typeText(emailToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.edt_password)).perform(typeText(passwordToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.btn_login)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.pgbar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

}