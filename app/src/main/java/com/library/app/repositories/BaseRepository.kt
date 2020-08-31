package com.library.app.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.library.app.networking.models.BaseResponseSchema
import com.library.app.networking.models.ErrorResponseSchema
import com.library.app.networking.models.LoginResponseSchema
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by Lalit Hajare, Software Engineer on 8/6/20
 * Consists of common Business Rules between all the repositories
 */
abstract class BaseRepository(val mRetrofit: Retrofit) {

    protected val successCodes = arrayOf(200, 201, 202, 203)

    /**
     * Converts the API response to local model that is manageable according to business rules
     * @param response: The Server response to parse
     * @param retrofit: used to serialize Server Response's Error Body
     * @see okhttp3.ResponseBody (Error Body)
     * to local model
     */
    fun parseError(errorBody: ResponseBody): ErrorResponseSchema {
        return try {
            mRetrofit.responseBodyConverter<ErrorResponseSchema>(
                ErrorResponseSchema::class.java,
                arrayOf()
            ).convert(errorBody)!!
        } catch (e: Exception) {
            ErrorResponseSchema("", arrayListOf())
        }
    }

}

