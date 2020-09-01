package com.library.app.screens.main

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.library.app.R
import com.library.app.databinding.FragmentBookDetailsBinding
import com.library.app.databinding.FragmentHomeBinding
import com.library.app.screens.common.BaseUITest
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

/**
 * Created by Lalit N. Hajare, Software Engineer on 01/09/2020
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class BookDetailsActivityTest : BaseUITest() {
    @get:Rule
    var activityRule = ActivityTestRule(BookDetailsActivity::class.java)

    var binding: FragmentBookDetailsBinding? = null

    @Before
    fun setUp() {
        initTest()
    }

    @After
    fun tearDown() {
        clearTest()
    }

    @Test
    fun book_details_success() {
        prepareSuccessMockAPIResponse()
        Awaitility.await().until {
            !activityRule.activity.bookDetailsFragment.mBookDetailsUIInteractor.loading
        }
        checkSuccessConditions()
    }

    private fun prepareSuccessMockAPIResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("book_apis/book_details_success.json"))
        mockResponse.setResponseCode(200)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        webServer.enqueue(mockResponse)
    }

    private fun checkSuccessConditions() {
        Espresso.onView(ViewMatchers.withId(R.id.txt_book_pagecount))
            .check(matches(withText("600")))

        Espresso.onView(ViewMatchers.withId(R.id.txt_book_isbn))
            .check(matches(withText("1935182420")))

        Espresso.onView(ViewMatchers.withId(R.id.txt_book_category))
            .check(matches(withText("[Internet]")))

        Espresso.onView(ViewMatchers.withId(R.id.txt_book_publish_date))
            .check(matches(withText("2010-11-15T08:00:00.000Z")))

        Espresso.onView(ViewMatchers.withId(R.id.txt_book_authors))
            .check(matches(withText("[Tariq Ahmed,Dan Orlando,John C. Bland II,Joel Hooks]")))

        Espresso.onView(ViewMatchers.withId(R.id.txt_book_long_desc))
            .check(matches(withText("Using Flex, you can create high-quality, effective, and interactive Rich Internet Applications (RIAs) quickly and easily. Flex removes the complexity barrier from RIA development by offering sophisticated tools and a straightforward programming language so you can focus on what you want to do instead of how to do it. And the new features added in Flex 4 give you an even wider range of options!    Flex 4 in Action is an easy-to-follow, hands-on Flex tutorial that goes beyond feature coverage and helps you put Flex to work in real day-to-day tasks. You'll quickly master the Flex API and learn to apply the techniques that make your Flex applications stand out from the crowd.    The expert authors of Flex 4 in Action have one goal-to help you get down to business with Flex. Fast. Flex 4 in Action filters out the noise and dives into the core topics you need every day. Using numerous easy-to-understand examples, Flex 4 in Action gives you a strong foundation that you can build on as the complexity of your projects increases.    Interesting themes, styles, and skins  It's in there.  Working with databases  You got it.  Interactive forms and validation  You bet.  Charting techniques to help you visualize data  Bam!  And you'll get full coverage of these great Flex 4 upgrades:  Next generation Spark components-New buttons, form inputs, navigation controls and other visual components replace the Flex 3 \"Halo\" versions. Spark components are easier to customize, which makes skinning and theme design much faster  A new \"network monitor\" allows you to see the data communications between a Flex application and a backend server, which helps when trying to debug applications that are communicating to another system/service  Numerous productivity boosting features that speed up the process of creating applications  A faster compiler to take your human-written source code and convert it into a machine-readable format  Built-in support for unit testing allows you to improve the quality of your software, and reduce the time spent in testing")))

        Espresso.onView(ViewMatchers.withId(R.id.pgbar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    }
}