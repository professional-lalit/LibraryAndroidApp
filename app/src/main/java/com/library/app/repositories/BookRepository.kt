package com.library.app.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.library.app.common.Prefs
import com.library.app.models.Book
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
        val type: Type = object : TypeToken<ArrayList<BookListResponseSchema>>() {}.type
        return Gson().fromJson(FileReader.readStringFromFile("TrendingBookList.json"), type)
    }

    suspend fun getRecentVisitedBooks(): ArrayList<Book>? {
        val type: Type = object : TypeToken<ArrayList<BookListResponseSchema>>() {}.type
        return Gson().fromJson(FileReader.readStringFromFile("TrendingBookList.json"), type)
    }

    suspend fun getBookCategories(): ArrayList<Category>? {
        return arrayListOf(Category("Programming"), Category("Art"), Category("Life Style"))
    }

}