package com.test.chatterwave.beta.domain.model


import com.google.gson.annotations.SerializedName

data class UserSignInDomainModel(
    @SerializedName("dateOfBirth")
    var dateOfBirth: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("phoneNumber")
    var phoneNumber: String,
    @SerializedName("fullName")
    var fullName: String,
    @SerializedName("nickname")
    var nickname: String,
    @SerializedName("password")
    var password: String
)