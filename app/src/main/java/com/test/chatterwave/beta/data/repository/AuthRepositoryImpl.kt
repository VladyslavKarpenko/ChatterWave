package com.test.chatterwave.beta.data.repository

import android.content.ContentValues.TAG
import com.test.chatterwave.beta.data.source.local.preferences.AppPreferences
import com.test.chatterwave.beta.data.source.remote.retrofit.api.TokenRefreshService
import com.test.chatterwave.beta.domain.repository.AuthRepository
import com.test.chatterwave.beta.utils.EMPTY_STRING
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val service: TokenRefreshService, private val appPreferences: AppPreferences) :
    AuthRepository {
    override suspend fun refreshToken(refreshToken: String): String {
        try {
            val result = service.refreshTokens(refreshToken = refreshToken)
            appPreferences.apply {
                this.setRefreshToken(result.refreshToken!!)
                this.setAccessToken(result.accessToken!!)
            }
            return result.accessToken!!
        }catch (e: Exception) {
            Timber.tag(TAG).d("refreshToken: " + e.localizedMessage)
        }
        return EMPTY_STRING
    }

    override fun getAccessToken(): String {
        return appPreferences.getAccessToken()
    }

    override fun getRefreshToken(): String {
        return appPreferences.getRefreshToken()
    }
}