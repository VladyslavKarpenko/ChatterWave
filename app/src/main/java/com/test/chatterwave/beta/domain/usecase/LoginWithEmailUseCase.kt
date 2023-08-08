package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.LoginWithEmailDomainModel
import com.test.chatterwave.beta.domain.repository.LoginRepository
import javax.inject.Inject

class LoginWithEmailUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend fun execute(loginData: LoginWithEmailDomainModel) = repository.loginWithEmail(loginData)
}