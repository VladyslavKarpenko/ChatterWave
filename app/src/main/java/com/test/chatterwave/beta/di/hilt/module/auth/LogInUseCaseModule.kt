package com.test.chatterwave.beta.di.hilt.module.auth

import com.test.chatterwave.beta.domain.repository.LoginRepository
import com.test.chatterwave.beta.domain.usecase.ChangeUserPasswordUseCase
import com.test.chatterwave.beta.domain.usecase.LoginWithEmailUseCase
import com.test.chatterwave.beta.domain.usecase.LoginWithNumberUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LogInUseCaseModule {

    @Provides
    @Singleton
    fun provideLoginWithPhoneUseCase(repository: LoginRepository) : LoginWithNumberUseCase {
        return LoginWithNumberUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginWithEmailUseCase(repository: LoginRepository) : LoginWithEmailUseCase {
        return LoginWithEmailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideChangeUserPasswordUseCase(repository: LoginRepository) : ChangeUserPasswordUseCase {
        return ChangeUserPasswordUseCase(repository)
    }

}