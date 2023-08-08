package com.test.chatterwave.beta.features.auth_activity.sign_up.add_bio_fragment

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.UpdateUserDomainModel
import com.test.chatterwave.beta.domain.usecase.UpdateUserUseCase
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import com.test.chatterwave.beta.utils.UIResponseState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddBioViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    @ApplicationContext private val mContext: Context,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<AddBioViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): AddBioViewModel
    }

    private val _bioFragmentState = MutableStateFlow<UIResponseState>(UIResponseState.Empty)
    var bioFragmentState = _bioFragmentState.asStateFlow()

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    fun nextButtonClicked(updateUserDomainModel: UpdateUserDomainModel) {
        _bioFragmentState.value = UIResponseState.Loading
        viewModelScope.launch {
            val result = updateUserUseCase.execute(userUpdates = updateUserDomainModel)
            when(result){
                is NetworkResult.Error -> _bioFragmentState.emit(UIResponseState.Error(errorMessage = mContext.resources!!.getString(result.message!!)))
                is NetworkResult.Success -> {
                    _bioFragmentState.emit(UIResponseState.Empty)
                    _onButtonClickEvent.emit(NavigationEvent.NavigateNextScreen)
                }
                else -> {}
            }
        }
    }

    fun previousButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigatePreviousScreen)
        }
    }

}