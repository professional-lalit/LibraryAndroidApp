package com.library.app.networking

import com.library.app.networking.models.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This is the interface to call APIs, this interface is basically an endpoint for application
 */
interface ApiCallInterface {

    /**
     * Gets the user details from Server
     */
    @GET("user/user-details")
    fun getUserDetailsAsync(@Query("user_id") userId: String): Deferred<UserResponseSchema>

    /**
     * Gets the access token from server
     */
    @POST("auth/login")
    fun getAccessTokenAsync(@Body loginRequestSchema: LoginRequestSchema): Deferred<Response<LoginResponseSchema>>

    /**
     * Registers user with the system
     */
    @POST("auth/signup")
    fun signupAsync(@Body signupRequestSchema: SignupRequestSchema): Deferred<Response<SignupResponseSchema>>

    /**
     * API used to recover password.
     * OTP is sent to the email passed in this API
     */
    @POST("auth/forgot-password")
    fun forgotPasswordAsync(@Body forgotPasswordRequestSchema: ForgotPasswordRequestSchema): Deferred<Response<ForgotPasswordResposeSchema>>

    /**
     * Changes the user's existing password.
     */
    @POST("auth/change-password")
    fun changePasswordAsync(@Body changePasswordRequestSchema: ChangePasswordRequestSchema): Deferred<Response<ChangePasswordResponseSchema>>


    /**
     * All the categories of books.
     */
    @GET("books/cat-list")
    fun getCategoriesAsync(): Deferred<Response<CategoryListResponseSchema>>

    /**
     * All the books.
     */
    @GET("books/book-list")
    fun getBooksAsync(
        @Query("page") pageIndex: Int,
        @Query("cat") category: String?
    ): Deferred<Response<BookListResponseSchema>>


    /**
     * All the books.
     */
    @GET("books/trending-book-list")
    fun getTrendingBooksAsync(
        @Query("page") pageIndex: Int,
        @Query("cat") category: String?
    ): Deferred<Response<BookListResponseSchema>>

    /**
     * Details of the book, as isbn is specified in request query param.
     */
    @GET("books/book-details")
    fun getBookDetailsAsync(@Query("isbn") isbn: String): Deferred<Response<BookDetailsResponseSchema>>

    /**
     * Details of the book, as isbn is specified in request query param.
     */
    @GET("books/book-preview")
    fun getBookPreviewAsync(@Query("isbn") isbn: String): Deferred<Response<BookPreviewResponseSchema>>

}