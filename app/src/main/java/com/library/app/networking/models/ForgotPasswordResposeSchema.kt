package com.library.app.networking.models

/**
 * Created by Lalit Hajare, Software Engineer on 22/6/20
 * This class is used to parse the response from ForgotPassword API call
 */
data class ForgotPasswordResposeSchema(val userId: String?) : BaseResponseSchema()