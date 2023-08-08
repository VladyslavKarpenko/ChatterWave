package com.test.chatterwave.beta.features.main_activity

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.CreatePostDomainModel
import com.test.chatterwave.beta.domain.model.CreatePostSourceDomainModel
import com.test.chatterwave.beta.domain.model.UpdateUserDomainModel
import com.test.chatterwave.beta.domain.model.UserDomainModel
import com.test.chatterwave.beta.domain.repository.AudioRepository
import com.test.chatterwave.beta.domain.usecase.GetCurrentUserUseCase
import com.test.chatterwave.beta.utils.EMPTY_STRING
import com.test.chatterwave.beta.utils.UIResponseState
import com.test.chatterwave.beta.utils.encodeImage
import com.test.chatterwave.beta.utils.formNetworkToUI
import com.test.chatterwave.beta.utils.isInstanceOf
import com.test.chatterwave.beta.utils.loadBitmap
import com.test.chatterwave.beta.utils.parseHashtags
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val cardRepository: AudioRepository,
    @ApplicationContext private val mContext: Context
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<MainActivityViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): MainActivityViewModel
    }

    private val _bottomNavAvatar = MutableStateFlow<String?>(null)
    var bottomNavAvatar = _bottomNavAvatar.asStateFlow()

    private val _userHeaderInformationState =
        MutableStateFlow<UIResponseState>(UIResponseState.Loading)
    var userHeaderInformationState = _userHeaderInformationState.asStateFlow()

    private val _userHeaderInfo = MutableStateFlow<UserDomainModel>(UserDomainModel())
    var userHeaderInfo = _userHeaderInfo.asStateFlow()

    private val _userCreatePostPhoto = MutableStateFlow<UIResponseState>(UIResponseState.Loading)
    var userCreatePostPhoto = _userCreatePostPhoto.asStateFlow()

    private val _userCreatePostDescription = MutableStateFlow<String>(EMPTY_STRING)
    var userCreatePostDescription = _userCreatePostDescription.asStateFlow()

    private val _userEditProfileAvatar = MutableStateFlow<UIResponseState>(UIResponseState.Loading)
    var userEditProfileAvatar = _userEditProfileAvatar.asStateFlow()

    private val _userEditProfileNewAvatar = MutableStateFlow<UIResponseState>(UIResponseState.Loading)
    var userEditProfileNewAvatar = _userEditProfileNewAvatar.asStateFlow()

    private val _userHeaderInfoForEditProfile = MutableStateFlow<UpdateUserDomainModel>(
        UpdateUserDomainModel(
            bio = EMPTY_STRING,
            city = EMPTY_STRING,
            fullName = EMPTY_STRING,
            nickname = EMPTY_STRING,
            photo = EMPTY_STRING
        )
    )
    var userHeaderInfoForEditProfile = _userHeaderInfoForEditProfile.asStateFlow()

    fun getStringExtra(): String?{
        return handle["key"]
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            val currentUserResult = getCurrentUserUseCase.execute()
            val result = currentUserResult.formNetworkToUI()
            _userHeaderInformationState.emit(result)
            if (result is UIResponseState.Success<*>){
                _userHeaderInfo.emit(result.content as UserDomainModel)
                _userEditProfileAvatar.emit(UIResponseState.Success(userHeaderInfo.value.photo.loadBitmap()))
                _userHeaderInfoForEditProfile.emit(
                    UpdateUserDomainModel(bio = userHeaderInfo.value.bio
                , city = userHeaderInfo.value.city
                , fullName = userHeaderInfo.value.fullName
                , nickname = userHeaderInfo.value.nickname
                , photo = userHeaderInfo.value.photo)
                )
                setAvatarToBottomNavigation(userHeaderInfo.value.photo)
            }
        }
    }

    fun saveUserHeaderInfoForEditProfile(updateUserDomainModel: UpdateUserDomainModel){
        viewModelScope.launch {
            _userHeaderInfoForEditProfile.emit(updateUserDomainModel)
        }
    }

    fun saveUserNewAvatar(photo: Bitmap){
        viewModelScope.launch {
            _userEditProfileNewAvatar.emit(UIResponseState.Success(photo))
        }
    }

    fun saveUserAvatar(photo: Bitmap){
        viewModelScope.launch {
            _userEditProfileAvatar.emit(UIResponseState.Success(photo))
        }
    }

    fun deleteEditAvatar(){
        viewModelScope.launch {
            _userEditProfileAvatar.emit(UIResponseState.Loading)
        }
    }

    fun deleteEditAvatarNew(){
        viewModelScope.launch {
            _userEditProfileNewAvatar.emit(UIResponseState.Loading)
        }
    }


    private fun setAvatarToBottomNavigation(avatar: String){
        viewModelScope.launch {
            _bottomNavAvatar.emit(avatar)
        }
    }

    fun saveCreatePostPhoto(bitmap: Bitmap){
        viewModelScope.launch {
            _userCreatePostPhoto.emit(UIResponseState.Success(bitmap))
        }
    }

    fun deleteCreatePostPhoto(){
        viewModelScope.launch {
            _userCreatePostPhoto.emit(UIResponseState.Loading)
        }
    }

    fun savePostDescription(text: String){
        viewModelScope.launch {
            _userCreatePostDescription.emit(text)
        }
    }

    suspend fun createPost(): CreatePostDomainModel {
        var userAvatarBase64: String = EMPTY_STRING
        withContext(Dispatchers.IO) {
            if (isInstanceOf<UIResponseState.Success<Bitmap>>(_userCreatePostPhoto.value)) {
                userAvatarBase64 =
                    (_userCreatePostPhoto.value as UIResponseState.Success<Bitmap>).content.encodeImage()
            }
        }
        return CreatePostDomainModel(
            description = userCreatePostDescription.value,
            hashtagsNames = userCreatePostDescription.value.parseHashtags(),
            createPostSourceDataModel = mutableListOf(
                CreatePostSourceDomainModel(
                    base64File = userAvatarBase64
                )
            )
        )
    }
}