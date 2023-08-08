package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.PhoneEmailDomainResponseModel
import com.test.chatterwave.beta.domain.repository.SignUpRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class CheckUserPhoneExistsUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

    suspend fun execute(phone: String) : NetworkResult<PhoneEmailDomainResponseModel> {
        return signUpRepository.checkUserPhoneExists(phone)
    }

}