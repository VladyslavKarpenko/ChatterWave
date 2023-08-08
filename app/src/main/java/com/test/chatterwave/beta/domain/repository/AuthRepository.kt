package com.test.chatterwave.beta.domain.repository

interface AuthRepository {

   suspend fun refreshToken(refreshToken: String): String

   fun getAccessToken(): String

   fun getRefreshToken(): String

}