package com.library.app.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.library.app.common.Prefs
import com.library.app.models.Book
import com.library.app.models.BookDetails
import com.library.app.models.Category
import com.library.app.networking.ApiCallInterface
import com.library.app.networking.models.BookListResponseSchema
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

    suspend fun getTrendingBooks(): ArrayList<Book>? {
        val bookList = ArrayList<Book>()
        val response = mApiCallInterface.getBooksAsync(1, "").await()
        if (response.isSuccessful && response.body() != null && response.body()!!.books.isNotEmpty()) {
            for (bookSchema in response.body()!!.books) {
                bookList.add(bookSchema.convert())
            }
            return bookList
        }
        return null
    }

    suspend fun getRecentVisitedBooks(): ArrayList<Book>? {
        val bookList = ArrayList<Book>()
        val response = mApiCallInterface.getBooksAsync(1, "").await()
        if (response.isSuccessful && response.body() != null && response.body()!!.books.isNotEmpty()) {
            for (bookSchema in response.body()!!.books) {
                bookList.add(bookSchema.convert())
            }
            return bookList
        }
        return null
    }

    suspend fun getBookCategories(): ArrayList<Category>? {
        val categoryListResponseSchema = mApiCallInterface.getCategoriesAsync().await()
        if (categoryListResponseSchema.isSuccessful) {
            val list = ArrayList<Category>()
            categoryListResponseSchema.body()!!.cats.forEach { categorySchema ->
                list.add(categorySchema.convert())
            }
            return list
        }
        return null
    }

    suspend fun getBooksOfCategory(categoryName: String?, pageIndex: Int): ArrayList<Book>? {
        val bookList = ArrayList<Book>()
        val response = mApiCallInterface.getBooksAsync(pageIndex, categoryName).await()
        if (response.isSuccessful && response.body() != null && response.body()!!.books.isNotEmpty()) {
            for (bookSchema in response.body()!!.books) {
                bookList.add(bookSchema.convert())
            }
            return bookList
        }
        return null
    }

    suspend fun getBookDetails(isbn: String): BookDetails? {
        val response = mApiCallInterface.getBookDetailsAsync(isbn).await()
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.convert()
        }
        return null
    }

}