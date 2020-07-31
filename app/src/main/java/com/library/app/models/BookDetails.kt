package com.library.app.models

/**
 * Created by Lalit N. Hajare, Software Engineer on 30/07/2020
 */
data class BookDetails(
    var authors: ArrayList<String>? = null,
    var pageCount: Int? = null,
    var publishedDate: String? = null,
    var longDescription: String? = null
): Book()