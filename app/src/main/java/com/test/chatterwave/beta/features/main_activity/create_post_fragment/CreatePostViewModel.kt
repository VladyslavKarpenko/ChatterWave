package com.test.chatterwave.beta.features.main_activity.create_post_fragment

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.CreatePostDomainModel
import com.test.chatterwave.beta.domain.usecase.CreatePostUseCase
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

class CreatePostViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    private val createPostUseCase: CreatePostUseCase,
    @ApplicationContext private val mContext: Context
    ) : ViewModel() {

    @AssistedFactory
    interface Factory :
        BaseForAssistedSavedStateViewModelFactory<CreatePostViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): CreatePostViewModel
    }

    private val _createPostFragmentState = MutableStateFlow<UIResponseState>(UIResponseState.Empty)
    var createPostFragmentState = _createPostFragmentState.asStateFlow()

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    fun onCancelButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigatePreviousScreen)
        }
    }

    fun onShareButtonClicked(newPost: CreatePostDomainModel) {
        _createPostFragmentState.value = UIResponseState.Loading
        viewModelScope.launch {
            val result = createPostUseCase.execute(newPost = newPost)
            when(result){
                is NetworkResult.Error -> _createPostFragmentState.emit(UIResponseState.Error(errorMessage = mContext.resources!!.getString(result.message!!)))
                is NetworkResult.Success -> {
                    _createPostFragmentState.emit(UIResponseState.Empty)
                    _onButtonClickEvent.emit(NavigationEvent.NavigateNextScreen)
                }
                else -> {}
            }
        }
    }
}