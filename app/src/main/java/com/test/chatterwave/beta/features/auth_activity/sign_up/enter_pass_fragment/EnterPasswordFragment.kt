package com.test.chatterwave.beta.features.auth_activity.sign_up.enter_pass_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.FragmentEnterPasswordBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnterPasswordFragment : Fragment() {

    private var _binding: FragmentEnterPasswordBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: EnterPasswordViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[EnterPasswordViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeOnButtonClick()
        observePasswordValidation()
        observeConfirmPasswordValidation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterPasswordBinding.inflate(layoutInflater)
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
                    viewModel.nextButtonClicked()
                } else confirmPasswordInputLayout.error =
                    resources.getString(R.string.password_match)
            }
            backImage.setOnClickListener {
                viewModel.previousButtonClicked()
            }
        }
    }

    private fun observeOnButtonClick() {
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect { navEvent ->
                when (navEvent) {
                    is NavigationEvent.NavigateNextScreen -> {
                        authViewModel.saveUserPassword(binding.passwordEditText.text.toString())
                        findNavController().navigate(R.id.action_enterPasswordFragment_to_nicknameFragment)
                    }
                    is NavigationEvent.NavigatePreviousScreen -> findNavController().popBackStack()
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