package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.UpdateUserDomainModel
import com.test.chatterwave.beta.domain.model.UserDomainModel
import com.test.chatterwave.beta.domain.repository.UserRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(userUpdates: UpdateUserDomainModel) : NetworkResult<UserDomainModel> {
        return userRepository.updateUser(userUpdates = userUpdates)
    }

}