package com.test.chatterwave.beta.utils

import android.content.Context
import android.graphics.PorterDuff
import androidx.core.content.ContextCompat
import com.google.android.material.R
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable

fun progressIndicatorDrawable(
    context: Context,
    color: Int
): IndeterminateDrawable<CircularProgressIndicatorSpec> {
    val spec = CircularProgressIndicatorSpec(
        context,  /*attrs=*/null, 0,
        R.style.Widget_Material3_CircularProgressIndicator_ExtraSmall
    )
    val progressIndicatorDrawable =
        IndeterminateDrawable.createCircularDrawable(context, spec)
    progressIndicatorDrawable.setColorFilter(
        ContextCompat.getColor(context, color),
        PorterDuff.Mode.SRC_IN
    )

    return progressIndicatorDrawable
}