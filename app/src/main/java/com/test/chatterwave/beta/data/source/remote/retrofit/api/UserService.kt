package com.test.chatterwave.beta.data.source.remote.retrofit.api

import com.chi.interngram.echo.data.model.*
import com.test.chatterwave.beta.data.model.UpdateUserDataModel
import com.test.chatterwave.beta.data.model.UserDataModel
import com.test.chatterwave.beta.data.model.UserPostDataModel
import com.test.chatterwave.beta.data.model.CreatePostDataModel
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @PATCH("users")
    suspend fun updateUser(
        @Body userUpdates: UpdateUserDataModel
    ): Response<UserDataModel>

    @GET("users/user/currentUser")
    suspend fun getCurrentUser(): Response<UserDataModel>

    @GET("posts/user/{userId}")
    suspend fun getAllPostsOfUser(
        @Path("userId") userId: String,
        @Query("page") page: String = "1",
        @Query("take") count: String = "10"
    ): Response<List<UserPostDataModel>>

    @POST("posts")
    suspend fun createPost(
        @Body newPost: CreatePostDataModel
    ): Response<UserPostDataModel>

    @PATCH("users/avatar")
    suspend fun deleteUserAvatar(): Response<UserPostDataModel>

}