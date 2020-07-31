package com.library.app.models

/**
 * Holds the data related to Book.
 * This model is used for local business logic,
 * the data is acquired from network schema.
 */
open class Book {
    var _id: String? = null
    var isbn: String? = null
    var title: String? = null
    var thumbnailUrl: String? = null
    var shortDescription: String? = null
    var categories: ArrayList<String>? = null
}