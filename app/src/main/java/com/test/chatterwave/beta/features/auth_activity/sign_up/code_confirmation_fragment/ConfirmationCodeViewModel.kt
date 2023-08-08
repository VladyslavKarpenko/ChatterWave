package com.test.chatterwave.beta.features.auth_activity.sign_up.code_confirmation_fragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatterwave.beta.data.source.local.preferences.AppPreferences
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.ConfirmCodeErrorDomainResponseModel
import com.test.chatterwave.beta.domain.model.TokenDomainResponse
import com.test.chatterwave.beta.domain.model.UserSignInDomainModel
import com.test.chatterwave.beta.domain.usecase.SignUpUserUseCase
import com.test.chatterwave.beta.domain.usecase.ValidateConfirmCodeByEmailUseCase
import com.test.chatterwave.beta.domain.usecase.ValidateConfirmCodeByPhoneUseCase
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ONE
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ConfirmationCodeViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    private val validateConfirmCodeByEmailUseCase: ValidateConfirmCodeByEmailUseCase,
    private val validateConfirmCodeByPhoneUseCase: ValidateConfirmCodeByPhoneUseCase,
    private val signUpUserUseCase: SignUpUserUseCase,
    private val sharedPreferences: AppPreferences
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<ConfirmationCodeViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): ConfirmationCodeViewModel
    }

    private val _validateConfirmCode =
        MutableSharedFlow<NetworkResult<ConfirmCodeErrorDomainResponseModel>>(
            SHARED_FLOW_REPLY_ONE
        )
    var validateConfirmCode = _validateConfirmCode.asSharedFlow()

    private val _signUpUser = MutableSharedFlow<NetworkResult<TokenDomainResponse>>(
        SHARED_FLOW_REPLY_ONE
    )
    var signUpUser = _signUpUser.asSharedFlow()

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    fun validateConfirmCodeByPhone(code: String, phone: String) {
        viewModelScope.launch {
            val result = validateConfirmCodeByPhoneUseCase.execute(code = code, phone = phone)
            _validateConfirmCode.emit(result)
        }
    }

    fun validateConfirmCodeByEmail(code: String, email: String) {
        viewModelScope.launch {
            val result = validateConfirmCodeByEmailUseCase.execute(code = code, email = email)
            _validateConfirmCode.emit(result)
        }
    }

    fun signUpUser(user: UserSignInDomainModel) {
        viewModelScope.launch {
            val result = signUpUserUseCase.execute(user = user)
            _signUpUser.emit(result)
        }
    }

    fun saveTokens(tokenData: TokenDomainResponse) {
        viewModelScope.launch {
            with(sharedPreferences) {
                setAccessToken(tokenData.accessToken)
                setRefreshToken(tokenData.refreshToken)
            }
            _onButtonClickEvent.emit(NavigationEvent.NavigateNextScreen)
        }
    }

    fun onBackButtonClicked(){
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigatePreviousScreen)
        }
    }

    fun onChangeEmailOrPhoneClicked(){
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigationToCreateAnAccount)
        }
    }
}