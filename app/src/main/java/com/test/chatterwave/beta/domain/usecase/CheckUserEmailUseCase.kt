package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.PhoneEmailDomainResponseModel
import com.test.chatterwave.beta.domain.repository.SignUpRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class CheckUserEmailUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

    suspend fun execute(email: String): NetworkResult<PhoneEmailDomainResponseModel> {
        return signUpRepository.checkUserEmail(email)
    }

}