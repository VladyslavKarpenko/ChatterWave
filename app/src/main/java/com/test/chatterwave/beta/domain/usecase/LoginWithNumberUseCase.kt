package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.LoginWithPhoneDomainModel
import com.test.chatterwave.beta.domain.repository.LoginRepository
import javax.inject.Inject

class LoginWithNumberUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend fun execute(loginData: LoginWithPhoneDomainModel) = repository.loginWithNumber(loginData)
}