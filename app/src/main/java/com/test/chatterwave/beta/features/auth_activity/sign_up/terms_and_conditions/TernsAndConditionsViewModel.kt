package com.test.chatterwave.beta.features.auth_activity.sign_up.terms_and_conditions

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

class TernsAndConditionsViewModel  @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<TernsAndConditionsViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): TernsAndConditionsViewModel
    }

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    fun backToSignUpButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigatePreviousScreen)
        }
    }

}