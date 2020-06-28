package com.library.app.repositories

import com.library.app.common.Prefs
import com.library.app.models.User
import com.library.app.networking.ApiCallInterface
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class performs all the low level operations for `User` data
 * like getting the information from server and saving it locally,
 * providing the locally stored `User` data to business logic clients
 */
class UserRepository @Inject constructor(
    val mApiCallInterface: ApiCallInterface,
    val mPreferences: Prefs
) {

    @Throws(Exception::class)
    suspend fun getUser(): User {
        val user: User
        if (mPreferences.user != null) {
            user = mPreferences.user!!
        } else {
            val response = mApiCallInterface.getUserDetailsAsync(mPreferences.userId!!).await()
            if (response.userId.isNotEmpty()) {
                mPreferences.user = response.getLocalModel<User>(response)
                user = mPreferences.user!!
            } else {
                throw Exception(response.message)
            }
        }
        return user
    }


}