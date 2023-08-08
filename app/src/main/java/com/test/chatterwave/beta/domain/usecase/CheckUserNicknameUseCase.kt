package com.test.chatterwave.beta.domain.usecase

import com.test.chatterwave.beta.domain.model.NicknameDomainResponseModel
import com.test.chatterwave.beta.domain.repository.SignUpRepository
import com.test.chatterwave.beta.utils.NetworkResult
import javax.inject.Inject

class CheckUserNicknameUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {

    suspend fun execute(nickname: String) : NetworkResult<NicknameDomainResponseModel> {
        return signUpRepository.checkUserNickname(nickname)
    }

}