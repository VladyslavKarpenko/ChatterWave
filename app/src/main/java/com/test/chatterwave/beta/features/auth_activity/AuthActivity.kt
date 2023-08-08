package com.test.chatterwave.beta.features.auth_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.utils.callbacks.OnBackPressedListener
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity(R.layout.activity_auth)  {

    private var onBackPressedListener: OnBackPressedListener? = null

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this, viewModelFactory.get().create(this))[AuthViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
    }

    override fun onBackPressed() {
        if (onBackPressedListener != null){
            onBackPressedListener?.onBackPressedClickListener()
        }else super.onBackPressed()

    }

    fun setOnBackPressedListener(listener: OnBackPressedListener?) {
        onBackPressedListener = listener
    }
}