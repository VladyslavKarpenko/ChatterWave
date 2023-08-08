package com.test.chatterwave.beta.features.auth_activity.sign_up.birthday_fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.FragmentBirthdayBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.showFormattedDate
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import java.time.ZonedDateTime
import java.util.*
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class BirthdayFragment : Fragment() {

    private var _binding: FragmentBirthdayBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: BirthdayViewModel by lazy {
        ViewModelProvider(this, viewModelFactory.get().create(this))[BirthdayViewModel::class.java]
    }
    private val authViewModel: AuthViewModel by activityViewModels()

    /**Maybe should be provided*/
    private val minLegalYearOfRegistration by lazy {
        ZonedDateTime.now().minusYears(MIN_USER_AGE).toInstant().toEpochMilli()
    }

    /**Maybe should be provided*/
    private val maxLegalYearOfRegistration by lazy {
        ZonedDateTime.now().minusYears(MAX_USER_AGE).toInstant().toEpochMilli()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        observeButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBirthdayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            authViewModel.userBirthday.collect {
                binding.birthdayEditText.setText(it.showFormattedDate())
            }
        }
        authViewModel.saveBirthdayToViewModel(minLegalYearOfRegistration)
    }

    private fun observeButtonClick() {
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect { navEvent ->
                when (navEvent) {
                    is NavigationEvent.NavigateNextScreen -> findNavController().navigate(R.id.action_birthdayFragment_to_confirmationCodeFragment)
                    is NavigationEvent.NavigatePreviousScreen -> findNavController().popBackStack()
                    is NavigationEvent.NavigateBirthdayPecker -> navigateToBirthdayPecker()
                    else -> {}
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            authViewModel.onButtonClickEvent.collect { navEvent ->
                when (navEvent) {
                    is NavigationEvent.NavigateNextScreen -> binding.nexButton.isEnabled = true
                    else -> {}
                }
            }
        }
    }

    private fun navigateToBirthdayPecker() {
        val args = Bundle().apply {
            this.putLong(MIN_YEAR_REGISTRATION, minLegalYearOfRegistration)
            this.putLong(MAX_YEAR_REGISTRATION, maxLegalYearOfRegistration)
        }
        findNavController().navigate(
            R.id.action_birthdayFragment_to_birthdayDatePickerFragment,
            args
        )
    }

    private fun bindings() {
        with(binding) {
            birthdayEditText.setOnClickListener {
                viewModel.birthdayButtonClicked()
            }

            nexButton.setOnClickListener {
                viewModel.nextButtonClicked()
            }

            arrowBack.setOnClickListener {
                viewModel.previousButtonClicked()
            }
        }
    }

    companion object {
        private const val MIN_USER_AGE: Long = 16
        private const val MAX_USER_AGE: Long = 93
        private const val MIN_YEAR_REGISTRATION = "minYearRegistration"
        private const val MAX_YEAR_REGISTRATION = "maxYearRegistration"
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}