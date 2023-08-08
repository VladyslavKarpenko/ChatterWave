package com.test.chatterwave.beta.features.auth_activity.login_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.FragmentLogInBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.LoginWithEmailDomainModel
import com.test.chatterwave.beta.domain.model.LoginWithPhoneDomainModel
import com.test.chatterwave.beta.domain.model.TokenDomainResponse
import com.test.chatterwave.beta.utils.LoginErrorResult
import com.test.chatterwave.beta.utils.LoginEvent
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.progressIndicatorDrawable
import com.test.chatterwave.beta.utils.toast
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this, viewModelFactory.get().create(this))[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLoginResponse()
        observeLoginSendData()
        observeLoginErrorHandling()
        observeButtonClickEvent()
        observeShowErrorDialogEvent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            passwordTextInput.doOnTextChanged { text, _, _, _ ->
                passwordTextLayout.error = null
                loginButton.isEnabled = true
            }

            loginTextInput.doOnTextChanged { text, _, _, _ ->
                passwordTextLayout.error = null
                loginButton.isEnabled = true
            }

            loginButton.setOnClickListener {
                viewModel.createLoginData(loginTextInput.text.toString(),
                    passwordTextInput.text.toString())
            }

            forgotPassword.setOnClickListener {
                viewModel.recoveryPasswordButtonClicked()
            }

            createAnAccount.setOnClickListener {
                viewModel.createAnAccountButtonClicked()
            }

            loginWithGoogle.setOnClickListener {
                viewModel.loginWithGoogleButtonClicked()
            }

            loginWithFacebook.setOnClickListener {
                viewModel.loginWithFacebookButtonClicked()
            }
        }
    }

    private fun observeLoginResponse() {
        lifecycleScope.launchWhenStarted {
            viewModel.tokenResponse.collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> loadingLoginState()
                    is NetworkResult.Success -> successLoginState(networkResult.data!!)
                    is NetworkResult.Error -> networkResult.message?.let {viewModel.handleShowError(it)}
                    else -> networkResult.message?.let { viewModel.handleShowError(it) }
                }
            }
        }
    }

    private fun observeLoginSendData(){
        lifecycleScope.launchWhenStarted {
            viewModel.loginValidationLocal.collect{logEvent ->
                when(logEvent){
                    is LoginEvent.LoginWithEmail -> viewModel.loginWithEmail(
                        LoginWithEmailDomainModel(logEvent.email!!, logEvent.password!!)
                    )
                    is LoginEvent.LoginWithPhone -> viewModel.loginWithNumber(
                        LoginWithPhoneDomainModel(logEvent.phone!!, logEvent.password!!)
                    )
                }
            }
        }
    }

    private fun observeLoginErrorHandling(){
        lifecycleScope.launchWhenStarted {
            viewModel.loginErrorResult.collect{loginError ->
                when(loginError){
                    is LoginErrorResult.LoginPhoneEmailError -> errorDialog()
                    is LoginErrorResult.LoginPasswordError -> invalidPasswordError()
                    else -> toast(getString(R.string.toast_invalid_credentials))
                }
            }
        }
    }

    private fun observeButtonClickEvent(){
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect{ navEvent ->
                when(navEvent){
                    is NavigationEvent.NavigationToCreateAnAccount -> findNavController().navigate(R.id.action_loginFragment_to_phoneEmailFragment)
                    is NavigationEvent.NavigateNextScreen -> {
                        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                        requireActivity().finish()
                    }
                    is NavigationEvent.NavigationToRecoveryPassword -> findNavController().navigate(R.id.action_loginFragment_to_changePasswordFragment)
                    is NavigationEvent.NavigationToLoginWithFacebook -> findNavController().navigate(R.id.action_loginFragment_to_termsAndConditionsFragment)
                    is NavigationEvent.NavigationToLoginWithGoogle -> findNavController().navigate(R.id.action_loginFragment_to_termsAndConditionsFragment)
                    else -> {}
                }
            }
        }
    }

    private fun observeShowErrorDialogEvent(){
        lifecycleScope.launchWhenStarted {
            viewModel.showErrorDialogEvent.collect{ navEvent ->
                when(navEvent){
                    is NavigationEvent.NavigateToDialog -> findNavController().navigate(R.id.action_loginFragment_to_loginErrorDialog)
                    else -> {}
                }
            }
        }
    }

    private fun invalidPasswordError() {
        with(binding){
            passwordTextLayout.error = getString(R.string.error_invalid_pasword)
            loginButton.isEnabled = false
            loginButton.icon = null
        }
    }

    private fun errorDialog() {
        binding.loginButton.icon = null
        viewModel.showErrorDialog()
    }

    private fun successLoginState(tokenData: TokenDomainResponse) {
        binding.loginButton.icon = null
        viewModel.saveTokens(tokenData = tokenData)
    }

    private fun loadingLoginState() {
        binding.loginButton.icon = progressIndicatorDrawable(requireContext(), R.color.white)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}