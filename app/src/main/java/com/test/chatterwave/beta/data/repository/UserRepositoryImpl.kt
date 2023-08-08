package com.test.chatterwave.beta.data.repository

import androidx.paging.*
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.data.source.remote.retrofit.api.UserService
import com.test.chatterwave.beta.domain.repository.UserRepository
import com.google.gson.Gson
import com.test.chatterwave.beta.domain.model.ChangePasswordDomainResponseModel
import com.test.chatterwave.beta.domain.model.CreatePostDomainModel
import com.test.chatterwave.beta.domain.model.UpdateUserDomainModel
import com.test.chatterwave.beta.domain.model.UserDomainModel
import com.test.chatterwave.beta.domain.model.UserPostDomainModel
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.STATUS_CODE_200
import com.test.chatterwave.beta.utils.STATUS_CODE_201
import com.test.chatterwave.beta.utils.STATUS_CODE_401
import com.test.chatterwave.beta.utils.STATUS_CODE_500
import com.test.chatterwave.beta.utils.fromDataToDomainMapper
import com.test.chatterwave.beta.utils.fromDomainToDataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val service: UserService, private val userPostsPagingDataSource: UserPostsPagingDataSource.Factory) :
    UserRepository {

    override suspend fun updateUser(userUpdates: UpdateUserDomainModel): NetworkResult<UserDomainModel> {
        try {
            val result = service.updateUser(userUpdates = userUpdates.fromDomainToDataMapper())
            return when (result.code()) {
                STATUS_CODE_200 -> {
                    NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                }
                STATUS_CODE_500 -> {
                    NetworkResult.Error(
                        message = R.string.server_error
                    )
                }
                else -> {
                    NetworkResult.Error(message = R.string.server_error)
                }
            }
        } catch (e: Exception) {
            return NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun getCurrentUser(): NetworkResult<UserDomainModel> {
        try {
            val result = service.getCurrentUser()
            val errorResponse = Gson().fromJson(result.errorBody()?.string(), ChangePasswordDomainResponseModel::class.java)
            return when(result.code()){
                STATUS_CODE_200 -> {
                    NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                }
                STATUS_CODE_401 -> {
                    NetworkResult.Exception(exception = errorResponse.error)
                }
                else -> {
                    NetworkResult.Error(message = R.string.server_error)
                }
            }
        } catch (e: Exception) {
            return NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun getAllUserPosts(userId: String): Flow<PagingData<UserPostDomainModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { userPostsPagingDataSource.create(userId = userId) }
        ).flow
    }

    override suspend fun createPost(newPost: CreatePostDomainModel): NetworkResult<UserPostDomainModel> {
        try {
            val result = service.createPost( newPost = newPost.fromDataToDomainMapper())
            return when(result.code()){
                STATUS_CODE_201 -> {
                    NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                }
                else -> {
                    NetworkResult.Error(message = R.string.server_error)
                }
            }
        } catch (e: Exception) {
            return NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun deleteUserAvatar(): NetworkResult<UserPostDomainModel> {
        try {
            val result = service.deleteUserAvatar()
            return when(result.code()){
                STATUS_CODE_200 -> {
                    NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                }
                else -> {
                    NetworkResult.Error(message = R.string.server_error)
                }
            }
        } catch (e: Exception) {
            return NetworkResult.Error(message = R.string.server_error)
        }
    }

}