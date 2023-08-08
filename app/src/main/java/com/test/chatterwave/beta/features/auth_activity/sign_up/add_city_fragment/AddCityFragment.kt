package com.test.chatterwave.beta.features.auth_activity.sign_up.add_city_fragment

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
import com.chi.interngram.echo.databinding.FragmentAddCityBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.auth_activity.AuthViewModel
import com.test.chatterwave.beta.utils.NavigationEvent
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddCityFragment : Fragment() {

    private var _binding: FragmentAddCityBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>
    private val viewModel: AddCityViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[AddCityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCityBinding.inflate(layoutInflater)
        initDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.arrowBack.setOnClickListener {
            viewModel.previousButtonClicked()
        }
        binding.nextButton.setOnClickListener {
            viewModel.nextButtonClicked()
        }
        binding.cityEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.validateCityLocal(text.toString())
        }
    }

    private fun initDataBinding(){
        with(binding) {
            cityViewModel = viewModel
            lifecycleOwner = this@AddCityFragment
        }
    }

    private fun observeButtonClick(){
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect{ navEvent ->
                when(navEvent){
                    is NavigationEvent.NavigateNextScreen -> {
                        authViewModel.saveUserCity(binding.cityEditText.text.toString().trim())
                        findNavController().navigate(R.id.action_addCityFragment_to_addBioFragment)

                    }
                    is NavigationEvent.NavigatePreviousScreen -> findNavController().popBackStack()
                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}