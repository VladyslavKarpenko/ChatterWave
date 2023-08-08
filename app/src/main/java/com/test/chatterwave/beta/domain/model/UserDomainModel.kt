package com.test.chatterwave.beta.domain.model


import com.test.chatterwave.beta.utils.EMPTY_STRING
import com.google.gson.annotations.SerializedName

data class UserDomainModel(
    @SerializedName("bio")
    val bio: String = EMPTY_STRING,
    @SerializedName("city")
    val city: String = EMPTY_STRING,
    @SerializedName("_count")
    val userCountsDomainModel: UserCountsDomainModel = UserCountsDomainModel("0","0","0"),
    @SerializedName("createdAt")
    val createdAt: String = EMPTY_STRING,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String = EMPTY_STRING,
    @SerializedName("email")
    val email: String = EMPTY_STRING,
    @SerializedName("followed")
    val followed: Boolean = false,
    @SerializedName("fullName")
    val fullName: String = EMPTY_STRING,
    @SerializedName("id")
    val id: String = EMPTY_STRING,
    @SerializedName("nickname")
    val nickname: String = EMPTY_STRING,
    @SerializedName("phoneNumber")
    val phoneNumber: String = EMPTY_STRING,
    @SerializedName("photo")
    val photo: String = EMPTY_STRING,
    @SerializedName("updatedAt")
    val updatedAt: String = EMPTY_STRING
)