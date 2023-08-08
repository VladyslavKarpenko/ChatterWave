package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.ConfirmCodeErrorDomainResponseModel
import com.test.chatterwave.beta.domain.repository.SignUpRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class ValidateConfirmCodeByEmailUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

    suspend fun execute(code: String, email: String): NetworkResult<ConfirmCodeErrorDomainResponseModel> {
        return signUpRepository.confirmCodeVerificationByEmail(code = code, email = email)
    }

}