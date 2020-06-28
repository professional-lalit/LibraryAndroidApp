package com.library.app.networking.models

/**
 * This class is used to send in request body in Login API call
 */
data class LoginRequestSchema(val email: String, val password: String)