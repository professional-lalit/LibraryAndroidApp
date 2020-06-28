package com.library.app.networking.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * This class is used to parse the response from Login API call
 */
data class LoginResponseSchema(val token: String?, val userId: String?) : BaseResponseSchema() {
    inline fun <reified T> getMapped(expectedInstance: T): T {
        return getLocalModel(this) as T
    }
}