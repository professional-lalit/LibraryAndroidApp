package com.library.app.networking.models

/**
 * Created by Lalit Hajare, Software Engineer on 11/6/20
 * This class is used to send in request body in Signup API call
 */
data class SignupRequestSchema(val name: String, val email: String, val password: String)