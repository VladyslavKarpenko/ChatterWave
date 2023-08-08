package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.UserDomainModel
import com.test.chatterwave.beta.domain.repository.UserRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute() : NetworkResult<UserDomainModel> {
        return userRepository.getCurrentUser()
    }

}