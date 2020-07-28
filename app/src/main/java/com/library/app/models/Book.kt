package com.library.app.models

/**
 * Holds the data related to Book.
 * This model is used for local business logic,
 * the data is acquired from network schema.
 */
data class Book(
    var title: String? = "",
    var isbn: String? = "",
    var pageCount: String? = "",
    var publishedDate: String? = "",
    var thumbnailUrl: String? = "",
    var shortDescription: String? = "",
    var categories: ArrayList<String>? = null
)