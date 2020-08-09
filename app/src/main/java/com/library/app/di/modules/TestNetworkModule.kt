package com.library.app.di.modules

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.library.app.common.Constants
import com.library.app.common.Prefs
import com.library.app.common.UITestApplication
import com.library.app.networking.ApiCallInterface
import com.library.app.networking.models.ErrorObj
import com.library.app.networking.models.ErrorResponseSchema
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


@Module
class TestNetworkModule {

    @Singleton
    @Provides
    fun provideAPIClient(
        prefs: Prefs,
        customApplication: UITestApplication
    ): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(ResponseInterceptor(prefs, customApplication))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(apiClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(apiClient)
            .baseUrl(Constants.TEST_BASE_URL)
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
    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var contentLength = 0L
            if (chain.request().body != null) {
                contentLength = chain.request().body!!.contentLength()
            }
            val request: Request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Length", contentLength.toString())
                .build()

            return try {
                chain.proceed(request)
            } catch (ex: Exception) {
                return createResponse(request)
            }
        }

    }

    /**
     * This class is used to check if AWT is expired in http response,
     * if expired the user is taken out to Login Screen.
     */
    class ResponseInterceptor(
        val prefs: Prefs,
        val customApplication: UITestApplication
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
            } catch (ex: Exception) {
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

            val errorResponse = ErrorResponseSchema(
                "Please check internet connection", arrayListOf(
                    ErrorObj("", "", "", "")
                )
            )

            val errorJSON = Gson().toJson(errorResponse)

            return Response.Builder()
                .code(408)
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .message("Please check internet connection")
                .body(
                    errorJSON.toString().toResponseBody("application/json".toMediaTypeOrNull())
                ).build()
        }

    }


}