package com.library.app.networking.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class created in order to get error message, if any inconsistencies occur in api call
 */
abstract class BaseResponseSchema {
    var message: String? = ""
    var statusCode: Int = 0

    inline fun <reified T> getLocalModel(responseInstance: Any): T {
        return Gson().fromJson(Gson().toJson(responseInstance), TypeToken.get(T::class.java).type)
    }
}