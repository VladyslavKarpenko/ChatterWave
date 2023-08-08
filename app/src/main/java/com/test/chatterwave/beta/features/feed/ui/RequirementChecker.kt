package com.test.chatterwave.beta.features.feed.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chi.interngram.echo.R
import timber.log.Timber

class RequirementChecker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val requirementText: TextView by lazy { this.findViewById(R.id.requirementText) }
    private val requirementChecker: ImageView by lazy { this.findViewById(R.id.requirementCheckImage) }

    init {

        View.inflate(getContext(), R.layout.password_requirement_checker, this)
        val arr = context.obtainStyledAttributes(attrs, R.styleable.RequirementChecker, 0, 0)

        try {
            val text = arr.getString(R.styleable.RequirementChecker_requirementText)
            val checker = arr.getDrawable(R.styleable.RequirementChecker_requirementChecker)
            val enabled = arr.getBoolean(R.styleable.RequirementChecker_requirementEnabled, true)

            requirementText.text = text
            requirementChecker.setImageDrawable(checker)
            if (enabled) {
                enabledTrue()
            } else {
                enabledFalse()
            }

        } catch (exception: Exception) {
            Timber.d(exception.printStackTrace().toString())
        } finally {
            arr.recycle()
        }
    }

    fun setText(text: String) {
        requirementText.text = text
    }

    fun setImage(image: Int) {
        requirementChecker.setImageResource(image)
    }

    fun setEnabledState(enabled: Boolean) {
        if (enabled) {
            enabledTrue()
        } else {
            enabledFalse()
        }
    }

    private fun enabledTrue() {
        requirementText.isEnabled = true
        requirementChecker.visibility = View.VISIBLE
        requirementText.setTextColor(ContextCompat.getColor(context, R.color.greyscale_black_65))
    }

    private fun enabledFalse() {
        requirementText.isEnabled = false
        requirementText.setTextColor(ContextCompat.getColor(context, R.color.greyscale_black_45))
        requirementChecker.visibility = View.INVISIBLE
    }
}