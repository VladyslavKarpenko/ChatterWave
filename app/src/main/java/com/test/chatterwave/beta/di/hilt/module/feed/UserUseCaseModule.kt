package com.test.chatterwave.beta.di.hilt.module.feed

import com.test.chatterwave.beta.domain.repository.UserRepository
import com.chi.interngram.echo.domain.usecase.*
import com.test.chatterwave.beta.domain.usecase.CreatePostUseCase
import com.test.chatterwave.beta.domain.usecase.DeleteUserAvatarUseCase
import com.test.chatterwave.beta.domain.usecase.GetCurrentUserUseCase
import com.test.chatterwave.beta.domain.usecase.GetUserPostsList
import com.test.chatterwave.beta.domain.usecase.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserUseCaseModule {

    @Provides
    @Singleton
    fun provideCheckUserEmailUseCase(userRepository: UserRepository): UpdateUserUseCase {
        return UpdateUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(userRepository: UserRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserPostsListUseCase(userRepository: UserRepository): GetUserPostsList {
        return GetUserPostsList(userRepository)
    }

    @Provides
    @Singleton
    fun provideCreatePostUseCase(userRepository: UserRepository): CreatePostUseCase {
        return CreatePostUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteUserAvatarUseCase(userRepository: UserRepository): DeleteUserAvatarUseCase {
        return DeleteUserAvatarUseCase(userRepository)
    }

}