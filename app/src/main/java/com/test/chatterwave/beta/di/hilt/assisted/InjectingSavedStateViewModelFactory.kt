package com.test.chatterwave.beta.di.hilt.assisted

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Provider

@Reusable
class InjectingSavedStateViewModelFactory @Inject constructor(
    val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<BaseForAssistedSavedStateViewModelFactory<out ViewModel>>>
) {
    fun create(owner: SavedStateRegistryOwner, defaultArgs: Bundle? = null): AbstractSavedStateViewModelFactory {
        return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                // Attempt to get ViewModel from assisted inject factories

                providers[modelClass]?.let {
                    try {
                        return it.get().create(handle) as T
                    } catch (e: Exception) {
                        throw RuntimeException(e)
                    }
                } ?: throw IllegalArgumentException("Unknown model class $modelClass")
            }
        }
    }
}
