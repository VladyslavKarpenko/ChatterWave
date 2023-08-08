package com.test.chatterwave.beta.features.auth_activity.sign_up.add_city_fragment

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import com.test.chatterwave.beta.utils.UIResponseState
import com.test.chatterwave.beta.utils.cityRegex
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCityViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    @ApplicationContext private val mContext: Context
) : ViewModel() {

    @AssistedFactory
    interface Factory : BaseForAssistedSavedStateViewModelFactory<AddCityViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): AddCityViewModel
    }

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    private val _userCityValidationLocal =
        MutableStateFlow<UIResponseState>(UIResponseState.Loading)
    var userCityValidationLocal = _userCityValidationLocal.asStateFlow()


    fun nextButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigateNextScreen)
        }
    }

    fun previousButtonClicked() {
        viewModelScope.launch {
            _onButtonClickEvent.emit(NavigationEvent.NavigatePreviousScreen)
        }
    }

    fun validateCityLocal(city: String){
        viewModelScope.launch {
            if (city.isEmpty()) {
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

    companion object{
        private const val CITY_MIN_LENGTH = 2
        private const val CITY_MAX_LENGTH = 50
    }

}