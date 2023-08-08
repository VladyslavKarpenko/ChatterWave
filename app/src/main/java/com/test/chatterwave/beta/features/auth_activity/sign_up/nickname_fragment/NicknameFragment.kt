package com.test.chatterwave.beta.features.auth_activity.sign_up.nickname_fragment

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
import com.chi.interngram.echo.databinding.FragmentNicknameBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.PhoneEmailLocalValidationResult
import com.test.chatterwave.beta.utils.progressIndicatorDrawable
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NicknameFragment : Fragment() {

    private var _binding: FragmentNicknameBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: NicknameViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[NicknameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeRequestApiNicknameValidation()
        observeNicknameLocalValidation()
        observeButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNicknameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            nicknameEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.checkNicknameValidLocal(text.toString())
            }

            nextButton.setOnClickListener {
                viewModel.nextButtonClicked()
            }

            arrowBack.setOnClickListener {
                viewModel.previousButtonClicked()
            }

            nicknameLayout.setErrorIconOnClickListener {
                nicknameEditText.text?.clear()
            }
        }
    }

    private fun observeNicknameLocalValidation() {
        lifecycleScope.launchWhenStarted {
            viewModel.nicknameValidationLocal.collect { localValidation ->
                when (localValidation) {
                    is PhoneEmailLocalValidationResult.Empty -> emptyState()
                    is PhoneEmailLocalValidationResult.Success -> successState()
                    is PhoneEmailLocalValidationResult.Error -> localValidation.message?.let {
                        resources.getString(it)
                    }?.let { errorState(message = it) }
                }
            }
        }
    }

    private fun observeRequestApiNicknameValidation(){
        lifecycleScope.launchWhenStarted {
            viewModel.nicknameValidation.collect{networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> loadingState()
                    is NetworkResult.Success -> {
                        authViewModel.saveUserNickname(binding.nicknameEditText.text.toString().trim())
                        findNavController().navigate(R.id.action_nicknameFragment_to_userFullNameFragment)
                    }
                    is NetworkResult.Error -> networkResult.message?.let { getString(it) }
                        ?.let { errorState(message = it) }
                    else -> errorState(message = networkResult.message.toString())
                }
            }
        }
    }

    private fun observeButtonClick(){
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect{navEvent ->
                when(navEvent){
                    is NavigationEvent.NavigateNextScreen -> viewModel.checkNicknameValid(binding.nicknameEditText.text.toString().trim())
                    is NavigationEvent.NavigatePreviousScreen -> findNavController().popBackStack()
                    else -> {}
                }
            }
        }
    }

    private fun emptyState() {
        with(binding){
            nicknameLayout.error = null
            nextButton.isEnabled = false
            nicknameLayout.endIconDrawable = null
        }
    }

    private fun loadingState(){
        with(binding) {
            nicknameEditText.isClickable = false
            nextButton.isClickable = false
            nextButton.icon = progressIndicatorDrawable(requireContext(), R.color.white)
        }
    }

    private fun errorState(message: String) {
        with(binding){
            nicknameLayout.error = message
            nextButton.isEnabled = false
            nextButton.icon = null
        }
    }

    private fun successState() {
        with(binding){
            nicknameLayout.error = null
            nextButton.isEnabled = true
            nicknameLayout.endIconDrawable = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.success_circle
            )
            nextButton.icon = null
            nextButton.isClickable = true
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}