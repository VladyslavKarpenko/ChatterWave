package com.test.chatterwave.beta.data.source.remote.retrofit.api

import com.test.chatterwave.beta.data.model.TokenResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenRefreshService {

    @POST("refreshToken")
    suspend fun refreshTokens(
        @Header("Authorization") refreshToken: String
    ): TokenResponse

}