package com.test.chatterwave.beta.features.auth_activity.sign_up.code_confirmation_fragment

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
import com.chi.interngram.echo.databinding.FragmentConfirmationCodeBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.utils.CODE_VERIFICATION_SEPARATOR
import com.test.chatterwave.beta.utils.CODE_VERIFICATION_UNDERLINE
import com.test.chatterwave.beta.utils.CONFIRM_CODE_LENGTH
import com.test.chatterwave.beta.utils.CONFIRM_CODE_LENGTH_FIVE
import com.test.chatterwave.beta.utils.CONFIRM_CODE_LENGTH_ZERO
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.TimerEvent
import com.test.chatterwave.beta.utils.showTime
import com.test.chatterwave.beta.utils.toast
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConfirmationCodeFragment : Fragment() {

    private var _binding: FragmentConfirmationCodeBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>

    private val viewModel: ConfirmationCodeViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[ConfirmationCodeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeTimer()
        observeSendCode()
        observeButtonClick()
        observeUserSignIn()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmationCodeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.onResendButtonClicked()
        observeBindings()
    }

    private fun setCode(text: CharSequence?){
        val codeCharList = text.toString().toCharArray().toMutableList()
        if(codeCharList.size < CONFIRM_CODE_LENGTH){
            for (item in CONFIRM_CODE_LENGTH_ZERO .. CONFIRM_CODE_LENGTH_FIVE - codeCharList.size){
                codeCharList.add(CODE_VERIFICATION_UNDERLINE)
            }
        }
        binding.confirmCodeTextView.text = codeCharList.joinToString(separator = CODE_VERIFICATION_SEPARATOR)
    }

    private fun observeSendCode() {
        lifecycleScope.launchWhenStarted {
            viewModel.validateConfirmCode.collect {
                when (it) {
                    is NetworkResult.Success -> viewModel.signUpUser(authViewModel.userSignIn())
                    is NetworkResult.Error -> showError(it.message)
                    else -> showError(it.message)
                }
            }
        }
    }

    private fun observeButtonClick(){
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect{ navEvent ->
                when(navEvent){
                    is NavigationEvent.NavigateNextScreen -> {
                        findNavController().navigate(R.id.action_confirmationCodeFragment_to_avatarFragment)
                    }
                    is NavigationEvent.NavigatePreviousScreen -> findNavController().popBackStack()
                    is NavigationEvent.NavigationToCreateAnAccount -> findNavController().navigate(R.id.action_confirmationCodeFragment_to_phoneEmailFragment)
                    else -> {}
                }
            }
        }
    }

    private fun observeUserSignIn(){
        lifecycleScope.launchWhenStarted {
            viewModel.signUpUser.collect{ netResult ->
                when(netResult){
                    is NetworkResult.Success -> netResult.data?.let { viewModel.saveTokens(it) }
                    is NetworkResult.Error -> toast(resources.getString(R.string.server_error))
                    else -> toast(resources.getString(R.string.server_error))
                }
            }
        }
    }

    private fun observeTimer(){
        lifecycleScope.launchWhenStarted {
            authViewModel.timerResult.collect{
                when(it){
                    is TimerEvent.TimerStop -> enableResentButton()
                    is TimerEvent.TimerStart -> {
                        disableResendButton()
                        binding.timerTextView.text = it.data?.showTime()
                    }
                }
            }
        }
    }

    private fun observeBindings() {
        with(binding){
            with(authViewModel.userEmail) {
                if (value.isNotEmpty()) {
                    changeEmailOrPhoneText.text = getString(R.string.change_email_address)
                } else {
                    changeEmailOrPhoneText.text = getString(R.string.change_phone_number)
                }
            }

            setCode(confirmCodeEditText.text)

            confirmButton.setOnClickListener {
                with(authViewModel) {
                    if (userEmail.value.isNotEmpty()) {
                        viewModel.validateConfirmCodeByEmail(code = confirmCodeEditText.text.toString() , email = userEmail.value)
                    } else viewModel.validateConfirmCodeByPhone(code = confirmCodeEditText.text.toString(), phone = userPhone.value)
                }
            }
            confirmCodeEditText.setOnClickListener {
                confirmCodeInputLayout.error = null
                confirmButton.isEnabled = true
            }

            confirmCodeEditText.doOnTextChanged { text, _, _, _ ->
                confirmButton.isEnabled = text.toString().trim().length >= CONFIRM_CODE_LENGTH
                setCode(text)
            }

            resendText.setOnClickListener {
                authViewModel.onResendButtonClicked()
            }

            arrowBack.setOnClickListener {
                viewModel.onBackButtonClicked()
            }

            changeEmailOrPhoneText.setOnClickListener {
                viewModel.onChangeEmailOrPhoneClicked()
            }

        }
    }

    private fun disableResendButton() {
        with(binding){
            resendText.apply {
                this.isEnabled = false
                this.setTextColor(ContextCompat.getColor(context, R.color.greyscale_black_45))
            }
            timerTextView.visibility = View.VISIBLE
        }
    }

    private fun enableResentButton() {
        with(binding){
            resendText.apply {
                this.isEnabled = true
                this.setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
            timerTextView.visibility = View.INVISIBLE
        }
    }

    private fun showError(message: Int?){
        binding.confirmCodeInputLayout.error = message?.let { getString(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}