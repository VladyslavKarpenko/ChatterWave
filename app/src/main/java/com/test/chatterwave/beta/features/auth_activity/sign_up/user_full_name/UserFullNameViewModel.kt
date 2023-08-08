package com.test.chatterwave.beta.features.auth_activity.sign_up.user_full_name

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.PhoneEmailLocalValidationResult
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ONE
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import com.test.chatterwave.beta.utils.fullNameRegex
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class UserFullNameViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<UserFullNameViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): UserFullNameViewModel
    }

    fun getStringExtra(): String? {
        return handle["key"]
    }

    private val _userFullName = MutableSharedFlow<String>(1)
    var userFullName = _userFullName.asSharedFlow()

    private val _fullNameLocalValidation =
        MutableSharedFlow<PhoneEmailLocalValidationResult>(SHARED_FLOW_REPLY_ONE)
    var fullNameLocalValidation = _fullNameLocalValidation.asSharedFlow()

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    fun saveUserFullName(fullName: String) {
        viewModelScope.launch {
            _userFullName.emit(fullName)
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

    fun validateUserFullName(fullName: String) {
        with(viewModelScope) {
            if (fullName.isEmpty()) {
                launch { _fullNameLocalValidation.emit(PhoneEmailLocalValidationResult.Empty()) }
            } else if (fullName.length < 2) {
                launch { _fullNameLocalValidation.emit(PhoneEmailLocalValidationResult.Error(message = R.string.full_name_min_length_error)) }
            } else if (fullName.length > 35) {
                launch { _fullNameLocalValidation.emit(PhoneEmailLocalValidationResult.Error(message = R.string.full_name_max_length_error)) }
            } else if (fullName.contains(fullNameRegex)) {
                launch { _fullNameLocalValidation.emit(PhoneEmailLocalValidationResult.Success()) }
            } else {
                launch { _fullNameLocalValidation.emit(PhoneEmailLocalValidationResult.Error(message = R.string.full_name_error)) }
            }
        }

    }

}