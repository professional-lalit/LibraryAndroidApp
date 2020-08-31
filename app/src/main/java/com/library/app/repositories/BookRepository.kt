package com.library.app.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.library.app.common.Prefs
import com.library.app.models.Book
import com.library.app.models.BookDetails
import com.library.app.models.BookPreview
import com.library.app.models.Category
import com.library.app.networking.ApiCallInterface
import com.library.app.networking.Result
import com.library.app.networking.models.BookDetailsResponseSchema
import com.library.app.networking.models.BookListResponseSchema
import com.library.app.networking.models.CategoryListResponseSchema
import com.library.app.networking.models.ForgotPasswordResposeSchema
import com.library.app.utils.FileReader
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 */
class BookRepository @Inject constructor(
    mRetrofit: Retrofit,
    val mApiCallInterface: ApiCallInterface,
    val mPreferences: Prefs
) : BaseRepository(mRetrofit) {

    suspend fun getTrendingBooks(): Result<Any> {
        val response = mApiCallInterface.getTrendingBooksAsync(1, "").await()
        return if (successCodes.contains(response.code())) {
            val booksResponse = response.body() as BookListResponseSchema
            Result.Success(booksResponse)
        } else {
            Result.Error(parseError(response.errorBody()!!))
        }
    }

    suspend fun getRecentVisitedBooks(): Result<Any> {
        val response = mApiCallInterface.getBooksAsync(1, "").await()
        return if (successCodes.contains(response.code())) {
            val booksResponse = response.body() as BookListResponseSchema
            Result.Success(booksResponse)
        } else {
            Result.Error(parseError(response.errorBody()!!))
        }
    }

    suspend fun getBookCategories(): Result<Any> {
        val response = mApiCallInterface.getCategoriesAsync().await()
        return if (successCodes.contains(response.code())) {
            val booksResponse = response.body() as CategoryListResponseSchema
            Result.Success(booksResponse)
        } else {
            Result.Error(parseError(response.errorBody()!!))
        }
    }

    suspend fun getBooksOfCategory(categoryName: String?, pageIndex: Int): Result<Any> {
        val response = mApiCallInterface.getBooksAsync(pageIndex, categoryName).await()
        return if (successCodes.contains(response.code())) {
            val booksResponse = response.body() as BookListResponseSchema
            Result.Success(booksResponse)
        } else {
            Result.Error(parseError(response.errorBody()!!))
        }
    }

    suspend fun getRecentBooks(): ArrayList<Book>? {
        val bookList = ArrayList<Book>()
        return bookList
    }

    suspend fun getSavedBooks(): ArrayList<Book>? {
        val bookList = ArrayList<Book>()
        return bookList
    }

    suspend fun getCartBooks(): ArrayList<Book>? {
        val bookList = ArrayList<Book>()
        return bookList
    }

    suspend fun getBookDetails(isbn: String): Result<Any> {
        val response = mApiCallInterface.getBookDetailsAsync(isbn).await()
        return if (successCodes.contains(response.code())) {
            val booksResponse = response.body() as BookDetailsResponseSchema
            Result.Success(booksResponse)
        } else {
            Result.Error(parseError(response.errorBody()!!))
        }
    }


}