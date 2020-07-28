package com.library.app.networking.models

/**
 * Holds the data related book acquired from server after parsing
 */
data class BookResponseSchema(
    val authors: ArrayList<String>? = null,
    val categories: ArrayList<String>? = null,
    val _id: String? = null,
    val title: String? = null,
    val isbn: String? = null,
    val pageCount: Int? = null,
    val publishedDate: String? = null,
    val thumbnailUrl: String? = null,
    val longDescription: String? = null,
    val shortDescription: String? = "",
    val status: String? = null
)

data class BookListResponseSchema(
    val books: ArrayList<BookResponseSchema>
) : BaseResponseSchema() {

}