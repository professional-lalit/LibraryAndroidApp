package com.library.app.repositories

import com.library.app.common.Prefs
import com.library.app.networking.ApiCallInterface
import com.library.app.networking.Result
import com.library.app.networking.models.*
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by Lalit N. Hajare, Software Engineer on 31/08/2020
 * The purpose of this class is ensure the behaviour of `BookRepository`
 * @see com.library.app.repositories.BookRepository for more information
 *
 */
class BookRepositoryTest {

    /**
     * Dependencies for SUT
     */
    private val mRetrofit: Retrofit = Mockito.mock(Retrofit::class.java)
    private val mApiCallInterface: ApiCallInterface = Mockito.mock(ApiCallInterface::class.java)
    private val mPrefs: Prefs = Mockito.mock(Prefs::class.java)

    private lateinit var SUT: BookRepository

    @Before
    fun setUp() {
        SUT = BookRepository(mRetrofit, mApiCallInterface, mPrefs)
    }

    @Test
    fun `trending books success`() = runBlockingTest {
        //Arrange
        val bookListResponseSchema = BookListResponseSchema(arrayListOf())
        val successApiResponse = Response.success(bookListResponseSchema)
        `when`(mApiCallInterface.getBooksAsync(any(), any())).thenReturn(
            CompletableDeferred(
                successApiResponse
            )
        )

        //Act
        val result: Result<Any> = SUT.getTrendingBooks()
        delay(100)

        //Assert
        assertEquals(Result.Success::class.java, result.javaClass)
    }

    @Test
    fun `trending books failure`() = runBlockingTest {
        //Arrange
        val bookListResponseSchema = BookListResponseSchema(arrayListOf())
        val failApiResponse = Response.error<BookListResponseSchema>(
            400,
            bookListResponseSchema.toString().toResponseBody("application/json".toMediaTypeOrNull())
        )
        `when`(mApiCallInterface.getBooksAsync(any(), any())).thenReturn(
            CompletableDeferred(failApiResponse)
        )

        //Act
        val result: Result<Any> = SUT.getTrendingBooks()
        delay(100)

        //Assert
        assertEquals(Result.Error::class.java, result.javaClass)
    }

    @Test
    fun `recent visited books success`() = runBlockingTest {
        //Arrange
        val bookListResponseSchema = BookListResponseSchema(arrayListOf())
        val successApiResponse = Response.success(bookListResponseSchema)
        `when`(mApiCallInterface.getBooksAsync(any(), any())).thenReturn(
            CompletableDeferred(
                successApiResponse
            )
        )

        //Act
        val result: Result<Any> = SUT.getRecentVisitedBooks()
        delay(100)

        //Assert
        assertEquals(Result.Success::class.java, result.javaClass)
    }

    @Test
    fun `recent visited books failure`() = runBlockingTest {
        //Arrange
        val bookListResponseSchema = BookListResponseSchema(arrayListOf())
        val failApiResponse = Response.error<BookListResponseSchema>(
            400,
            bookListResponseSchema.toString().toResponseBody("application/json".toMediaTypeOrNull())
        )
        `when`(mApiCallInterface.getBooksAsync(any(), any())).thenReturn(
            CompletableDeferred(failApiResponse)
        )

        //Act
        val result: Result<Any> = SUT.getRecentVisitedBooks()
        delay(100)

        //Assert
        assertEquals(Result.Error::class.java, result.javaClass)
    }

    @Test
    fun `categories success`() = runBlockingTest {
        //Arrange
        val categoryListResponseSchema = CategoryListResponseSchema(arrayListOf())
        val successApiResponse = Response.success(categoryListResponseSchema)
        `when`(mApiCallInterface.getCategoriesAsync()).thenReturn(
            CompletableDeferred(
                successApiResponse
            )
        )

        //Act
        val result: Result<Any> = SUT.getBookCategories()
        delay(100)

        //Assert
        assertEquals(Result.Success::class.java, result.javaClass)
    }

    @Test
    fun `categories failure`() = runBlockingTest {
        //Arrange
        val categoryListResponseSchema = CategoryListResponseSchema(arrayListOf())
        val failApiResponse = Response.error<CategoryListResponseSchema>(
            400,
            categoryListResponseSchema.toString()
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        `when`(mApiCallInterface.getCategoriesAsync()).thenReturn(
            CompletableDeferred(failApiResponse)
        )

        //Act
        val result: Result<Any> = SUT.getBookCategories()
        delay(100)

        //Assert
        assertEquals(Result.Error::class.java, result.javaClass)
    }

    @Test
    fun `books of category success`() = runBlockingTest {
        //Arrange
        val bookListResponseSchema = BookListResponseSchema(arrayListOf())
        val successApiResponse = Response.success(bookListResponseSchema)
        `when`(mApiCallInterface.getBooksAsync(any(), any())).thenReturn(
            CompletableDeferred(
                successApiResponse
            )
        )

        //Act
        val result: Result<Any> = SUT.getRecentVisitedBooks()
        delay(100)

        //Assert
        assertEquals(Result.Success::class.java, result.javaClass)
    }

    @Test
    fun `books of category failure`() = runBlockingTest {
        //Arrange
        val bookListResponseSchema = BookListResponseSchema(arrayListOf())
        val failApiResponse = Response.error<BookListResponseSchema>(
            400,
            bookListResponseSchema.toString().toResponseBody("application/json".toMediaTypeOrNull())
        )
        `when`(mApiCallInterface.getBooksAsync(any(), any())).thenReturn(
            CompletableDeferred(failApiResponse)
        )

        //Act
        val result: Result<Any> = SUT.getRecentVisitedBooks()
        delay(100)

        //Assert
        assertEquals(Result.Error::class.java, result.javaClass)
    }

    @Test
    fun `book details success`() = runBlockingTest {
        //Arrange
        val bookDetailsSchema = BookDetailsSchema(
            arrayListOf(), arrayListOf(), "", "", "1234", 10,
            "", "", "", "", ""
        )
        val bookDetailsResponseSchema = BookDetailsResponseSchema(bookDetailsSchema)
        val successApiResponse = Response.success(bookDetailsResponseSchema)
        `when`(mApiCallInterface.getBookDetailsAsync(any())).thenReturn(
            CompletableDeferred(
                successApiResponse
            )
        )

        //Act
        val result: Result<Any> = SUT.getBookDetails(bookDetailsSchema.isbn!!)
        delay(100)

        //Assert
        assertEquals(Result.Success::class.java, result.javaClass)
    }

    @Test
    fun `book details failure`() = runBlockingTest {
        //Arrange
        val bookDetailsSchema = BookDetailsSchema(
            arrayListOf(), arrayListOf(), "", "", "1234", 10,
            "", "", "", "", ""
        )
        val bookDetailsResponseSchema = BookDetailsResponseSchema(bookDetailsSchema)
        val failApiResponse = Response.error<BookDetailsResponseSchema>(
            400,
            bookDetailsResponseSchema.toString()
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        `when`(mApiCallInterface.getBookDetailsAsync(any())).thenReturn(
            CompletableDeferred(failApiResponse)
        )

        //Act
        val result: Result<Any> = SUT.getBookDetails(bookDetailsSchema.isbn!!)
        delay(100)

        //Assert
        assertEquals(Result.Error::class.java, result.javaClass)
    }

}