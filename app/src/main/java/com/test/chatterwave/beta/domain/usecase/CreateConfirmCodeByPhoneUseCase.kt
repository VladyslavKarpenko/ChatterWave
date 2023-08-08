package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.ConfirmCodeErrorDomainResponseModel
import com.test.chatterwave.beta.domain.repository.SignUpRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class CreateConfirmCodeByPhoneUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

    suspend fun execute(phone: String): NetworkResult<ConfirmCodeErrorDomainResponseModel> {
        return signUpRepository.createConfirmCodeByPhone(phone = phone)
    }

}