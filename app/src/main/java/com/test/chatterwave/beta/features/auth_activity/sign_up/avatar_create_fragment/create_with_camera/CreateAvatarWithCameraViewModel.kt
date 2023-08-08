package com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_create_fragment.create_with_camera

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import com.otaliastudios.cameraview.controls.Facing
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateAvatarWithCameraViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<CreateAvatarWithCameraViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): CreateAvatarWithCameraViewModel
    }

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    private val _cameraFacing = MutableStateFlow<Facing>(Facing.FRONT)
    var cameraFacing = _cameraFacing.asStateFlow()

    fun changeCameraFacing(){
        viewModelScope.launch{
            when(_cameraFacing.value ){
               Facing.FRONT -> _cameraFacing.emit(Facing.BACK)
               Facing.BACK -> _cameraFacing.emit(Facing.FRONT)
            }
        }
    }

    fun onNextButtonClicked() {
        viewModelScope.launch{
            _onButtonClickEvent.emit(NavigationEvent.NavigateNextScreen)
        }
    }

    fun onCancelButtonClicked() {
        viewModelScope.launch{
            _onButtonClickEvent.emit(NavigationEvent.NavigatePreviousScreen)
        }
    }
}