package com.test.chatterwave.beta.di.hilt.module

import com.test.chatterwave.beta.data.repository.AudioRepositoryImpl
import com.test.chatterwave.beta.domain.repository.AudioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [AppModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
class AppModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
        @Binds
        abstract fun bindStockRepository(audioRepositoryImpl: AudioRepositoryImpl): AudioRepository
    }
}