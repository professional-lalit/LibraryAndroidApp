package com.library.app.networking.models

/**
 * Created by Lalit Hajare, Software Engineer on 11/6/20
 * The model to parse the Signup API response
 */
data class SignupResponseSchema(var userId: String?) : BaseResponseSchema() {
    inline fun <reified T> getMapped(expectedInstance: T): T {
        return getLocalModel(this) as T
    }
}