package com.library.app.networking.models

/**
 * Created by Lalit Hajare, Software Engineer on 26/6/20
 *This class is used to send in request body in Change Password API call
 */
data class ChangePasswordRequestSchema(
    val userId: String,
    val oldPassword: String,
    val newPassword: String
)