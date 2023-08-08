package com.test.chatterwave.beta.di.hilt.module.feed

import com.chi.interngram.echo.BuildConfig
import com.test.chatterwave.beta.data.source.remote.retrofit.api.UserService
import com.test.chatterwave.beta.di.hilt.annotation.MainRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainNetworkModule {

    @Provides
    @Singleton
    @MainRetrofit
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideUserService(@MainRetrofit retrofit: Retrofit): UserService = retrofit
        .create(UserService::class.java)
}