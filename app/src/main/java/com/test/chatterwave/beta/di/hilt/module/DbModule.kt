package com.test.chatterwave.beta.di.hilt.module

import android.content.Context
import androidx.room.Room
import com.chi.interngram.echo.BuildConfig
import com.test.chatterwave.beta.data.source.local.room.ChatterWaveDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ChatterWaveDatabase::class.java, BuildConfig.DB_NAME)
        .build()

    @Provides
    @Singleton
    fun provideDao(db: ChatterWaveDatabase) = db.userDao

}