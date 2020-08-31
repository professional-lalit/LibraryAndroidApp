package com.library.app.screens.main.fragments.books.book_details

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.library.app.R
import com.library.app.databinding.FragmentHomeBinding
import com.library.app.screens.common.BaseUITest
import com.library.app.screens.common.SCREEN_DELAY
import com.library.app.screens.main.MainActivity
import com.library.app.screens.main.fragments.books.BookViewHolder
import com.library.app.screens.main.fragments.books.CategoryViewHolder
import com.library.app.screens.main.fragments.books.book_list.BookListFragment
import com.library.app.screens.main.fragments.home.HomeFragment
import com.library.app.screens.onboarding.login.LoginActivity
import com.library.app.utils.FileReader
import com.library.app.utils.ToastMatcher
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.awaitility.Awaitility
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Duration
import java.util.concurrent.TimeUnit

/**
 * Created by Lalit N. Hajare, Software Engineer on 31/08/2020
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class MainActivityTest : BaseUITest() {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    var binding: FragmentHomeBinding? = null

    @Before
    fun setUp() {
        initTest()
    }

    @After
    fun tearDown() {
        clearTest()
    }

    @Test
    fun `home_api_fail`() {

        binding = activityRule.activity!!.mHomeFragment.mHomeUIInteractor.mHomeBinding!!

        prepareFailMockAPIResponse()

        Awaitility.await().until {
            !binding?.homeUIInteractor?.categoriesLoading!!
                    && !binding?.homeUIInteractor?.recentVisitedBooksLoading!!
                    && !binding?.homeUIInteractor?.trendingBooksLoading!!
        }

        checkFailingConditions()
    }

    private fun prepareFailMockAPIResponse() {
        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                when {
                    request.path!!.contains("/trending-book-list") -> {
                        val mockResponse = MockResponse()
                        mockResponse.setBody(FileReader.readStringFromFile("book_apis/empty_book_list.json"))
                        mockResponse.setResponseCode(200)
                        return mockResponse
                    }

                    request.path!!.contains("/book-list") -> {
                        val mockResponse = MockResponse()
                        mockResponse.setBody(FileReader.readStringFromFile("book_apis/empty_book_list.json"))
                        mockResponse.setResponseCode(200)
                        return mockResponse
                    }

                    request.path!!.contains("/cat-list") -> {
                        val mockResponse = MockResponse()
                        mockResponse.setBody(FileReader.readStringFromFile("book_apis/categories_empty.json"))
                        mockResponse.setResponseCode(200)
                        return mockResponse
                    }
                    else -> {
                        return MockResponse().setResponseCode(404)
                    }
                }
            }
        }
        webServer.dispatcher = dispatcher
    }

    private fun checkFailingConditions() {
        val trendingBooksRecycler: RecyclerView = binding?.trendingBooksRecycler!!
        assert(trendingBooksRecycler.adapter?.itemCount!! == 0)

        val recentVisitedBooksRecycler: RecyclerView = binding?.recentVisitedBooksRecycler!!
        assert(recentVisitedBooksRecycler.adapter?.itemCount!! == 0)

        val categoriesRecycler: RecyclerView = binding?.categoriesRecycler!!
        assert(categoriesRecycler.adapter?.itemCount!! == 0)
    }

    @Test
    fun `home_api_success`() {
        binding = activityRule.activity!!.mHomeFragment.mHomeUIInteractor.mHomeBinding!!
        prepareSuccessMockAPIResponse()
        Awaitility.await().until {
            !binding?.homeUIInteractor?.categoriesLoading!!
        }

        checkSuccessConditions()
    }

    private fun prepareSuccessMockAPIResponse() {
        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                when {
                    request.path!!.contains("/trending-book-list") -> {
                        val mockResponse = MockResponse()
                        mockResponse.setBody(FileReader.readStringFromFile("book_apis/book_list_success.json"))
                        mockResponse.setResponseCode(200)
                        return mockResponse
                    }

                    request.path!!.contains("/book-list") -> {
                        val mockResponse = MockResponse()
                        mockResponse.setBody(FileReader.readStringFromFile("book_apis/book_list_success.json"))
                        mockResponse.setResponseCode(200)
                        return mockResponse
                    }

                    request.path!!.contains("/cat-list") -> {
                        val mockResponse = MockResponse()
                        mockResponse.setBody(FileReader.readStringFromFile("book_apis/categories_success.json"))
                        mockResponse.setResponseCode(200)
                        return mockResponse
                    }
                    else -> {
                        return MockResponse().setResponseCode(404)
                    }
                }
            }
        }
        webServer.dispatcher = dispatcher
    }

    private fun checkSuccessConditions() {
        val trendingBooksRecycler: RecyclerView = binding?.trendingBooksRecycler!!
        assert(trendingBooksRecycler.adapter?.itemCount!! > 0)

        val recentVisitedBooksRecycler: RecyclerView = binding?.recentVisitedBooksRecycler!!
        assert(recentVisitedBooksRecycler.adapter?.itemCount!! > 0)

        val categoriesRecycler: RecyclerView = binding?.categoriesRecycler!!
        assert(categoriesRecycler.adapter?.itemCount!! > 0)
    }

    @Test
    fun `open_book_list`() {
        binding = activityRule.activity!!.mHomeFragment.mHomeUIInteractor.mHomeBinding!!
        prepareSuccessMockAPIResponse()
        Awaitility.await().until {
            !binding?.homeUIInteractor?.categoriesLoading!!
        }

        onView(withId(binding?.categoriesRecycler!!.id))
            .perform(actionOnItemAtPosition<CategoryViewHolder>(0, click()))

        val tag = MainActivity.MainScreenFragments.BOOK_LIST.name
        assert(activityRule.activity!!.supportFragmentManager.findFragmentByTag(tag) != null)
    }

    @Test
    fun `open_book_details`() {
        binding = activityRule.activity!!.mHomeFragment.mHomeUIInteractor.mHomeBinding!!
        prepareSuccessMockAPIResponse()
        Awaitility.await().until {
            !binding?.homeUIInteractor?.recentVisitedBooksLoading!!
        }

        onView(withId(binding?.recentVisitedBooksRecycler!!.id))
            .perform(actionOnItemAtPosition<BookViewHolder>(0, click()))

        val tag = MainActivity.MainScreenFragments.BOOK_DETAILS.name
        assert(activityRule.activity!!.supportFragmentManager.findFragmentByTag(tag) != null)
    }

}