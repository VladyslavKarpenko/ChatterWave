package com.test.chatterwave.beta.data.repository

import android.content.Context
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.data.source.remote.retrofit.api.SignUpService
import com.test.chatterwave.beta.domain.repository.SignUpRepository
import com.test.chatterwave.beta.data.model.SendCodeDataModel
import com.test.chatterwave.beta.data.model.UserEmailDataModel
import com.test.chatterwave.beta.data.model.UserNicknameDataModel
import com.test.chatterwave.beta.data.model.UserPhoneDataModel
import com.test.chatterwave.beta.data.model.ValidateCodeDataModel
import com.test.chatterwave.beta.domain.model.ConfirmCodeErrorDomainResponseModel
import com.test.chatterwave.beta.domain.model.NicknameDomainResponseModel
import com.test.chatterwave.beta.domain.model.PhoneEmailDomainResponseModel
import com.test.chatterwave.beta.domain.model.TokenDomainResponse
import com.test.chatterwave.beta.domain.model.UserSignInDomainModel
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.STATUS_CODE_200
import com.test.chatterwave.beta.utils.STATUS_CODE_201
import com.test.chatterwave.beta.utils.STATUS_CODE_400
import com.test.chatterwave.beta.utils.STATUS_CODE_404
import com.test.chatterwave.beta.utils.fromDataToDomainMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val signUpService: SignUpService,
    @ApplicationContext val context: Context
) :
    SignUpRepository {

    override suspend fun checkUserEmail(email: String): NetworkResult<PhoneEmailDomainResponseModel> {
        try {
            val result = signUpService.getUserEmailValidation(UserEmailDataModel(email))
            return when (result.code()) {
                STATUS_CODE_404 -> {
                    NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                }
                STATUS_CODE_200 -> {
                    NetworkResult.Error(
                        message = R.string.this_email_registered
                    )
                }
                else -> {
                    NetworkResult.Error(message = R.string.email_invalid_format)
                }
            }
        } catch (e: Exception) {
            return NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun checkUserPhone(phone: String): NetworkResult<PhoneEmailDomainResponseModel> {
        try {
            val result = signUpService.getUserPhoneValidation(UserPhoneDataModel(phone))
            return when (result.code()) {
                STATUS_CODE_404 -> {
                    NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                }
                STATUS_CODE_200 -> {
                    NetworkResult.Error(
                        message = R.string.phone_number_error
                    )
                }
                else -> {
                    NetworkResult.Error(message = R.string.phone_number_invalid_format)
                }
            }
        } catch (e: Exception) {
            return NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun checkUserNickname(nickname: String): NetworkResult<NicknameDomainResponseModel> {
        try {
            val result = signUpService.getUserNicknameValidation(UserNicknameDataModel(nickname))
            return when(result.code()){
                STATUS_CODE_404 -> {
                    NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                }
                STATUS_CODE_200 -> {
                    NetworkResult.Error(
                        message = R.string.this_nickname_registered
                    )
                }else -> {
                    NetworkResult.Error(message = R.string.this_nickname_registered)
                }
            }
        } catch (e: Exception) {
            return NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun createConfirmCodeByPhone(phone: String): NetworkResult<ConfirmCodeErrorDomainResponseModel> {
        return try {
            val result = signUpService.createConfirmCode(SendCodeDataModel(phoneNumber = phone))
            when(result.code()){
                STATUS_CODE_200 -> NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                STATUS_CODE_400 -> NetworkResult.Error(R.string.invalid_code)
                else -> NetworkResult.Error(R.string.invalid_code)
            }
        }catch (e: Exception){
            NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun createConfirmCodeByEmail(email: String): NetworkResult<ConfirmCodeErrorDomainResponseModel> {
        return try {
            val result = signUpService.createConfirmCode(SendCodeDataModel(email = email))
            when(result.code()){
                STATUS_CODE_200 -> NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                STATUS_CODE_400 -> NetworkResult.Error(R.string.invalid_code)
                else -> NetworkResult.Error(R.string.invalid_code)
            }
        }catch (e: Exception){
            NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun confirmCodeVerificationByEmail(code: String, email: String): NetworkResult<ConfirmCodeErrorDomainResponseModel> {
        return try {
            val result = signUpService.validateConfirmCode(ValidateCodeDataModel(code = code, email = email))
            when(result.code()){
                STATUS_CODE_200 -> NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                STATUS_CODE_400 -> NetworkResult.Error(R.string.invalid_code)
                else -> NetworkResult.Error(R.string.invalid_code)
            }
        }catch (e: Exception){
            NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun confirmCodeVerificationByPhone(code: String, phone: String): NetworkResult<ConfirmCodeErrorDomainResponseModel> {
        return try {
            val result = signUpService.validateConfirmCode(ValidateCodeDataModel(code = code, phoneNumber = phone))
            when(result.code()){
                STATUS_CODE_200 -> NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                STATUS_CODE_400 -> NetworkResult.Error(R.string.invalid_code)
                else -> NetworkResult.Error(R.string.invalid_code)
            }
        }catch (e: Exception){
            NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun signUpUser(user: UserSignInDomainModel): NetworkResult<TokenDomainResponse> {
        return try {
            val result = signUpService.signUpUser(user.fromDataToDomainMapper())
            when(result.code()){
                STATUS_CODE_201 -> NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                STATUS_CODE_400 -> NetworkResult.Error(message = result.code())
                else -> NetworkResult.Error(message = result.code())
            }
        }catch (e: Exception){
            NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun checkUserPhoneExists(phone: String): NetworkResult<PhoneEmailDomainResponseModel> {
        try {
            val result = signUpService.getUserPhoneValidation(UserPhoneDataModel(phone))
            return when (result.code()) {
                STATUS_CODE_200 -> {
                    NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                }
                STATUS_CODE_404 -> {
                    NetworkResult.Error(
                        message = R.string.account_does_not_exist
                    )
                }
                else -> {
                    NetworkResult.Error(message = R.string.phone_number_invalid_format)
                }
            }
        } catch (e: Exception) {
            return NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun checkUserEmailExists(email: String): NetworkResult<PhoneEmailDomainResponseModel> {
        try {
            val result = signUpService.getUserEmailValidation(UserEmailDataModel(email))
            return when (result.code()) {
                STATUS_CODE_200 -> {
                    NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                }
                STATUS_CODE_404 -> {
                    NetworkResult.Error(
                        message = R.string.account_does_not_exist
                    )
                }
                else -> {
                    NetworkResult.Error(message = R.string.email_invalid_format)
                }
            }
        } catch (e: Exception) {
            return NetworkResult.Error(message = R.string.server_error)
        }
    }
}