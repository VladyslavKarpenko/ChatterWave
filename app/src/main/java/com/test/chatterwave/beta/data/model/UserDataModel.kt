package com.test.chatterwave.beta.data.model


import com.google.gson.annotations.SerializedName

data class UserDataModel(
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("_count")
    val userCountsDataModel: UserCountsDataModel?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("followed")
    val followed: Boolean?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)