package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.TokenDomainResponse
import com.test.chatterwave.beta.domain.model.UserSignInDomainModel
import com.test.chatterwave.beta.domain.repository.SignUpRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

    suspend fun execute(user: UserSignInDomainModel): NetworkResult<TokenDomainResponse> {
        return signUpRepository.signUpUser(user)
    }

}