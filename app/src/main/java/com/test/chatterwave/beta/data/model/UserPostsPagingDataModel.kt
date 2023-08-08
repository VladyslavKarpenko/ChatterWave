package com.test.chatterwave.beta.data.model

data class UserPostsPagingDataModel(

    val data: List<UserPostDataModel>,
    val totalPages: Int,
    val totalPassengers: Int

)
