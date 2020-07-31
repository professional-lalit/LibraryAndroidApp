package com.library.app.repositories

import com.library.app.common.Prefs
import com.library.app.networking.ApiCallInterface
import com.library.app.networking.Result
import com.library.app.networking.models.*
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * Consists of all the on-boarding APIs like login, signup, forgot-password, change-password
 * @param mRetrofit used to serialize Server Response's Error Body
 * @see okhttp3.ResponseBody (Error Body)
 * to local model
 * @param mApiCallInterface used to call Server APIs
 * @param mPreferences used to store the necessary data locally acquired from Server APIs.
 */
class AuthRepository @Inject constructor(
    mRetrofit: Retrofit,
    val mApiCallInterface: ApiCallInterface,
    val mPreferences: Prefs
) : BaseRepository(mRetrofit) {

    /**
     * Calls the API to get userId & access token
     * @param loginRequestSchema: This is the request body of API call
     */
    suspend fun login(loginRequestSchema: LoginRequestSchema): Result<Any> {
        val response = mApiCallInterface.getAccessTokenAsync(loginRequestSchema).await()
        return if (response.isSuccessful) {
            val loginResponseSchema = response.body() as LoginResponseSchema
            mPreferences.userId = loginResponseSchema.userId
            mPreferences.accessToken = loginResponseSchema.token
            Result.Success(loginResponseSchema)
        } else {
            mPreferences.userId = null
            mPreferences.accessToken = null
            Result.Error(parseError(response.errorBody()!!))
        }
    }

    /**
     * Calls the API to signup user with System.
     * If the response contains the `userId`, then the signup was successfull
     * @param signupRequestSchema: This is the request body of API call
     */
    suspend fun signup(signupRequestSchema: SignupRequestSchema): Result<Any> {
        val response = mApiCallInterface.signupAsync(signupRequestSchema).await()
        return if (response.isSuccessful) {
            val signupResponseResponseSchema = response.body() as SignupResponseSchema
            Result.Success(signupResponseResponseSchema)
        } else {
            Result.Error(parseError(response.errorBody()!!))
        }
    }

    /**
     * Calls the API to recover password
     * @param forgotPasswordRequestSchema: This is the request body of API call
     * Saves the 'userId' sent from server, this is then used while changing the password.
     */
    suspend fun forgotPassword(forgotPasswordRequestSchema: ForgotPasswordRequestSchema): Result<Any> {
        val response = mApiCallInterface.forgotPasswordAsync(forgotPasswordRequestSchema).await()
        return if (response.isSuccessful) {
            val forgotPasswordResposeSchema = response.body() as ForgotPasswordResposeSchema
            mPreferences.userId = forgotPasswordResposeSchema.userId
            Result.Success(forgotPasswordResposeSchema)
        } else {
            Result.Error(parseError(response.errorBody()!!))
        }
    }

    /**
     * Calls the API to change password
     * @param oldPassword: The old verified password
     * @param newPassword: The new desired valid password
     */
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Any> {
        val changePasswordRequestSchema =
            ChangePasswordRequestSchema(mPreferences.userId!!, oldPassword, newPassword)
        val response = mApiCallInterface.changePasswordAsync(changePasswordRequestSchema).await()
        return if (response.isSuccessful) {
            val changePasswordResponseSchema = response.body() as ChangePasswordResponseSchema
            Result.Success(changePasswordResponseSchema)
        } else {
            Result.Error(parseError(response.errorBody()!!))
        }
    }

}