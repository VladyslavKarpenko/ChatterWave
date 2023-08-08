package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.UserPostDomainModel
import com.test.chatterwave.beta.domain.repository.UserRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class DeleteUserAvatarUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(): NetworkResult<UserPostDomainModel> {
        return userRepository.deleteUserAvatar()
    }

}