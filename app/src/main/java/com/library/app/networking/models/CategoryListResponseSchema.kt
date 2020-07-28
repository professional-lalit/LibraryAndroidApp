package com.library.app.networking.models

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 * Consists of category related information acquired from server after parsing
 */
data class CategoryResponseSchema(
    val name: String
)

data class CategoryListResponseSchema(val categories: ArrayList<CategoryResponseSchema>) :
    BaseResponseSchema() {
}