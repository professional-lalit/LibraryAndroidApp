package com.library.app.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.library.app.common.Constants
import com.library.app.common.CustomApplication
import com.library.app.common.Prefs
import com.library.app.networking.ApiCallInterface
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class is used to construct the networking layer by resolving the internal dependencies
 * and just providing the interface for calling APIs
 */

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideAPIClient(
        prefs: Prefs,
        customApplication: CustomApplication
    ): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor(HeaderInterceptor(prefs))
            .addInterceptor(ResponseInterceptor(prefs, customApplication))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(apiClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(apiClient)
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAPI(retrofit: Retrofit): ApiCallInterface {
        return retrofit.create(ApiCallInterface::class.java)
    }

    /**
     * This class is used to write headers at run time, when APIs are called
     */
    class HeaderInterceptor(val prefs: Prefs) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var contentLength = 0L
            if (chain.request().body != null) {
                contentLength = chain.request().body!!.contentLength()
            }
            val requestBuilder: Request.Builder = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Length", contentLength.toString())
            if (prefs.accessToken != null && prefs.accessToken!!.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer "+prefs.accessToken!!)
            }
            return try {
                chain.proceed(requestBuilder.build())
            } catch (ex: SocketTimeoutException) {
                return createResponse(requestBuilder.build())
            }
        }
    }

    /**
     * This class is used to check if AWT is expired in http response,
     * if expired the user is taken out to Login Screen.
     */
    class ResponseInterceptor(
        val prefs: Prefs,
        val customApplication: CustomApplication
    ) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var response: Response? = null
            val request = chain.request()
            return try {
                response = chain.proceed(request)
                if (response.code == 401 && prefs.accessToken!!.isNotEmpty()) {
                    handleUnauthorisedResponse()
                }
                response
            } catch (ex: SocketTimeoutException) {
                ex.printStackTrace()
                return createResponse(request)
            }
        }

        private fun handleUnauthorisedResponse() {
            customApplication.logout()
        }

    }

    companion object {

        @JvmStatic
        fun createResponse(request: Request): Response {
            return Response.Builder()
                .code(408)
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .message("Please check internet connection")
                .body(
                    "{\"message\":[\"Please check internet connection\"]}".toResponseBody("application/json".toMediaTypeOrNull())
                ).build()
        }

    }

}