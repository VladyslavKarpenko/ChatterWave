package com.test.chatterwave.beta.features.auth_activity.password_recovery.create_new_password

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
import com.chi.interngram.echo.databinding.FragmentCreateNewPasswordBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.progressIndicatorDrawable
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateNewPasswordFragment : Fragment() {

    private var _binding: FragmentCreateNewPasswordBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: CreateNewPasswordViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[CreateNewPasswordViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeOnButtonClick()
        observePasswordChange()
        observePasswordValidation()
        observeConfirmPasswordValidation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewPasswordBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings()
    }

    private fun bindings() {
        with(binding) {
            passwordEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.checkPasswordValidLocal(password = text!!.toString().trim())
            }
            confirPasswordEditText.doOnTextChanged { text, _, _, _ ->
                confirmPasswordInputLayout.error = null
                viewModel.checkPasswordConfirmLocal(
                    text!!.toString().trim(),
                    passwordEditText.text!!.toString().trim()
                )
            }
            nextBtn.setOnClickListener {
                if (passwordEditText.text.toString() == confirPasswordEditText.text.toString()) {
                    with(authViewModel) {
                            viewModel.changeUserPassword(password = passwordEditText.text.toString().trim(),
                                email = authViewModel.userEmail.value,
                                phone = authViewModel.userPhone.value
                            )
                    }

                } else confirmPasswordInputLayout.error =
                    resources.getString(R.string.password_match)
            }
            backImage.setOnClickListener {
                viewModel.previousButtonClicked()
            }
        }
    }


    private fun errorState(error: String?) {
        binding.nextBtn.icon = null
        binding.nextBtn.isClickable = true
        binding.passwordInputLayout.error = error
    }

    private fun successState() {
        binding.nextBtn.icon = null
        binding.nextBtn.isClickable = true
        viewModel.nextButtonClicked()
    }

    private fun loadingState() {
        binding.nextBtn.icon = progressIndicatorDrawable(requireContext(), R.color.white)
        binding.nextBtn.isClickable = false
    }

    private fun observeOnButtonClick() {
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect { navEvent ->
                when (navEvent) {
                    is NavigationEvent.NavigateNextScreen -> {
                        findNavController().navigate(R.id.action_createNewPasswordFragment_to_loginFragment)
                    }
                    is NavigationEvent.NavigatePreviousScreen -> findNavController().popBackStack()
                    else -> {}
                }
            }
        }
    }

    private fun observePasswordChange(){
        lifecycleScope.launchWhenStarted {
            viewModel.passwordChange.collect{ networkResult ->
                when(networkResult){
                    is NetworkResult.Loading -> loadingState()
                    is NetworkResult.Success -> successState()
                    is NetworkResult.Exception -> errorState(networkResult.exception)
                    else -> {}
                }
            }
        }
    }

    private fun observeConfirmPasswordValidation() {
        lifecycleScope.launchWhenStarted {
            viewModel.passwordConfirmLocalValidation.collect {
                binding.nextBtn.isEnabled = it
            }
        }
    }

    private fun observePasswordValidation() {
        lifecycleScope.launchWhenStarted {
            viewModel.passwordLocalValidation.collect { passValidation ->
                with(binding) {
                    eightCharacters.setEnabledState(passValidation.minCharacterPassed)
                    oneLowercase.setEnabledState(passValidation.oneLowerCasePassed)
                    oneUppercase.setEnabledState(passValidation.oneUpperCasePassed)
                    oneDigit.setEnabledState(passValidation.oneDigitPassed)
                    oneSpecial.setEnabledState(passValidation.oneSpecialPassed)

                    if (passValidation.fullRegexPassed) {
                        passwordInputLayout.error = null
                        confirPasswordEditText.isEnabled = true
                    }else if (passValidation.empty) {
                        passwordInputLayout.error = null
                        confirPasswordEditText.isEnabled = false
                    }else if (passValidation.onlyLatin){
                        passwordInputLayout.error =
                            resources.getString(R.string.password_latin)
                        confirPasswordEditText.isEnabled = false
                        nextBtn.isEnabled = false
                    }  else {
                        passwordInputLayout.error =
                            resources.getString(R.string.password_min_requirements)
                        confirPasswordEditText.isEnabled = false
                        nextBtn.isEnabled = false
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