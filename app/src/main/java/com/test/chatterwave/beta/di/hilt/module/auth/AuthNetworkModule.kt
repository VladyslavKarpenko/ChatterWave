package com.test.chatterwave.beta.di.hilt.module.auth

import com.chi.interngram.echo.BuildConfig
import com.test.chatterwave.beta.data.repository.AuthInterceptor
import com.test.chatterwave.beta.data.source.remote.retrofit.api.LogInService
import com.test.chatterwave.beta.data.source.remote.retrofit.api.SignUpService
import com.test.chatterwave.beta.data.source.remote.retrofit.api.TokenRefreshService
import com.test.chatterwave.beta.di.hilt.annotation.AuthRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_AUTH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideSignUpService(@AuthRetrofit retrofit: Retrofit): SignUpService =
        retrofit.create(SignUpService::class.java)

    @Provides
    @Singleton
    fun provideLoginService(@AuthRetrofit retrofit: Retrofit): LogInService =
        retrofit.create(LogInService::class.java)

    @Provides
    @Singleton
    fun provideTokenRefreshService(@AuthRetrofit retrofit: Retrofit): TokenRefreshService =
        retrofit.create(TokenRefreshService::class.java)

}