package com.test.chatterwave.beta.features.main_activity.edit_profile_fragment

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.NicknameDomainResponseModel
import com.test.chatterwave.beta.domain.model.UpdateUserDomainModel
import com.test.chatterwave.beta.domain.model.UserDomainModel
import com.test.chatterwave.beta.domain.usecase.CheckUserNicknameUseCase
import com.test.chatterwave.beta.domain.usecase.DeleteUserAvatarUseCase
import com.test.chatterwave.beta.domain.usecase.UpdateUserUseCase
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import com.test.chatterwave.beta.utils.UIResponseState
import com.test.chatterwave.beta.utils.cityRegex
import com.test.chatterwave.beta.utils.fullNameRegex
import com.test.chatterwave.beta.utils.nicknameRegex
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    @ApplicationContext private val mContext: Context,
    private val checkUserNicknameUseCase: CheckUserNicknameUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserAvatarUseCase: DeleteUserAvatarUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<EditProfileViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): EditProfileViewModel
    }

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    private val _userCityValidationLocal =
        MutableStateFlow<UIResponseState>(UIResponseState.Loading)
    var userCityValidationLocal = _userCityValidationLocal.asStateFlow()

    private val _nicknameValidation =
        MutableStateFlow<NetworkResult<NicknameDomainResponseModel>>(NetworkResult.Empty(null))
    var nicknameValidation = _nicknameValidation.asStateFlow()

    private val _nicknameValidationLocal =
        MutableStateFlow<UIResponseState>(UIResponseState.Loading)
    var nicknameValidationLocal = _nicknameValidationLocal.asStateFlow()

    private val _fullNameLocalValidation =
        MutableStateFlow<UIResponseState>(UIResponseState.Loading)
    var fullNameLocalValidation = _fullNameLocalValidation.asStateFlow()

    private val _userUpdateResultState = MutableStateFlow<NetworkResult<UserDomainModel>>(
        NetworkResult.Empty(null))
    var userUpdateResultState = _userUpdateResultState.asStateFlow()

    fun validateCityLocal(city: String){
        viewModelScope.launch {
            if (city.trim().isEmpty()) {
                _userCityValidationLocal.emit(UIResponseState.Loading)
            } else if (city.length < CITY_MIN_LENGTH) {
                _userCityValidationLocal.emit(UIResponseState.Error(mContext.getString(R.string.city_enter_min_2)))
            } else if(city.length > CITY_MAX_LENGTH){
                _userCityValidationLocal.emit(UIResponseState.Error(mContext.getString(R.string.city_enter_max_50)))
            }else if (city.contains(cityRegex)){
                _userCityValidationLocal.emit(UIResponseState.Success(city))
            }else _userCityValidationLocal.emit(UIResponseState.Error(mContext.getString(R.string.enter_city_error)))
        }
    }

    fun checkNicknameValidLocal(nickname: String) {
        viewModelScope.launch {
            if (nickname.isEmpty()) {
                _nicknameValidationLocal.emit(UIResponseState.Error(errorMessage = "This field cannot be empty"))
            } else if (nickname.length < NICKNAME_MIN_LENGTH) {
                _nicknameValidationLocal.emit(UIResponseState.Error(errorMessage = mContext.getString(R.string.nickname_min_length)))
            } else if(nickname.length > NICKNAME_MAX_LENGTH){
                _nicknameValidationLocal.emit(UIResponseState.Error(errorMessage = mContext.getString(R.string.nickname_max_length)))
            } else if (nickname.contains(nicknameRegex)){
                _nicknameValidationLocal.emit(UIResponseState.Success(nickname))
            }else {
                _nicknameValidationLocal.emit(UIResponseState.Error(errorMessage = mContext.getString(R.string.nickname_local_validation_error)))
            }
        }
    }

    fun validateUserFullName(fullName: String) {
        with(viewModelScope) {
            if (fullName.isEmpty()) {
                launch { _fullNameLocalValidation.emit(UIResponseState.Error(errorMessage = "This field cannot be empty")) }
            } else if (fullName.length < 2) {
                launch { _fullNameLocalValidation.emit(UIResponseState.Error(errorMessage = mContext.getString(R.string.full_name_min_length_error))) }
            } else if (fullName.length > 35) {
                launch { _fullNameLocalValidation.emit(UIResponseState.Error(errorMessage = mContext.getString(R.string.full_name_max_length_error))) }
            } else if (fullName.contains(fullNameRegex)) {
                launch { _fullNameLocalValidation.emit(UIResponseState.Success(fullName)) }
            } else {
                launch { _fullNameLocalValidation.emit(UIResponseState.Error(errorMessage = mContext.getString(R.string.full_name_error))) }
            }
        }

    }
    fun checkNicknameValid(nickname: String) {
        viewModelScope.launch {
            _userUpdateResultState.emit(NetworkResult.Loading())
            val result = checkUserNicknameUseCase.execute(nickname)
            _nicknameValidation.emit(result)
            if (result is NetworkResult.Error) _userUpdateResultState.emit(NetworkResult.Error(0))
        }
    }

    fun saveUserChanges(userNewInfo: UpdateUserDomainModel) {
        viewModelScope.launch {
            _userUpdateResultState.emit(NetworkResult.Loading())
            if (userNewInfo.photo.isEmpty()) deleteUserAvatarUseCase.execute()
            val result = updateUserUseCase.execute(userNewInfo)
            _userUpdateResultState.emit(result)
        }
    }

    companion object{
        private const val CITY_MIN_LENGTH = 2
        private const val CITY_MAX_LENGTH = 50
        private const val NICKNAME_MIN_LENGTH = 8
        private const val NICKNAME_MAX_LENGTH = 20
    }

}