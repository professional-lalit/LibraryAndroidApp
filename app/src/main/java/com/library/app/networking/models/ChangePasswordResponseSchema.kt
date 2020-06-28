package com.library.app.networking.models

/**
 * Created by Lalit Hajare, Software Engineer on 26/6/20
 *This class is used to parse the response from ChangePassword API call
 */
data class ChangePasswordResponseSchema(val userId: String) : BaseResponseSchema()