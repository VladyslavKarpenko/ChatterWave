package com.test.chatterwave.beta.data.source.remote.retrofit.api

import com.chi.interngram.echo.data.model.*
import com.test.chatterwave.beta.data.model.ConfirmCodeErrorDataResponseModel
import com.test.chatterwave.beta.data.model.NicknameResponseModel
import com.test.chatterwave.beta.data.model.PhoneEmailResponseModel
import com.test.chatterwave.beta.data.model.SendCodeDataModel
import com.test.chatterwave.beta.data.model.TokenResponse
import com.test.chatterwave.beta.data.model.UserEmailDataModel
import com.test.chatterwave.beta.data.model.UserNicknameDataModel
import com.test.chatterwave.beta.data.model.UserPhoneDataModel
import com.test.chatterwave.beta.data.model.UserSignInDataModel
import com.test.chatterwave.beta.data.model.ValidateCodeDataModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {

    @POST("checkEmail")
    suspend fun getUserEmailValidation(
        @Body email: UserEmailDataModel
    ): Response<PhoneEmailResponseModel>

    @POST("checkPhone")
    suspend fun getUserPhoneValidation(
        @Body phone: UserPhoneDataModel
    ): Response<PhoneEmailResponseModel>

    @POST("checkNickname")
    suspend fun getUserNicknameValidation(
        @Body nickname: UserNicknameDataModel
    ): Response<NicknameResponseModel>

    @POST("signUp")
    suspend fun signUpUser(
        @Body user: UserSignInDataModel
    ): Response<TokenResponse>

    @POST("createCode")
    suspend fun createConfirmCode(
        @Body sendCodeDomainModel: SendCodeDataModel
    ): Response<ConfirmCodeErrorDataResponseModel>

    @POST("confirmCode")
    suspend fun validateConfirmCode(
        @Body validateCodeDataModel: ValidateCodeDataModel
    ): Response<ConfirmCodeErrorDataResponseModel>

}