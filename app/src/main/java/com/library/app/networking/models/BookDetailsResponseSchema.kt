package com.library.app.networking.models

import com.google.gson.Gson
import com.library.app.models.Book
import com.library.app.models.BookDetails


/**
 * Created by Lalit N. Hajare, Software Engineer on 30/07/2020
 */
data class BookDetailsSchema(
    val authors: ArrayList<String>?,
    val categories: ArrayList<String>?,
    val _id: String?,
    val title: String?,
    val isbn: String?,
    val pageCount: Int?,
    val publishedDate: String?,
    val thumbnailUrl: String?,
    val longDescription: String?,
    val shortDescription: String?,
    val status: String?
) : BaseResponseSchema() {
    fun convert(): BookDetails {
        return Gson().fromJson(Gson().toJson(this), BookDetails::class.java)
    }
}

data class BookDetailsResponseSchema(
    val book: BookDetailsSchema?
) : BaseResponseSchema() {
    fun convert(): BookDetails {
        return Gson().fromJson(Gson().toJson(this.book), BookDetails::class.java)
    }
}