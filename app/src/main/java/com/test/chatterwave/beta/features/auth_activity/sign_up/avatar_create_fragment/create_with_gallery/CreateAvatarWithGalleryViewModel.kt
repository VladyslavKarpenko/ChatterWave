package com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_create_fragment.create_with_gallery

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.di.hilt.assisted.BaseForAssistedSavedStateViewModelFactory
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ONE
import com.test.chatterwave.beta.utils.SHARED_FLOW_REPLY_ZERO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CreateAvatarWithGalleryViewModel@AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    @ApplicationContext private val mContext: Context
) : ViewModel() {

    @AssistedFactory
    interface Factory :
        BaseForAssistedSavedStateViewModelFactory<CreateAvatarWithGalleryViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): CreateAvatarWithGalleryViewModel
    }

    private val _userPhotos = MutableSharedFlow<MutableList<String>>(SHARED_FLOW_REPLY_ONE)
    var userPhotos = _userPhotos.asSharedFlow()

    private val _onButtonClickEvent = MutableSharedFlow<NavigationEvent>(SHARED_FLOW_REPLY_ZERO)
    var onButtonClickEvent = _onButtonClickEvent.asSharedFlow()

    private var nameList : MutableList<String> = mutableListOf()

    private fun fetchImages(): MutableList<String>{
        val columns = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.MIME_TYPE)
        val selection = mContext.resources.getString(R.string.image_selection, MediaStore.Images.Media.SIZE, mContext.resources.getInteger(R.integer.camera_picture_size_max_area), MediaStore.Images.Media.MIME_TYPE )
        val orderBy = mContext.resources.getString(R.string.image_order_by_desc, MediaStore.Images.Media.DATE_TAKEN )
        val imageCursor: Cursor? = mContext.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, selection,
            null , orderBy
        )
        for (i in 0 until imageCursor!!.count) {
            imageCursor.moveToPosition(i)
            val dataColumnIndex =
                imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)
            nameList.add(imageCursor.getString(dataColumnIndex))
        }
        imageCursor.close()
        return nameList
    }

    fun getImages(){
        viewModelScope.launch(Dispatchers.IO) {
            _userPhotos.emit(fetchImages())
        }
    }

    fun onNextButtonClicked() {
        viewModelScope.launch{
            _onButtonClickEvent.emit(NavigationEvent.NavigateNextScreen)
        }
    }

    fun onCancelButtonClicked() {
        viewModelScope.launch{
            _onButtonClickEvent.emit(NavigationEvent.NavigatePreviousScreen)
        }
    }

}