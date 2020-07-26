package com.library.app.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.library.app.common.Constants
import com.library.app.networking.ApiCallInterface
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
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
open class NetworkModule {

    @Singleton
    @Provides
    fun provideAPIClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    @Singleton
    @Provides
    open fun provideRetrofit(apiClient: OkHttpClient): Retrofit {
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
    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var contentLength: Long = 0L
            if (chain.request().body != null) {
                contentLength = chain.request().body!!.contentLength()
            }

            val request: Request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Length", contentLength.toString())
                .build()
            return chain.proceed(request)
        }

    }
}