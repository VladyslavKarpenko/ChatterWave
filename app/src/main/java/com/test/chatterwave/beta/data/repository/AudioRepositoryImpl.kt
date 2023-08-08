package com.test.chatterwave.beta.data.repository

import android.content.Context
import com.test.chatterwave.beta.domain.repository.AudioRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(
    @ApplicationContext val appContext: Context
) : AudioRepository {
    override fun getAudioDataFlow(): Flow<List<String>> = callbackFlow {}
}