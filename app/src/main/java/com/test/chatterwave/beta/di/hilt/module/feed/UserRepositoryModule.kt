package com.test.chatterwave.beta.di.hilt.module.feed

import com.test.chatterwave.beta.data.repository.UserRepositoryImpl
import com.test.chatterwave.beta.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UserRepositoryModule {

    @Binds
    @Singleton
    fun provideUserRepositoryRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository


}