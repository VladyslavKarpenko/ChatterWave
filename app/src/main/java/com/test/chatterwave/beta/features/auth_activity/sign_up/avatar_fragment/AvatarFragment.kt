package com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.FragmentAvatarBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.toast
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AvatarFragment : Fragment() {

    private var _binding: FragmentAvatarBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>

    private val viewModel: AvatarViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[AvatarViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAvatarBinding.inflate(layoutInflater)
        initDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addPhotoButton.setOnClickListener {
            viewModel.onAddButtonClicked()
        }
        binding.deleteAvatarButton.setOnClickListener {
            viewModel.onDeleteButtonClicked()
        }
        binding.changePhotoButton.setOnClickListener {
            viewModel.onChangeButtonClicked()
        }

        binding.skipButton.setOnClickListener {
            viewModel.onSkipButtonClicked()
        }
        binding.nexButton.setOnClickListener {
            viewModel.onNextButtonClicked()
        }
    }

    private fun initDataBinding(){
        with(binding) {
            authVM = authViewModel
            lifecycleOwner = this@AvatarFragment
        }
    }

    private fun observeButtonClick(){
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect{ navEvent ->
                when(navEvent){
                    is NavigationEvent.NavigateToDialog -> findNavController().navigate(R.id.action_avatarFragment_to_addAvatarDialog)
                    is NavigationEvent.NavigateNextScreen -> findNavController().navigate(R.id.action_avatarFragment_to_addCityFragment)
                    is NavigationEvent.NavigateFeedScreen -> toast("Navigate to feed fragment")
                    is NavigationEvent.NavigateToDeleteDialog -> findNavController().navigate(R.id.action_avatarFragment_to_deleteAvatarDialog)
                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}