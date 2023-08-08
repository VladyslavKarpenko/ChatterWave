package com.test.chatterwave.beta.data.repository

import com.chi.interngram.echo.R
import com.test.chatterwave.beta.data.source.remote.retrofit.api.LogInService
import com.test.chatterwave.beta.domain.repository.LoginRepository
import com.google.gson.Gson
import com.test.chatterwave.beta.domain.model.ChangePasswordDomainModel
import com.test.chatterwave.beta.domain.model.ChangePasswordDomainResponseModel
import com.test.chatterwave.beta.domain.model.LoginWithEmailDomainModel
import com.test.chatterwave.beta.domain.model.LoginWithPhoneDomainModel
import com.test.chatterwave.beta.domain.model.TokenDomainResponse
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.STATUS_CODE_200
import com.test.chatterwave.beta.utils.STATUS_CODE_400
import com.test.chatterwave.beta.utils.STATUS_CODE_401
import com.test.chatterwave.beta.utils.fromDataToDomainMapper
import com.test.chatterwave.beta.utils.fromDomainToDataMapper
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val service: LogInService) : LoginRepository {

    override suspend fun loginWithEmail(loginData: LoginWithEmailDomainModel): NetworkResult<TokenDomainResponse> {
        return try {
            val result = service.signInWithEmail(loginData.fromDataToDomainMapper())
            when (result.code()) {
                STATUS_CODE_200 -> NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                STATUS_CODE_400 -> NetworkResult.Error(message = result.code())
                STATUS_CODE_401 -> NetworkResult.Error(message = result.code())
                else -> NetworkResult.Error(message = result.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun loginWithNumber(loginData: LoginWithPhoneDomainModel): NetworkResult<TokenDomainResponse> {
        return try {
            val result = service.signInWithNumber(loginData.fromDataToDomainMapper())
            when (result.code()) {
                STATUS_CODE_200 -> NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                STATUS_CODE_400 -> NetworkResult.Error(message = result.code())
                STATUS_CODE_401 -> NetworkResult.Error(message = result.code())
                else -> NetworkResult.Error(message = result.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(message = R.string.server_error)
        }
    }

    override suspend fun changeUserPassword(changePasswordDomainModel: ChangePasswordDomainModel): NetworkResult<ChangePasswordDomainResponseModel> {
        return try {
            val result = service.changeUserPassword(changePasswordDomainModel.fromDomainToDataMapper())
            val errorResponse = Gson().fromJson(result.errorBody()?.string(), ChangePasswordDomainResponseModel::class.java)
            when (result.code()) {
                STATUS_CODE_200 -> NetworkResult.Success(data = result.body()?.fromDataToDomainMapper())
                STATUS_CODE_400 -> NetworkResult.Exception(exception = errorResponse.error)
                STATUS_CODE_401 -> NetworkResult.Exception(exception = errorResponse.error)
                else -> NetworkResult.Exception(exception = errorResponse.error)
            }
        } catch (e: Exception) {
            NetworkResult.Error(message = R.string.server_error)
        }
    }

}