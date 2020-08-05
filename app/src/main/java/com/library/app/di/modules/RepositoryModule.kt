package com.library.app.di.modules

import com.library.app.common.Prefs
import com.library.app.di.annotations.AuthScope
import com.library.app.networking.ApiCallInterface
import com.library.app.repositories.AuthRepository
import com.library.app.repositories.BookRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * Provides instances of repositories used throughout the app.
 */
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        retrofit: Retrofit,
        apiCallInterface: ApiCallInterface,
        prefs: Prefs
    ): AuthRepository {
        return AuthRepository(retrofit, apiCallInterface, prefs)
    }

    @Singleton
    @Provides
    fun provideBookRepository(
        retrofit: Retrofit,
        apiCallInterface: ApiCallInterface,
        prefs: Prefs
    ): BookRepository {
        return BookRepository(retrofit, apiCallInterface, prefs)
    }

}