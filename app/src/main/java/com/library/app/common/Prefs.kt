package com.library.app.common

import android.content.Context
import com.google.gson.Gson
import com.library.app.models.User
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class is used to store data locally in protected file in app's memory
 */
class Prefs @Inject constructor(application: CustomApplication) {

    private val USER_ID = "user_id"
    private val ACCESS_TOKEN = "access_token"
    private val USER = "user"
    private val mPreferences =
        application.getSharedPreferences(Constants.APP_PREFS_NAME, Context.MODE_PRIVATE)

    var user: User? = null
        set(value) {
            field = value
            mPreferences.edit().putString(USER, Gson().toJson(value).toString()).apply()
        }
        get() {
            return Gson().fromJson(
                Gson().toJson(mPreferences.getString(USER, "")),
                User::class.java
            )
        }

    var accessToken: String? = null
        set(value) {
            field = value
            mPreferences.edit().putString(ACCESS_TOKEN, value).apply()
        }
        get() {
            return mPreferences.getString(ACCESS_TOKEN, "")!!
        }

    var userId: String? = null
        set(value) {
            field = value
            mPreferences.edit().putString(USER_ID, value).apply()
        }
        get() {
            return mPreferences.getString(USER_ID, "")!!
        }

}