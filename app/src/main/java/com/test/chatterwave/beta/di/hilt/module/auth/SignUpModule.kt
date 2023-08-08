package com.test.chatterwave.beta.di.hilt.module.auth

import androidx.lifecycle.ViewModel
import com.test.chatterwave.beta.di.hilt.annotation.ViewModelKey
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.sign_up.add_bio_fragment.AddBioViewModel
import com.test.chatterwave.beta.features.auth_activity.sign_up.nickname_fragment.NicknameViewModel
import com.test.chatterwave.beta.features.auth_activity.sign_up.birthday_fragment.BirthdayViewModel
import com.test.chatterwave.beta.features.auth_activity.sign_up.enter_pass_fragment.EnterPasswordViewModel
import com.test.chatterwave.beta.features.auth_activity.login_fragment.LoginViewModel
import com.test.chatterwave.beta.features.auth_activity.sign_up.add_city_fragment.AddCityViewModel
import com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_create_fragment.create_with_camera.CreateAvatarWithCameraViewModel
import com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_create_fragment.create_with_gallery.CreateAvatarWithGalleryViewModel
import com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_fragment.AvatarViewModel
import com.test.chatterwave.beta.features.auth_activity.sign_up.code_confirmation_fragment.ConfirmationCodeViewModel
import com.test.chatterwave.beta.features.auth_activity.sign_up.phone_email_fragment.PhoneEmailViewModel
import com.test.chatterwave.beta.features.auth_activity.sign_up.terms_and_conditions.TernsAndConditionsViewModel
import com.test.chatterwave.beta.features.auth_activity.test.TestViewModel
import com.test.chatterwave.beta.features.auth_activity.sign_up.user_full_name.UserFullNameViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoMap

@Module(includes = [SignUpModule.BindsModule::class])
@InstallIn(ActivityRetainedComponent::class)
class SignUpModule {

    @Module
    @InstallIn(ActivityRetainedComponent::class)
    interface BindsModule {

        @Binds
        @IntoMap
        @ViewModelKey(NicknameViewModel::class)
        fun bindNicknameViewModelFactory(f: NicknameViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(BirthdayViewModel::class)
        fun bindVBirthdayViewModelFactory(f: BirthdayViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(TestViewModel::class)
        fun bindTestViewModelFactory(f: TestViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(UserFullNameViewModel::class)
        fun bindUserFullNameViewModelFactory(f: UserFullNameViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(TernsAndConditionsViewModel::class)
        fun bindTernsAndConditionsViewModelFactory(f: TernsAndConditionsViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(EnterPasswordViewModel::class)
        fun bindEnterPasswordViewModelFactory(f: EnterPasswordViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(LoginViewModel::class)
        fun bindLoginViewModelFactory(f: LoginViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(PhoneEmailViewModel::class)
        fun bindViewModelFactory(f: PhoneEmailViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(ConfirmationCodeViewModel::class)
        fun bindConfirmationCodeViewModelFactory(f: ConfirmationCodeViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(AvatarViewModel::class)
        fun bindAvatarViewModelViewModelFactory(f: AvatarViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(CreateAvatarWithCameraViewModel::class)
        fun bindCreateAvatarWithCameraViewModelFactory(f: CreateAvatarWithCameraViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(CreateAvatarWithGalleryViewModel::class)
        fun bindCreateAvatarWithGalleryViewModelFactory(f: CreateAvatarWithGalleryViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(AddCityViewModel::class)
        fun bindAddCityViewModelFactory(f: AddCityViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(AddBioViewModel::class)
        fun bindAddBioViewModelFactory(f: AddBioViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

    }
}