package com.test.chatterwave.beta.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.chatterwave.beta.data.source.local.room.dao.UserDao
import com.test.chatterwave.beta.data.source.local.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class ChatterWaveDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}