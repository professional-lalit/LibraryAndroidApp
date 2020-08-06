package com.library.app.networking.models

import com.google.gson.Gson
import com.library.app.models.BookDetails
import com.library.app.models.BookPreview

/**
 * Created by Lalit N. Hajare, Software Engineer on 06/08/2020
 */
data class BookPreviewResponseSchema(
    val preview: String?
) : BaseResponseSchema() {
    fun convert(): BookPreview {
        return Gson().fromJson(Gson().toJson(this), BookPreview::class.java)
    }
}