package com.test.chatterwave.beta.features.auth_activity.test

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class TestViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<TestViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): TestViewModel
    }
}