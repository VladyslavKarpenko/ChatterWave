package com.test.chatterwave.beta.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.chatterwave.beta.utils.usersTableName

@Entity(tableName = usersTableName)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0
)
