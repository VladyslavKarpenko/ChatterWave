package com.test.chatterwave.beta.domain.repository

import com.test.chatterwave.beta.domain.model.ConfirmCodeErrorDomainResponseModel
import com.test.chatterwave.beta.domain.model.NicknameDomainResponseModel
import com.test.chatterwave.beta.domain.model.PhoneEmailDomainResponseModel
import com.test.chatterwave.beta.domain.model.TokenDomainResponse
import com.test.chatterwave.beta.domain.model.UserSignInDomainModel
import com.test.chatterwave.beta.utils.NetworkResult

interface SignUpRepository {

    suspend fun checkUserEmail(email: String) : NetworkResult<PhoneEmailDomainResponseModel>

    suspend fun checkUserPhone(phone: String) : NetworkResult<PhoneEmailDomainResponseModel>

    suspend fun checkUserNickname(nickname: String) : NetworkResult<NicknameDomainResponseModel>

    suspend fun createConfirmCodeByPhone(phone: String) : NetworkResult<ConfirmCodeErrorDomainResponseModel>

    suspend fun createConfirmCodeByEmail(email: String) : NetworkResult<ConfirmCodeErrorDomainResponseModel>

    suspend fun confirmCodeVerificationByPhone(code: String, phone: String) : NetworkResult<ConfirmCodeErrorDomainResponseModel>

    suspend fun confirmCodeVerificationByEmail(code: String, email: String) : NetworkResult<ConfirmCodeErrorDomainResponseModel>

    suspend fun signUpUser(user: UserSignInDomainModel) : NetworkResult<TokenDomainResponse>

    suspend fun checkUserPhoneExists(phone: String) : NetworkResult<PhoneEmailDomainResponseModel>

    suspend fun checkUserEmailExists(email: String) : NetworkResult<PhoneEmailDomainResponseModel>

}