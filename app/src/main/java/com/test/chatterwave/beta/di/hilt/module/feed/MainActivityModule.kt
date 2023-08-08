package com.test.chatterwave.beta.di.hilt.module.feed

import androidx.lifecycle.ViewModel
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.di.hilt.annotation.ViewModelKey
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoMap


@Module(includes = [MainActivityModule.BindsModule::class])
@InstallIn(ActivityRetainedComponent::class)
class MainActivityModule {

    @Module
    @InstallIn(ActivityRetainedComponent::class)
    interface BindsModule {

        @Binds
        @IntoMap
        @ViewModelKey(MainActivityViewModel::class)
        fun bindViewModelFactory(f: MainActivityViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>
    }
}
