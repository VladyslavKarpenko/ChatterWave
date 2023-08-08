package com.test.chatterwave.beta.features.auth_activity.password_recovery.change_pass_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.FragmentChangePasswordBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.google.android.material.tabs.TabLayout
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.PhoneEmailLocalValidationResult
import com.test.chatterwave.beta.utils.progressIndicatorDrawable
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: ChangePasswordViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[ChangePasswordViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeButtonClick()
        observePhoneEmailLocalValidation()
        observeRequestApiPhoneEmailValidation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workWithTabs()
        workWithButton()
        workWithEmailField()
        workWithPhoneField()

        binding.createNewAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_changePasswordFragment_to_phoneEmailFragment)
        }
    }

    private fun workWithButton() {
        with(binding) {
            nextButton.setOnClickListener {
                lifecycleScope.launch {
                    when (tabLayout.selectedTabPosition) {
                        PHONE_EDIT_TEXT_POSITION -> viewModel.checkUserPhoneValid("${inputLayoutPhone.prefixText.toString()}${editTextPhone.text}")
                        EMAIL_EDIT_TEXT_POSITION -> viewModel.checkUserEmailValid(editTextEmail.text.toString())
                    }
                }
            }
            arrowBack.setOnClickListener {
                viewModel.arrowBackClicked()
            }
            createNewAccountButton.setOnClickListener {
                viewModel.logInButtonClicked()
            }
        }
    }

    private fun workWithTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    PHONE_EDIT_TEXT_POSITION -> hideEmail()
                    EMAIL_EDIT_TEXT_POSITION -> hidePhone()
                }
            }

            /**Unnecessary*/
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            /**Unnecessary*/
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun hidePhone() {
        with(binding) {
            editTextPhone.visibility = View.INVISIBLE
            inputLayoutPhone.visibility = View.INVISIBLE
            nextButton.isEnabled = false
            textInputLayoutEmail.visibility = View.VISIBLE
            editTextEmail.visibility = View.VISIBLE
            hideKeyboard()
            hideErrorPhone()
            changeButtonConstraint(R.id.textInputLayoutEmail)
        }
    }

    private fun hideEmail() {
        with(binding) {
            editTextPhone.visibility = View.VISIBLE
            inputLayoutPhone.visibility = View.VISIBLE
            nextButton.isEnabled = false
            textInputLayoutEmail.visibility = View.INVISIBLE
            editTextEmail.visibility = View.INVISIBLE
            hideKeyboard()
            hideErrorEmail()
            changeButtonConstraint(R.id.inputLayoutPhone)
        }
    }

    private fun hideErrorPhone() {
        binding.inputLayoutPhone.error = null
    }

    private fun hideErrorEmail() {
        binding.textInputLayoutEmail.error = null
    }

    private fun hideKeyboard() {
        val inputMethodManager: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun changeButtonConstraint(inputLayout: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintLayout)
        constraintSet.connect(
            R.id.nextButton,
            ConstraintSet.TOP,
            inputLayout,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(binding.constraintLayout)
    }

    private fun observeButtonClick() {
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect { navEvent ->
                when (navEvent) {
                    NavigationEvent.NavigatePreviousScreen -> findNavController().popBackStack()
                    NavigationEvent.NavigateNextScreen -> findNavController().navigate(R.id.action_changePasswordFragment_to_confirmChangePasswordFragment)

                    else -> {}
                }
            }
        }
    }

    private fun observeRequestApiPhoneEmailValidation() {
        lifecycleScope.launchWhenStarted {
            viewModel.phoneEmailValidation.collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> loadingState()
                    is NetworkResult.Success -> successState()
                    is NetworkResult.Error -> networkResult.message?.let { getString(it) }
                        ?.let { errorState(message = it) }
                    else -> errorState(message = networkResult.message.toString())
                }
            }
        }
    }

    private fun loadingState() {
        with(binding) {
            editTextPhone.isClickable = false
            editTextEmail.isClickable = false
            nextButton.isClickable = false
            nextButton.icon = progressIndicatorDrawable(requireContext(), R.color.white)
        }
    }

    private fun successState() {
        with(binding) {
            editTextPhone.isClickable = true
            editTextEmail.isClickable = true
            nextButton.isClickable = true
            nextButton.icon = null

            /**Navigate to next screen and save param to activity viewModel*/
            when(tabLayout.selectedTabPosition){
                PHONE_EDIT_TEXT_POSITION -> authViewModel.saveUserPhoneToViewModel(phone = "${inputLayoutPhone.prefixText.toString()}${editTextPhone.text}")
                EMAIL_EDIT_TEXT_POSITION -> authViewModel.saveUserEmailToViewModel(email = editTextEmail.text.toString())
            }
            viewModel.nextButtonClicked()
        }
    }

    private fun errorState(message: String) {
        with(binding) {
            editTextPhone.isClickable = true
            editTextEmail.isClickable = true
            nextButton.isClickable = true
            nextButton.icon = null
            nextButton.isEnabled = false

            if (tabLayout.selectedTabPosition == PHONE_EDIT_TEXT_POSITION) {
                inputLayoutPhone.error = message
                editTextPhone.clearFocus()
            } else {
                textInputLayoutEmail.error = message
                editTextEmail.clearFocus()
            }
        }
    }

    private fun workWithEmailField() {
        with(binding) {
            editTextEmail.doOnTextChanged { text, _, _, _ ->
                viewModel.checkUserEmailValidLocal(email = text.toString().trim())
            }

            editTextEmail.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    viewModel.checkUserEmailValidLocal(email = editTextEmail.text.toString().trim())
                }
            }

            textInputLayoutEmail.setErrorIconOnClickListener {
                editTextEmail.text = null
                textInputLayoutEmail.error = null
            }
        }
    }

    private fun workWithPhoneField() {
        with(binding) {
            editTextPhone.doOnTextChanged { text, _, _, _ ->
                viewModel.checkUserPhoneValidLocal(phone = text.toString().trim())
            }

            editTextPhone.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    viewModel.checkUserPhoneValidLocal(phone = editTextPhone.text.toString().trim())
                }
            }

            inputLayoutPhone.setErrorIconOnClickListener {
                editTextPhone.text = null
                inputLayoutPhone.error = null
            }
        }
    }

    private fun observePhoneEmailLocalValidation() {
        lifecycleScope.launchWhenStarted {
            viewModel.phoneEmailValidationLocal.collect { localValidationResult ->
                when (localValidationResult) {
                    is PhoneEmailLocalValidationResult.Empty -> with(binding) {
                        when (tabLayout.selectedTabPosition) {
                            PHONE_EDIT_TEXT_POSITION -> {
                                inputLayoutPhone.error = null
                                nextButton.isEnabled = false
                            }
                            EMAIL_EDIT_TEXT_POSITION -> {
                                textInputLayoutEmail.error = null
                                nextButton.isEnabled = false
                            }
                        }
                    }
                    is PhoneEmailLocalValidationResult.Success -> with(binding) {
                        when (tabLayout.selectedTabPosition) {
                            PHONE_EDIT_TEXT_POSITION -> {
                                inputLayoutPhone.error = null
                                nextButton.isEnabled = true
                            }
                            EMAIL_EDIT_TEXT_POSITION -> {
                                textInputLayoutEmail.error = null
                                nextButton.isEnabled = true
                            }
                        }
                    }
                    is PhoneEmailLocalValidationResult.Error -> with(binding) {
                        when (tabLayout.selectedTabPosition) {
                            PHONE_EDIT_TEXT_POSITION -> {
                                inputLayoutPhone.error =
                                    localValidationResult.message?.let { getString(it) }
                                nextButton.isEnabled = false
                            }
                            EMAIL_EDIT_TEXT_POSITION -> {
                                textInputLayoutEmail.error =
                                    localValidationResult.message?.let { getString(it) }
                                nextButton.isEnabled = false
                            }
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

    companion object {
        private const val PHONE_EDIT_TEXT_POSITION = 0
        private const val EMAIL_EDIT_TEXT_POSITION = 1
    }

}