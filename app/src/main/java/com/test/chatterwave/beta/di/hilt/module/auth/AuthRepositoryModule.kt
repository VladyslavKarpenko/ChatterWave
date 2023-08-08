package com.test.chatterwave.beta.di.hilt.module.auth

import com.test.chatterwave.beta.data.repository.AuthRepositoryImpl
import com.test.chatterwave.beta.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthRepositoryModule {

    @Binds
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

}