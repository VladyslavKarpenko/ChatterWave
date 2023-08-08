package com.test.chatterwave.beta.features.auth_activity.login_fragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatterwave.beta.data.source.local.preferences.AppPreferences
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.LoginWithEmailDomainModel
import com.test.chatterwave.beta.domain.model.LoginWithPhoneDomainModel
import com.test.chatterwave.beta.domain.model.TokenDomainResponse
import com.test.chatterwave.beta.domain.usecase.LoginWithEmailUseCase
import com.test.chatterwave.beta.domain.usecase.LoginWithNumberUseCase
import com.test.chatterwave.beta.utils.LoginErrorResult
import com.test.chatterwave.beta.utils.LoginEvent
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ONE
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import com.test.chatterwave.beta.utils.STATUS_CODE_400
import com.test.chatterwave.beta.utils.STATUS_CODE_401
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel @AssistedInject constructor(
    private val loginWithEmailUseCase: LoginWithEmailUseCase,
    private val loginWithNumberUseCase: LoginWithNumberUseCase,
    @Assisted private val handle: SavedStateHandle,
    private val sharedPreferences: AppPreferences
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<LoginViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): LoginViewModel
    }

    fun getStringExtra(): String? {
        return handle["key"]
    }

    private val _tokenResponse = MutableSharedFlow<NetworkResult<TokenDomainResponse>>(1)
    var tokenResponse = _tokenResponse.asSharedFlow()

    private val _loginValidationLocal =
        MutableSharedFlow<LoginEvent>(SHARED_FLOW_REPLY_ONE)
    var loginValidationLocal = _loginValidationLocal.asSharedFlow()

    private val _loginErrorResult = MutableSharedFlow<LoginErrorResult>(SHARED_FLOW_REPLY_ONE)
    var loginErrorResult = _loginErrorResult.asSharedFlow()

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    private val _showErrorDialogEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var showErrorDialogEvent = _showErrorDialogEvent.asSharedFlow()

    fun loginWithEmail(loginData: LoginWithEmailDomainModel) =
        viewModelScope.launch {
            _tokenResponse.emit(NetworkResult.Loading())
            val result = loginWithEmailUseCase.execute(loginData)
            _tokenResponse.emit(result)
        }

    fun loginWithNumber(loginData: LoginWithPhoneDomainModel) =
        viewModelScope.launch {
            _tokenResponse.emit(NetworkResult.Loading())
            val result = loginWithNumberUseCase.execute(loginData)
            _tokenResponse.emit(result)
        }

    fun createLoginData(login: String, pass: String) {
        viewModelScope.launch {
            if (login.contains("+")) _loginValidationLocal.emit(LoginEvent.LoginWithPhone(login, pass))
            else _loginValidationLocal.emit(LoginEvent.LoginWithEmail(login, pass))
        }
    }

    fun handleShowError(errorCode: Int){
       viewModelScope.launch {
           when (errorCode) {
               STATUS_CODE_400 -> _loginErrorResult.emit(LoginErrorResult.LoginPhoneEmailError)
               STATUS_CODE_401 -> _loginErrorResult.emit(LoginErrorResult.LoginPasswordError)
               else -> _loginErrorResult.emit(LoginErrorResult.LoginUnknownError)
           }
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

    fun recoveryPasswordButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigationToRecoveryPassword)
        }
    }

    fun createAnAccountButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigationToCreateAnAccount)
        }
    }

    fun loginWithGoogleButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigationToLoginWithGoogle)
        }
    }

    fun loginWithFacebookButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigationToLoginWithFacebook)
        }
    }

    fun showErrorDialog() {
        viewModelScope.launch {
            _showErrorDialogEvent.emit(NavigationEvent.NavigateToDialog)
        }
    }

}