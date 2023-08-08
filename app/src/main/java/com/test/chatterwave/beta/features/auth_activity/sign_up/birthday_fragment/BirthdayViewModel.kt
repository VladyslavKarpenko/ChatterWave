package com.test.chatterwave.beta.features.auth_activity.sign_up.birthday_fragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class BirthdayViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<BirthdayViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): BirthdayViewModel
    }

    private val _userBirthday = MutableSharedFlow<Long>(1)
    var userBirthday = _userBirthday.asSharedFlow()

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    fun getStringExtra(): String? {
        return handle["key"]
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

    fun birthdayButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigateBirthdayPecker)
        }
    }
    fun setBaseBirthday(time: Long){
       viewModelScope.launch {
           _userBirthday.emit(time)
       }
    }

    fun saveBirthdayToViewModel(userBirthday: Long?) {
        viewModelScope.launch {
            if (userBirthday != null) {
                _userBirthday.emit(userBirthday)
            }
        }
    }
}