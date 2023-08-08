package com.test.chatterwave.beta.utils

import com.test.chatterwave.beta.data.model.ChangePasswordDataModel
import com.test.chatterwave.beta.data.model.ChangePasswordDataResponseModel
import com.test.chatterwave.beta.data.model.ConfirmCodeErrorDataResponseModel
import com.test.chatterwave.beta.data.model.CreatePostDataModel
import com.test.chatterwave.beta.data.model.CreatePostSourceDataModel
import com.test.chatterwave.beta.data.model.LoginWithEmail
import com.test.chatterwave.beta.data.model.LoginWithNumber
import com.test.chatterwave.beta.data.model.NicknameResponseModel
import com.test.chatterwave.beta.data.model.PhoneEmailResponseModel
import com.test.chatterwave.beta.data.model.PostLikesCountDataModel
import com.test.chatterwave.beta.data.model.SendCodeDataModel
import com.test.chatterwave.beta.data.model.TokenResponse
import com.test.chatterwave.beta.data.model.UpdateUserDataModel
import com.test.chatterwave.beta.data.model.UserCountsDataModel
import com.test.chatterwave.beta.data.model.UserDataModel
import com.test.chatterwave.beta.data.model.UserPostDataModel
import com.test.chatterwave.beta.data.model.UserSignInDataModel
import com.test.chatterwave.beta.data.model.ValidateCodeDataModel
import com.test.chatterwave.beta.domain.model.ChangePasswordDomainModel
import com.test.chatterwave.beta.domain.model.ChangePasswordDomainResponseModel
import com.test.chatterwave.beta.domain.model.ConfirmCodeErrorDomainResponseModel
import com.test.chatterwave.beta.domain.model.CreatePostDomainModel
import com.test.chatterwave.beta.domain.model.CreatePostSourceDomainModel
import com.test.chatterwave.beta.domain.model.LoginWithEmailDomainModel
import com.test.chatterwave.beta.domain.model.LoginWithPhoneDomainModel
import com.test.chatterwave.beta.domain.model.NicknameDomainResponseModel
import com.test.chatterwave.beta.domain.model.PhoneEmailDomainResponseModel
import com.test.chatterwave.beta.domain.model.PostLikesCountDomainModel
import com.test.chatterwave.beta.domain.model.SendCodeDomainModel
import com.test.chatterwave.beta.domain.model.TokenDomainResponse
import com.test.chatterwave.beta.domain.model.UpdateUserDomainModel
import com.test.chatterwave.beta.domain.model.UserCountsDomainModel
import com.test.chatterwave.beta.domain.model.UserDomainModel
import com.test.chatterwave.beta.domain.model.UserPostDomainModel
import com.test.chatterwave.beta.domain.model.UserSignInDomainModel
import com.test.chatterwave.beta.domain.model.ValidateCodeDomainModel

fun PhoneEmailResponseModel.fromDataToDomainMapper(): PhoneEmailDomainResponseModel {
    return PhoneEmailDomainResponseModel(
        exists = this.exists ?: false
    )
}

fun NicknameResponseModel.fromDataToDomainMapper(): NicknameDomainResponseModel {
    return NicknameDomainResponseModel(
        exists = this.exists ?: false
    )
}

fun LoginWithPhoneDomainModel.fromDataToDomainMapper(): LoginWithNumber {
    return LoginWithNumber(
        phoneNumber = this.phoneNumber,
        password = this.password
    )
}

fun LoginWithEmailDomainModel.fromDataToDomainMapper(): LoginWithEmail {
    return LoginWithEmail(
        email = this.email,
        password = this.password
    )
}

fun TokenResponse.fromDataToDomainMapper(): TokenDomainResponse {
    return TokenDomainResponse(
        accessToken = this.accessToken ?: com.test.chatterwave.beta.utils.EMPTY_STRING,
        refreshToken = this.refreshToken ?: com.test.chatterwave.beta.utils.EMPTY_STRING
    )
}

fun UserSignInDomainModel.fromDataToDomainMapper(): UserSignInDataModel {
    return UserSignInDataModel(
        dateOfBirth = this.dateOfBirth,
        email = this.email.ifEmpty { null },
        phoneNumber = this.phoneNumber.ifEmpty { null },
        fullName = this.fullName,
        nickname = this.nickname,
        password = this.password
    )
}

fun SendCodeDataModel.fromDataToDomainMapper(): SendCodeDomainModel {
    return SendCodeDomainModel(
        email = this.email ?: EMPTY_STRING,
        phoneNumber = this.phoneNumber ?: EMPTY_STRING
    )
}

fun ConfirmCodeErrorDataResponseModel.fromDataToDomainMapper(): ConfirmCodeErrorDomainResponseModel {
    return ConfirmCodeErrorDomainResponseModel(
        error = this.error ?: EMPTY_STRING
    )
}

fun ValidateCodeDataModel.fromDataToDomainMapper() : ValidateCodeDomainModel {
    return ValidateCodeDomainModel(
        code = this.code ?: EMPTY_STRING,
        phoneNumber = this.phoneNumber ?: EMPTY_STRING,
        email = this.email ?: EMPTY_STRING
    )
}

fun UpdateUserDomainModel.fromDomainToDataMapper(): UpdateUserDataModel {
    return UpdateUserDataModel(
        bio = this.bio.ifEmpty { null },
        city = this.city.ifEmpty { null },
        fullName = this.fullName.ifEmpty { null },
        nickname = this.nickname.ifEmpty { null },
        photo = this.photo.ifEmpty { null }
    )
}

fun UserDataModel.fromDataToDomainMapper(): UserDomainModel {
    return UserDomainModel(

         bio  = this.bio ?: com.test.chatterwave.beta.utils.EMPTY_STRING,
         city  = this.city ?: com.test.chatterwave.beta.utils.EMPTY_STRING,
         userCountsDomainModel = this.userCountsDataModel.fromDataToDomain(),
         createdAt  = this.createdAt ?: com.test.chatterwave.beta.utils.EMPTY_STRING,
         dateOfBirth  = this.dateOfBirth ?: com.test.chatterwave.beta.utils.EMPTY_STRING,
         email  = this.email ?: com.test.chatterwave.beta.utils.EMPTY_STRING,
         followed  = this.followed ?: false,
         fullName  = this.fullName ?: com.test.chatterwave.beta.utils.EMPTY_STRING,
         id  = this.id ?: com.test.chatterwave.beta.utils.EMPTY_STRING,
         nickname  = this.nickname ?: com.test.chatterwave.beta.utils.EMPTY_STRING,
         phoneNumber  = this.phoneNumber ?: com.test.chatterwave.beta.utils.EMPTY_STRING,
         photo  = this.photo ?: com.test.chatterwave.beta.utils.EMPTY_STRING,
         updatedAt = this.updatedAt ?: com.test.chatterwave.beta.utils.EMPTY_STRING
    )
}

fun UserCountsDataModel?.fromDataToDomain(): UserCountsDomainModel {
    return  UserCountsDomainModel(
        this?.followers.toString(),
        this?.following.toString(),
        this?.posts.toString()
    )
}

fun ChangePasswordDomainModel.fromDomainToDataMapper(): ChangePasswordDataModel {
    return ChangePasswordDataModel(
        email = this.email.ifEmpty { null },
        password = this.password.ifEmpty { null },
        phoneNumber = this.phoneNumber.ifEmpty { null }
    )
}

fun ChangePasswordDataResponseModel.fromDataToDomainMapper(): ChangePasswordDomainResponseModel {
    return ChangePasswordDomainResponseModel(
        error = this.error ?: EMPTY_STRING
    )
}

fun <T> NetworkResult<T>.formNetworkToUI(): UIResponseState {
    return when (this) {
        is NetworkResult.Success -> UIResponseState.Success(this.data!!)
        is NetworkResult.Error -> UIResponseState.Error(this.message.toString())
        is NetworkResult.Exception -> UIResponseState.Error(this.exception!!)
        is NetworkResult.Loading -> UIResponseState.Loading
        is NetworkResult.Empty -> UIResponseState.Empty
    }
}

fun UserPostDataModel.fromDataToDomainMapper(): UserPostDomainModel {
    return UserPostDomainModel(
        likesCount = likesCount.fromDataToDomainMapper(),
        createdAt = this.createdAt ?: EMPTY_STRING,
        description = this.description ?: EMPTY_STRING,
        id = this.id ?: EMPTY_STRING,
        liked = this.liked ?: false,
        source = this.source ?: emptyList(),
        updatedAt = this.updatedAt ?: EMPTY_STRING,
        userId = this.userId ?: EMPTY_STRING
    )
}

fun PostLikesCountDataModel?.fromDataToDomainMapper(): PostLikesCountDomainModel {
    return PostLikesCountDomainModel(
        likes = this?.likes ?: 0
    )
}

fun CreatePostDataModel.fromDataToDomainMapper(): CreatePostDomainModel {
    return CreatePostDomainModel(
        description = this.description ?: EMPTY_STRING,
        hashtagsNames = this.hashtagsNames ?: emptyList(),
        createPostSourceDataModel =  this.createPostSourceDataModel.fromDataToDomain(),
    )
}

fun List<CreatePostSourceDataModel>?.fromDataToDomain(): List<CreatePostSourceDomainModel>{
    return this?.map {
        CreatePostSourceDomainModel(
            base64File = it.base64File ?: EMPTY_STRING,
            ext = it.ext ?: "jpg")
    } ?: emptyList()
}

fun CreatePostDomainModel.fromDataToDomainMapper(): CreatePostDataModel {
    return CreatePostDataModel(
        description = this.description,
        hashtagsNames = this.hashtagsNames,
        createPostSourceDataModel =  this.createPostSourceDataModel.fromDomainToData(),
    )
}

fun List<CreatePostSourceDomainModel>.fromDomainToData(): List<CreatePostSourceDataModel>{
    return this.map {
        CreatePostSourceDataModel(
            base64File = it.base64File,
            ext = it.ext
        )
    }
}
