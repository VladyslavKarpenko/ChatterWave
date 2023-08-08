package com.test.chatterwave.beta.domain.repository

import kotlinx.coroutines.flow.Flow

interface AudioRepository {
    fun getAudioDataFlow(): Flow<List<String>>
}