package com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_fragment

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

class AvatarViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<AvatarViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): AvatarViewModel
    }

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    fun onAddButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigateToDialog)
        }
    }

    fun onChangeButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigateToDialog)
        }
    }

    fun onSkipButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigateFeedScreen)
        }
    }

    fun onNextButtonClicked() {
        viewModelScope.launch{
            _onButtonClickEvent.emit(NavigationEvent.NavigateNextScreen)
        }
    }

    fun onDeleteButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigateToDeleteDialog)
        }
    }

}