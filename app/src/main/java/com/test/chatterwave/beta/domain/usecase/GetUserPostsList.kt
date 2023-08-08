package com.test.chatterwave.beta.domain.usecase

import androidx.paging.*
import com.test.chatterwave.beta.domain.model.UserPostDomainModel
import com.test.chatterwave.beta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPostsList @Inject
constructor(private val userRepository: UserRepository) {

    suspend fun execute(userId: String): Flow<PagingData<UserPostDomainModel>>  {
        return userRepository.getAllUserPosts(userId = userId)
    }

}