package com.test.chatterwave.beta.features.auth_activity.sign_up.add_bio_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.FragmentAddBioBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddBioFragment : Fragment() {

    private var _binding: FragmentAddBioBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: AddBioViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[AddBioViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBioBinding.inflate(layoutInflater)
        initDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.nextButtonClicked(authViewModel.updateUser())
            }
        }
        binding.arrowBack.setOnClickListener {
            viewModel.previousButtonClicked()
        }
        binding.bioEditText.doOnTextChanged { text, _, _, _ ->
            authViewModel.saveUserBio(text.toString())
        }
    }

    private fun observeButtonClick(){
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect{ navEvent ->
                when(navEvent){
                    is NavigationEvent.NavigateNextScreen -> {
                        authViewModel.saveUserBio(binding.bioEditText.text.toString())
                        findNavController().navigate(R.id.action_addBioFragment_to_notificationDialog)
                    }
                    is NavigationEvent.NavigatePreviousScreen -> {
                        findNavController().popBackStack()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initDataBinding() {
        with(binding) {
            addBioViewModel = viewModel
            lifecycleOwner = this@AddBioFragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}