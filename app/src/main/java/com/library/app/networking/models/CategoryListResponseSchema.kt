package com.library.app.networking.models

import com.google.gson.Gson
import com.library.app.models.Category

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 * Consists of category related information acquired from server after parsing
 */
data class CategoryResponseSchema(
    val name: String
) {
    fun convert(): Category {
        return Gson().fromJson(Gson().toJson(this), Category::class.java)
    }
}

data class CategoryListResponseSchema(val cats: ArrayList<CategoryResponseSchema>) :
    BaseResponseSchema() {
}