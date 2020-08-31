package com.library.app.screens.main

import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.library.app.databinding.FragmentBookDetailsBinding
import com.library.app.databinding.FragmentHomeBinding
import com.library.app.screens.common.BaseUITest
import com.library.app.utils.FileReader
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

/**
 * Created by Lalit N. Hajare, Software Engineer on 01/09/2020
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class BookDetailsFragmentTest: BaseUITest() {
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    var binding: FragmentBookDetailsBinding? = null

    @Before
    fun setUp() {
        initTest()
    }

    @After
    fun tearDown() {
        clearTest()
    }

    private fun prepareSuccessMockAPIResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(FileReader.readStringFromFile("book_apis/"))
        mockResponse.setResponseCode(200)
        mockResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        webServer.enqueue(mockResponse)
    }
}