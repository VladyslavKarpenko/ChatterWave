package com.test.chatterwave.beta.domain.repository

import androidx.paging.PagingData
import com.test.chatterwave.beta.domain.model.CreatePostDomainModel
import com.test.chatterwave.beta.domain.model.UpdateUserDomainModel
import com.test.chatterwave.beta.domain.model.UserDomainModel
import com.test.chatterwave.beta.domain.model.UserPostDomainModel
import com.test.chatterwave.beta.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun updateUser(userUpdates: UpdateUserDomainModel) : NetworkResult<UserDomainModel>

    suspend fun getCurrentUser(): NetworkResult<UserDomainModel>

    suspend fun getAllUserPosts(userId: String): Flow<PagingData<UserPostDomainModel>>

    suspend fun createPost(newPost: CreatePostDomainModel): NetworkResult<UserPostDomainModel>

    suspend fun deleteUserAvatar(): NetworkResult<UserPostDomainModel>

}