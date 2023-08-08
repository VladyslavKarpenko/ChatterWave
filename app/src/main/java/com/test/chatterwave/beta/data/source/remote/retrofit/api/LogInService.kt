package com.test.chatterwave.beta.data.source.remote.retrofit.api

import com.test.chatterwave.beta.data.model.ChangePasswordDataModel
import com.test.chatterwave.beta.data.model.ChangePasswordDataResponseModel
import com.test.chatterwave.beta.data.model.LoginWithEmail
import com.test.chatterwave.beta.data.model.LoginWithNumber
import com.test.chatterwave.beta.data.model.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInService {

    @POST("signIn")
    suspend fun signInWithEmail(@Body loginData: LoginWithEmail): Response<TokenResponse>


    @POST("signIn")
    suspend fun signInWithNumber(@Body loginData: LoginWithNumber): Response<TokenResponse>

    @POST("changePassword")
    suspend fun changeUserPassword(@Body changedUserPassword: ChangePasswordDataModel): Response<ChangePasswordDataResponseModel>
}