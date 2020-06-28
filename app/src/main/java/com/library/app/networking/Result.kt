package com.library.app.networking

import com.library.app.networking.models.ErrorResponseSchema

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()

    data class Error(val errorResponseSchema: ErrorResponseSchema) : Result<Nothing>()

}