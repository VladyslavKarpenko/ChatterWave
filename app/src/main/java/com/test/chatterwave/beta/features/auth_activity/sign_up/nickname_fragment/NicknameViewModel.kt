package com.test.chatterwave.beta.features.auth_activity.sign_up.nickname_fragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.NicknameDomainResponseModel
import com.test.chatterwave.beta.domain.usecase.CheckUserNicknameUseCase
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.PhoneEmailLocalValidationResult
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ONE
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import com.test.chatterwave.beta.utils.nicknameRegex
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class NicknameViewModel  @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    private val checkUserNicknameUseCase: CheckUserNicknameUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<NicknameViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): NicknameViewModel
    }

    private val _nicknameValidation =
        MutableSharedFlow<NetworkResult<NicknameDomainResponseModel>>(SHARED_FLOW_REPLY_ONE)
    var nicknameValidation = _nicknameValidation.asSharedFlow()

    private val _nicknameValidationLocal =
        MutableSharedFlow<PhoneEmailLocalValidationResult>(SHARED_FLOW_REPLY_ONE)
    var nicknameValidationLocal = _nicknameValidationLocal.asSharedFlow()

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()


    fun checkNicknameValidLocal(nickname: String) {
        viewModelScope.launch {
            if (nickname.isEmpty()) {
                _nicknameValidationLocal.emit(PhoneEmailLocalValidationResult.Empty())
            } else if (nickname.length < NICKNAME_MIN_LENGTH) {
                _nicknameValidationLocal.emit(PhoneEmailLocalValidationResult.Error(message = R.string.nickname_min_length))
            } else if(nickname.length > NICKNAME_MAX_LENGTH){
                _nicknameValidationLocal.emit(PhoneEmailLocalValidationResult.Error(message = R.string.nickname_max_length))
            } else if (nickname.contains(nicknameRegex)){
                _nicknameValidationLocal.emit(PhoneEmailLocalValidationResult.Success())
            }else {
                _nicknameValidationLocal.emit(PhoneEmailLocalValidationResult.Error(message = R.string.nickname_local_validation_error))
            }
        }
    }

    fun checkNicknameValid(nickname: String) {
        viewModelScope.launch {
            _nicknameValidation.emit(NetworkResult.Loading())
            val result = checkUserNicknameUseCase.execute(nickname)
            _nicknameValidation.emit(result)
        }
    }

    fun nextButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigateNextScreen)
        }
    }

    fun previousButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigatePreviousScreen)
        }
    }

    companion object{
        private const val NICKNAME_MIN_LENGTH = 8
        private const val NICKNAME_MAX_LENGTH = 20
    }

}