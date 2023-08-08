package com.test.chatterwave.beta.di.hilt.module.auth

import com.test.chatterwave.beta.data.repository.LoginRepositoryImpl
import com.test.chatterwave.beta.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LogInRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

}