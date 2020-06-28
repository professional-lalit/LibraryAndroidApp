package com.library.app.di.modules

import com.library.app.common.Prefs
import com.library.app.di.annotations.AuthScope
import com.library.app.networking.ApiCallInterface
import com.library.app.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 */
@Module
class RepositoryModule {

    @AuthScope
    @Provides
    fun provideAuthRepository(
        retrofit: Retrofit,
        apiCallInterface: ApiCallInterface,
        prefs: Prefs
    ): AuthRepository {
        return AuthRepository(retrofit, apiCallInterface, prefs)
    }

}