package com.test.chatterwave.beta.features.auth_activity.sign_up.user_full_name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.FragmentUserFullNameBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.PhoneEmailLocalValidationResult
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserFullNameFragment : Fragment() {

    private var _binding: FragmentUserFullNameBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: UserFullNameViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[UserFullNameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeFullNameValidationViewModel()
        observeButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserFullNameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateFullName()

        binding.nextBtn.setOnClickListener {
            viewModel.nextButtonClicked()
        }
        binding.backImage.setOnClickListener {
            viewModel.previousButtonClicked()
        }
        binding.fullNameLayout.setErrorIconOnClickListener {
            binding.fullNameEditText.text?.clear()
        }
    }

    private fun validateFullName() {
        with(binding) {
            fullNameEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.validateUserFullName(text.toString().trim())
            }
        }
    }

    private fun observeButtonClick() {
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect { navEvent ->
                when (navEvent) {
                    is NavigationEvent.NavigateNextScreen -> {
                        authViewModel.saUserFullName(binding.fullNameEditText.text.toString())
                        findNavController().navigate(R.id.action_userFullNameFragment_to_birthdayFragment)
                    }
                    is NavigationEvent.NavigatePreviousScreen -> findNavController().popBackStack()
                    else -> {}
                }
            }
        }
    }

    private fun observeFullNameValidationViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.fullNameLocalValidation.collect { localValidation ->
                with(binding) {
                    when (localValidation) {
                        is PhoneEmailLocalValidationResult.Empty -> {
                            fullNameLayout.error = null
                            nextBtn.isEnabled = false
                            fullNameLayout.endIconDrawable = null
                        }
                        is PhoneEmailLocalValidationResult.Success -> {
                            fullNameLayout.error = null
                            nextBtn.isEnabled = true
                            fullNameLayout.endIconDrawable = ContextCompat.getDrawable(
                                requireActivity(),
                                R.drawable.success_circle
                            )
                            viewModel.saveUserFullName(binding.fullNameEditText.text.toString())
                        }
                        is PhoneEmailLocalValidationResult.Error -> {
                            fullNameLayout.error =
                                localValidation.message?.let { resources.getString(it) }
                            nextBtn.isEnabled = false
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}