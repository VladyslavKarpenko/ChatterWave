package com.test.chatterwave.beta.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.chatterwave.beta.data.source.remote.retrofit.api.UserService
import com.test.chatterwave.beta.domain.model.UserPostDomainModel
import com.test.chatterwave.beta.utils.fromDataToDomainMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class UserPostsPagingDataSource @AssistedInject constructor(private val api: UserService, @Assisted("userId") private val userId: String) : PagingSource<Int, UserPostDomainModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserPostDomainModel> {
        return try {
            val pageNumber = params.key ?: 1
            val pageSize = params.loadSize
            val response = api.getAllPostsOfUser(userId = userId, page = pageNumber.toString(), count = pageSize.toString())

            if (response.isSuccessful){
                val result = response.body()!!.map { it.fromDataToDomainMapper() }
                val nextPageNumber = if (result.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                return LoadResult.Page(result, prevPageNumber, nextPageNumber)
            }else{
                return LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserPostDomainModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("userId") userId: String): UserPostsPagingDataSource
    }

}