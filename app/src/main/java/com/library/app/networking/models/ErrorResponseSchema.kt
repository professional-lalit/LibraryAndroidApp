package com.library.app.networking.models

import java.util.ArrayList

/**
 * Created by Lalit Hajare, Software Engineer on 16/6/20
 * This class is used to parse the special `Error` response sent by Server.
 * The schema of this response is agreed upon by client & server side programmers.
 */
data class ErrorResponseSchema(val message: String, val data: ArrayList<ErrorObj>)

data class ErrorObj(
    val location: String,
    val param: String,
    val value: String,
    val msg: String
)