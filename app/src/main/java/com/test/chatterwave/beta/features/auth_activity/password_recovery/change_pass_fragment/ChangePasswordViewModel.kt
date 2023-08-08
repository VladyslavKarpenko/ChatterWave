package com.test.chatterwave.beta.features.auth_activity.password_recovery.change_pass_fragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.PhoneEmailDomainResponseModel
import com.test.chatterwave.beta.domain.usecase.CheckUserEmailExistsUseCase
import com.test.chatterwave.beta.domain.usecase.CheckUserPhoneExistsUseCase
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.PhoneEmailLocalValidationResult
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ONE
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import com.test.chatterwave.beta.utils.mailRegex
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ChangePasswordViewModel @AssistedInject constructor(
    private val checkUserEmailExistsUseCase: CheckUserEmailExistsUseCase,
    private val checkUserPhoneExistsUseCase: CheckUserPhoneExistsUseCase,
    @Assisted private val handle: SavedStateHandle,
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<ChangePasswordViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): ChangePasswordViewModel
    }

    fun getStringExtra(): String? {
        return handle["key"]
    }

    private val _phoneEmailValidation =
        MutableSharedFlow<NetworkResult<PhoneEmailDomainResponseModel>>(SHARED_FLOW_REPLY_ONE)
    var phoneEmailValidation = _phoneEmailValidation.asSharedFlow()

    private val _phoneEmailValidationLocal =
        MutableSharedFlow<PhoneEmailLocalValidationResult>(SHARED_FLOW_REPLY_ONE)
    var phoneEmailValidationLocal = _phoneEmailValidationLocal.asSharedFlow()

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    fun checkUserEmailValid(email: String) {
        viewModelScope.launch {
            _phoneEmailValidation.emit(NetworkResult.Loading())
            val result = checkUserEmailExistsUseCase.execute(email)
            _phoneEmailValidation.emit(result)
        }
    }

    fun checkUserPhoneValid(phone: String) {
        viewModelScope.launch {
            _phoneEmailValidation.emit(NetworkResult.Loading())
            val result = checkUserPhoneExistsUseCase.execute(phone)
            _phoneEmailValidation.emit(result)
        }
    }

    fun checkUserEmailValidLocal(email: String) {
        viewModelScope.launch {
            if (email.isEmpty()) {
                _phoneEmailValidationLocal.emit(PhoneEmailLocalValidationResult.Empty())
            } else if (email.matches(mailRegex)) {
                _phoneEmailValidationLocal.emit(PhoneEmailLocalValidationResult.Success())
            } else {
                _phoneEmailValidationLocal.emit(PhoneEmailLocalValidationResult.Error(message = R.string.email_invalid_format))
            }
        }
    }

    fun checkUserPhoneValidLocal(phone: String) {
        viewModelScope.launch {
            if (phone.isEmpty()) {
                _phoneEmailValidationLocal.emit(PhoneEmailLocalValidationResult.Empty())
            } else if (phone.length == PHONE_LENGTH) {
                _phoneEmailValidationLocal.emit(PhoneEmailLocalValidationResult.Success())
            } else {
                _phoneEmailValidationLocal.emit(PhoneEmailLocalValidationResult.Error(message = R.string.phone_number_error))
            }
        }
    }

    fun nextButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigateNextScreen)
        }
    }

    fun arrowBackClicked(){
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigatePreviousScreen)
        }
    }

    fun logInButtonClicked(){
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigatePreviousScreen)
        }
    }

    companion object {
        private const val PHONE_LENGTH = 9
    }
}