package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.ChangePasswordDomainModel
import com.test.chatterwave.beta.domain.model.ChangePasswordDomainResponseModel
import com.test.chatterwave.beta.domain.repository.LoginRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class ChangeUserPasswordUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend fun execute(changePasswordDomainModel: ChangePasswordDomainModel): NetworkResult<ChangePasswordDomainResponseModel> {
        return loginRepository.changeUserPassword(changePasswordDomainModel)
    }

}