package com.test.chatterwave.beta.features.main_activity.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.data.source.local.preferences.AppPreferences
import com.chi.interngram.echo.databinding.ActivityMainBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import com.test.chatterwave.beta.utils.bottomNavigationImage
import com.test.chatterwave.beta.utils.callbacks.OnBackPressedListener
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val args = Bundle()
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this, viewModelFactory.get().create(this, args))[MainActivityViewModel::class.java]
    }

    @Inject
    lateinit var sharedPreferences: AppPreferences

    private var onBackPressedListener: OnBackPressedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
        binding = ActivityMainBinding.inflate(layoutInflater)
        observeAvatar()
        binding.bottomNav.itemIconTintList = null
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.main_activity_container_view
        ) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.parent?.id == R.id.create_post_navigation || (destination.parent?.id == R.id.user_page_navigation && destination.id != R.id.currentUserFragment) ) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
        binding.bottomNav.setupWithNavController(navController)
        }

    override fun onBackPressed() {
        if (onBackPressedListener != null){
            onBackPressedListener?.onBackPressedClickListener()
        }else super.onBackPressed()

    }

    fun setOnBackPressedListener(listener: OnBackPressedListener?) {
        onBackPressedListener = listener
    }

    private fun observeAvatar(){
        lifecycleScope.launchWhenStarted {
            viewModel.bottomNavAvatar.collect{
                if (!it.isNullOrEmpty()){
                    binding.bottomNav.menu.findItem(R.id.user_page_navigation).icon = it.bottomNavigationImage(context = this@MainActivity)
                }else binding.bottomNav.menu.findItem(R.id.user_page_navigation).icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.photo)
            }
        }
    }
}