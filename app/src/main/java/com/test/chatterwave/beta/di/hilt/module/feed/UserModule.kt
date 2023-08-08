package com.test.chatterwave.beta.di.hilt.module.feed

import androidx.lifecycle.ViewModel
import com.test.chatterwave.beta.di.hilt.annotation.ViewModelKey
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.features.main_activity.create_post_fragment.CreatePostViewModel
import com.test.chatterwave.beta.features.main_activity.create_post_fragment.create_post_with_camera.CreatePostWithCameraViewModel
import com.test.chatterwave.beta.features.main_activity.create_post_fragment.create_post_with_gallery.CreatePostWithGalleryViewModel
import com.test.chatterwave.beta.features.main_activity.current_user_fragment.CurrentUserViewModel
import com.test.chatterwave.beta.features.main_activity.edit_profile_fragment.EditProfileViewModel
import com.test.chatterwave.beta.features.main_activity.edit_profile_fragment.edit_with_camera_fragment.EditAvatarCameraViewModel
import com.test.chatterwave.beta.features.main_activity.edit_profile_fragment.edit_with_gallery_fragment.EditAvatarGalleryViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoMap

@Module(includes = [UserModule.BindsModule::class])
@InstallIn(ActivityRetainedComponent::class)
class UserModule {

    @Module
    @InstallIn(ActivityRetainedComponent::class)
    interface BindsModule {

        @Binds
        @IntoMap
        @ViewModelKey(CurrentUserViewModel::class)
        fun bindCurrentUserViewModelFactory(f: CurrentUserViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(CreatePostWithCameraViewModel::class)
        fun bindCreatePostWithCameraViewModelFactory(f: CreatePostWithCameraViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(CreatePostViewModel::class)
        fun bindCreatePostViewModelFactory(f: CreatePostViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(CreatePostWithGalleryViewModel::class)
        fun bindCreatePostWithGalleryViewModelFactory(f: CreatePostWithGalleryViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(EditProfileViewModel::class)
        fun bindEditProfileViewModelFactory(f: EditProfileViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(EditAvatarCameraViewModel::class)
        fun bindEditAvatarCameraViewModelFactory(f: EditAvatarCameraViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>

        @Binds
        @IntoMap
        @ViewModelKey(EditAvatarGalleryViewModel::class)
        fun bindEditAvatarGalleryViewModelFactory(f: EditAvatarGalleryViewModel.Factory): BaseForAssistedSavedStateViewModelFactory<out ViewModel>
    }
}