package com.library.app.networking.models

import com.google.gson.Gson
import com.library.app.models.User

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 */
data class UserResponseSchema(
    val userId: String,
    val name: String,
    val email: String
) : BaseResponseSchema() {
    inline fun <reified T> getMapped(expectedInstance: T): T {
        return getLocalModel(this) as T
    }
}