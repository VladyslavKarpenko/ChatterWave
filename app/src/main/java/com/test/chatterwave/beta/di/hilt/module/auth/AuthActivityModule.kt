package com.test.chatterwave.beta.di.hilt.module.auth

import androidx.lifecycle.ViewModel
import com.test.chatterwave.beta.di.hilt.annotation.ViewModelKey
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.features.auth_activity.password_recovery.change_pass_fragment.ChangePasswordViewModel
import com.test.chatterwave.beta.features.auth_activity.password_recovery.confirm_change_password_fragment.ConfirmChangePasswordViewModel
import com.test.chatterwave.beta.features.auth_activity.password_recovery.create_new_password.CreateNewPasswordViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoMap

@Module(includes = [AuthActivityModule.BindsModule::class])
@InstallIn(ActivityRetainedComponent::class)
class AuthActivityModule {

    @Module
    @InstallIn(ActivityRetainedComponent::class)
    interface BindsModule {


        /**Here will be provide authActivityViewModel*/
        @Binds
        @IntoMap
        @ViewModelKey(AuthViewModel::class)
        fun bindAuthViewModelFactory(f: AuthViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(ChangePasswordViewModel::class)
        fun bindChangePasswordViewModelFactory(f: ChangePasswordViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(ConfirmChangePasswordViewModel::class)
        fun bindConfirmChangePasswordViewModelFactory(f: ConfirmChangePasswordViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(CreateNewPasswordViewModel::class)
        fun bindCreateNewPasswordViewModelFactory(f: CreateNewPasswordViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

    }
}