package com.test.chatterwave.beta.features.auth_activity

import android.graphics.Bitmap
import android.os.CountDownTimer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatterwave.beta.data.source.local.preferences.AppPreferences
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.UpdateUserDomainModel
import com.test.chatterwave.beta.domain.model.UserSignInDomainModel
import com.test.chatterwave.beta.domain.usecase.CreateConfirmCodeByEmailUseCase
import com.test.chatterwave.beta.domain.usecase.CreateConfirmCodeByPhoneUseCase
import com.test.chatterwave.beta.utils.BIRTHDAY_TIME
import com.test.chatterwave.beta.utils.EMPTY_STRING
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.REQUEST_TIME_OUT
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import com.test.chatterwave.beta.utils.TIMER_COUNT_FOUR
import com.test.chatterwave.beta.utils.TIMER_COUNT_INCREASE
import com.test.chatterwave.beta.utils.TIMER_COUNT_ZERO
import com.test.chatterwave.beta.utils.TIMER_MINUTE
import com.test.chatterwave.beta.utils.TIMER_SECOND
import com.test.chatterwave.beta.utils.TIMER_SIX_MINUTES
import com.test.chatterwave.beta.utils.TimerEvent
import com.test.chatterwave.beta.utils.UIResponseState
import com.test.chatterwave.beta.utils.encodeImage
import com.test.chatterwave.beta.utils.getCurrentTimeInMillis
import com.test.chatterwave.beta.utils.isInstanceOf
import com.test.chatterwave.beta.utils.sendFormattedDate
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

/** Shared viewModel for handling user inputs during signUp */
class AuthViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    private val createConfirmCodeByEmailUseCase: CreateConfirmCodeByEmailUseCase,
    private val createConfirmCodeByPhoneUseCase: CreateConfirmCodeByPhoneUseCase,
    private val appPreferences: AppPreferences
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<AuthViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): AuthViewModel
    }

    private val _timerResult = MutableStateFlow<TimerEvent<Long>>(TimerEvent.TimerStop())
    var timerResult = _timerResult.asStateFlow()

    private val _userBirthday = MutableStateFlow(BIRTHDAY_TIME)
    var userBirthday = _userBirthday.asStateFlow()

    private val _userPhone = MutableStateFlow(EMPTY_STRING)
    val userPhone = _userPhone.asStateFlow()

    private val _userEmail = MutableStateFlow(EMPTY_STRING)
    val userEmail = _userEmail.asStateFlow()

    private val _userPassword = MutableStateFlow(EMPTY_STRING)
    val userPassword = _userPassword.asStateFlow()

    private val _userNickname = MutableStateFlow(EMPTY_STRING)
    val userNickname = _userNickname.asStateFlow()

    private val _userFullName = MutableStateFlow(EMPTY_STRING)
    val userFullName = _userFullName.asStateFlow()

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    private val _userAvatar = MutableStateFlow<UIResponseState>(UIResponseState.Loading)
    var userAvatar = _userAvatar.asStateFlow()

    private val _userCity = MutableStateFlow(EMPTY_STRING)
    var userCity = _userCity.asStateFlow()

    private val _userBio = MutableStateFlow(EMPTY_STRING)
    var userBio = _userBio.asStateFlow()

    suspend fun updateUser(): UpdateUserDomainModel {
        var userAvatarBase64: String = EMPTY_STRING
        withContext(Dispatchers.IO) {
            if (isInstanceOf<UIResponseState.Success<Bitmap>>(_userAvatar.value)) {
                userAvatarBase64 = (_userAvatar.value as UIResponseState.Success<Bitmap>).content.encodeImage()
            }
        }
        return UpdateUserDomainModel(
            bio = _userBio.value,
            city = _userCity.value,
            fullName = EMPTY_STRING,
            nickname = EMPTY_STRING,
            photo = userAvatarBase64
        )
    }

    fun saveUserBio(bio: String) {
        viewModelScope.launch {
            _userBio.emit(bio)
        }
    }

    fun saveUserCity(city: String){
        viewModelScope.launch {
            _userCity.emit(city)
        }
    }

    fun saveUserAvatar(bitmap: Bitmap){
        viewModelScope.launch {
            _userAvatar.emit(UIResponseState.Success(bitmap))
        }
    }

    fun deleteUserAvatar(){
        viewModelScope.launch {
            _userAvatar.emit(UIResponseState.Loading)
        }
    }

    private fun createConfirmCodeByEmail(email: String) {
        viewModelScope.launch {
            createConfirmCodeByEmailUseCase.execute(email = email)
        }
    }

    private fun createConfirmCodeByPhone(phone: String) {
        viewModelScope.launch {
            createConfirmCodeByPhoneUseCase.execute(phone = phone)
        }
    }

    fun saveBirthdayToViewModel(userBirthday: Long?) {
        viewModelScope.launch {
            if (userBirthday != null) {
                _userBirthday.emit(userBirthday)
                _onButtonClickEvent.emit(NavigationEvent.NavigateNextScreen)
            }
        }
    }

    fun saveUserPhoneToViewModel(phone: String) {
        viewModelScope.launch {
            _userEmail.emit(EMPTY_STRING)
            _userPhone.emit(phone)
        }
    }

    fun saveUserEmailToViewModel(email: String) {
        viewModelScope.launch {
            _userPhone.emit(EMPTY_STRING)
            _userEmail.emit(email)
        }
    }

    fun saveUserPassword(password: String) {
        viewModelScope.launch {
            _userPassword.emit(password)
        }
    }

    fun saveUserNickname(nickname: String) {
        viewModelScope.launch {
            _userNickname.emit(nickname)
        }
    }

    fun saUserFullName(fullName: String) {
        viewModelScope.launch {
            _userFullName.emit(fullName)
        }
    }

    fun userSignIn(): UserSignInDomainModel {
        return UserSignInDomainModel(
            dateOfBirth = userBirthday.value.sendFormattedDate().toString(),
            email = userEmail.value,
            phoneNumber = userPhone.value,
            fullName = userFullName.value,
            nickname = userNickname.value,
            password = userPassword.value
        )
    }

    fun onResendButtonClicked() {
        val currentTime = getCurrentTimeInMillis()
        if (getTime(currentTime) != REQUEST_TIME_OUT) {
            timer(getTime(currentTime) )
        } else {
            if (_userEmail.value.isEmpty()) {
                createConfirmCodeByPhone(_userPhone.value)
            } else {
                createConfirmCodeByEmail(_userEmail.value)
            }
            timer(countTime())
        }
    }

    private fun timer(milliseconds: Long) {
        object : CountDownTimer(milliseconds, TIMER_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _timerResult.value = TimerEvent.TimerStart(millisUntilFinished)
            }

            override fun onFinish() {
                _timerResult.value = TimerEvent.TimerStop()
            }
        }.start()
    }

    private fun getTime(currentTime: Long): Long {
        if (currentTime - appPreferences.getTimerTime() > TIMER_SIX_MINUTES
        ) {
            appPreferences.setTimerCounter(TIMER_COUNT_ZERO)
            return REQUEST_TIME_OUT
        } else {
            if (appPreferences.getTimerCounter() > TIMER_COUNT_FOUR) {
                return TIMER_SIX_MINUTES - (currentTime - appPreferences.getTimerTime())
            } else {
                if (currentTime - appPreferences.getTimerTime() >= TIMER_MINUTE
                ) {
                    return REQUEST_TIME_OUT
                } else {
                    return TIMER_MINUTE - (currentTime - appPreferences.getTimerTime())
                }
            }
        }
    }

    private fun countTime(): Long {
        appPreferences.setTimerTime(ZonedDateTime.now().toInstant().toEpochMilli())
        val counter = appPreferences.getTimerCounter() + TIMER_COUNT_INCREASE
        appPreferences.setTimerCounter(counter)
        return if (appPreferences.getTimerCounter() > TIMER_COUNT_FOUR) {
            TIMER_SIX_MINUTES
        } else TIMER_MINUTE
    }
}