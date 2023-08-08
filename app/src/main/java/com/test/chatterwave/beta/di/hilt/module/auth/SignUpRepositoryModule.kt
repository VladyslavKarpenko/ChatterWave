package com.test.chatterwave.beta.di.hilt.module.auth

import com.test.chatterwave.beta.data.repository.SignUpRepositoryImpl
import com.test.chatterwave.beta.domain.repository.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SignUpRepositoryModule {

    @Binds
    @Singleton
    fun provideSignUpRepository(signUpRepositoryImpl: SignUpRepositoryImpl): SignUpRepository

}