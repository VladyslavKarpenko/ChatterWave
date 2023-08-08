package com.test.chatterwave.beta.domain.repository


import com.test.chatterwave.beta.domain.model.ChangePasswordDomainModel
import com.test.chatterwave.beta.domain.model.ChangePasswordDomainResponseModel
import com.test.chatterwave.beta.domain.model.LoginWithEmailDomainModel
import com.test.chatterwave.beta.domain.model.LoginWithPhoneDomainModel
import com.test.chatterwave.beta.domain.model.TokenDomainResponse
import com.test.chatterwave.beta.utils.NetworkResult

interface LoginRepository {

    suspend fun loginWithEmail(loginData: LoginWithEmailDomainModel): NetworkResult<TokenDomainResponse>

    suspend fun loginWithNumber(loginData: LoginWithPhoneDomainModel): NetworkResult<TokenDomainResponse>

    suspend fun changeUserPassword(changePasswordDomainModel: ChangePasswordDomainModel): NetworkResult<ChangePasswordDomainResponseModel>
}