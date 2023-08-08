package com.test.chatterwave.beta.features.main_activity.current_user_fragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.UserPostDomainModel
import com.test.chatterwave.beta.domain.usecase.GetUserPostsList
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CurrentUserViewModel @AssistedInject constructor(
    private val getUserPostsList: GetUserPostsList,
    @Assisted private val handle: SavedStateHandle,

    ) : ViewModel() {

    @AssistedFactory
    interface Factory :
        BaseForAssistedSavedStateViewModelFactory<CurrentUserViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): CurrentUserViewModel
    }

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    suspend fun getUserPosts(userId: String): Flow<PagingData<UserPostDomainModel>> =
        getUserPostsList.execute(userId)
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun onRetryButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.Retry)
        }
    }

    fun onEditButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigateNextScreen)
        }
    }
}