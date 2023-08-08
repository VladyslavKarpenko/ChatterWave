package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.ConfirmCodeErrorDomainResponseModel
import com.test.chatterwave.beta.domain.repository.SignUpRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class ValidateConfirmCodeByPhoneUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

    suspend fun execute(code: String, phone: String) : NetworkResult<ConfirmCodeErrorDomainResponseModel> {
        return signUpRepository.confirmCodeVerificationByPhone(code = code, phone = phone)
    }

}