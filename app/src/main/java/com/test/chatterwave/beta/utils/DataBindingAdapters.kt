package com.test.chatterwave.beta.utils

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.chi.interngram.echo.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.controls.Facing

@BindingAdapter("hideOnLoading")
fun View.hideOnLoading(responseState: UIResponseState) {
    visibility = if (responseState is UIResponseState.Loading)
        View.INVISIBLE
    else
        View.VISIBLE
}

@BindingAdapter("showOnLoading")
fun View.showOnLoading(responseState: UIResponseState) {
    visibility = if (responseState is UIResponseState.Loading)
        View.VISIBLE
    else
        View.INVISIBLE
}

@BindingAdapter("showOnSuccess")
fun View.showOnSuccess(responseState: UIResponseState) {
    visibility = if (isInstanceOf<UIResponseState.Success<Bitmap>>(responseState))
        View.VISIBLE
    else
        View.INVISIBLE
}

@BindingAdapter("hideOnSuccess")
fun View.hideOnSuccess(responseState: UIResponseState) {
    visibility = if (isInstanceOf<UIResponseState.Success<Bitmap>>(responseState))
        View.INVISIBLE
    else
        View.VISIBLE
}

@BindingAdapter("hideOnError")
fun View.hideOnError(responseState: UIResponseState) {
    visibility = if (responseState is UIResponseState.Error)
        View.INVISIBLE
    else
        View.VISIBLE
}

@BindingAdapter("showOnError")
fun TextView.showError(responseState: UIResponseState) {
    visibility = if (responseState is UIResponseState.Error)
        View.VISIBLE
    else
        View.INVISIBLE
    text = (responseState as UIResponseState.Error).errorMessage
}

@BindingAdapter("showOnError")
fun View.showError(responseState: UIResponseState) {
    visibility = if (responseState is UIResponseState.Error)
        View.VISIBLE
    else
        View.INVISIBLE
}

@BindingAdapter("setText")
fun TextView.setText(responseState: UIResponseState) {
    if (isInstanceOf<UIResponseState.Success<String>>(responseState)) {
        val people = responseState as UIResponseState.Success<String>
        text = people.content
    }else{
        text = null
    }
}

@BindingAdapter("setEnabled")
fun View.setEnabled(responseState: UIResponseState){
    isEnabled = responseState is UIResponseState.Success<*> || responseState is UIResponseState.Loading
}

@BindingAdapter("setClickable")
fun View.setClickable(responseState: UIResponseState){
    isClickable = responseState !is UIResponseState.Loading
}

@BindingAdapter("setButtonLoading")
fun MaterialButton.setButtonLoading(responseState: UIResponseState){
    if (responseState is UIResponseState.Loading){
        this.icon = progressIndicatorDrawable(this.context, R.color.white)
    }else this.icon = null
}

@BindingAdapter("setError")
fun TextInputLayout.setError(responseState: UIResponseState){
    if (responseState is UIResponseState.Error){
        this.error = responseState.errorMessage
    }else this.error = null
}

@BindingAdapter("setButtonNetworkLoading")
fun MaterialButton.setButtonNetworkLoading(networkResult: NetworkResult<*>){
    if (networkResult is NetworkResult.Loading){
        this.isClickable = false
        this.icon = progressIndicatorDrawable(this.context, R.color.white)
    }else {
        this.icon = null
        this.isClickable = true
    }
}

@BindingAdapter("setEndIconDrawable")
fun TextInputLayout.setEndIconDrawable(responseState: UIResponseState){
     when (responseState) {
        is UIResponseState.Error -> { errorIconDrawable =
            ContextCompat.getDrawable(
                this.context,
                R.drawable.multiply_circle
            )
        }
        is UIResponseState.Success<*> -> { endIconDrawable =
            ContextCompat.getDrawable(
                this.context,
                R.drawable.success_circle
            )
        }
         else -> endIconDrawable = null
     }
}

@BindingAdapter("setNetworkEndIconDrawable")
fun TextInputLayout.setNetworkEndIconDrawable(networkResult: NetworkResult<*>){
    when (networkResult) {
        is NetworkResult.Error<*> -> { errorIconDrawable =
            ContextCompat.getDrawable(
                this.context,
                R.drawable.multiply_circle
            )
        }
        is NetworkResult.Success<*> -> { endIconDrawable =
            ContextCompat.getDrawable(
                this.context,
                R.drawable.success_circle
            )
        }
        else -> {}
    }
}

@BindingAdapter("setNetworkError")
fun TextInputLayout.setNetworkError(networkResult: NetworkResult<*>){
    if (networkResult is NetworkResult.Error<*>){
        this.error = resources.getText(networkResult.message!!)
    }else this.error = null
}

@BindingAdapter("setImage")
fun ImageView.setImage(responseState: UIResponseState) {
    if (isInstanceOf<UIResponseState.Success<Bitmap>>(responseState)) {
        val people = responseState as UIResponseState.Success<Bitmap>
        this.setImageBitmap(people.content)
    }else{
        this.setImageBitmap(null)
    }
}

@BindingAdapter("cameraFacing")
fun CameraView.cameraFacing(facing: Facing){
    when(facing){
        Facing.FRONT -> this.facing = Facing.FRONT
        Facing.BACK -> this.facing = Facing.BACK
    }
}

@BindingAdapter("urlImage")
fun ImageView.bindUrlImage(imageUrl: String?) {
    if (imageUrl != null) {
        Glide.with(this).load(imageUrl).into(this)
    } else {
        this.setImageBitmap(null)
    }
}

@BindingAdapter("editAvatarStateEmpty")
fun View.editAvatarStateEmpty(photo: String){
     this.isVisible = photo.isEmpty()
}

@BindingAdapter("editAvatarState")
fun View.editAvatarState(photo: String){
    this.isVisible = photo.isNotEmpty()
}

inline fun <reified T> isInstanceOf(instance: Any?): Boolean {
    return instance is T
}