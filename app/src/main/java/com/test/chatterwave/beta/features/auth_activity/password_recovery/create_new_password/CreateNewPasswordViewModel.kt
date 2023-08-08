package com.test.chatterwave.beta.features.auth_activity.password_recovery.create_new_password

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.ChangePasswordDomainModel
import com.test.chatterwave.beta.domain.model.ChangePasswordDomainResponseModel
import com.test.chatterwave.beta.domain.usecase.ChangeUserPasswordUseCase
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.PasswordValidation
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ONE
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import com.test.chatterwave.beta.utils.passwordDigitalRegex
import com.test.chatterwave.beta.utils.passwordLowerCaseRegex
import com.test.chatterwave.beta.utils.passwordMinLengthRegex
import com.test.chatterwave.beta.utils.passwordOnlyLatinRegex
import com.test.chatterwave.beta.utils.passwordRegex
import com.test.chatterwave.beta.utils.passwordSpecialCharacterRegex
import com.test.chatterwave.beta.utils.passwordUpperCaseRegex
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CreateNewPasswordViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    private val changeUserPasswordUseCase: ChangeUserPasswordUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<CreateNewPasswordViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): CreateNewPasswordViewModel
    }

    private val _userPassword = MutableSharedFlow<String>(SHARED_FLOW_REPLY_ONE)
    var userPassword = _userPassword.asSharedFlow()

    private val _passwordLocalValidation =
        MutableSharedFlow<PasswordValidation>(SHARED_FLOW_REPLY_ONE)
    var passwordLocalValidation = _passwordLocalValidation.asSharedFlow()

    private val _passwordConfirmLocalValidation =
        MutableSharedFlow<Boolean>(SHARED_FLOW_REPLY_ONE)
    var passwordConfirmLocalValidation = _passwordConfirmLocalValidation.asSharedFlow()

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    private val _passwordChange = MutableSharedFlow<NetworkResult<ChangePasswordDomainResponseModel>>(
        SHARED_FLOW_REPLY_ONE
    )
    var passwordChange = _passwordChange.asSharedFlow()

    fun changeUserPassword(password: String, phone: String, email: String) {
        viewModelScope.launch {
            _passwordChange.emit(NetworkResult.Loading())
            val result = changeUserPasswordUseCase.execute(
                ChangePasswordDomainModel(password = password, phoneNumber = phone, email = email)
            )
            _passwordChange.emit(result)
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

    fun checkPasswordValidLocal(password: String) {
        viewModelScope.launch {
            _passwordLocalValidation.emit(
                PasswordValidation(
                    minCharacterPassed = isPassContainEightCharacters(password = password),
                    oneLowerCasePassed = isPassContainOneLowerCase(password = password),
                    oneUpperCasePassed = isPassContainOneUpperCase(password = password),
                    oneDigitPassed = isPassContainOneDigit(password = password),
                    oneSpecialPassed = isPassContainOneSpecial(password = password),
                    empty = isPassIsEmpty(password = password),
                    fullRegexPassed = isPassValid(password = password),
                    onlyLatin = isPassOnlyLatin(password = password)
                )
            )
        }
    }

    private fun isPassContainEightCharacters(password: String): Boolean {
        return password.contains(passwordMinLengthRegex)
    }

    private fun isPassContainOneLowerCase(password: String): Boolean {
        return password.contains(passwordLowerCaseRegex)
    }

    private fun isPassContainOneUpperCase(password: String): Boolean {
        return password.contains(passwordUpperCaseRegex)
    }

    private fun isPassContainOneDigit(password: String): Boolean {
        return password.contains(passwordDigitalRegex)
    }

    private fun isPassContainOneSpecial(password: String): Boolean {
        return password.contains(passwordSpecialCharacterRegex)
    }

    private fun isPassValid(password: String): Boolean {
        return password.contains(passwordRegex)
    }

    private fun isPassIsEmpty(password: String): Boolean {
        return password.isEmpty()
    }

    private fun isPassOnlyLatin(password: String): Boolean{
        return password.contains(passwordOnlyLatinRegex)
    }

    fun checkPasswordConfirmLocal(password: String, confirmPassword: String) {
        viewModelScope.launch {
            if (confirmPassword.isNotEmpty() && password.isNotEmpty()) {
                _passwordConfirmLocalValidation.emit(true)
            } else _passwordConfirmLocalValidation.emit(false)
        }

    }
}