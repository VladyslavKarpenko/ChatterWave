package com.test.chatterwave.beta.features.main_activity.create_post_fragment

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
import com.chi.interngram.echo.databinding.FragmentCreatePostBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import com.test.chatterwave.beta.features.main_activity.ui.MainActivity
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.callbacks.OnBackPressedListener
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreatePostFragment : Fragment() {

    private var _binding: FragmentCreatePostBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>

    private val viewModel: CreatePostViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[CreatePostViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeOnButtonClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostBinding.inflate(layoutInflater)
        initDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            viewModel.onCancelButtonClicked()
        }
        binding.shareButton.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.saveCreatePostPhoto(binding.cropperView.cropBitmap())
                viewModel.onShareButtonClicked(mainViewModel.createPost())
            }
        }
        binding.bioEditText.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launch{
                mainViewModel.savePostDescription(text.toString())
            }
        }
    }

    private fun initDataBinding() {
        with(binding) {
            mainActivityViewModel = mainViewModel
            createPostViewModel = viewModel
            lifecycleOwner = this@CreatePostFragment
        }
    }

    private fun observeOnButtonClick() {
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect { navEvent ->
                when (navEvent) {
                    NavigationEvent.NavigateNextScreen -> {
                        //TODO: problem with navigation,
                        // after nav to current page,
                        // bottom nav invisible because addAvatarDialog2 from create_post_navigation still not closed
                        // not actual because of CreatePostFragment comment, onCreate method
                        mainViewModel.deleteCreatePostPhoto()
                        findNavController().popBackStack(R.id.create_post_navigation,true)
                    }
                    NavigationEvent.NavigatePreviousScreen -> {
                        findNavController().navigate(R.id.action_createPostFragment_to_cancelCreatePostDialog)
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setOnBackPressedListener(object : OnBackPressedListener {
            override fun onBackPressedClickListener() {
                viewModel.onCancelButtonClicked()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).setOnBackPressedListener(null)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}