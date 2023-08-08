package com.test.chatterwave.beta.features.feed.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import com.chi.interngram.echo.R

class PostFooter(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val likeButton: ImageView by lazy { findViewById(R.id.heart) }
    private val likesText: TextView by lazy { findViewById(R.id.likes) }
    private val postTimeText: TextView by lazy { findViewById(R.id.postTime) }
    private var isLiked = false

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PostFooter,
            0, 0
        ).apply {

            try {
                isLiked = getBoolean(R.styleable.PostFooter_isLiked, false)
                if (isLiked) like()
                else unlike()

                val likes = getInteger(R.styleable.PostFooter_likes, 0)
                if (likes > 0) likesText.text = resources.getString(R.string.likes, likes)
                else likesText.text = ""

                val postTime = getInteger(R.styleable.PostFooter_postTime, 0)
                postTimeText.text = resources.getString(R.string.hrs_ago, postTime)
            } finally {
                recycle()
            }
        }

        likeButton.setOnClickListener {
            if (isLiked) unlike()
            else like()
        }
    }

    fun like() {
        isLiked = true
        likeButton.setImageDrawable(R.drawable.heart_fill.toDrawable())
    }

    fun unlike() {
        isLiked = false
        likeButton.setImageDrawable(R.drawable.heart.toDrawable())
    }
}