package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.PhoneEmailDomainResponseModel
import com.test.chatterwave.beta.domain.repository.SignUpRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class CheckUserEmailExistsUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

    suspend fun execute(email: String): NetworkResult<PhoneEmailDomainResponseModel> {
        return signUpRepository.checkUserEmailExists(email)
    }

}