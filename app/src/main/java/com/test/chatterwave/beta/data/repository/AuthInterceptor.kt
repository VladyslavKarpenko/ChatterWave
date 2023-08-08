package com.test.chatterwave.beta.data.repository

import com.test.chatterwave.beta.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authRepository: AuthRepository,
) : Interceptor {

    private val lock = Any()

    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(lock) {
            val originalRequest = chain.request()
            val accessToken = authRepository.getAccessToken()

            val requestBuilder = originalRequest.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .method(originalRequest.method, originalRequest.body)

            var response = chain.proceed(requestBuilder.build())

            if (response.code == 401) {
                val refreshToken = authRepository.getRefreshToken()

                val newAccessToken = runBlocking { authRepository.refreshToken("Bearer $refreshToken") }

                response.close()

                val newRequestBuilder = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .method(originalRequest.method, originalRequest.body)

                response = chain.proceed(newRequestBuilder.build())
            }

            return response
        }
    }
}