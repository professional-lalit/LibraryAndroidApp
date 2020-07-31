package com.library.app.di.modules

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.library.app.common.Constants
import com.library.app.common.CustomApplication
import com.library.app.common.Prefs
import com.library.app.networking.ApiCallInterface
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
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
            var contentLength: Long = 0L
            if (chain.request().body != null) {
                contentLength = chain.request().body!!.contentLength()
            }
            val requestBuilder: Request.Builder = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Length", contentLength.toString())
            if (prefs.accessToken!!.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer " + prefs.accessToken)
            }
            return chain.proceed(requestBuilder.build())
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

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)
            if (response.code == 401 && prefs.accessToken!!.isNotEmpty()) {
                handleUnauthorisedResponse()
            }
            return response
        }

        private fun handleUnauthorisedResponse() {
            customApplication.logout()
        }

    }
}